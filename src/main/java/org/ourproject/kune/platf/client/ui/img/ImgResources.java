package org.ourproject.kune.platf.client.ui.img;

import com.google.gwt.libideas.resources.client.CssResource;
import com.google.gwt.libideas.resources.client.ImageResource;
import com.google.gwt.libideas.resources.client.ImmutableResourceBundle;

public interface ImgResources extends ImmutableResourceBundle {

    @Resource("kimg.css")
    CssResource css();

    @Resource("info.png")
    ImageResource info();

    @Resource("kune-icon16.png")
    ImageResource kuneIcon16();

    @Resource("language.png")
    ImageResource language();

}