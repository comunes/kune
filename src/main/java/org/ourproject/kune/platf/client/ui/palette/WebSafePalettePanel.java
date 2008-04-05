package org.ourproject.kune.platf.client.ui.palette;

import com.google.gwt.user.client.DOM;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Grid;
import com.google.gwt.user.client.ui.SourcesTableEvents;
import com.google.gwt.user.client.ui.TableListener;

/**
 * 
 * A Web safe colors palette. See:
 * http://en.wikipedia.org/wiki/Web_colors#Web-safe_colors
 * 
 */
public class WebSafePalettePanel extends Composite implements WebSafePaletteView {

    private final WebSafePalettePresenter presenter;

    public WebSafePalettePanel(final WebSafePalettePresenter initPresenter) {
        this.presenter = initPresenter;

        Grid paletteGrid = new Grid(ROWS, COLS);

        initWidget(paletteGrid);

        paletteGrid.setCellSpacing(1);
        // Put color values in the grid cells

        int row;
        int col;
        int n = 0;
        for (int a = 0; a < COLORS.length; a++) {
            for (int b = 0; b < COLORS.length; b++) {
                for (int c = 0; c < COLORS.length; c++) {
                    row = n / COLS;
                    col = n % COLS;
                    String currentColor = "#" + COLORS[c] + COLORS[a] + COLORS[b];
                    paletteGrid.setText(row, col, " ");
                    DOM.setStyleAttribute(paletteGrid.getCellFormatter().getElement(row, col), "backgroundColor",
                            currentColor);
                    n++;
                }
            }
        }
        paletteGrid.addStyleName("kune-WebSafePalette");
        paletteGrid.addTableListener(new TableListener() {
            public void onCellClicked(final SourcesTableEvents sender, final int row, final int col) {
                presenter.onColorSelected(row, col);
            }
        });
    }

}
