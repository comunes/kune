package org.ourproject.kune.platf.client.ui.rte.insertmedia.abstractmedia;

import org.ourproject.kune.platf.client.View;
import org.ourproject.kune.platf.client.ui.rte.insertmedia.InsertMediaDialog;

import com.allen_sauer.gwt.log.client.Log;
import com.calclab.suco.client.events.Listener0;

public class InsertMediaAbstractPresenter implements InsertMediaAbstract {

    protected InsertMediaAbstractView view;
    protected final InsertMediaDialog insertMediaDialog;
    private final Listener0 onInsertMediaPressed;

    public InsertMediaAbstractPresenter(final InsertMediaDialog insertMediaDialog) {
        this.insertMediaDialog = insertMediaDialog;
        onInsertMediaPressed = new Listener0() {
            public void onEvent() {
                if (isValid()) {
                    String embedInfo = updateMediaInfo();
                    insertMediaDialog.fireOnInsertMedia(embedInfo);
                } else {
                    Log.debug("Form in InsertMedia not valid");
                }
            }
        };
    }

    public View getView() {
        return view;
    }

    public void init(final InsertMediaAbstractView view) {
        this.view = view;
        insertMediaDialog.addTab(view);
    }

    public boolean isValid() {
        return view.isValid();
    }

    public void onActivate() {
        insertMediaDialog.setOnInsertPressed(onInsertMediaPressed);
    }

    public void reset() {
        view.reset();
    }

    protected String updateMediaInfo() {
        return "";
    }
}
