/*
* The Apache Software License, Version 1.1
*
*
* Copyright (c) 2003 The Apache Software Foundation.  All rights
* reserved.
*
* Redistribution and use in source and binary forms, with or without
* modification, are permitted provided that the following conditions
* are met:
*
* 1. Redistributions of source code must retain the above copyright
*    notice, this list of conditions and the following disclaimer.
*
* 2. Redistributions in binary form must reproduce the above copyright
*    notice, this list of conditions and the following disclaimer in
*    the documentation and/or other materials provided with the
*    distribution.
*
* 3. The end-user documentation included with the redistribution,
*    if any, must include the following acknowledgment:
*       "This product includes software developed by the
*        Apache Software Foundation (http://www.apache.org/)."
*    Alternately, this acknowledgment may appear in the software itself,
*    if and wherever such third-party acknowledgments normally appear.
*
* 4. The names "Apache" and "Apache Software Foundation" must
*    not be used to endorse or promote products derived from this
*    software without prior written permission. For written
*    permission, please contact apache@apache.org.
*
* 5. Products derived from this software may not be called "Apache
*    XMLBeans", nor may "Apache" appear in their name, without prior
*    written permission of the Apache Software Foundation.
*
* THIS SOFTWARE IS PROVIDED ``AS IS'' AND ANY EXPRESSED OR IMPLIED
* WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES
* OF MERCHANTABILITY AND FITNESS FOR A PARTICULAR PURPOSE ARE
* DISCLAIMED.  IN NO EVENT SHALL THE APACHE SOFTWARE FOUNDATION OR
* ITS CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL,
* SPECIAL, EXEMPLARY, OR CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT
* LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR SERVICES; LOSS OF
* USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND
* ON ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY,
* OR TORT (INCLUDING NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT
* OF THE USE OF THIS SOFTWARE, EVEN IF ADVISED OF THE POSSIBILITY OF
* SUCH DAMAGE.
* ====================================================================
*
* This software consists of voluntary contributions made by many
* individuals on behalf of the Apache Software Foundation and was
* originally based on software copyright (c) 2000-2003 BEA Systems
* Inc., <http://www.bea.com/>. For more information on the Apache Software
* Foundation, please see <http://www.apache.org/>.
*/

package org.apache.xmlbeans.impl.marshal;

import org.apache.xmlbeans.BindingContext;
import org.apache.xmlbeans.MarshalContext;
import org.apache.xmlbeans.Marshaller;
import org.apache.xmlbeans.UnmarshalContext;
import org.apache.xmlbeans.Unmarshaller;
import org.apache.xmlbeans.XmlException;
import org.apache.xmlbeans.impl.binding.bts.BindingLoader;

import javax.xml.namespace.NamespaceContext;
import javax.xml.stream.XMLStreamReader;
import java.util.Collection;

/**
 * Main entry point into marshalling framework.
 * Use the BindingContextFactory to create one
 */
final class BindingContextImpl
    implements BindingContext
{
    private final BindingLoader bindingLoader;
    private final RuntimeBindingTypeTable typeTable;

    /* package protected -- use the factory */
    BindingContextImpl(BindingLoader bindingLoader,
                       RuntimeBindingTypeTable typeTable)
    {
        this.bindingLoader = bindingLoader;
        this.typeTable = typeTable;
    }


    public Unmarshaller createUnmarshaller()
        throws XmlException
    {
        return new UnmarshallerImpl(bindingLoader, typeTable);
    }

    public UnmarshalContext createUnmarshallContext(Collection errors,
                                                    XMLStreamReader reader)
        throws XmlException

    {
        if (errors == null) {
            throw new IllegalArgumentException("errors must not be null");
        }

        final int prev = errors.size();
        final UnmarshalContextImpl uc =
            new UnmarshalContextImpl(reader, bindingLoader, typeTable, errors);
        checkErrors(prev, errors, "error creating UnmarshalContext");
        return uc;
    }

    public UnmarshalContext createUnmarshallContext(Collection errors)
        throws XmlException

    {
        if (errors == null) {
            throw new IllegalArgumentException("errors must not be null");
        }

        final int prev = errors.size();
        final UnmarshalContextImpl unmarshalContext =
            new UnmarshalContextImpl(bindingLoader, typeTable, errors);
        checkErrors(prev, errors, "error creating UnmarshalContext");
        return unmarshalContext;
    }


    public Marshaller createMarshaller()

        throws XmlException
    {
        return new MarshallerImpl(bindingLoader, typeTable);
    }

    public MarshalContext createMarshallContext(Collection errors,
                                                NamespaceContext ns_context)
        throws XmlException

    {
        if (errors == null) {
            throw new IllegalArgumentException("errors must not be null");
        }

        final int prev = errors.size();
        final MarshalContextImpl mc = new MarshalContextImpl(ns_context,
                                                             bindingLoader,
                                                             typeTable, errors);
        checkErrors(prev, errors, "error creating MarshalContext");
        return mc;
    }

    public MarshalContext createMarshallContext(Collection errors)
        throws XmlException
    {
        return createMarshallContext(errors,
                                     EmptyNamespaceContext.getInstance());
    }


    static void checkErrors(int prev_size, Collection errors, String err_msg)
        throws XmlException
    {
        if (errors.size() <= prev_size) return;
        throw new XmlException(err_msg, null, errors);
    }

}