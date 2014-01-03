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
package cc.kune.core.client.groups.newgroup;

import static org.junit.Assert.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.groups.newgroup.NewGroupPresenter.NewGroupProxy;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.impl.SessionChecker;
import cc.kune.gspace.client.options.GroupOptions;

import com.google.gwt.event.shared.EventBus;

// TODO: Auto-generated Javadoc
/**
 * The Class NewGroupPresenterTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class NewGroupPresenterTest {

  /** The gr presenter. */
  private NewGroupPresenter grPresenter;

  /** The mock gr panel. */
  private NewGroupPanel mockGrPanel;

  /**
   * Before.
   */
  @Before
  public void before() {
    mockGrPanel = Mockito.mock(NewGroupPanel.class);
    grPresenter = new NewGroupPresenter(Mockito.mock(EventBus.class), mockGrPanel,
        Mockito.mock(NewGroupProxy.class), Mockito.mock(I18nTranslationService.class),
        Mockito.mock(Session.class), Mockito.mock(StateManager.class), null, null,
        Mockito.mock(GroupOptions.class), Mockito.mock(SessionChecker.class));
  }

  /**
   * Test generate short name accents.
   */
  @Test
  public void testGenerateShortNameAccents() {
    Mockito.when(mockGrPanel.getLongName()).thenReturn("áéíóúàèìòùâêîôûäëïöü");
    assertEquals(grPresenter.generateShortName(), "aeiouaeiouaeiouaeiou");
  }

  /**
   * Test generate short name capital accents.
   */
  @Test
  public void testGenerateShortNameCapitalAccents() {
    Mockito.when(mockGrPanel.getLongName()).thenReturn("ÁÉÍÓÚÀÈÌÒÙÂÊÎÔÛÄËÏÖÜñÑçÇ");
    assertEquals(grPresenter.generateShortName(), "aeiouaeiouaeiouaeiounncc");
  }

  /**
   * Test generate short name sentence.
   */
  @Test
  public void testGenerateShortNameSentence() {
    Mockito.when(mockGrPanel.getLongName()).thenReturn("Los Desposeidos: una utopía ambigua!!");
    assertEquals(grPresenter.generateShortName(), "losdesposeidosunautopiaambigua");
  }

  /**
   * Test generate short name symbols.
   */
  @Test
  public void testGenerateShortNameSymbols() {
    Mockito.when(mockGrPanel.getLongName()).thenReturn("a!·$%&/()=?¿¡'|@#'12");
    assertEquals(grPresenter.generateShortName(), "a12");
  }

  /**
   * Test generate short name very long sentence.
   */
  @Test
  public void testGenerateShortNameVeryLongSentence() {
    Mockito.when(mockGrPanel.getLongName()).thenReturn(
        "Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua");
    assertEquals(grPresenter.generateShortName(), "loremipsumdolorsitametconsect");
  }
}
