package cc.kune.msgs.client.resources;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface UserMessageImages extends ClientBundle {

    public static final UserMessageImages INST = GWT.create(UserMessageImages.class);

    ImageResource error();

    ImageResource info();

    ImageResource warning();

    ImageResource important();

    ImageResource severe();

    ImageResource remove();

    @Source("remove-grey.png")
    ImageResource removeGrey();

    @Source("remove-over.png")
    ImageResource removeOver();
}
