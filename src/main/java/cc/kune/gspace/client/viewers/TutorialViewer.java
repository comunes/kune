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
package cc.kune.gspace.client.viewers;

import java.util.List;

import cc.kune.common.client.log.Log;
import cc.kune.common.client.ui.dialogs.CloseDialogButton;
import cc.kune.core.client.events.ToolChangedEvent;
import cc.kune.core.client.events.ToolChangedEvent.ToolChangedHandler;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.FileConstants;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Singleton;

@Singleton
public class TutorialViewer extends Composite {

  public interface OnTutorialClose {
    void onClose();
  }
  interface TutorialViewerUiBinder extends UiBinder<Widget, TutorialViewer> {
  }

  private static final int FOOTBAR = 12;
  private static TutorialViewerUiBinder uiBinder = GWT.create(TutorialViewerUiBinder.class);
  @UiField
  CloseDialogButton closeBtn;
  private String defLang;
  @UiField
  public Frame frame;
  private final I18nUITranslationService i18n;
  private List<String> langs;
  private OnTutorialClose onTutorialClose;
  private final Session session;

  @Inject
  public TutorialViewer(final I18nUITranslationService i18n, final Session session,
      final StateManager stateManager) {
    this.i18n = i18n;
    this.session = session;
    initWidget(uiBinder.createAndBindUi(this));
    stateManager.onToolChanged(true, new ToolChangedHandler() {
      @Override
      public void onToolChanged(final ToolChangedEvent event) {
        setTool(event.getNewTool());
      }
    });
    closeBtn.addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        if (onTutorialClose != null) {
          onTutorialClose.onClose();
        }
      }
    });
  }

  private String getTutorialLang() {
    if (langs == null) {
      langs = session.getInitData().getTutorialLanguages();
      defLang = session.getInitData().getDefTutorialLanguage();
    }
    final String currentLang = i18n.getCurrentLanguage();
    // We show the default tutorial lang is it's not translated (configured via
    // kune.properties)
    return langs.contains(currentLang) ? currentLang : defLang;
  }

  public void setHeigth(final Integer height) {
    if (height > FOOTBAR) {
      final String he = (height - FOOTBAR) + "px";
      frame.setWidth("100%");
      frame.setHeight(he);
      Log.debug("Resizing to: " + height);
    }
  }

  private void setTool(final String tool) {
    final String currentLang = getTutorialLang();
    frame.setUrl(FileConstants.TUTORIALS_PREFIX + tool + ".svg" + "#" + currentLang);
  }

  public void setTutorialCloseHandler(final OnTutorialClose onTutorialClose) {
    this.onTutorialClose = onTutorialClose;
  }

}
