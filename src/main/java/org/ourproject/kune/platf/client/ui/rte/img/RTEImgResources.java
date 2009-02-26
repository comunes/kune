package org.ourproject.kune.platf.client.ui.rte.img;

import com.google.gwt.core.client.GWT;
import com.google.gwt.libideas.resources.client.CssResource;
import com.google.gwt.libideas.resources.client.ImageResource;
import com.google.gwt.libideas.resources.client.ImmutableResourceBundle;

public interface RTEImgResources extends ImmutableResourceBundle {

    public static final RTEImgResources INSTANCE = GWT.create(RTEImgResources.class);

    @Resource("alignleft.png")
    ImageResource alignleft();

    @Resource("alignright.png")
    ImageResource alignright();

    @Resource("backcolor.png")
    ImageResource backcolor();

    @Resource("bold.png")
    ImageResource bold();

    @Resource("centerpara.png")
    ImageResource centerpara();

    @Resource("charfontname.png")
    ImageResource charfontname();

    @Resource("rteimg.css")
    CssResource css();

    @Resource("decrementindent.png")
    ImageResource decrementindent();

    @Resource("defaultbullet.png")
    ImageResource defaultbullet();

    @Resource("defaultnumbering.png")
    ImageResource defaultnumbering();

    @Resource("edithtml.png")
    ImageResource edithtml();

    @Resource("fontcolor.png")
    ImageResource fontcolor();

    @Resource("fontheight.png")
    ImageResource fontheight();

    @Resource("hfixedline.png")
    ImageResource hfixedline();

    @Resource("images.png")
    ImageResource images();

    @Resource("incrementindent.png")
    ImageResource incrementindent();

    @Resource("italic.png")
    ImageResource italic();

    @Resource("link.png")
    ImageResource link();

    @Resource("link_break.png")
    ImageResource link_break();

    @Resource("removeFormat.png")
    ImageResource removeFormat();

    @Resource("strikeout.png")
    ImageResource strikeout();

    @Resource("subscript.png")
    ImageResource subscript();

    @Resource("superscript.png")
    ImageResource superscript();

    @Resource("underline.png")
    ImageResource underline();

}