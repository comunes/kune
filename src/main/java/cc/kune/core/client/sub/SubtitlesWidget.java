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
package cc.kune.core.client.sub;

import org.adamtacy.client.ui.effects.events.EffectCompletedEvent;
import org.adamtacy.client.ui.effects.events.EffectCompletedHandler;
import org.adamtacy.client.ui.effects.examples.Show;
import org.adamtacy.client.ui.effects.examples.SlideRight;

import cc.kune.common.shared.utils.SimpleCallback;
import cc.kune.core.client.sub.SubtitlesManager.SubtitlesView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.ResizeEvent;
import com.google.gwt.event.logical.shared.ResizeHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Event;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

// TODO: Auto-generated Javadoc
/**
 * The Class SubtitlesWidget.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class SubtitlesWidget extends ViewImpl implements SubtitlesView {

  /**
   * The Interface SubtitlesWidgetUiBinder.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  interface SubtitlesWidgetUiBinder extends UiBinder<Widget, SubtitlesWidget> {
  }

  /** The Constant SUBTITLE_MANAGER_ID. */
  public static final String SUBTITLE_MANAGER_ID = "k-subt-widget";

  /** The ui binder. */
  private static SubtitlesWidgetUiBinder uiBinder = GWT.create(SubtitlesWidgetUiBinder.class);

  /** The callback. */
  private SimpleCallback callback;

  /** The description. */
  @UiField
  InlineLabel description;

  /** The popup. */
  private final PopupPanel popup;

  /** The showing. */
  private boolean showing;

  /** The title. */
  @UiField
  InlineLabel title;

  /** The widget. */
  private final Widget widget;

  /**
   * Instantiates a new subtitles widget.
   */
  public SubtitlesWidget() {
    popup = new PopupPanel(false, false);
    popup.ensureDebugId(SUBTITLE_MANAGER_ID);
    widget = uiBinder.createAndBindUi(this);
    popup.addDomHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        if (showing) {
          final SlideRight slideAtEnd = new SlideRight(widget.getElement());
          slideAtEnd.invert();
          slideAtEnd.setDuration(2);
          slideAtEnd.play();
          // final Fade fadeAtEnd = new Fade(popup.getElement());
          // fadeAtEnd.setDuration(2);
          // fadeAtEnd.play();
          slideAtEnd.addEffectCompletedHandler(new EffectCompletedHandler() {
            @Override
            public void onEffectCompleted(final EffectCompletedEvent event) {
              popup.hide();
              callback.onCallback();
            }
          });
          showing = false;
        }
      }
    }, ClickEvent.getType());
    Window.addResizeHandler(new ResizeHandler() {
      @Override
      public void onResize(final ResizeEvent event) {
        setSize(event.getWidth(), event.getHeight());
      }
    });
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.View#asWidget()
   */
  @Override
  public Widget asWidget() {
    return popup;
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sub.SubtitlesManager.SubtitlesView#setDescription(java
   * .lang.String)
   */
  @Override
  public void setDescription(final String descr) {
    description.setText(descr);
  }

  /**
   * Sets the size.
   * 
   * @param width
   *          the width
   * @param height
   *          the height
   */
  private void setSize(final int width, final int height) {
    popup.setSize(width + "px", height + "px");
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sub.SubtitlesManager.SubtitlesView#setTitleText(java
   * .lang.String)
   */
  @Override
  public void setTitleText(final String text) {
    title.setText(text);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.core.client.sub.SubtitlesManager.SubtitlesView#show(cc.kune.common
   * .shared.utils.SimpleCallback)
   */
  @Override
  public void show(final SimpleCallback callback) {
    this.callback = callback;
    popup.setWidget(widget);
    setSize(Window.getClientWidth(), Window.getClientHeight());
    popup.sinkEvents(Event.MOUSEEVENTS);
    popup.show();
    final Show showAtIni = new Show(widget.getElement());
    showAtIni.setDuration(2);
    showAtIni.play();
    final SlideRight slideAtIni = new SlideRight(widget.getElement());
    slideAtIni.setDuration(2);
    slideAtIni.play();
    this.showing = true;
  }

}
