package org.ourproject.kune.workspace.client.editor;

import com.google.gwt.user.client.ui.impl.RichTextAreaImplSafari;

public class WrappedRichTextAreaImplSafari extends RichTextAreaImplSafari {

    @Override
    public native void initElement() /*-{
                          // Most browsers don't like setting designMode until slightly _after_
                          // the iframe becomes attached to the DOM. Any non-zero timeout will do
                          // just fine.
                          var _this = this;
                          _this.@com.google.gwt.user.client.ui.impl.RichTextAreaImplStandard::initializing = true;
                          setTimeout(function() {
                            // Turn on design mode.
                            _this.@com.google.gwt.user.client.ui.impl.RichTextAreaImpl::elem.contentWindow.document.designMode = 'On';
                            
                           // patch css inject:
                           var elem = _this.@com.google.gwt.user.client.ui.impl.RichTextAreaImpl::elem;
                           var doc = elem.contentWindow.document;
                           var ct = "<html><head><style>@import url('" + "css/richtext.css" + "');</style></head><body CONTENTEDITABLE='true'></body></html>" ;
                           doc.write( ct );
                           // -- patch

                            // Send notification that the iframe has reached design mode.
                            _this.@com.google.gwt.user.client.ui.impl.RichTextAreaImplStandard::onElementInitialized()();
                          }, 1);
                        }-*/;

}
