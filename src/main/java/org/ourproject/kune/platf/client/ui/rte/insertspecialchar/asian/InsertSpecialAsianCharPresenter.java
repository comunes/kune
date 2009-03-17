package org.ourproject.kune.platf.client.ui.rte.insertspecialchar.asian;

import org.ourproject.kune.platf.client.View;

public class InsertSpecialAsianCharPresenter implements InsertSpecialAsianChar {

    private InsertSpecialAsianCharView view;

    public InsertSpecialAsianCharPresenter() {
    }

    public View getView() {
        return view;
    }

    public void init(InsertSpecialAsianCharView view) {
        this.view = view;
    }
}
