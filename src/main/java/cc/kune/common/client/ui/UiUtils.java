package cc.kune.common.client.ui;

import com.google.gwt.user.client.ui.InsertPanel.ForIsWidget;
import com.google.gwt.user.client.ui.Panel;

public class UiUtils {

    public static void clear(final ForIsWidget widget) {
        final Panel panel = (Panel) widget;
        panel.clear();
        // final int widgetCount = panel.getWidgetCount();
        // for (int i = 0; i < widgetCount && widgetCount > 0; i++) {
        // panel.remove(i);
        // }
    }

}
