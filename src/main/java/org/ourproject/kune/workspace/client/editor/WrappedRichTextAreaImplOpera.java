/*
 *
 * Copyright (C) 2007-2008 The kune development team (see CREDITS for details)
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
 \*/
package org.ourproject.kune.workspace.client.editor;

import org.ourproject.kune.platf.client.ui.rte.impl.RichTextAreaImplOpera;

public class WrappedRichTextAreaImplOpera extends RichTextAreaImplOpera {

    @Override
    public native void initElement() /*-{
                                   // Most browsers don't like setting designMode until slightly _after_
                                   // the iframe becomes attached to the DOM. Any non-zero timeout will do
                                   // just fine.
                                   var _this = this;
                                   _this.@org.ourproject.kune.platf.client.ui.rte.impl.RichTextAreaImplStandard::initializing = true;
                                   setTimeout(function() {
                                     // Turn on design mode.
                                     _this.@org.ourproject.kune.platf.client.ui.rte.impl.RichTextAreaImpl::elem.contentWindow.document.designMode = 'On';
                                          // patch css inject:
                                          var elem = _this.@org.ourproject.kune.platf.client.ui.rte.impl.RichTextAreaImpl::elem;
                                          var doc = elem.contentWindow.document;
                                          var ct = "<html><head><style>@import url('" + "css/richtext.css" + "');</style></head><body CONTENTEDITABLE='true'></body></html>" ;
                                          doc.write( ct );
                                          // -- patch

                                     // Send notification that the iframe has reached design mode.
                                     _this.@org.ourproject.kune.platf.client.ui.rte.impl.RichTextAreaImplStandard::onElementInitialized()();
                                   }, 1);
                                 }-*/;

}
