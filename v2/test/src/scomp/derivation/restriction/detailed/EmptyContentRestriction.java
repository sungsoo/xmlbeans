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
package scomp.derivation.restriction.detailed;

import xbean.scomp.derivation.emtpy.RestrictedEmptyEltDocument;
import xbean.scomp.derivation.emtpy.RestrictedEmptyT;
import scomp.common.BaseCase;
import org.apache.xmlbeans.XmlCursor;

/**
 * @owner: ykadiysk
 * Date: Jul 22, 2004
 * Time: 5:22:07 PM
 */
public class EmptyContentRestriction extends BaseCase {
    public void testRestriction() throws Throwable {
        RestrictedEmptyEltDocument doc = RestrictedEmptyEltDocument.Factory.newInstance();

        RestrictedEmptyT elt = doc.addNewRestrictedEmptyElt();
        elt.setEmptyAttr("foobar");
        assertTrue(!doc.validate());
        showErrors();
        elt.setEmptyAttr("myval");
        try {
            assertTrue(doc.validate());
        }
        catch (Throwable t) {
            doc.validate(validateOptions);
            showErrors();
            throw t;
        }
        XmlCursor cur = elt.newCursor();
        cur.toFirstContentToken();
        cur.toNextToken();
        cur.beginElement("foobar");
        assertEquals("<xml-fragment>" +
                "<emt:RestrictedEmptyElt emptyAttr=\"myval\" " +
                "xmlns:emt=\"http://xbean/scomp/derivation/Emtpy\"/>" +
                "<foobar/></xml-fragment>", doc.xmlText());
        assertTrue(!doc.validate());
        showErrors();

    }
}
