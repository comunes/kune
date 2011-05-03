package cc.kune.msgs.client.resources;

import cc.kune.common.client.notify.NotifyLevel;

import com.google.gwt.resources.client.ImageResource;

public class UserMessageImagesUtil {

    public static ImageResource getIcon(final NotifyLevel level) {

        switch (level) {
        case important:
            return UserMessageImages.INST.important();
        case info:
            return UserMessageImages.INST.info();
        case veryImportant:
            return UserMessageImages.INST.warning();
        case error:
        default:
            return UserMessageImages.INST.error();
        }
    }

}
