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

      head.appendChild("<style>@import url('" + "css/richtext.css" + "');</style>");

      // -- patch

      };


    };
    }-*/;

}
