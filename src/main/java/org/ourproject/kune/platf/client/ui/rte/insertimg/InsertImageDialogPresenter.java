package org.ourproject.kune.platf.client.ui.rte.insertimg;

import org.ourproject.kune.platf.client.ui.dialogs.tabbed.AbstractTabbedDialogPresenter;

import com.calclab.suco.client.events.Listener;
import com.calclab.suco.client.events.Listener0;

public class InsertImageDialogPresenter extends AbstractTabbedDialogPresenter implements InsertImageDialog {

    private Listener<ImageInfo> onCreateListener;
    private ImageInfo imageInfo;
    private Listener0 onInsertPressed;

    public InsertImageDialogPresenter() {
        initImageInfo();
    }

    public void fireOnInsertImage(final ImageInfo imageInfo) {
        onCreateListener.onEvent(imageInfo);
        super.hide();
    }

    public ImageInfo getImageInfo() {
        return imageInfo;
    }

    public void reset() {
        initImageInfo();
    }

    public void setOnCreateImage(final Listener<ImageInfo> listener) {
        onCreateListener = listener;
    }

    public void setOnInsertImagePressed(final Listener0 onInsertPressed) {
        this.onInsertPressed = onInsertPressed;
    }

    protected void onCancel() {
        super.hide();
    }

    protected void onInsert() {
        onInsertPressed.onEvent();
    }

    private void initImageInfo() {
        imageInfo = new ImageInfo("", ImageInfo.DEF_WRAP_VALUE, ImageInfo.DEF_CLICK_ORIGINAL_VALUE,
                ContentPosition.LEFT, ImageInfo.SIZE_ORIGINAL);
    }
}
