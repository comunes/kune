package org.ourproject.kune.platf.client.ui.rte.insertspecialchar.occidental;

import org.ourproject.kune.platf.client.ui.dialogs.BasicPopupPanel;
import org.ourproject.kune.platf.client.ui.rte.insertspecialchar.InsertSpecialCharDialog;
import org.ourproject.kune.platf.client.ui.rte.insertspecialchar.InsertSpecialCharDialogView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.HTMLTable.Cell;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.Widget;
import com.gwtext.client.widgets.Panel;

public class AbstractInsertCharPanel extends Panel {

    private MouseOverHandler mouseOverhandler;
    protected BasicPopupPanel popup;
    private MouseOutHandler mouseOutHandler;

    public AbstractInsertCharPanel(final InsertSpecialCharDialog dialog, final String title, final String initialLabel,
            final char[] specialChars, final int rows, final int cols) {
        super(title);
        setAutoWidth(true);
        setHeight(InsertSpecialCharDialogView.HEIGHT - 10);
        setPaddings(20);
        final Label label = new Label(initialLabel);
        label.addStyleName("kune-Margin-Medium-b");
        add(label);
        final Grid grid = new Grid(rows, cols);
        grid.setCellSpacing(1);

        int row;
        int col;
        int num = 0;
        for (final char c : specialChars) {
            row = num / cols;
            col = num % cols;
            grid.setWidget(row, col, createButton(c));
            num++;
        }
        grid.addClickHandler(new ClickHandler() {
            public void onClick(final ClickEvent event) {
                final Cell cell = grid.getCellForEvent(event);
                if (cell != null) {
                    dialog.onInsert(specialChars[cell.getRowIndex() * cols + cell.getCellIndex()]);
                }
            }
        });
        grid.addStyleName("k-specialchar-grid");
        add(grid);
    }

    private Widget createButton(final char character) {
        final PushButton button = new PushButton();
        button.setText(String.valueOf(character));
        button.setStyleName("k-specialchar-pb");

        if (mouseOverhandler == null) {
            mouseOverhandler = new MouseOverHandler() {
                public void onMouseOver(final MouseOverEvent event) {
                    popup = new BasicPopupPanel(true);
                    final Label characterLabel = new Label(event.getRelativeElement().getInnerText());
                    characterLabel.setStyleName("k-specialchar-big");
                    popup.setWidget(characterLabel);
                    popup.show(event.getClientX() + 10, event.getClientY() + 10);
                    popup.addStyleName("k-specialchar-popup");
                }
            };
            mouseOutHandler = new MouseOutHandler() {
                public void onMouseOut(final MouseOutEvent event) {
                    popup.hide();
                }
            };
        }
        button.addMouseOverHandler(mouseOverhandler);
        button.addMouseOutHandler(mouseOutHandler);
        return button;
    }
}
