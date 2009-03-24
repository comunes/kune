package org.ourproject.kune.platf.client.ui.rte.insertimg;

import org.ourproject.kune.platf.client.ui.dialogs.tabbed.AbstractTabbedDialogPresenter;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;

public class InsertImageDialogPresenter extends AbstractTabbedDialogPresenter implements InsertImageDialog {

    private Listener<ImageInfo> onCreateListener;
    private ImageInfo imageInfo;
    private Listener0 onInsertPressed;

    public void fireOnInsertImage(ImageInfo imageInfo) {
        onCreateListener.onEvent(imageInfo);
        super.hide();
    }

    public ImageInfo getImageInfo() {
        return imageInfo;
    }

    public void setImageInfo(ImageInfo imageInfo) {
        this.imageInfo = imageInfo;
    }

    public void setImagePosition(String position) {
        imageInfo.setPosition(position);
    }

    public void setImageSize(String size) {
        imageInfo.setSize(size);
    }

    public void setOnCreateImage(Listener<ImageInfo> listener) {
        onCreateListener = listener;
    }

    public void setOnInsertImagePressed(Listener0 onInsertPressed) {
        this.onInsertPressed = onInsertPressed;
    }

    protected void onCancel() {
        super.hide();
    }

    protected void onInsert() {
        onInsertPressed.onEvent();
    }
}
