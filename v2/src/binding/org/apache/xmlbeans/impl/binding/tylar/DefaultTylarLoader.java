/*   Copyright 2004 The Apache Software Foundation
 *
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package org.apache.xmlbeans.impl.binding.tylar;

import java.io.*;
import java.net.URI;
import java.util.ArrayList;
import java.util.Collection;
import java.util.jar.JarEntry;
import java.util.jar.JarInputStream;
import org.apache.xml.xmlbeans.bindingConfig.BindingConfigDocument;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.impl.binding.bts.BindingFile;
import org.w3.x2001.xmlSchema.SchemaDocument;

/**
 * Default implementation of TylarLoader.  Currently, only directory and jar
 * tylars are supported.
 *
 * @author Patrick Calahan <pcal@bea.com>
 */
public class DefaultTylarLoader implements TylarLoader {

  // ========================================================================
  // Constants

  private static final String FILE_SCHEME = "file";

  private static final char[] OTHER_SEPCHARS = {'\\'};
  private static final char SEPCHAR = '/';

  private static final boolean VERBOSE = false;

  private static final String BINDING_FILE_JARENTRY =
          normalizeEntryName(TylarConstants.BINDING_FILE);

  private static final String SCHEMA_DIR_JARENTRY =
          normalizeEntryName(TylarConstants.SCHEMA_DIR);

  private static final String SCHEMA_EXT = ".xsd";

  // ========================================================================
  // Singleton

  //REVIEW someday we might want to make the default TylarLoader a pluggable
  //parameter in a properties file somewhere.  pcal 12/16/03
  public static final TylarLoader getInstance() {
    return DEFAULT_INSTANCE;
  }

  private static /*final*/ TylarLoader
          DEFAULT_INSTANCE = new DefaultTylarLoader();

  /**
   * This is a gross quick hack to support pluggability for tylar loader.
   * In the future, we need a cleaner mechansim, probably letting them
   * specifiy the TylarLoader impl class name in some properties file
   * in the classpath.
   *
   * @deprecated eventually; currently there is no other option.
   */
  public static void setInstance(TylarLoader newDefaultLoader) {
    DEFAULT_INSTANCE = newDefaultLoader;
  }

  // ========================================================================
  // Constructor

  protected DefaultTylarLoader() {}

  // ========================================================================
  // Public methods

  /**
   * Loads the tylar from the given uri.
   *
   * @param uri uri of where the tylar is stored.
   * @return
   * @throws IOException if an i/o error occurs while processing
   * @throws XmlException if an error occurs parsing the contents of the tylar.
   */
  public Tylar load(URI uri) throws IOException, XmlException
  {
    if (uri == null) throw new IllegalArgumentException("null uri");
    String scheme = uri.getScheme();
    if (scheme.equals(FILE_SCHEME)) {
      File file;
      try {
        file = new File(uri);
      } catch(Exception e) {
        //sometimes File can't deal for some reason, so as a last ditch
        //we assume it's a jar and read the stream directly
        return loadFromJar(new JarInputStream(uri.toURL().openStream()),uri);
      }
      if (!file.exists()) throw new FileNotFoundException(uri.toString());
      if (file.isDirectory()) {
        return ExplodedTylarImpl.load(file);
      } else {
        return loadFromJar(new JarInputStream(new FileInputStream(file)),uri);
      }
    } else {
      throw new IOException("Sorry, the '"+scheme+
                            "' scheme is not supported for loading tylars" +
                            "("+uri+")");
    }
  }

  public Tylar load(URI[] uris) throws IOException, XmlException {
    Tylar[] tylars = new Tylar[uris.length];
    for(int i=0; i<tylars.length; i++) {
      tylars[i] = load(uris[i]);
    }
    return new CompositeTylar(tylars);
  }

  public Tylar load(JarInputStream jar) throws IOException, XmlException {
    if (jar == null) throw new IllegalArgumentException("null stream");
    return loadFromJar(jar,null);
  }

