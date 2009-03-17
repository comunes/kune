package org.ourproject.kune.platf.client.ui.rte.insertspecialchar.occidental;

import org.ourproject.kune.platf.client.ui.rte.insertspecialchar.InsertSpecialCharDialog;
import org.ourproject.kune.platf.client.ui.rte.insertspecialchar.InsertSpecialCharDialogView;

import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.Panel;

public class AbstractInsertCharPanel extends Panel {

    public AbstractInsertCharPanel(final InsertSpecialCharDialog insertSpecialCharDialog, String title,
            String initialLabel, final char[] specialChars, int rows, final int cols) {
        super(title);
        setAutoWidth(true);
        setHeight(InsertSpecialCharDialogView.HEIGHT - 10);
        setPaddings(20);
        Label label = new Label(initialLabel);
        label.addStyleName("kune-Margin-Medium-b");
        add(label);
        final Grid grid = new Grid(rows, cols);
        grid.setCellSpacing(1);

        int row;
        int col;
        int n = 0;
        for (char c : specialChars) {
            row = n / cols;
            col = n % cols;
            grid.setWidget(row, col, createButton(c));
            n++;
        }
        grid.addTableListener(new TableListener() {
            public void onCellClicked(SourcesTableEvents sender, int row, int cell) {
                if (sender == grid) {
                    insertSpecialCharDialog.onInsert(specialChars[row * cols + cell]);
                }
            }
        });
        grid.addStyleName("k-specialchar-grid");
        add(grid);
    }

    private Widget createButton(char c) {
        PushButton button = new PushButton();
        button.setText("" + c);
        button.setStyleName("k-specialchar-pb");
        return button;
    }

}
