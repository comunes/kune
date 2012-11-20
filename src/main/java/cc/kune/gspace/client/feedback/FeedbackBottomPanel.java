/*
 *
 * Copyright (C) 2007-2012 The kune development team (see CREDITS for details)
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
package cc.kune.gspace.client.feedback;

import cc.kune.common.client.notify.NotifyLevel;
import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.client.ui.AbstractAtBorderPopupPanel;
import cc.kune.common.client.ui.IconLabel;
import cc.kune.common.shared.i18n.I18n;
import cc.kune.core.client.auth.SignIn;
import cc.kune.core.client.i18n.I18nUITranslationService;
import cc.kune.core.client.resources.iconic.IconicResources;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.SiteTokens;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.client.state.TokenUtils;
import cc.kune.core.client.ui.dialogs.PromptTopDialog;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class FeedbackBottomPanel extends AbstractAtBorderPopupPanel {
  public static final String GIVE_US_FEEDBACK_ID = "k-give-feed-diag-id";
  public static final String GIVE_US_FEEDBACK_SEND_BTN_ID = "k-give-feed-diag-send-btn-id";
  public static final String ID = "k-give-feedback-btn";

  private PromptTopDialog diag;

  @Inject
  public FeedbackBottomPanel(final IconicResources res, final Session session,
      final Provider<SignIn> signIn, final StateManager stateManager,
      final I18nUITranslationService i18n, final Provider<ContentServiceAsync> contentService) {
    super(false, false);
    final IconLabel btn = new IconLabel(res.refresh(), i18n.t("Give us feedback!"));
    Tooltip.to(
        btn,
        I18n.t("Write us with some feedback for help us to improve the services on [%s]",
            i18n.getSiteCommonName()));
    btn.ensureDebugId(ID);
    setStyleName("k-feedback-btn");
    addStyleName("k-popup-bottom-centered");
    // addStyleName("k-top-5corners");
    add(btn);
    setCenterPositionImpl();
    btn.getFocus().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        if (session.isLogged()) {
          final String title = i18n.t("Feedback of [%s] about [%s]",
              session.getCurrentUser().getShortName(), i18n.getSiteCommonName());
          final PromptTopDialog.Builder builder = new PromptTopDialog.Builder(GIVE_US_FEEDBACK_ID,
              title, false, true, i18n.getDirection(), new PromptTopDialog.OnEnter() {
                @Override
                public void onEnter() {
                  // We do nothing to allow multiple lines
                }
              });
          builder.emptyTextField(i18n.t("Please, edit and write here your feedback about this tool. "
              + "We find your comments very useful, especially "
              + "if you mention the things you would like to see, " + "your personal/group needs, etc."));
          builder.promptWidth(300).promptLines(6).width("320px").height("120px").firstButtonId(
              GIVE_US_FEEDBACK_SEND_BTN_ID).firstButtonTitle(i18n.t("Send")).sndButtonTitle(
              i18n.t("Cancel"));
          diag = builder.build();
          diag.getFirstBtn().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
              diag.hide();
              contentService.get().sendFeedback(session.getUserHash(), title, diag.getTextFieldValue(),
                  new AsyncCallbackSimple<String>() {
                    @Override
                    public void onSuccess(final String url) {
                      stateManager.gotoHistoryToken(url);
                    }
                  });
            }
          });
          diag.getSecondBtn().addClickHandler(new ClickHandler() {
            @Override
            public void onClick(final ClickEvent event) {
              diag.hide();
            }
          });
          diag.showCentered();
        } else {
          signIn.get().setErrorMessage(i18n.t("Sign in or create an account to give us feedback"),
              NotifyLevel.info);
          stateManager.gotoHistoryToken(TokenUtils.addRedirect(SiteTokens.SIGN_IN,
              session.getCurrentStateToken().toString()));
        }

      }
    });

  }

  @Override
  protected void setCenterPositionImpl() {
    setPopupPositionAndShow(new PopupPanel.PositionCallback() {
      @Override
      public void setPosition(final int offsetWidth, final int offsetHeight) {
        final Widget widget = getWidget();
        final int x = 60;
        final int y = Window.getClientHeight() - (widget != null ? getWidget().getOffsetHeight() : 0)
            - 23;
        FeedbackBottomPanel.this.setPopupPosition(x, y);
      }
    });
  }

}
