package cc.kune.common.client.ui;

import com.google.gwt.user.client.ui.IsWidget;

public interface MaskWidgetView {

    void mask(IsWidget widget);

    void mask(IsWidget widget, String message);

    void unMask();

}
