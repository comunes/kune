package org.ourproject.kune.platf.client.ui.rte.insertimg;

import org.ourproject.kune.platf.client.ui.dialogs.tabbed.AbstractTabbedDialog;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;

public interface InsertImageDialog extends AbstractTabbedDialog {

    void fireOnInsertImage(ImageInfo imageInfo);

    ImageInfo getImageInfo();

    void setImageInfo(ImageInfo imageInfo);

    void setImagePosition(String position);

    void setImageSize(String size);

    void setOnCreateImage(Listener<ImageInfo> listener);

    void setOnInsertImagePressed(Listener0 onInsertLinkPressed);

}
