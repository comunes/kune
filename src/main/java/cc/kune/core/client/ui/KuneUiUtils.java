package cc.kune.core.client.ui;

import com.extjs.gxt.ui.client.widget.form.Field;
import com.google.gwt.user.client.Timer;

public class KuneUiUtils {
    public static void focusOnField(final Field<?> field) {
        new Timer() {
            @Override
            public void run() {
                field.focus();
            }
        }.schedule(50);
    }
}
