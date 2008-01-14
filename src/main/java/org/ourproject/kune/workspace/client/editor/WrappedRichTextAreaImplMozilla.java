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

      // patch ccs inject:

      var doc = _this.@com.google.gwt.user.client.ui.impl.RichTextAreaImpl::elem.contentWindow.document;
      head=doc.getElementsByTagName('head')[0];
      link=document.createElement('link');
      link.setAttribute('rel',"stylesheet");
      link.setAttribute('type',"text/css");
      link.setAttribute('href',"css/richtext.css" );
      head.appendChild(link);

      var styles = document.createElement('style');
      styles.setAttribute('type', 'text/css');
      var newStyle = document.createTextNode('\n<!--\nbody{color:#000;background:#FFF;}body,div,dl,dt,dd,ul,ol,li,h1,h2,h3,h4,h5,h6,pre,code,form,fieldset,legend,input,textarea,p,blockquote,th,td{margin:0;padding:0;}table{border-collapse:collapse;border-spacing:0;}fieldset,img{border:0;}address,caption,cite,code,dfn,em,strong,th,var{font-style:normal;font-weight:400;}li{list-style:none;}caption,th{text-align:left;}h1,h2,h3,h4,h5,h6{font-size:100%;font-weight:400;}q:before,q:after{content:\'\';}abbr,acronym{border:0;font-variant:normal;}sup,sub{line-height:-1px;vertical-align:text-top;}sub{vertical-align:text-bottom;}input,textarea,select{font-family:inherit;font-size:inherit;font-weight:inherit;}body{font:13px/1.22 arial,helvetica,clean,sans-serif;font-size:small;font:x-small;}table{font-size:inherit;font:100%;}pre,code,kbd,samp,tt{font-family:monospace;font-size:108%;line-height:99%;}h1{font-size:138.5%;}h2{font-size:123.1%;}h3{font-size:108%;}h1,h2,h3{margin:1em 0;}h1,h2,h3,h4,h5,h6,strong{font-weight:700;}abbr,acronym{border-bottom:1px dotted #000;cursor:help;}em{font-style:italic;}blockquote,ul,ol,dl{margin:1em;}ol,ul,dl{margin-left:2em;}ol li{list-style:decimal outside;}ul li{list-style:disc outside;}dl dd{margin-left:1em;}th,td{border:1px solid #000;padding:.5em;}th{font-weight:700;text-align:center;}caption{margin-bottom:.5em;text-align:center;}p,fieldset,table,pre{margin-bottom:1em;}input[type=text],input[type=password],textarea{width:12.25em;width:11.9em;}body{font-family:arial, sans;margin:5px}\n-->\n');
      styles.appendChild(newStyle);
      head.appendChild(styles);

      // -- patch

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
     };

    };
    }-*/;

}
