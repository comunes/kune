package org.ourproject.massmob.client;

import org.ourproject.massmob.client.actions.OptionsActions;
import org.ourproject.massmob.client.ui.MassmobMainPanel;
import org.ourproject.massmob.client.ui.date.DatePanel;
import org.ourproject.massmob.client.ui.date.DatePresenter;
import org.ourproject.massmob.client.ui.img.Images;

import com.google.gwt.inject.client.AbstractGinModule;
import com.google.inject.Singleton;

import cc.kune.common.client.actions.gwtui.GwtGuiProvider;
import cc.kune.common.client.actions.ui.DefaultGuiProvider;
import cc.kune.common.client.actions.ui.GuiProvider;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.events.EventBusInstance;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.notify.SimpleUserNotifierPopup;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.i18n.I18nTranslationServiceMocked;

public class MassmobGinModule extends AbstractGinModule {

  @Override
  protected void configure() {
    bind(I18nTranslationService.class).to(I18nTranslationServiceMocked.class).in(Singleton.class);
    bind(I18n.class).in(Singleton.class);
    requestStaticInjection(I18n.class);
    requestStaticInjection(EventBusInstance.class);
    bind(SimpleUserNotifierPopup.class).asEagerSingleton();
    requestStaticInjection(NotifyUser.class);
    // bind(EventBus.class).to(EventBusWithLogging.class).in(Singleton.class);
    bind(MassmobMainPanel.class).in(Singleton.class);
    bind(WaveUtils.class).in(Singleton.class);
    bind(StateManager.class).in(Singleton.class);
    bind(Images.class).in(Singleton.class);
    bind(UserSelfPreferences.class).to(CookiesUserSelfPreferences.class).in(Singleton.class);
    bind(DatePresenter.DateView.class).to(DatePanel.class);
    bind(DatePresenter.class);
    bind(GuiProvider.class).to(DefaultGuiProvider.class).in(Singleton.class);
    bind(GwtGuiProvider.class).in(Singleton.class);
    bind(GuiActionDescCollection.class).in(Singleton.class);
    bind(OptionsActions.class).in(Singleton.class);
  }

}
