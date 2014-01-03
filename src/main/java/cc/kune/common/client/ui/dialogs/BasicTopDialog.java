/*
 *
 * Copyright (C) 2007-2014 Licensed to the Comunes Association (CA) under
 * one or more contributor license agreements (see COPYRIGHT for details).
 * The CA licenses this file to you under the GNU Affero General Public
 * License version 3, (the "License"); you may not use this file except in
 * compliance with the License. This file is part of kune.
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
package cc.kune.common.client.ui.dialogs;

import static com.google.gwt.query.client.GQuery.$;
import static com.google.gwt.query.client.GQuery.Effects;
import cc.kune.common.client.events.EventBusInstance;
import cc.kune.common.client.shortcuts.OnEscapePressedEvent;
import cc.kune.common.client.shortcuts.OnEscapePressedEvent.OnEscapePressedHandler;
import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.client.ui.Animations;
import cc.kune.common.client.ui.PopupTopPanel;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.common.shared.utils.TextUtils;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.HasCloseHandlers;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.i18n.client.HasDirection.Direction;
import com.google.gwt.query.client.Function;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.UIObject;

// TODO: Auto-generated Javadoc
/**
 * The Class BasicTopDialog.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class BasicTopDialog extends BasicDialog {

  /**
   * The Class Builder.
   *
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  public static class Builder {
    
    /** The autohide. */
    private final boolean autohide;
    
    /** The autoscroll. */
    @SuppressWarnings("unused")
    private boolean autoscroll = false;
    
    /** The close btn. */
    private boolean closeBtn = true;
    
    /** The dialog id. */
    private final String dialogId;
    
    /** The direction. */
    private final Direction direction;
    
    /** The first button id. */
    private String firstButtonId;
    
    /** The first button title. */
    private String firstButtonTitle;
    
    /** The height. */
    private String height;
    
    /** The icon. */
    private String icon;
    
    /** The modal. */
    private final boolean modal;
    
    /** The snd button id. */
    private String sndButtonId;
    
    /** The snd button title. */
    private String sndButtonTitle;
    
    /** The tab index start. */
    private int tabIndexStart = 0;
    
    /** The title. */
    private String title;
    
    /** The width. */
    private String width;

    /**
     * Instantiates a new builder.
     *
     * @param dialogId the dialog id
     * @param autohide the autohide
     * @param modal the modal
     * @param direction the direction
     */
    public Builder(final String dialogId, final boolean autohide, final boolean modal,
        final Direction direction) {
      // Required params
      this.autohide = autohide;
      this.modal = modal;
      this.dialogId = dialogId;
      this.direction = direction;
    }

    /**
     * Autoscroll.
     *
     * @param autoscroll the autoscroll
     * @return the builder
     */
    public Builder autoscroll(final boolean autoscroll) {
      // Not used for now
      this.autoscroll = autoscroll;
      return this;
    }

    /**
     * Builds the.
     *
     * @return the basic top dialog
     */
    public BasicTopDialog build() {
      return new BasicTopDialog(this);
    }

    /**
     * Close btn.
     *
     * @param closeBtn the close btn
     * @return the builder
     */
    public Builder closeBtn(final boolean closeBtn) {
      this.closeBtn = closeBtn;
      return this;
    }

    /**
     * First button id.
     *
     * @param firstButtonId the first button id
     * @return the builder
     */
    public Builder firstButtonId(final String firstButtonId) {
      this.firstButtonId = firstButtonId;
      return this;
    }

    /**
     * First button title.
     *
     * @param firstButtonTitle the first button title
     * @return the builder
     */
    public Builder firstButtonTitle(final String firstButtonTitle) {
      this.firstButtonTitle = firstButtonTitle;
      return this;
    }

    /**
     * Height.
     *
     * @param height the height
     * @return the builder
     */
    public Builder height(final int height) {
      this.height = String.valueOf(height + "px");
      return this;
    }

    /**
     * Height.
     *
     * @param height the height
     * @return the builder
     */
    public Builder height(final String height) {
      this.height = height;
      return this;
    }

    /**
     * Icon.
     *
     * @param icon the icon
     * @return the builder
     */
    public Builder icon(final String icon) {
      this.icon = icon;
      return this;
    }

    /**
     * Snd button id.
     *
     * @param sndButtonId the snd button id
     * @return the builder
     */
    public Builder sndButtonId(final String sndButtonId) {
      this.sndButtonId = sndButtonId;
      return this;
    }

    /**
     * Snd button title.
     *
     * @param sndButtonTitle the snd button title
     * @return the builder
     */
    public Builder sndButtonTitle(final String sndButtonTitle) {
      this.sndButtonTitle = sndButtonTitle;
      return this;
    }

    /**
     * Tab index start.
     *
     * @param tabIndexStart the tab index start
     * @return the builder
     */
    public Builder tabIndexStart(final int tabIndexStart) {
      this.tabIndexStart = tabIndexStart;
      return this;
    }

    /**
     * Title.
     *
     * @param title the title
     * @return the builder
     */
    public Builder title(final String title) {
      this.title = title;
      return this;
    }

    /**
     * Width.
     *
     * @param width the width
     * @return the builder
     */
    public Builder width(final int width) {
      this.width = String.valueOf(width + "px");
      return this;
    }

    /**
     * Width.
     *
     * @param width the width
     * @return the builder
     */
    public Builder width(final String width) {
      this.width = width;
      return this;
    }

  }

  /** The Constant CLOSE_ID. */
  public static final String CLOSE_ID = "-close";

  /** The close click handler. */
  private HandlerRegistration closeClickHandler;
  
  /** The height. */
  private String height;
  
  /** The popup. */
  private final PopupTopPanel popup;
  
  /** The width. */
  private String width;

  /**
   * Instantiates a new basic top dialog.
   *
   * @param builder the builder
   */
  protected BasicTopDialog(final Builder builder) {
    popup = new PopupTopPanel(builder.autohide, builder.modal);
    popup.add(this);
    popup.ensureDebugId(builder.dialogId);
    closeBtn.ensureDebugId(builder.dialogId + CLOSE_ID);
    if (TextUtils.notEmpty(builder.icon)) {
      super.setTitleIcon(builder.icon);
    }
    setCloseBtn(builder.closeBtn);
    super.setFirstBtnText(builder.firstButtonTitle);
    super.setFirstBtnId(builder.firstButtonId);
    super.setFirstBtnTabIndex(builder.tabIndexStart);
    super.setSecondBtnText(builder.sndButtonTitle);
    super.setSecondBtnId(builder.sndButtonId);
    super.setSecondBtnTabIndex(builder.tabIndexStart + 1);
    super.getTitleText().setText(builder.title, builder.direction);

    setWidthImpl(builder.width);
    setHeightImpl(builder.height);
    recalculateSize();
    Window.addResizeHandler(new ResizeHandler() {
      @Override
      public void onResize(final ResizeEvent event) {
        setSizes(event.getWidth(), event.getHeight());
      }
    });
  }

  /**
   * Calculate percent.
   *
   * @param currentSize the current size
   * @param percent the percent
   * @return the string
   */
  private String calculatePercent(final int currentSize, final String percent) {
    return String.valueOf(currentSize * Integer.valueOf(percent.replace("%", "")) / 100) + "px";
  }

  /**
   * Gets the close.
   *
   * @return the close
   */
  public HasCloseHandlers<PopupPanel> getClose() {
    return popup;
  }

  /**
   * Hide.
   */
  public void hide() {
    if (popup.isShowing()) {
      if (Animations.enabled) {
        $(popup).as(Effects).slideUp(new Function() {
          @Override
          public void f() {
            popup.hide();
          }
        });
      } else {
        popup.hide();
      }
    }
  }

  /**
   * Recalculate size.
   */
  public void recalculateSize() {
    setSizes(Window.getClientWidth(), Window.getClientHeight());
  }

  /**
   * Sets the close btn.
   *
   * @param closeBtn the new close btn
   */
  private void setCloseBtn(final boolean closeBtn) {
    setCloseBtnVisible(closeBtn);
    if (closeBtn) {
      setCloseBtnTooltip(I18n.t("Close") + " (Esc)");
      EventBusInstance.get().addHandler(OnEscapePressedEvent.getType(), new OnEscapePressedHandler() {
        @Override
        public void onOnEscapePressed(final OnEscapePressedEvent event) {
          if (popup.isShowing()) {
            hide();
          }
        }
      });
      if (closeClickHandler == null) {
        closeClickHandler = super.getCloseBtn().addClickHandler(new ClickHandler() {
          @Override
          public void onClick(final ClickEvent event) {
            hide();
          }
        });
      }
    } else {
      if (closeClickHandler != null) {
        closeClickHandler.removeHandler();
      }
    }
  }

  /* (non-Javadoc)
   * @see cc.kune.common.client.ui.dialogs.BasicDialog#setFirstBtnTitle(java.lang.String)
   */
  @Override
  public void setFirstBtnTitle(final String title) {
    super.setFirstBtnTitle(title);
  }

  /* (non-Javadoc)
   * @see com.google.gwt.user.client.ui.UIObject#setHeight(java.lang.String)
   */
  @Override
  public void setHeight(final String height) {
    setHeightImpl(height);
  }

  /**
   * Sets the height impl.
   *
   * @param height the new height impl
   */
  private void setHeightImpl(final String height) {
    this.height = height;
    recalculateSize();
  }

  /* (non-Javadoc)
   * @see com.google.gwt.user.client.ui.UIObject#setSize(java.lang.String, java.lang.String)
   */
  @Override
  public void setSize(final String width, final String height) {
    this.width = width;
    setHeightImpl(height);
  }

  /**
   * Sets the sizes.
   *
   * @param windowWidth the window width
   * @param windowHeight the window height
   */
  private void setSizes(final int windowWidth, final int windowHeight) {
    String newWidth = null;
    String newHeight = null;
    if (width != null) {
      if (width.contains("%")) {
        newWidth = calculatePercent(windowWidth, width);
      } else {
        newWidth = width;
      }
    }
    if (height != null) {
      if (height.contains("%")) {
        newHeight = calculatePercent(windowHeight, height);
      } else {
        newHeight = height;
      }
    }
    if (newWidth != null) {
      super.setInnerWidth(newWidth);
    }
    if (newHeight != null) {
      super.setInnerHeight(newHeight);
    }
  }

  /**
   * Sets the text.
   *
   * @param text the text
   * @param direction the direction
   */
  public void setText(final String text, final Direction direction) {
    super.getTitleText().setText(text, direction);
  }

  /* (non-Javadoc)
   * @see com.google.gwt.user.client.ui.UIObject#setWidth(java.lang.String)
   */
  @Override
  public void setWidth(final String width) {
    setWidthImpl(width);
  }

  /**
   * Sets the width impl.
   *
   * @param width the new width impl
   */
  private void setWidthImpl(final String width) {
    this.width = width;
    recalculateSize();
  }

  /**
   * Show centered.
   */
  public void showCentered() {
    Tooltip.hideCurrent();
    showEffect();
    popup.showCentered();
  }

  /**
   * Show effect.
   */
  private void showEffect() {
    if (Animations.enabled) {
      $(popup).stop(true).show();
      // $(popup).stop(true).hide().as(Effects).slideDown(new Function() {
      // @Override
      // public void f() {
      // }
      // });
    }
  }

  /**
   * Show relative to.
   *
   * @param object the object
   */
  public void showRelativeTo(final UIObject object) {
    showEffect();
    popup.showRelativeTo(object);
  }

}
