package org.ourproject.kune.workspace.client.cnt;

import com.google.gwt.user.client.ui.Widget;

public interface AbstractContentView {

    public void setRawContent(final String content);

    void attach();

    void detach();

    void setContent(String content, boolean showPreviewMsg);

    void setInfo(String info);

    void setInfoMessage(String text);

    void setNoPreview();

    void setWidgetAsContent(final Widget widget, boolean setDefMargins);

    void showImage(String imageUrl, String imageResizedUrl, boolean showPreviewMsg);
}
