/*
 *
 * Copyright (C) 2007-2011 The kune development team (see CREDITS for details)
 * This file is part of kune.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 *
 */
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

    public void init(final InsertTableDialogView view) {
        this.view = view;
    }

    public void onCancel() {
        view.hide();
        view.reset();
    }

    public void onInsert(final String rowsS, final String colsS, final String borderS) {
        if (onInsertTable != null) {
            final Integer rows = Integer.valueOf(rowsS);
            final Integer cols = Integer.valueOf(colsS);
            final Integer border = Integer.valueOf(borderS);
            final boolean validRows = rows < MAX_ROWS && rows > 0;
            final boolean validCols = cols < MAX_COLS && cols > 0;
            final boolean validBorder = border < MAX_BORDER && border > 0;
            onInsertTable.onEvent(createTable(validRows ? rows : MAX_ROWS, validCols ? cols : MAX_COLS,
                    validBorder ? border : MAX_BORDER));
        }
        view.hide();
        view.reset();
    }

    public void setOnInsertTable(final Listener<String> listener) {
        this.onInsertTable = listener;
    }

    public void show() {
        view.show();
    }

    private String createTable(final int rows, final int cols, final int border) {
        return view.generateTable(rows, cols, view.getSameColWidth(), view.getBackgroundColor(), border,
                view.getBorderColor());
    }
}
