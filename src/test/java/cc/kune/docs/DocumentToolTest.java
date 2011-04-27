/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
package cc.kune.docs;

import static cc.kune.docs.shared.DocsConstants.NAME;
import static cc.kune.docs.shared.DocsConstants.TYPE_DOCUMENT;
import static cc.kune.docs.shared.DocsConstants.TYPE_FOLDER;
import static cc.kune.docs.shared.DocsConstants.TYPE_ROOT;
import static org.junit.Assert.assertEquals;

import org.junit.Test;

import cc.kune.docs.shared.DocsConstants;

public class DocumentToolTest {

    @Test
    public void clientAndServerSync() {
        assertEquals(NAME, DocsConstants.NAME);
        assertEquals(TYPE_ROOT, DocsConstants.TYPE_ROOT);
        assertEquals(TYPE_FOLDER, DocsConstants.TYPE_FOLDER);
        assertEquals(TYPE_DOCUMENT, DocsConstants.TYPE_DOCUMENT);
    }
}
