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

    public void init(InsertImageAbstractView view) {
        this.view = view;
        insertImageDialog.addTab(view);
    }

    public boolean isValid() {
        return view.isValid();
    }

    public void onActivate() {
        insertImageDialog.setOnInsertImagePressed(onInsertImagePressed);
    }

    public void onInsert(ImageInfo linkInfo) {
        insertImageDialog.fireOnInsertImage(linkInfo);
        reset();
    }

    public void onPositionFieldChanged(String position) {
        insertImageDialog.setImagePosition(position);
    }

    public void onSizeFieldChanged(String size) {
        insertImageDialog.setImageSize(size);
    }

    public void reset() {
        view.reset();
    }

    protected ImageInfo updateImageInfo() {
        return new ImageInfo(view.getSrc(), view.getWrapText(), view.getClickOriginal(), view.getPosition(),
                view.getSize());
    }
}
