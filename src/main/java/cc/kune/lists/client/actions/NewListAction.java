package cc.kune.lists.client.actions;

import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.ui.dialogs.PromptTopDialog;
import cc.kune.common.client.ui.dialogs.PromptTopDialog.Builder;
import cc.kune.core.client.actions.RolAction;
import cc.kune.core.client.rpcservices.AsyncCallbackSimple;
import cc.kune.core.client.state.ContentCache;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.AccessRolDTO;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.viewers.FolderViewerPresenter;
import cc.kune.lists.client.rpc.ListsServiceAsync;
import cc.kune.lists.shared.ListsConstants;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.inject.Inject;
import com.google.inject.Provider;
import com.google.inject.Singleton;

@Singleton
public class NewListAction extends RolAction {
  private static final String CANCEL_ID = "k-nla-cancel";
  private static final String CREATE_ID = "k-nla-create";
  private static final String ID = "k-nla-dialog";
  private static final String TEXTBOX_ID = "k-nla-textbox";

  private final ContentCache cache;
  private final FolderViewerPresenter folderViewer;
  private final I18nTranslationService i18n;
  private final Provider<ListsServiceAsync> listsService;
  private final Session session;
  private final StateManager stateManager;

  @Inject
  public NewListAction(final Session session, final StateManager stateManager,
      final I18nTranslationService i18n, final Provider<ListsServiceAsync> listsService,
      final ContentCache cache, final FolderViewerPresenter folderViewer) {
    super(AccessRolDTO.Administrator, true);
    this.session = session;
    this.stateManager = stateManager;
    this.i18n = i18n;
    this.listsService = listsService;
    this.cache = cache;
    this.folderViewer = folderViewer;
  }

  @Override
  public void actionPerformed(final ActionEvent event) {
    final Builder builder = new PromptTopDialog.Builder(ID, i18n.t("Name of the new list?"), false,
        true, i18n.getDirection());
    builder.width("300px").height("50px").firstButtonTitle(i18n.t("Create")).sndButtonTitle(
        i18n.t("Cancel")).firstButtonId(CREATE_ID).sndButtonId(CANCEL_ID).width(270);
    builder.textboxId(TEXTBOX_ID);
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
          diag.hide();
          listsService.get().createList(session.getUserHash(), session.getCurrentStateToken(),
              diag.getTextFieldValue(), ListsConstants.TYPE_LIST, true,
              new AsyncCallbackSimple<StateContainerDTO>() {
                @Override
                public void onSuccess(final StateContainerDTO state) {
                  stateManager.setRetrievedStateAndGo(state);
                  NotifyUser.hideProgress();

                  NotifyUser.info(i18n.t("List created"));
                  folderViewer.highlightTitle();
                }
              });
          cache.removeContent(session.getCurrentStateToken());
        }
      }
    });

  }

}
