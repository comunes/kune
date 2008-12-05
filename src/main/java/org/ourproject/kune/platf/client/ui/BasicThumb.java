package org.ourproject.kune.platf.client.ui;

import com.google.gwt.user.client.ui.ClickListener;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.gwtext.client.util.Format;

// http://en.wikipedia.org/wiki/Thumbnail

public class BasicThumb extends Composite {
    private static final int NOSIZE = -1;
    private final Image thumbImg;
    private final Label thumbLabel;

    public BasicThumb(String imageUrl, int imgSize, String text, int textMaxLenght, boolean crop,
            ClickListener clickListener) {
        VerticalPanel vp = new VerticalPanel();
        if (imgSize != NOSIZE) {
            if (crop) {
                thumbImg = new Image(imageUrl, 0, 0, imgSize, imgSize);
            } else {
                thumbImg = new Image(imageUrl);
                thumbImg.setPixelSize(imgSize, imgSize);
            }
        } else {
            thumbImg = new Image(imageUrl);
        }
        String title = textMaxLenght == NOSIZE ? text : Format.ellipsis(text, textMaxLenght);
        thumbLabel = new Label(title);
        vp.add(thumbImg);
        vp.add(thumbLabel);
        vp.addStyleName("k-basic-thumb");
        vp.addStyleName("kune-Margin-Small-trbl");
        vp.addStyleName("kune-pointer");
        vp.addStyleName("kune-floatleft");
        thumbLabel.addClickListener(clickListener);
        vp.setCellHorizontalAlignment(thumbLabel, VerticalPanel.ALIGN_CENTER);
        thumbImg.addClickListener(clickListener);
        initWidget(vp);
    }

    public BasicThumb(String imageUrl, String thumText, ClickListener clickListener) {
        this(imageUrl, NOSIZE, thumText, NOSIZE, false, clickListener);
    }

    public BasicThumb(String imageUrl, String text, int textMaxLenght, ClickListener clickListener) {
        this(imageUrl, NOSIZE, text, textMaxLenght, false, clickListener);
    }

    public void setText(String text) {
        thumbLabel.setText(text);
    }

    public void setThumbUrl(String url) {
        thumbImg.setUrl(url);
    }

    public void setTooltip(String tip) {
        KuneUiUtils.setQuickTip(thumbImg, tip);
        KuneUiUtils.setQuickTip(thumbLabel, tip);
    }

    public void setTooltip(String tipTitle, String tip) {
        KuneUiUtils.setQuickTip(thumbImg, tip, tipTitle);
        KuneUiUtils.setQuickTip(thumbLabel, tip, tipTitle);
    }
}
