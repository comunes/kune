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
package cc.kune.common.client.ui;

import cc.kune.common.client.utils.TextUtils;

import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;

/**
 * BasicThumb.java
 */
public class BasicThumb extends Composite {
    private static final int NOSIZE = -1;
    private final Image image;
    private final Label label;
    private boolean onOverLabel;

    public BasicThumb(final String imageUrl, final int imgSize, final String text, final int textMaxLenght,
            final boolean crop) {
        this(imageUrl, imgSize, text, textMaxLenght, crop, null);
    }

    public BasicThumb(final String imageUrl, final int imgSize, final String text, final int textMaxLenght,
            final boolean crop, final ClickHandler clickHandler) {
        super();
        onOverLabel = false;
        final VerticalPanel vpanel = new VerticalPanel();
        if (imgSize == NOSIZE) {
            image = new Image(imageUrl);
        } else {
            if (crop) {
                image = new Image(imageUrl, 0, 0, imgSize, imgSize);
            } else {
                image = new Image(imageUrl);
                image.setPixelSize(imgSize, imgSize);
            }
        }
        final String title = textMaxLenght == NOSIZE ? text : TextUtils.ellipsis(text, textMaxLenght);
        label = new Label(title);
        vpanel.add(image);
        vpanel.add(label);
        vpanel.addStyleName("k-basic-thumb");
        vpanel.addStyleName("kune-Margin-Small-trbl");
        vpanel.addStyleName("kune-pointer");
        vpanel.addStyleName("kune-floatleft");
        vpanel.setCellHorizontalAlignment(label, VerticalPanel.ALIGN_CENTER);
        if (clickHandler instanceof ClickHandler) {
            addClickHandlerImpl(clickHandler);
        }
        image.addMouseOverHandler(new MouseOverHandler() {

            @Override
            public void onMouseOver(final MouseOverEvent event) {
                if (onOverLabel) {
                    label.setVisible(true);
                }
            }
        });
        image.addMouseOutHandler(new MouseOutHandler() {

            @Override
            public void onMouseOut(final MouseOutEvent event) {
                if (onOverLabel) {
                    label.setVisible(false);
                }
            }
        });
        initWidget(vpanel);
    }

    public BasicThumb(final String imageUrl, final String thumText, final ClickHandler clickHandler) {
        this(imageUrl, NOSIZE, thumText, NOSIZE, false, clickHandler);
    }

    public BasicThumb(final String imageUrl, final String text, final int textMaxLenght, final ClickHandler clickHandler) {
        this(imageUrl, NOSIZE, text, textMaxLenght, false, clickHandler);
    }

    public void addClickHandler(final ClickHandler clickHandler) {
        addClickHandlerImpl(clickHandler);
    }

    private void addClickHandlerImpl(final ClickHandler clickHandler) {
        label.addClickHandler(clickHandler);
        image.addClickHandler(clickHandler);
    }

    public void setOnOverLabel(final boolean onOverLabel) {
        this.onOverLabel = onOverLabel;
        label.setVisible(!onOverLabel);
    }

    public void setText(final String text) {
        label.setText(text);
    }

    public void setThumbUrl(final String url) {
        image.setUrl(url);
    }

    public void setTooltip(final String tip) {
        // DOM.setElementAttribute(thumbImg.getElement(), "qtip", tip);
        image.setTitle(tip);
        label.setTitle(tip);
        // KuneUiUtils.setQuickTip(thumbImg, tip);
        // KuneUiUtils.setQuickTip(thumbLabel, tip);
    }

    public void setTooltip(final String tipTitle, final String tip) {
        image.setTitle(tip);
        label.setTitle(tip);
    }
}
