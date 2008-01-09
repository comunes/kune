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