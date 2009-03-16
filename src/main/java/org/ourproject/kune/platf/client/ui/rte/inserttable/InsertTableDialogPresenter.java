package org.ourproject.kune.platf.client.ui.rte.inserttable;

import com.calclab.suco.client.events.Listener;

public class InsertTableDialogPresenter implements InsertTableDialog {

    private static final int MAX_ROWS = 100;
    private static final int MAX_COLS = 25;
    private static final int MAX_BORDER = 50;
    private InsertTableDialogView view;
    private Listener<String> onInsertTable;

    public InsertTableDialogPresenter() {
    }

    public void hide() {
        view.hide();
    }

    public void init(InsertTableDialogView view) {
        this.view = view;
    }

    public void onCancel() {
        view.hide();
        view.reset();
    }

    public void onInsert(String rowsS, String colsS, String borderS) {
        if (onInsertTable != null) {
            Integer rows = new Integer(rowsS);
            Integer cols = new Integer(colsS);
            Integer border = new Integer(borderS);
            boolean validRows = rows < MAX_ROWS && rows > 0;
            boolean validCols = cols < MAX_COLS && cols > 0;
            boolean validBorder = border < MAX_BORDER && border > 0;
            onInsertTable.onEvent(createTable(validRows ? rows : MAX_ROWS, validCols ? cols : MAX_COLS,
                    validBorder ? border : MAX_BORDER));
        }
        view.hide();
        view.reset();
    }

    public void setOnInsertTable(Listener<String> listener) {
        this.onInsertTable = listener;
    }

    public void show() {
        view.show();
    }

    private String createTable(int rows, int cols, int border) {
        return view.generateTable(rows, cols, view.getSameColWidth(), view.getBackgroundColor(), border,
                view.getBorderColor());
    }
}
