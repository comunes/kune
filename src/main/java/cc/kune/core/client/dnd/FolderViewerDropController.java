package cc.kune.core.client.dnd;

import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.utils.TextUtils;
import cc.kune.core.client.errors.ErrorHandler;
import cc.kune.core.client.rpcservices.ContentServiceAsync;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.StateContainerDTO;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.gspace.client.viewers.FolderItemWidget;

import com.allen_sauer.gwt.dnd.client.DragContext;
import com.allen_sauer.gwt.dnd.client.VetoDragException;
import com.allen_sauer.gwt.dnd.client.drop.SimpleDropController;
import com.google.gwt.event.logical.shared.AttachEvent;
import com.google.gwt.event.logical.shared.AttachEvent.Handler;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;

/**
 * @author vjrj
 *
 */
/**
 * @author vjrj
 * 
 */
public class FolderViewerDropController implements DropTarget {

  private final ContentServiceAsync contentService;
  private final KuneDragController dragController;
  private SimpleDropController dropController;
  private final ErrorHandler erroHandler;
  private final I18nTranslationService i18n;
  private final Session session;
  private final StateManager stateManager;
  private Object target;

  @Inject
  public FolderViewerDropController(final KuneDragController dragController,
      final ContentServiceAsync contentService, final Session session, final StateManager stateManager,
      final ErrorHandler erroHandler, final I18nTranslationService i18n) {
    this.dragController = dragController;
    this.contentService = contentService;
    this.session = session;
    this.stateManager = stateManager;
    this.erroHandler = erroHandler;
    this.i18n = i18n;
  }

  @Override
  public void init(final Widget dropTarget) {
    dropController = new SimpleDropController(dropTarget) {

      @Override
      public void onDrop(final DragContext context) {
        super.onDrop(context);
        for (final Widget widget : context.selectedWidgets) {
          if (widget instanceof FolderItemWidget) {
            getDropTarget().removeStyleName("k-drop-allowed-hover");
            NotifyUser.showProgress(i18n.t("Moving"));
            if (target != null) {
              final StateToken destToken = (StateToken) target;
              widget.removeFromParent();
              contentService.moveContent(session.getUserHash(), ((FolderItemWidget) widget).getToken(),
                  destToken, new AsyncCallback<StateContainerDTO>() {
                    @Override
                    public void onFailure(final Throwable caught) {
                      erroHandler.process(caught);
                      stateManager.refreshCurrentState();
                      NotifyUser.hideProgress();
                    }

                    @Override
                    public void onSuccess(final StateContainerDTO result) {
                      NotifyUser.hideProgress();
                    }
                  });
            } else {
              NotifyUser.info(i18n.t(TextUtils.IN_DEVELOPMENT));
              NotifyUser.hideProgress();
            }
          }
        }
      }

      @Override
      public void onEnter(final DragContext context) {
        super.onEnter(context);
        for (final Widget widget : context.selectedWidgets) {
          if (widget instanceof FolderItemWidget) {
            getDropTarget().addStyleName("k-drop-allowed-hover");
          }
        }
      }

      @Override
      public void onLeave(final DragContext context) {
        super.onLeave(context);
        for (final Widget widget : context.selectedWidgets) {
          if (widget instanceof FolderItemWidget) {
            getDropTarget().removeStyleName("k-drop-allowed-hover");
          }
        }
      }

      @Override
      public void onPreviewDrop(final DragContext context) throws VetoDragException {
        for (final Widget widget : context.selectedWidgets) {
          if (widget instanceof FolderItemWidget) {
            getDropTarget().removeStyleName("k-drop-allowed-hover");
          } else {
            throw new VetoDragException();
          }
        }
        super.onPreviewDrop(context);
      }
    };

    dropTarget.addStyleName("k-drop-allowed");
    if (dropTarget.isAttached()) {
      dragController.registerDropController(dropController);
    }
    dropTarget.addAttachHandler(new Handler() {
      @Override
      public void onAttachOrDetach(final AttachEvent event) {
        if (!event.isAttached()) {
          dragController.unregisterDropController(dropController);
        } else {
          dragController.registerDropController(dropController);
        }
      }
    });
  }

  public void setTarget(final Object target) {
    this.target = target;
  }

}
