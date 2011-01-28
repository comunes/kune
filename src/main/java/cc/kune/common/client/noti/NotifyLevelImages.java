package cc.kune.common.client.noti;

import cc.kune.core.client.resources.CoreResources;

import com.google.gwt.resources.client.ImageResource;
import com.google.inject.Inject;

public class NotifyLevelImages {

    private final CoreResources images;

    @Inject
    public NotifyLevelImages(final CoreResources images) {
        this.images = images;
    }

    public ImageResource getImage(final NotifyLevel level) {
        switch (level) {
        case info:
            return images.info();
        case important:
            return images.important();
        case veryImportant:
            return images.alert();
        case error:
        default:
            return images.error();
        }
    }
}
