package org.ourproject.kune.platf.client.ui.rte.insertspecialchar.occidental;

import org.ourproject.kune.platf.client.View;

public class InsertSpecialOccCharPresenter implements InsertSpecialOccChar {

    private InsertSpecialOccCharView view;

    public InsertSpecialOccCharPresenter() {
    }

    public View getView() {
        return view;
    }

    public void init(InsertSpecialOccCharView view) {
        this.view = view;
    }
}
