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

package org.apache.xmlbeans;

/**
 * Corresponds to the XML Schema
 * <a target="_blank" href="http://www.w3.org/TR/xmlschema-2/#nonPositiveInteger">xs:nonPositiveInteger</a> type.
 * One of the derived types based on <a target="_blank" href="http://www.w3.org/TR/xmlschema-2/#decimal">xs:decimal</a>.
 * <p>
 * Verified to be zero or negative when validating.
 * <p>
 * Convertible to {@link java.math.BigInteger}.
 */ 
public interface XmlNonPositiveInteger extends XmlInteger
{
    /** The constant {@link SchemaType} object representing this schema type. */
    public static final SchemaType type = XmlBeans.getBuiltinTypeSystem().typeForHandle("_BI_nonPositiveInteger");
    
    /**
     * A class with methods for creating instances
     * of {@link XmlNonPositiveInteger}.
     */
    public static final class Factory
    {
        /** Creates an empty instance of {@link XmlNonPositiveInteger} */
        public static XmlNonPositiveInteger newInstance() {
          return (XmlNonPositiveInteger) XmlBeans.getContextTypeLoader().newInstance( type, null ); }
        
        /** Creates an empty instance of {@link XmlNonPositiveInteger} */
        public static XmlNonPositiveInteger newInstance(org.apache.xmlbeans.XmlOptions options) {
          return (XmlNonPositiveInteger) XmlBeans.getContextTypeLoader().newInstance( type, options ); }
        
        /** Creates an immutable {@link XmlNonPositiveInteger} value */
        public static XmlNonPositiveInteger newValue(Object obj) {
          return (XmlNonPositiveInteger) type.newValue( obj ); }
        
        /** Parses a {@link XmlNonPositiveInteger} fragment from a String. For example: "<code>&lt;xml-fragment&gt;-1234567890&lt;/xml-fragment&gt;</code>". */
        public static XmlNonPositiveInteger parse(java.lang.String s) throws org.apache.xmlbeans.XmlException {
          return (XmlNonPositiveInteger) XmlBeans.getContextTypeLoader().parse( s, type, null ); }
        
        /** Parses a {@link XmlNonPositiveInteger} fragment from a String. For example: "<code>&lt;xml-fragment&gt;-1234567890&lt;/xml-fragment&gt;</code>". */
        public static XmlNonPositiveInteger parse(java.lang.String s, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (XmlNonPositiveInteger) XmlBeans.getContextTypeLoader().parse( s, type, options ); }
        
        /** Parses a {@link XmlNonPositiveInteger} fragment from a File. */
        public static XmlNonPositiveInteger parse(java.io.File f) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (XmlNonPositiveInteger) XmlBeans.getContextTypeLoader().parse( f, type, null ); }
        
        /** Parses a {@link XmlNonPositiveInteger} fragment from a File. */
        public static XmlNonPositiveInteger parse(java.io.File f, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (XmlNonPositiveInteger) XmlBeans.getContextTypeLoader().parse( f, type, options ); }
        
        /** Parses a {@link XmlNonPositiveInteger} fragment from a URL. */
        public static XmlNonPositiveInteger parse(java.net.URL u) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (XmlNonPositiveInteger) XmlBeans.getContextTypeLoader().parse( u, type, null ); }

        /** Parses a {@link XmlNonPositiveInteger} fragment from a URL. */
        public static XmlNonPositiveInteger parse(java.net.URL u, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (XmlNonPositiveInteger) XmlBeans.getContextTypeLoader().parse( u, type, options ); }

        /** Parses a {@link XmlNonPositiveInteger} fragment from an InputStream. */
        public static XmlNonPositiveInteger parse(java.io.InputStream is) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (XmlNonPositiveInteger) XmlBeans.getContextTypeLoader().parse( is, type, null ); }
        
        /** Parses a {@link XmlNonPositiveInteger} fragment from an InputStream. */
        public static XmlNonPositiveInteger parse(java.io.InputStream is, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (XmlNonPositiveInteger) XmlBeans.getContextTypeLoader().parse( is, type, options ); }
        
        /** Parses a {@link XmlNonPositiveInteger} fragment from a Reader. */
        public static XmlNonPositiveInteger parse(java.io.Reader r) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (XmlNonPositiveInteger) XmlBeans.getContextTypeLoader().parse( r, type, null ); }
        
        /** Parses a {@link XmlNonPositiveInteger} fragment from a Reader. */
        public static XmlNonPositiveInteger parse(java.io.Reader r, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, java.io.IOException {
          return (XmlNonPositiveInteger) XmlBeans.getContextTypeLoader().parse( r, type, options ); }
        
        /** Parses a {@link XmlNonPositiveInteger} fragment from a DOM Node. */
        public static XmlNonPositiveInteger parse(org.w3c.dom.Node node) throws org.apache.xmlbeans.XmlException {
          return (XmlNonPositiveInteger) XmlBeans.getContextTypeLoader().parse( node, type, null ); }
        
        /** Parses a {@link XmlNonPositiveInteger} fragment from a DOM Node. */
        public static XmlNonPositiveInteger parse(org.w3c.dom.Node node, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException {
          return (XmlNonPositiveInteger) XmlBeans.getContextTypeLoader().parse( node, type, options ); }
        
        /** Parses a {@link XmlNonPositiveInteger} fragment from an XMLInputStream.
         * @deprecated Superceded by JSR 173 */
        public static XmlNonPositiveInteger parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (XmlNonPositiveInteger) XmlBeans.getContextTypeLoader().parse( xis, type, null ); }
        
        /** Parses a {@link XmlNonPositiveInteger} fragment from an XMLInputStream.
         * @deprecated Superceded by JSR 173 */
        public static XmlNonPositiveInteger parse(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return (XmlNonPositiveInteger) XmlBeans.getContextTypeLoader().parse( xis, type, options ); }
        
        /** Returns a validating XMLInputStream.
         * @deprecated Superceded by JSR 173 */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, null ); }
        
        /** Returns a validating XMLInputStream.
         * @deprecated Superceded by JSR 173 */
        public static org.apache.xmlbeans.xml.stream.XMLInputStream newValidatingXMLInputStream(org.apache.xmlbeans.xml.stream.XMLInputStream xis, org.apache.xmlbeans.XmlOptions options) throws org.apache.xmlbeans.XmlException, org.apache.xmlbeans.xml.stream.XMLStreamException {
          return XmlBeans.getContextTypeLoader().newValidatingXMLInputStream( xis, type, options ); }
        
        private Factory() { } // No instance of this class allowed
    }
}

