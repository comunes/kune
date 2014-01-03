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
package cc.kune.gspace.client.licensewizard;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;

import cc.kune.core.client.state.Session;
import cc.kune.gspace.client.licensewizard.pages.LicenseWizardFirstFormView;
import cc.kune.gspace.client.licensewizard.pages.LicenseWizardFrdFormView;
import cc.kune.gspace.client.licensewizard.pages.LicenseWizardSndFormView;
import cc.kune.gspace.client.licensewizard.pages.LicenseWizardTrdFormView;

// TODO: Auto-generated Javadoc
/**
 * The Class LicenseWizardPresenterTest.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class LicenseWizardPresenterTest {

  /** The first form. */
  private LicenseWizardFirstFormView firstForm;

  /** The frd form. */
  private LicenseWizardFrdFormView frdForm;

  /** The license wizard. */
  private LicenseWizardPresenter licenseWizard;

  /** The snd form. */
  private LicenseWizardSndFormView sndForm;

  /** The trd form. */
  private LicenseWizardTrdFormView trdForm;

  /** The view. */
  private LicenseWizardView view;

  /**
   * Before.
   */
  @Before
  public void before() {
    view = Mockito.mock(LicenseWizardView.class);
    firstForm = Mockito.mock(LicenseWizardFirstFormView.class);
    sndForm = Mockito.mock(LicenseWizardSndFormView.class);
    trdForm = Mockito.mock(LicenseWizardTrdFormView.class);
    frdForm = Mockito.mock(LicenseWizardFrdFormView.class);
    final Session session = Mockito.mock(Session.class);
    licenseWizard = new LicenseWizardPresenter(null, view, null, firstForm, sndForm, trdForm, frdForm,
        session);
  }

  /**
   * On another selected.
   */
  @Test
  public void onAnotherSelected() {
    licenseWizard.onAnotherLicenseSelecterd();
    Mockito.verify(view).setEnabled(false, true, true, false);
    Mockito.when(view.isCurrentPage(firstForm)).thenReturn(true);
    licenseWizard.onNext();
    Mockito.verify(view).show(sndForm);
    Mockito.verify(view).setEnabled(true, true, true, false);
    Mockito.when(view.isCurrentPage(sndForm)).thenReturn(true);
    licenseWizard.onBack();
    Mockito.verify(view).show(firstForm);
    Mockito.verify(view).setEnabled(false, true, true, true);
  }

  /**
   * On copyleft selected.
   */
  @Test
  public void onCopyleftSelected() {
    licenseWizard.onCopyLeftLicenseSelected();
    Mockito.verify(view).setEnabled(false, false, true, true);
  }

  /**
   * On show reset widget.
   */
  @Test
  public void onShowResetWidget() {
    licenseWizard.start(null);
    Mockito.verify(view).clear();
    Mockito.verify(view).setEnabled(false, false, true, true);
    Mockito.verify(view).show(firstForm);
    Mockito.verify(firstForm).reset();
    Mockito.verify(view).show();
  }
}
