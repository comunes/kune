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

import com.google.gwt.user.client.ui.impl.RichTextAreaImplMozilla;

/**
 * A wrapper used to inject css into RTA. As published in:
 * 
 * http://groups.google.com/group/Google-Web-Toolkit/browse_thread/thread/f64c0024d8676a66/d5f26a54145fc5d4?lnk=st&q=richtextarea+style#d5f26a54145fc5d4
 * 
 * FIXME: Only for IE and Mozilla
 * 
 */
public class WrappedRichTextAreaImplMozilla extends RichTextAreaImplMozilla {

    public native void initElement()
    /*-{
    // Mozilla doesn't allow designMode to be set reliably until the iframe is
    // fully loaded.
    var _this = this;
    var iframe = _this.@com.google.gwt.user.client.ui.impl.RichTextAreaImpl::elem;

    iframe.onload = function() {
      // Some Mozillae have the nasty habit of calling onload again when you set
      // designMode, so let's avoid doing it more than once.
      iframe.onload = null;

      // Send notification that the iframe has finished loading.
      _this.@com.google.gwt.user.client.ui.impl.RichTextAreaImplStandard::onElementInitialized()();

      // Don't set designMode until the RTA actually gets focused. This is
      // necessary because editing won't work on Mozilla if the iframe is
      // *hidden, but attached*. Waiting for focus gets around this issue.
      //
      // Note: This onfocus will not conflict with the addEventListener('focus',
      // ...) // in RichTextAreaImplStandard.
      iframe.contentWindow.onfocus = function() {
        iframe.contentWindow.onfocus = null;
        iframe.contentWindow.document.designMode = 'On';


      var doc = _this.@com.google.gwt.user.client.ui.impl.RichTextAreaImpl::elem.contentWindow.document;

      // patch ccs inject:

      head=doc.getElementsByTagName('head')[0];
      link=document.createElement('link');
      link.setAttribute('rel',"stylesheet");
      link.setAttribute('type',"text/css");
      link.setAttribute('href',"css/richtext.css" );
      head.appendChild(link);

      var styles = document.createElement('style');
      styles.setAttribute('type', 'text/css');
      var newStyle = document.createTextNode('\n<!--\nbody {\nbackground-color: yellow;}\n-->\n');
      styles.appendChild(newStyle);
      head.appendChild(styles);

      // -- patch

      };


    };
    }-*/;

}
