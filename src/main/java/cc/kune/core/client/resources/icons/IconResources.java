package cc.kune.core.client.resources.icons;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.ImageResource;

public interface IconResources extends ClientBundle {

    @Source("add-green.png")
    ImageResource addGreen();

    @Source("bug.png")
    ImageResource bug();

    @Source("e-icon.gif")
    ImageResource chat();

    @Source("kicon.css")
    // @Strict
    IconBorrarCssResource css();

    @Source("del-green.png")
    ImageResource delGreen();

    @Source("group-home.png")
    ImageResource groupHome();

    @Source("info.png")
    ImageResource info();

    @Source("kune-icon16.png")
    ImageResource kuneIcon16();

    @Source("language.png")
    ImageResource language();

    @Source("arrow_out.png")
    ImageResource maximize();

    @Source("arrow_in.png")
    ImageResource minimize();

    @Source("prefs.png")
    ImageResource prefs();
}
