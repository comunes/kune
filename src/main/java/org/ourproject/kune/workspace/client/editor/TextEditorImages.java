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

import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.ImageBundle;

public interface TextEditorImages extends ImageBundle {

    /**
     * @gwt.resource bold.png
     */
    AbstractImagePrototype bold();

    /**
     * @gwt.resource link.png
     */
    AbstractImagePrototype createLink();

    /**
     * @gwt.resource edithtml.png
     */
    AbstractImagePrototype editHtml();

    /**
     * @gwt.resource hfixedline.png
     */
    AbstractImagePrototype hr();

    /**
     * @gwt.resource incrementindent.png
     */
    AbstractImagePrototype indent();

    /**
     * @gwt.resource images.png
     */
    AbstractImagePrototype insertImage();

    /**
     * @gwt.resource italic.png
     */
    AbstractImagePrototype italic();

    /**
     * @gwt.resource centerpara.png
     */
    AbstractImagePrototype justifyCenter();

    /**
     * @gwt.resource alignleft.png
     */
    AbstractImagePrototype justifyLeft();

    /**
     * @gwt.resource alignright.png
     */
    AbstractImagePrototype justifyRight();

    /**
     * @gwt.resource defaultnumbering.png
     */
    AbstractImagePrototype ol();

    /**
     * @gwt.resource decrementindent.png
     */
    AbstractImagePrototype outdent();

    /**
     * @gwt.resource removeFormat.png
     */
    AbstractImagePrototype removeFormat();

    /**
     * @gwt.resource link_break.png
     */
    AbstractImagePrototype removeLink();

    /**
     * @gwt.resource strikeout.png
     */
    AbstractImagePrototype strikeThrough();

    /**
     * @gwt.resource subscript.png
     */
    AbstractImagePrototype subscript();

    /**
     * @gwt.resource superscript.png
     */
    AbstractImagePrototype superscript();

    /**
     * @gwt.resource defaultbullet.png
     */
    AbstractImagePrototype ul();

    /**
     * @gwt.resource underline.png
     */
    AbstractImagePrototype underline();

    /**
     * @gwt.resource backcolor.png
     */
    AbstractImagePrototype backcolor();

    /**
     * @gwt.resource fontcolor.png
     */
    AbstractImagePrototype fontcolor();

    /**
     * @gwt.resource charfontname.png
     */
    AbstractImagePrototype charfontname();

    /**
     * @gwt.resource fontheight.png
     */
    AbstractImagePrototype fontheight();

}