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

import cc.kune.common.client.log.Log;
import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.client.utils.TextUtils;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.dom.client.MouseOutEvent;
import com.google.gwt.event.dom.client.MouseOutHandler;
import com.google.gwt.event.dom.client.MouseOverEvent;
import com.google.gwt.event.dom.client.MouseOverHandler;
import com.google.gwt.resources.client.ImageResource;
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
  private Tooltip imageTooltip;
  private final Label label;
  private Tooltip labelTooltip;
  private boolean onOverLabel;
  private final VerticalPanel panel;

  public BasicThumb(final Object imageRef, final int imgSize, final String text,
      final int textMaxLenght, final boolean crop) {
    this(imageRef, imgSize, text, textMaxLenght, crop, null);
  }

  /**
   * 
   * @param imageRef
   *          This can be a ImageResource or a String Url
   * @param imgSize
   * @param text
   * @param textMaxLenght
   * @param crop
   * @param clickHandler
   */
  public BasicThumb(final Object imageRef, final int imgSize, final String text,
      final int textMaxLenght, final boolean crop, final ClickHandler clickHandler) {
    super();
    onOverLabel = false;
    panel = new VerticalPanel();
    if (imageRef instanceof String) {
      final String imageUrl = (String) imageRef;
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
    } else if (imageRef instanceof ImageResource) {
      image = new Image((ImageResource) imageRef);
      image.setPixelSize(imgSize, imgSize);
    } else {
      // This should not happen
      image = new Image();
      image.setPixelSize(imgSize, imgSize);
      Log.info("Unrecognized icon of BasicThumb: " + imageRef);
    }
    final String title = textMaxLenght == NOSIZE ? text : TextUtils.ellipsis(text, textMaxLenght);
    label = new Label(title);
    panel.add(image);
    panel.add(label);
    panel.addStyleName("k-basic-thumb");
    panel.addStyleName("kune-Margin-Mini-trbl");
    panel.addStyleName("k-pointer");
    panel.addStyleName("k-floatleft");
    panel.setCellHorizontalAlignment(label, VerticalPanel.ALIGN_CENTER);
    if (clickHandler != null) {
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
    initWidget(panel);
  }

  public BasicThumb(final Object imageRef, final String thumText, final ClickHandler clickHandler) {
    this(imageRef, NOSIZE, thumText, NOSIZE, false, clickHandler);
  }

  public BasicThumb(final Object imageRef, final String text, final int textMaxLenght,
      final ClickHandler clickHandler) {
    this(imageRef, NOSIZE, text, textMaxLenght, false, clickHandler);
  }

  public void addClickHandler(final ClickHandler clickHandler) {
    addClickHandlerImpl(clickHandler);
  }

  private void addClickHandlerImpl(final ClickHandler clickHandler) {
    panel.addDomHandler(clickHandler, ClickEvent.getType());
  }

  public void addDoubleClickHandler(final DoubleClickHandler clickHandler) {
    addDoubleClickHandlerImpl(clickHandler);
  }

  private void addDoubleClickHandlerImpl(final DoubleClickHandler clickHandler) {
    panel.addDomHandler(clickHandler, DoubleClickEvent.getType());
  }

  public Image getImage() {
    return image;
  }

  public void hideTooltip() {
    if (imageTooltip != null) {
      imageTooltip.hide();
      labelTooltip.hide();
    }
  }

  public void setLabelVisible(final boolean visible) {
    label.setVisible(visible);
  }

  public void setOnOverLabel(final boolean onOverLabel) {
    this.onOverLabel = onOverLabel;
  }

  public void setText(final String text) {
    label.setText(text);
  }

  public void setThumbUrl(final String url) {
    image.setUrl(url);
  }

  public void setTooltip(final String tip) {
    imageTooltip = Tooltip.to(image, tip);
    labelTooltip = Tooltip.to(label, tip);
  }

  public void setTooltip(final String tipTitle, final String tip) {
    imageTooltip = Tooltip.to(image, tip);
    labelTooltip = Tooltip.to(label, tip);
  }
}
