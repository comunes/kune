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

import java.util.List;

import cc.kune.common.client.log.Log;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.ui.dialogs.wizard.WizardListener;
import cc.kune.common.shared.utils.SimpleCallback;
import cc.kune.core.client.state.Session;
import cc.kune.core.shared.dto.LicenseDTO;
import cc.kune.gspace.client.licensewizard.pages.LicenseWizardFirstFormView;
import cc.kune.gspace.client.licensewizard.pages.LicenseWizardFrdFormView;
import cc.kune.gspace.client.licensewizard.pages.LicenseWizardSndFormView;
import cc.kune.gspace.client.licensewizard.pages.LicenseWizardTrdFormView;

import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.IsWidget;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

// TODO: Auto-generated Javadoc
/**
 * The Class LicenseWizardPresenter.
 * 
 * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
 */
public class LicenseWizardPresenter extends
    Presenter<LicenseWizardView, LicenseWizardPresenter.LicenseWizardProxy> implements LicenseWizard {

  /**
   * The Interface LicenseWizardProxy.
   * 
   * @author vjrj@ourproject.org (Vicente J. Ruiz Jurado)
   */
  @ProxyCodeSplit
  public interface LicenseWizardProxy extends Proxy<LicenseWizardPresenter> {
  }

  /** The cc vers. */
  private final String ccVers;

  /** The frd form. */
  private final LicenseWizardFrdFormView frdForm;

  /** The fst form. */
  private final LicenseWizardFirstFormView fstForm;

  /** The select license listener. */
  private LicenseChooseCallback selectLicenseListener;

  /** The session. */
  private final Session session;

  /** The snd form. */
  private final LicenseWizardSndFormView sndForm;

  /** The trd form. */
  private final LicenseWizardTrdFormView trdForm;

  /**
   * Instantiates a new license wizard presenter.
   * 
   * @param eventBus
   *          the event bus
   * @param view
   *          the view
   * @param proxy
   *          the proxy
   * @param firstForm
   *          the first form
   * @param sndForm
   *          the snd form
   * @param trdForm
   *          the trd form
   * @param frdForm
   *          the frd form
   * @param session
   *          the session
   */
  @Inject
  public LicenseWizardPresenter(final EventBus eventBus, final LicenseWizardView view,
      final LicenseWizardProxy proxy, final LicenseWizardFirstFormView firstForm,
      final LicenseWizardSndFormView sndForm, final LicenseWizardTrdFormView trdForm,
      final LicenseWizardFrdFormView frdForm, final Session session) {
    super(eventBus, view, proxy);
    this.fstForm = firstForm;
    this.sndForm = sndForm;
    this.trdForm = trdForm;
    this.frdForm = frdForm;
    this.session = session;
    ccVers = "-" + session.getCurrentCCversion();
  }

  /**
   * Gets the license from short name.
   * 
   * @param shortName
   *          the short name
   * @return the license from short name
   */
  private LicenseDTO getLicenseFromShortName(final String shortName) {
    final List<LicenseDTO> licenses = session.getLicenses();
    for (int i = 0; i < licenses.size(); i++) {
      final LicenseDTO licenseDTO = licenses.get(i);
      if (licenseDTO.getShortName().equals(shortName)) {
        return licenseDTO;
      }
    }
    Log.error("Internal error: License not found");
    throw new IndexOutOfBoundsException("License not found");
  }

  /**
   * In.
   * 
   * @param page
   *          the page
   * @return true, if successful
   */
  private boolean in(final IsWidget page) {
    return getView().isCurrentPage(page);
  }

  /**
   * On another license selecterd.
   */
  void onAnotherLicenseSelecterd() {
    getView().setEnabled(false, true, true, false);
  }

  /**
   * On back.
   */
  public void onBack() {
    if (getView().isCurrentPage(sndForm)) {
      showFst();
    } else if (in(trdForm)) {
      showSnd();
    } else if (in(frdForm)) {
      showSnd();
    } else {
      Log.error("Programatic error in LicenseWizardPresenter");
    }
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.HandlerContainerImpl#onBind()
   */
  @Override
  protected void onBind() {
    super.onBind();
    fstForm.onChange(new SimpleCallback() {
      @Override
      public void onCallback() {
        if (fstForm.isCopyleft()) {
          onCopyLeftLicenseSelected();
        } else {
          onAnotherLicenseSelecterd();
        }
      }
    });
    trdForm.onChange(new SimpleCallback() {
      @Override
      public void onCallback() {
        onCreativeCommonsChanged();
      }
    });
    frdForm.onChange(new SimpleCallback() {
      @Override
      public void onCallback() {
        if (frdForm.getSelectedLicense().length() > 0) {
          getView().setEnabled(true, false, true, true);
        }
      }
    });
    getView().add(fstForm);
    getView().add(sndForm);
    getView().add(trdForm);
    getView().add(frdForm);
    final WizardListener listener = new WizardListener() {

      @Override
      public void onBack() {
        LicenseWizardPresenter.this.onBack();

      }

      @Override
      public void onCancel() {
        LicenseWizardPresenter.this.onCancel();

      }

      @Override
      public void onClose() {
        LicenseWizardPresenter.this.onClose();
      }

      @Override
      public void onFinish() {
        LicenseWizardPresenter.this.onChange();
      }

      @Override
      public void onNext() {
        LicenseWizardPresenter.this.onNext();
      }
    };
    getView().setListener(listener);
    reset();
  }

  /**
   * On cancel.
   */
  public void onCancel() {
    getView().hide();
  }

  /**
   * On change.
   */
  public void onChange() {
    String licenseShortName;
    if (in(fstForm)) {
      licenseShortName = "by-sa" + ccVers;
    } else if (in(trdForm)) {
      if (trdForm.isAllowComercial()) {
        licenseShortName = trdForm.isAllowModif() ? "by" + ccVers
            : trdForm.isAllowModifShareAlike() ? "by-sa" + ccVers : "by-nd" + ccVers;
      } else {
        licenseShortName = trdForm.isAllowModif() ? "by-nc" + ccVers
            : trdForm.isAllowModifShareAlike() ? "by-nc-sa" + ccVers : "by-nc-nd" + ccVers;
      }
    } else if (in(sndForm)) {
      licenseShortName = "by-sa" + ccVers;
      NotifyUser.error("Programatic error in LicenseWizardPresenter");
    } else {
      licenseShortName = frdForm.getSelectedLicense();
    }
    getView().hide();
    selectLicenseListener.onSelected(getLicenseFromShortName(licenseShortName));
  }

  /**
   * On close.
   */
  public void onClose() {
    getView().hide();
    reset();
  }

  /**
   * On copy left license selected.
   */
  void onCopyLeftLicenseSelected() {
    getView().setEnabled(false, false, true, true);
  }

  /**
   * On creative commons changed.
   */
  private void onCreativeCommonsChanged() {
    final boolean isCopyleft = trdForm.isAllowComercial() && trdForm.isAllowModifShareAlike();
    final boolean isAppropiateForCulturalWorks = trdForm.isAllowComercial()
        && (trdForm.isAllowModif() || trdForm.isAllowModifShareAlike());
    trdForm.setFlags(isCopyleft, isAppropiateForCulturalWorks, !trdForm.isAllowComercial());
  }

  /**
   * On next.
   */
  public void onNext() {
    if (in(fstForm)) {
      getView().clear();
      showSnd();
    } else if (in(sndForm)) {
      if (sndForm.isCommonLicensesSelected()) {
        showTrd();
      } else {
        showFrd();
      }
    } else {
      Log.error("Programatic error in LicenseWizardPresenter");
    }
  }

  /**
   * Reset.
   */
  private void reset() {
    getView().clear();
    getView().setEnabled(false, false, true, true);
    getView().show(fstForm);
    fstForm.reset();
    sndForm.reset();
    trdForm.reset();
    frdForm.reset();
  }

  /*
   * (non-Javadoc)
   * 
   * @see com.gwtplatform.mvp.client.Presenter#revealInParent()
   */
  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire(this, this);
  }

  /**
   * Show frd.
   */
  private void showFrd() {
    getView().show(frdForm);
    getView().setEnabled(true, false, true, false);
  }

  /**
   * Show fst.
   */
  private void showFst() {
    getView().clear();
    getView().show(fstForm);
    getView().setEnabled(false, true, true, true);
  }

  /**
   * Show snd.
   */
  private void showSnd() {
    getView().show(sndForm);
    getView().setEnabled(true, true, true, false);
  }

  /**
   * Show trd.
   */
  private void showTrd() {
    getView().show(trdForm);
    getView().setEnabled(true, false, true, true);
  }

  /*
   * (non-Javadoc)
   * 
   * @see
   * cc.kune.gspace.client.licensewizard.LicenseWizard#start(cc.kune.gspace.
   * client.licensewizard.LicenseChooseCallback)
   */
  @Override
  public void start(final LicenseChooseCallback selectLicenseListener) {
    this.selectLicenseListener = selectLicenseListener;
    reset();
    getView().show();
  }
}