  // ========================================================================
  // Private methods

  /**
   * Loads a Tylar directly from the stream. given jar file.  This method
   * parses all of the tylar's binding artifacts; if it doesn't throw an
   * exception, you can be sure that the tylars binding files and schemas are
   * valid.
   *
   * @param jin input stream on the jar file.  This should NOT be a
   * JarInputStream
   * @param source uri from which the tylar was retrieved.  This is used
   * for informational purposes only and is not required.
   * @return Handle to the tylar
   * @throws IOException
   */
  protected static Tylar loadFromJar(JarInputStream jin, URI source)
          throws IOException, XmlException
  {
    if (jin == null) throw new IllegalArgumentException("null stream");
    //FIXME in the case where sourceURI is null, we could look in the
    //manifest or someplace to try to get at least some useful information
    JarEntry entry;
    BindingFile bf = null;
    Collection schemas = null;
    StubbornInputStream stubborn = new StubbornInputStream(jin);
    while ((entry = jin.getNextJarEntry()) != null) {
      if (entry.isDirectory()) continue;
      String name = normalizeEntryName(entry.getName());
      if (name.equals(BINDING_FILE_JARENTRY)) {
        if (VERBOSE) System.out.println("parsing binding file "+name);
        bf = BindingFile.forDoc(BindingConfigDocument.Factory.parse(stubborn));
      } else if (name.startsWith(SCHEMA_DIR_JARENTRY) &&
              name.endsWith(SCHEMA_EXT)) {
        if (schemas == null) schemas = new ArrayList();
        if (VERBOSE) System.out.println("parsing schema "+name);
        schemas.add(SchemaDocument.Factory.parse(stubborn));
      } else {
        if (VERBOSE) {
          System.out.println("ignoring unknown jar entry: "+name);
          System.out.println("  looking for "+BINDING_FILE_JARENTRY+" or "+
                             SCHEMA_DIR_JARENTRY);
        }
      }
      jin.closeEntry();
    }
    if (VERBOSE) System.out.println("Done reading jar entries");
    if (bf == null) {
      throw new IOException
              ("resource at '"+source+
               "' is not a tylar: it does not contain a binding file");
    }
    jin.close();
    return new TylarImpl(source,bf,schemas);
  }
  // ========================================================================
  // Private methods

  /**
   * Canonicalizes the given zip entry path so that we can look for what
   * we want without having to worry about different slashes or
   * leading slashes or anything else that can go wrong.
   */
  private static final String normalizeEntryName(String name) {
    name = name.toLowerCase().trim();
    for(int i=0; i<OTHER_SEPCHARS.length; i++) {
      name = name.replace(OTHER_SEPCHARS[i],SEPCHAR);
    }
    if (name.charAt(0) == SEPCHAR) name = name.substring(1);
    return name;
  }


  /**
   * This is another hack around what I believe is an xbeans bug - it
   * closes the stream on us.  When we're reading out of a jar, we want
   * to parse a whole bunch of files from the same stream - this class
   * just intercepts the close() call and ignores it until we call
   * reallyClose().
   */
  private static class StubbornInputStream extends FilterInputStream {

    StubbornInputStream(InputStream in) { super(in); }

    public void close() {}

    public void reallyClose() throws IOException {
      super.close();
    }
  }

  /**
   * Grab the contents of the current entry and stuffs them into a string -
   * sometimes useful for debugging.
   */
  /*
  private static String getEntryContents(JarInputStream in) throws IOException {
  StringWriter writer = new StringWriter();
  byte[] buffer = new byte[2056];
  int count = 0;
  while ((count = in.read(buffer, 0, buffer.length)) != -1) {
  writer.write(new String(buffer, 0, count));
  }
  if (VERBOSE) {
  System.out.println("=== ENTRY CONTENTS ===");
  System.out.println(writer.toString());
  System.out.println("=== ENTRY CONTENTS ===");
  }
  return writer.toString();
  }
  */

}