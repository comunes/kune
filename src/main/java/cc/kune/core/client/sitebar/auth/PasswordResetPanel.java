package cc.kune.core.client.sitebar.auth;

import cc.kune.common.client.notify.NotifyLevel;
import cc.kune.common.client.notify.NotifyLevelImages;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.ui.MaskWidgetView;
import cc.kune.common.client.ui.dialogs.MessageToolbar;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.auth.SignInAbstractPanel;
import cc.kune.core.client.auth.UserFieldFactory;
import cc.kune.core.client.errors.EmailHashInvalidException;
import cc.kune.core.client.events.StackErrorEvent;
import cc.kune.core.client.rpcservices.UserServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.TokenUtils;
import cc.kune.core.client.ui.DefaultForm;

import com.extjs.gxt.ui.client.widget.form.TextField;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Label;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class PasswordResetPanel extends SignInAbstractPanel {

  public static final String CANCEL_BUTTON_ID = "k-pwd-reset-cancel";
  public static final String ERRMSG = "k-pwd-reset-error";
  public static final String PASSWD_RESET_DIALOG = "k-pwd-reset-diag";
  public static final String PASSWD_RESET_ID = "k-pwd-reset-email";
  public static final String PASSWD2_RESET_DIALOG = "k-pwd-reset-diag";
  public static final String RESET_BUTTON_ID = "k-pwd-reset-reset";
  private final TextField<String> confirmPasswdField;
  private final TextField<String> newPasswdField;
  private String passwordHash;
  private final StateManager stateManager;

  @Inject
  public PasswordResetPanel(final I18nTranslationService i18n, final Session session,
      final MaskWidgetView mask, final NotifyLevelImages images, final EventBus eventbus,
      final UserFieldFactory userFieldFactory, final Provider<UserServiceAsync> userService,
      final StateManager stateManager) {
    super(PASSWD_RESET_DIALOG, mask, i18n, i18n.t("Reset your password"), true, true, true, "",
        i18n.t("Reset your password"), RESET_BUTTON_ID, i18n.tWithNT("Cancel", "used in button"),
        CANCEL_BUTTON_ID, images, ERRMSG, 1);
    this.stateManager = stateManager;
    final DefaultForm form = new DefaultForm();
    final Label desc = new Label(i18n.t("Enter your new password below."));
    desc.setWidth("300px");
    newPasswdField = userFieldFactory.createUserPasswd(PASSWD_RESET_ID, i18n.t("New password"));
    newPasswdField.setTabIndex(1);
    confirmPasswdField = userFieldFactory.createUserPasswd(PASSWD_RESET_ID, i18n.t("Confirm"));
    confirmPasswdField.setTabIndex(1);

    messageErrorBar = new MessageToolbar(images, errorLabelId);
    form.add(newPasswdField);
    form.add(confirmPasswdField);
    form.add(messageErrorBar);
    super.getFirstBtn().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        final String newPass = newPasswdField.getValue();
        final String confirmPass = confirmPasswdField.getValue();
        if (form.isValid()) {
          if (!newPass.equals(confirmPass)) {
            messageErrorBar.setErrorMessage(i18n.t("Passwords are different"), NotifyLevel.error);
            messageErrorBar.setVisible(true);
          } else {
            userService.get().resetPassword(getPasswordHash(), newPasswdField.getValue(),
                new AsyncCallback<Void>() {
                  @Override
                  public void onFailure(final Throwable caught) {
                    if (caught instanceof EmailHashInvalidException) {
                      PasswordResetPanel.this.setErrorMessage(i18n.t("Invalid confirmation code"),
                          NotifyLevel.error);
                    } else {
                      PasswordResetPanel.this.setErrorMessage(
                          i18n.t("Other error trying to reset your password"), NotifyLevel.error);
                    }
                    StackErrorEvent.fire(eventbus, caught);
                    PasswordResetPanel.this.messageErrorBar.setVisible(true);
                  }

                  @Override
                  public void onSuccess(final Void result) {
                    NotifyUser.info(i18n.t("Your password has been reset. Sign in"));
                    hide();
                    stateManager.gotoHistoryToken(TokenUtils.addRedirect(SiteTokens.HOME,
                        SiteTokens.SIGN_IN));
                  }
                });
          }
        }
      }
    });
    super.getSecondBtn().addClickHandler(new ClickHandler() {

      @Override
      public void onClick(final ClickEvent event) {
        hide();
      }
    });
    super.getInnerPanel().add(desc);
    super.getInnerPanel().add(form.getFormPanel());
  }

  public String getPasswordHash() {
    return passwordHash;
  }

  @Override
  public void hide() {
    super.hide();
    super.messageErrorBar.hideErrorMessage();
    newPasswdField.clear();
    confirmPasswdField.clear();
    stateManager.gotoHistoryToken(SiteTokens.HOME);
  }

  public void setPasswordHash(final String passwordHash) {
    this.passwordHash = passwordHash;
  }

  @Override
  public void show() {
    super.show();
  }
}
