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
package org.ourproject.kune.platf.client.ui;

import com.google.gwt.event.dom.client.ClickHandler;
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

    public BasicThumb(final String imageUrl, final int imgSize, final String text, final int textMaxLenght,
            final boolean crop, final ClickHandler clickHandler) {
        super();
        final VerticalPanel vpanel = new VerticalPanel();
        if (imgSize == NOSIZE) {
            thumbImg = new Image(imageUrl);
        } else {
            if (crop) {
                thumbImg = new Image(imageUrl, 0, 0, imgSize, imgSize);
            } else {
                thumbImg = new Image(imageUrl);
                thumbImg.setPixelSize(imgSize, imgSize);
            }
        }
        final String title = textMaxLenght == NOSIZE ? text : Format.ellipsis(text, textMaxLenght);
        thumbLabel = new Label(title);
        vpanel.add(thumbImg);
        vpanel.add(thumbLabel);
        vpanel.addStyleName("k-basic-thumb");
        vpanel.addStyleName("kune-Margin-Small-trbl");
        vpanel.addStyleName("kune-pointer");
        vpanel.addStyleName("kune-floatleft");
        thumbLabel.addClickHandler(clickHandler);
        vpanel.setCellHorizontalAlignment(thumbLabel, VerticalPanel.ALIGN_CENTER);
        thumbImg.addClickHandler(clickHandler);
        initWidget(vpanel);
    }

    public BasicThumb(final String imageUrl, final String thumText, final ClickHandler clickHandler) {
        this(imageUrl, NOSIZE, thumText, NOSIZE, false, clickHandler);
    }

    public BasicThumb(final String imageUrl, final String text, final int textMaxLenght, final ClickHandler clickHandler) {
        this(imageUrl, NOSIZE, text, textMaxLenght, false, clickHandler);
    }

    public void setText(final String text) {
        thumbLabel.setText(text);
    }

    public void setThumbUrl(final String url) {
        thumbImg.setUrl(url);
    }

    public void setTooltip(final String tip) {
        KuneUiUtils.setQuickTip(thumbImg, tip);
        KuneUiUtils.setQuickTip(thumbLabel, tip);
    }

    public void setTooltip(final String tipTitle, final String tip) {
        KuneUiUtils.setQuickTip(thumbImg, tip, tipTitle);
        KuneUiUtils.setQuickTip(thumbLabel, tip, tipTitle);
    }
}
