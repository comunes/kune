package org.ourproject.kune.platf.client.ui.rte.inserttable;

import org.ourproject.kune.platf.client.View;

public interface InsertTableDialogView extends View {

    String generateTable(int rows, int cols, boolean sameColWidth, String backgroundColor, int border,
            String borderColor);

    String getBackgroundColor();

    String getBorderColor();

    boolean getSameColWidth();

    void hide();

    void reset();

    void show();
}
