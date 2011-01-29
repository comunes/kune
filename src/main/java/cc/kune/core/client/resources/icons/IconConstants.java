package cc.kune.core.client.resources.icons;

import com.google.gwt.resources.client.ImageResource;

public final class IconConstants {

    public static final String CSS_SUFFIX = "k-icon-";

    public static final String PATH_PREFIX = "images/";

    public static String toPath(final ImageResource img) {
        return PATH_PREFIX + img.getName() + ".png";
    }

    private IconConstants() {
    }
}
