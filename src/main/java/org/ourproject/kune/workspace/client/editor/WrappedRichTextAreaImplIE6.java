/*
 * Copyright (C) 2007 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * Kune is free software; you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation; either version 3 of the License, or
 * (at your option) any later version.
 *
 * Kune is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */

package org.ourproject.kune.workspace.client.editor;

import com.google.gwt.user.client.ui.impl.RichTextAreaImplIE6;

/**
 * A wrapper used to inject css into RTA. As published in:
 * 
 * http://groups.google.com/group/Google-Web-Toolkit/browse_thread/thread/f64c0024d8676a66/d5f26a54145fc5d4?lnk=st&q=richtextarea+style#d5f26a54145fc5d4
 * 
 * FIXME: Only for IE and Mozilla
 * 
 */
public class WrappedRichTextAreaImplIE6 extends RichTextAreaImplIE6 {

    public native void initElement()
    /*-{
    var _this = this;
    window.setTimeout(function() {
      var elem = _this.@com.google.gwt.user.client.ui.impl.RichTextAreaImpl::elem;
      var doc = elem.contentWindow.document;
      var ct = "<html><head><style>@import url('" + "css/richtext.css" + "');</style></head><body CONTENTEDITABLE='true'></body></html>" ;
      doc.write( ct );

      // Send notification that the iframe has reached design mode.
      _this.@com.google.gwt.user.client.ui.impl.RichTextAreaImplStandard::onElementInitialized()();
    }, 1);
    }-*/;
}