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

package org.apache.xmlbeans.impl.jam_old.editable.impl;

import org.apache.xmlbeans.impl.jam_old.editable.EParameter;
import org.apache.xmlbeans.impl.jam_old.editable.EInvokable;
import org.apache.xmlbeans.impl.jam_old.editable.impl.ref.DirectJClassRef;
import org.apache.xmlbeans.impl.jam_old.editable.impl.ref.QualifiedJClassRef;
import org.apache.xmlbeans.impl.jam_old.editable.impl.ref.UnqualifiedJClassRef;
import org.apache.xmlbeans.impl.jam_old.editable.impl.ref.JClassRef;
import org.apache.xmlbeans.impl.jam_old.JClass;
import org.apache.xmlbeans.impl.jam_old.JParameter;

import java.util.List;
import java.util.ArrayList;
import java.io.StringWriter;

/**
 *
 * @author Patrick Calahan <pcal@bea.com>
 */
public abstract class EInvokableImpl extends EMemberImpl implements EInvokable {

  // ========================================================================
  // Variables

  private List mExceptionClassRefs = null;
  private List mParameters = null;

  // ========================================================================
  // Constructors

  /*package*/ EInvokableImpl(EClassImpl containingClass) {
    super(containingClass.getSimpleName(),containingClass);
  }

  protected EInvokableImpl(String methodName, EClassImpl containingClass) {
    super(methodName,containingClass);
  }

  // ========================================================================
  // EConstructor implementation

  public void addException(JClass exceptionClass) {
    if (exceptionClass == null) {
      throw new IllegalArgumentException("null exception class");
    }
    if (mExceptionClassRefs == null) mExceptionClassRefs = new ArrayList();
    mExceptionClassRefs.add(DirectJClassRef.create(exceptionClass));
  }

  public void addException(String qcname) {
    if (qcname == null) throw new IllegalArgumentException("null qcname");
    if (mExceptionClassRefs == null) mExceptionClassRefs = new ArrayList();
    mExceptionClassRefs.add(QualifiedJClassRef.
                            create(qcname,(EClassImpl)getContainingClass()));
  }

  public void addUnqualifiedException(String ucname) {
    if (ucname == null) throw new IllegalArgumentException("null qcname");
    if (mExceptionClassRefs == null) mExceptionClassRefs = new ArrayList();
    mExceptionClassRefs.add(UnqualifiedJClassRef.
                            create(ucname,(EClassImpl)getContainingClass()));
  }

  public void removeException(String exceptionClassName) {
    if (exceptionClassName == null) {
      throw new IllegalArgumentException("null classname");
    }
    if (mExceptionClassRefs != null) {
      mExceptionClassRefs.remove(exceptionClassName);
    }
  }

  public void removeException(JClass exceptionClass) {
    removeException(exceptionClass.getQualifiedName());
  }

  public EParameter addNewParameter() {
    if (mParameters == null) mParameters = new ArrayList();
    EParameter param = new EParameterImpl(defaultName(mParameters.size()),
                                          this,"java.lang.Object");
    mParameters.add(param);
    return param;
  }

  public void removeParameter(EParameter parameter) {
    if (mParameters != null) mParameters.remove(parameter);
  }

  public EParameter[] getEditableParameters() {
    if (mParameters == null || mParameters.size() == 0) {
    return new EParameter[0];
    } else {
      EParameter[] out = new EParameter[mParameters.size()];
      mParameters.toArray(out);
      return out;
    }
  }

  // ========================================================================
  // JConstructor implementation

  public JParameter[] getParameters() {
    return getEditableParameters();
  }

  public JClass[] getExceptionTypes() {
    if (mExceptionClassRefs == null || mExceptionClassRefs.size() == 0) {
      return new JClass[0];
    }
    JClass[] out = new JClass[mExceptionClassRefs.size()];
    for(int i=0; i<out.length; i++) {
      out[i] = ((JClassRef)mExceptionClassRefs.get(i)).getRefClass();
    }
    return out;
  }

  public String getQualifiedName() {
    //REVIEW this probably needs more thought
    StringWriter out = new StringWriter();
    out.write(getContainingClass().getQualifiedName());
    out.write('.');
    out.write(getSimpleName());
    out.write('(');
    JParameter[] params = getParameters();
    for(int i=0; i<params.length; i++) {
      out.write(params[i].getType().getQualifiedName());
      if (i<params.length-1) out.write(", ");
    }
    out.write(')');
    return out.toString();
  }

  // ========================================================================
  // Other public methods

  //FIXME this is here only so the parser can be lazy - please remove it
  public void setUnqualifiedThrows(List classnames) {
    if (classnames == null || classnames.size() == 0) {
      mExceptionClassRefs= null;
      return;
    }
    mExceptionClassRefs = new ArrayList(classnames.size());
    for(int i=0; i<classnames.size(); i++) {
      mExceptionClassRefs.add(UnqualifiedJClassRef.create
                              ((String)classnames.get(i),
                               (EClassImpl)getContainingClass()));
    }
  }
}