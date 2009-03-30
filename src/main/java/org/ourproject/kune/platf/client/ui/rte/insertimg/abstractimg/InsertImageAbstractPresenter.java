package org.ourproject.kune.platf.client.ui.rte.insertimg.abstractimg;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.ui.rte.insertimg.ImageInfo;
import org.ourproject.kune.platf.client.ui.rte.insertimg.InsertImageDialog;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.events.Listener0;

public class InsertImageAbstractPresenter implements InsertImageAbstract {

    protected InsertImageAbstractView view;
    protected final InsertImageDialog insertImageDialog;
    private final Listener0 onInsertImagePressed;
    protected ImageInfo imageInfo;

    public InsertImageAbstractPresenter(final InsertImageDialog insertImageDialog) {
        this.insertImageDialog = insertImageDialog;
        onInsertImagePressed = new Listener0() {
            public void onEvent() {
                if (isValid()) {
                    ImageInfo linkInfo = updateImageInfo();
                    insertImageDialog.fireOnInsertImage(linkInfo);
                } else {
                    Log.debug("Form in InsertImage not valid");
                }
            }
        };
    }

    public ImageInfo getImageInfo() {
        return insertImageDialog.getImageInfo();
    }

    public View getView() {
        return view;
    }

    public void init(final InsertImageAbstractView view) {
        this.view = view;
        insertImageDialog.addTab(view);
    }

    public boolean isValid() {
        return view.isValid();
    }

    public void onActivate() {
        insertImageDialog.setOnInsertImagePressed(onInsertImagePressed);
    }

    public void onClickOriginalChecked(final boolean checked) {
        insertImageDialog.getImageInfo().setClickOriginal(checked);
    }

    public void onInsert(final ImageInfo linkInfo) {
        insertImageDialog.fireOnInsertImage(linkInfo);
        reset();
    }

    public void onPositionFieldChanged(final String position) {
        insertImageDialog.getImageInfo().setPosition(position);
    }

    public void onSizeFieldChanged(final String size) {
        insertImageDialog.getImageInfo().setSize(size);
    }

    public void onWrapTextChecked(final boolean checked) {
        insertImageDialog.getImageInfo().setWraptext(checked);
    }

    public void reset() {
        view.reset();
    }

    public void setPosition(final String position) {
        insertImageDialog.getImageInfo().setPosition(position);
    }

    public void setSize(final String size) {
        insertImageDialog.getImageInfo().setSize(size);
    }

    protected ImageInfo updateImageInfo() {
        return new ImageInfo(view.getSrc(), view.getWrapText(), view.getClickOriginal(), view.getPosition(),
                view.getSize());
    }
}
