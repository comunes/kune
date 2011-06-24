package cc.kune.chat.client.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.ui.descrip.ButtonDescriptor;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.ui.dialogs.PromptTopDialog;
import cc.kune.common.client.ui.dialogs.PromptTopDialog.Builder;
import cc.kune.common.client.utils.TextUtils;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.resources.CoreMessages;
import cc.kune.core.client.resources.nav.NavResources;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class NewRoomBtn extends ButtonDescriptor {

  public static class NewRoomAction extends RolAction {

    private static final String CANCEL_ID = "k-nrbt-cancel";
    private static final String CREATE_ID = "k-nrbt-create";
    private static final String ID = "k-nrbt-dialog";
    private static final String TEXTBOX_ID = "k-nrbt-textbox";
    private final Provider<ContentServiceAsync> contentService;
    private final I18nTranslationService i18n;
    private final Session session;
    private final StateManager stateManager;

    @Inject
    public NewRoomAction(final Session session, final StateManager stateManager,
        final I18nTranslationService i18n, final Provider<ContentServiceAsync> contentService) {
      super(AccessRolDTO.Administrator, true);
      this.session = session;
      this.stateManager = stateManager;
      this.i18n = i18n;
      this.contentService = contentService;
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
      final Builder builder = new PromptTopDialog.Builder(ID, "Name of the new chatroom?", false, true);
      builder.width("200px").height("50px").firstButtonTitle(i18n.t("Create")).sndButtonTitle(
          i18n.t("Cancel")).firstButtonId(CREATE_ID).sndButtonId(CANCEL_ID);
      builder.regex(TextUtils.UNIX_NAME).regexText(
          i18n.t("The name must contain only characters, numbers and dashes")).textboxId(TEXTBOX_ID);
      builder.minLength(3).maxLength(15).allowBlank(false).minLengthText(
          CoreMessages.FIELD_MUST_BE_BETWEEN_3_AND_15_NO_CHARS).maxLengthText(
          CoreMessages.FIELD_MUST_BE_BETWEEN_3_AND_15_NO_CHARS);
      final PromptTopDialog diag = builder.build();
      diag.showCentered();
      diag.focusOnTextBox();
      diag.getSecondBtn().addClickHandler(new ClickHandler() {
        @Override
        public void onClick(final ClickEvent event) {
          diag.hide();
        }
      });
      diag.getFirstBtn().addClickHandler(new ClickHandler() {
        @Override
        public void onClick(final ClickEvent event) {
          if (diag.isValid()) {
            NotifyUser.showProgressProcessing();
            final String groupShortName = session.getCurrentState().getGroup().getShortName();
            contentService.get().addRoom(session.getUserHash(),
                session.getContainerState().getRootContainer().getStateToken(),
                groupShortName + "-" + diag.getText(), new AsyncCallbackSimple<StateContainerDTO>() {
                  @Override
                  public void onSuccess(final StateContainerDTO state) {
                    stateManager.setRetrievedStateAndGo(state);
                    NotifyUser.hideProgress();
                    NotifyUser.info(i18n.t("Chatroom created"));
                  }
                });
            diag.hide();
          }
        }
      });
    }
  }

  @Inject
  public NewRoomBtn(final I18nTranslationService i18n, final NewRoomAction action, final NavResources res) {
    super(action);
    this.withText(i18n.t("New room")).withToolTip(i18n.t("Create a new chat room")).withStyles(
        "k-def-docbtn, k-fl").withIcon(res.roomAdd());
  }

}
