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
package cc.kune.common.client.actions.ui;

import cc.kune.common.client.actions.AbstractAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.KeyStroke;
import cc.kune.common.client.actions.PropertyChangeEvent;
import cc.kune.common.client.actions.PropertyChangeListener;
import cc.kune.common.client.actions.ui.descrip.DropTarget;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescrip;
import cc.kune.common.client.errors.NotImplementedException;
import cc.kune.common.client.log.Log;
import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.shared.res.KuneIcon;
import cc.kune.common.shared.utils.TextUtils;
import cc.kune.common.shared.utils.Url;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.resources.client.ImageResource;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractGuiItem.
 *
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public abstract class AbstractGuiItem extends Composite implements GuiBinding {

  protected ClickHandler clickHandlerDefault = new ClickHandler() {
    @Override
    public void onClick(final ClickEvent event) {
      descriptor.fire(new ActionEvent(this, getTargetObjectOfAction(descriptor),
          Event.as(event.getNativeEvent())));
    }
  };

  /** The descriptor. */
  protected GuiActionDescrip descriptor;

  /** The tooltip. */
  protected Tooltip tooltip;

  /**
   * Instantiates a new abstract gui item.
   */
  public AbstractGuiItem() {
    super();
  }

  /**
   * Instantiates a new abstract gui item.
   *
   * @param descriptor
   *          the descriptor
   */
  public AbstractGuiItem(final GuiActionDescrip descriptor) {
    super();
    this.descriptor = descriptor;
  }

  /**
   * Adds the style.
   *
   * @param style
   *          the style
   */
  protected void addStyle(final String style) {
    if (super.isOrWasAttached()) {
      super.addStyleName(style);
    }
  }

  /**
   * Clear styles.
   */
  protected void clearStyles() {
    if (super.isOrWasAttached()) {
      super.setStyleName("k-none");
    }
  }

  /**
   * Configure.
   */
  private void configure() {
    configureProperties();
    final PropertyChangeListener changeListener = createActionPropertyChangeListener();
    descriptor.getAction().addPropertyChangeListener(changeListener);
    descriptor.addPropertyChangeListener(changeListener);
  }

  /**
   * Sets the item properties from the stored values.
   */
  public void configureItemFromProperties() {
    if (descriptor == null) {
      throw new RuntimeException("You didn't set the super.descriptor on GUI.create");
    }
    configure();
  }

  /**
   * Configure properties.
   */
  private void configureProperties() {
    setText((String) (getValue(Action.NAME)));
    setToolTipText((String) (getValue(Action.TOOLTIP)));
    setIcon(getValue(Action.SMALL_ICON));
    setEnabled((Boolean) getValue(AbstractAction.ENABLED));
    setVisible((Boolean) getValue(GuiActionDescrip.VISIBLE));
    setStyles((String) getValue(Action.STYLES));
    setDropTarget((DropTarget) getValue(GuiActionDescrip.DROP_TARGET));
  }

  /*
   * (non-Javadoc)
   *
   * @see
   * cc.kune.common.client.actions.ui.GuiBinding#create(cc.kune.common.client
   * .actions.ui.descrip.GuiActionDescrip)
   */
  @Override
  public abstract AbstractGuiItem create(final GuiActionDescrip descriptor);

  /**
   * Creates the action property change listener.
   *
   * @return the property change listener
   */
  private PropertyChangeListener createActionPropertyChangeListener() {
    return new PropertyChangeListener() {
      @Override
      public void propertyChange(final PropertyChangeEvent event) {
        final Object newValue = event.getNewValue();
        if (event.getPropertyName().equals(Action.ENABLED)) {
          setEnabled((Boolean) newValue);
        } else if (event.getPropertyName().equals(Action.NAME)) {
          setText((String) newValue);
        } else if (event.getPropertyName().equals(Action.SMALL_ICON)) {
          setIcon(newValue);
        } else if (event.getPropertyName().equals(Action.TOOLTIP)) {
          setToolTipText((String) newValue);
        } else if (event.getPropertyName().equals(GuiActionDescrip.VISIBLE)) {
          setVisible((Boolean) newValue);
        } else if (event.getPropertyName().equals(GuiActionDescrip.TOOGLE_TOOLTIP_VISIBLE)) {
          toogleTooltipVisible();
        } else if (event.getPropertyName().equals(Action.STYLES)) {
          setStyles((String) newValue);
        } else if (event.getPropertyName().equals(GuiActionDescrip.DROP_TARGET)) {
          setDropTarget((DropTarget) newValue);
        }
      }
    };
  }

  protected void ensureSingleton() {
    final Object attached = descriptor.getValue("attached");
    if (attached != null && (Boolean) attached) {
      Log.error("Warning: this descriptor: " + descriptor + " is already attached");
    }
    descriptor.putValue("attached", true);
  }

  /**
   * Gets the target object of action.
   *
   * @param descriptor
   *          the descriptor
   * @return the target object of action
   */
  protected Object getTargetObjectOfAction(final GuiActionDescrip descriptor) {
    // If the action is associated with a item (like a Group, a
    // group shortname, a username, etc) we pass this item to
    // the action, if not we only pass the menuitem
    return descriptor.hasTarget() ? descriptor.getTarget()
        : descriptor.isChild() ? descriptor.getParent().getTarget() : ActionEvent.NO_TARGET;
  }

  private Object getValue(final String key) {
    final Object value = descriptor.getValue(key);
    if (value == null) {
      return descriptor.getAction().getValue(key);
    }
    return value;
  }

  /*
   * (non-Javadoc)
   *
   * @see com.google.gwt.user.client.ui.Composite#onAttach()
   */
  @Override
  protected void onAttach() {
    super.onAttach();
    descriptor.onAttach();
  }

  /*
   * (non-Javadoc)
   *
   * @see com.google.gwt.user.client.ui.Composite#onDetach()
   */
  @Override
  protected void onDetach() {
    super.onDetach();
    descriptor.onDetach();
  }

  /**
   * Sets the drop target.
   *
   * @param dropTarget
   *          the new drop target
   */
  private void setDropTarget(final DropTarget dropTarget) {
    if (dropTarget != null) {
      dropTarget.init(this);
    }
  };

  /**
   * Sets the enabled.
   *
   * @param enabled
   *          the new enabled
   */
  protected abstract void setEnabled(boolean enabled);

  /**
   * Sets the enabled.
   *
   * @param enabled
   *          the new enabled
   */
  private void setEnabled(final Boolean enabled) {
    setEnabled(enabled == null ? true : enabled);
  }

  /**
   * Sets the icon.
   *
   * @param icon
   *          the new icon
   */
  public abstract void setIcon(final KuneIcon icon);

  /**
   * Sets the icon.
   *
   * @param icon
   *          the new icon
   */
  protected void setIcon(final Object icon) {
    if (icon instanceof KuneIcon) {
      setIcon((KuneIcon) icon);
    } else if (icon instanceof ImageResource) {
      setIconResource((ImageResource) icon);
    } else if (icon instanceof Url) {
      setIconUrl(((Url) icon).toString());
    } else if (icon instanceof String) {
      final String iconS = (String) icon;
      if (iconS.startsWith("http")) {
        setIconUrl(iconS);
      } else {
        if (iconS.startsWith("#")) {
          setIconBackColor(iconS);
        } else {
          setIconStyle(iconS);
        }
      }
    } else if (icon != null) {
      throw new NotImplementedException();
    }
  }

  /**
   * Sets the icon background.
   *
   * @param backgroundColor
   *          the new icon background
   */
  protected abstract void setIconBackColor(String backgroundColor);

  /**
   * Sets the icon resource.
   *
   * @param icon
   *          the new icon resource
   */
  public void setIconResource(final ImageResource icon) {
    setIconStyle(("k-icon-" + icon.getName()));
  }

  /**
   * Sets the icon style.
   *
   * @param style
   *          the new icon style
   */
  protected abstract void setIconStyle(String style);

  /**
   * Sets the icon url.
   *
   * @param url
   *          the new icon url
   */
  public abstract void setIconUrl(String url);

  /**
   * Sets the styles.
   *
   * @param styles
   *          the new styles
   */
  protected void setStyles(final String styles) {
    if (styles != null) {
      clearStyles();
      for (final String style : TextUtils.splitTags(styles)) {
        addStyle(style);
      }
    }
  }

  /**
   * Sets the text.
   *
   * @param text
   *          the new text
   */
  protected abstract void setText(String text);

  /**
   * Sets the tool tip text.
   *
   * @param tooltipText
   *          the new tool tip text
   */
  public void setToolTipText(final String tooltipText) {
    if (shouldBeAdded()) {
      setToolTipTextNextTo(getWidget(), tooltipText);
    }
  }

  /**
   * Sets the tool tip text next to.
   *
   * @param widget
   *          the widget
   * @param tooltipText
   *          the tooltip text
   */
  public void setToolTipTextNextTo(final Widget widget, final String tooltipText) {
    if (tooltipText != null && !tooltipText.isEmpty()) {
      final KeyStroke key = (KeyStroke) getValue(Action.ACCELERATOR_KEY);
      final String compountTooltip = key == null ? tooltipText : tooltipText + key.toString();
      tooltip = Tooltip.to(widget, compountTooltip);
    }
  }

  /**
   * Sets the visible.
   *
   * @param visible
   *          the new visible
   */
  private void setVisible(final Boolean visible) {
    // if you have problems with setVisible check if the GuiItem calls
    // configureItemFromProperties on creation
    setVisible(visible == null ? true : visible);
  }

  /*
   * (non-Javadoc)
   *
   * @see cc.kune.common.client.actions.ui.GuiBinding#shouldBeAdded()
   */
  @Override
  public boolean shouldBeAdded() { // NOPMD by vjrj on 18/01/11 0:48
    return true;
  }

  /**
   * Toogle tooltip visible.
   */
  public void toogleTooltipVisible() {
    if (tooltip != null) {
      if (tooltip.isVisibleOrWillBe()) {
        tooltip.hide();
      } else {
        tooltip.showTemporally();
      }
    }
  }
}
