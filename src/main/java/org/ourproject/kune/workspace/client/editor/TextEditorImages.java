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
package org.ourproject.kune.workspace.client.editor;

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

public interface TextEditorImages extends ImageBundle {

    @Resource("alignleft.png")
    AbstractImagePrototype alignleft();

    @Resource("alignright.png")
    AbstractImagePrototype alignright();

    @Resource("backcolor.png")
    AbstractImagePrototype backcolor();

    @Resource("bold.png")
    AbstractImagePrototype bold();

    @Resource("centerpara.png")
    AbstractImagePrototype centerpara();

    @Resource("charfontname.png")
    AbstractImagePrototype charfontname();

    @Resource("decrementindent.png")
    AbstractImagePrototype decrementindent();

    @Resource("defaultbullet.png")
    AbstractImagePrototype defaultbullet();

    @Resource("defaultnumbering.png")
    AbstractImagePrototype defaultnumbering();

    @Resource("edithtml.png")
    AbstractImagePrototype edithtml();

    @Resource("fontcolor.png")
    AbstractImagePrototype fontcolor();

    @Resource("fontheight.png")
    AbstractImagePrototype fontheight();

    @Resource("hfixedline.png")
    AbstractImagePrototype hfixedline();

    @Resource("images.png")
    AbstractImagePrototype images();

    @Resource("incrementindent.png")
    AbstractImagePrototype incrementindent();

    @Resource("italic.png")
    AbstractImagePrototype italic();

    @Resource("link.png")
    AbstractImagePrototype link();

    @Resource("link_break.png")
    AbstractImagePrototype linkBreak();

    @Resource("removeFormat.png")
    AbstractImagePrototype removeFormat();

    @Resource("strikeout.png")
    AbstractImagePrototype strikeout();

    @Resource("subscript.png")
    AbstractImagePrototype subscript();

    @Resource("superscript.png")
    AbstractImagePrototype superscript();

    @Resource("underline.png")
    AbstractImagePrototype underline();

}
