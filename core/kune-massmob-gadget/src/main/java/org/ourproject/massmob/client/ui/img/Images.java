package org.ourproject.massmob.client.ui.img;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.DataResource;
import com.google.gwt.resources.client.ImageResource;

public interface Images extends ClientBundle {
    ImageResource arrowDown();

    ImageResource arrowRight();

    ImageResource bg();

    @Source("bg.png")
    DataResource bgResource();

    @Source("images.css")
    ImageCssResource css();

    ImageResource map();

    ImageResource picture();
}
