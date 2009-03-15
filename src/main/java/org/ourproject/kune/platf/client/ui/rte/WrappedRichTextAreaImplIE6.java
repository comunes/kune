/*
 *
 * Copyright (C) 2007-2009 The kune development team (see CREDITS for details)
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
package org.ourproject.kune.platf.client.ui.rte;

import org.ourproject.kune.platf.client.ui.rte.impl.RichTextAreaImplIE6;

/**
 * A wrapper used to inject css into RTA. As published in:
 * 
 * http://groups.google.com/group/Google-Web-Toolkit/browse_thread/thread/
 * f64c0024d8676a66
 * /d5f26a54145fc5d4?lnk=st&q=richtextarea+style#d5f26a54145fc5d4
 * 
 * FIXME: Only for IE and Mozilla
 * 
 */
public class WrappedRichTextAreaImplIE6 extends RichTextAreaImplIE6 {

    @Override
    public native void initElement() /*-{
          var _this = this;
          _this.@org.ourproject.kune.platf.client.ui.rte.impl.RichTextAreaImplStandard::initializing = true;

          setTimeout(function() {
            if (_this.@org.ourproject.kune.platf.client.ui.rte.impl.RichTextAreaImplStandard::initializing == false) {
              return;
            }

            // Attempt to set the iframe document's body to 'contentEditable' mode.
            // There's no way to know when the body will actually be available, so
            // keep trying every so often until it is.
            // Note: The body seems to be missing only rarely, so please don't remove
            // this retry loop just because it's hard to reproduce.
            var elem = _this.@org.ourproject.kune.platf.client.ui.rte.impl.RichTextAreaImpl::elem;
            var doc = elem.contentWindow.document;
            if (!doc.body) {
                // Retry in 50 ms. Faster would run the risk of pegging the CPU. Slower
                // would increase the probability of a user-visible delay.
              setTimeout(arguments.callee, 50);
              return;
            }
            var ct = "<html><head><style>@import url('" + $wnd.location.protocol + "//" + $wnd.location.host + $wnd.location.pathname + "css/richtext.css" + "');</style></head><body CONTENTEDITABLE='true'></body></html>" ;
            doc.write( ct );
            doc.body.contentEditable = true;

            // Send notification that the iframe has reached design mode.
            _this.@org.ourproject.kune.platf.client.ui.rte.impl.RichTextAreaImplStandard::onElementInitialized()();
          }, 1);
        }-*/;
}
