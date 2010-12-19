package cc.kune.core.client.resources;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;
import com.google.gwt.resources.client.ImageResource;

public interface CoreResources extends ClientBundle {
    public interface Style extends CssResource {
        String loadingSpiner();
    }

    @Source("loading-spiner.gif")
    ImageResource loadingSpiner();

    @Source("core.css")
    Style css();
}
