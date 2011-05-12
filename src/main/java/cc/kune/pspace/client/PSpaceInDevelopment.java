package cc.kune.pspace.client;

import cc.kune.core.client.resources.CoreResources;
import cc.kune.core.client.services.FileDownloadUtils;
import cc.kune.core.client.state.GroupChangedEvent;
import cc.kune.core.client.state.GroupChangedEvent.GroupChangedHandler;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.domain.utils.StateToken;
import cc.kune.core.shared.dto.GroupDTO;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.inject.Provider;

public class PSpaceInDevelopment extends Composite {

  interface PSpaceInDevelopmentUiBinder extends UiBinder<Widget, PSpaceInDevelopment> {
  }

  private static PSpaceInDevelopmentUiBinder uiBinder = GWT.create(PSpaceInDevelopmentUiBinder.class);
  @UiField
  FlowPanel container;
  @UiField
  FlowPanel contentPanel;
  private final Provider<FileDownloadUtils> downloadProvider;
  @UiField
  Image entityLogo;
  @UiField
  Label entityName;
  @UiField
  FlowPanel headerPanel;
  private final CoreResources images;
  @UiField
  FlowPanel mainPanel;
  @UiField
  FlowPanel photoPanel;

  @Inject
  public PSpaceInDevelopment(final StateManager stateManager, final CoreResources images,
      final Session session, final Provider<FileDownloadUtils> downloadProvider) {
    this.images = images;
    this.downloadProvider = downloadProvider;
    initWidget(uiBinder.createAndBindUi(this));
    stateManager.onGroupChanged(true, new GroupChangedHandler() {
      @Override
      public void onGroupChanged(final GroupChangedEvent event) {
        setGroupLogo(session.getCurrentState().getGroup());
      }
    });
  }

  void setGroupLogo(final GroupDTO group) {
    if (group.hasLogo()) {
      setLogoText(group.getLongName());
      setLogoImage(group.getStateToken());
      setLogoImageVisible(true);
    } else {
      setLogoText(group.getLongName());
      if (group.isPersonal()) {
        showDefUserLogo();
        setLogoImageVisible(true);
      } else {
        setLogoImageVisible(false);
      }
    }
  }

  private void setLogoImage(final StateToken stateToken) {
    entityLogo.setUrl(downloadProvider.get().getLogoImageUrl(stateToken));
  }

  private void setLogoImageVisible(final boolean visible) {
    entityLogo.setVisible(visible);
  }

  private void setLogoText(final String longName) {
    entityName.setText(longName);
  }

  public void showDefUserLogo() {
    (AbstractImagePrototype.create(images.unknown60())).applyTo(entityLogo);
  }
}
