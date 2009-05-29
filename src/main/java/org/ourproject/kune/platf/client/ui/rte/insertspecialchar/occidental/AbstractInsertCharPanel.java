package org.ourproject.kune.platf.client.ui.rte.insertspecialchar.occidental;

import org.ourproject.kune.platf.client.ui.rte.insertspecialchar.InsertSpecialCharDialog;
import org.ourproject.kune.platf.client.ui.rte.insertspecialchar.InsertSpecialCharDialogView;

import com.allen_sauer.gwt.log.client.Log;
import com.google.gwt.user.client.ui.FocusListener;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.Panel;

public class AbstractInsertCharPanel extends Panel {

    private FocusListener focusListener;

    public AbstractInsertCharPanel(final InsertSpecialCharDialog insertSpecialCharDialog, final String title,
            final String initialLabel, final char[] specialChars, final int rows, final int cols) {
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
            public void onCellClicked(final SourcesTableEvents sender, final int row, final int cell) {
                if (sender.equals(grid)) {
                    insertSpecialCharDialog.onInsert(specialChars[row * cols + cell]);
                }
            }
        });
        grid.addStyleName("k-specialchar-grid");
        add(grid);
    }

    private Widget createButton(final char c) {
        PushButton button = new PushButton();
        button.setText("" + c);
        button.setStyleName("k-specialchar-pb");
        if (focusListener == null) {
            focusListener = new FocusListener() {
                public void onFocus(final Widget sender) {
                    PopupPanel popup = new PopupPanel(true);
                    popup.setStyleName("k-specialchar-popup");
                    Label characterLabel = new Label(sender.getElement().getInnerText());
                    characterLabel.setStyleName("k-specialchar-big");
                    popup.add(characterLabel);
                    popup.show();
                    Log.info("Focus!!!!!");
                }

                public void onLostFocus(final Widget sender) {
                }
            };
        }
        // button.addFocusListener(focusListener);
        return button;
    }

}
