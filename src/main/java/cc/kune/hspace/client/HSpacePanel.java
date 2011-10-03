package cc.kune.hspace.client;

import cc.kune.hspace.client.HSpacePresenter.HSpaceView;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Widget;
import com.gwtplatform.mvp.client.ViewImpl;

public class HSpacePanel extends ViewImpl implements HSpaceView {

  interface HSpacePanelUiBinder extends UiBinder<Widget, HSpacePanel> {
  }

  private static HSpacePanelUiBinder uiBinder = GWT.create(HSpacePanelUiBinder.class);

  private final Widget widget;

  public HSpacePanel() {
    widget = uiBinder.createAndBindUi(this);
  }

  @Override
  public Widget asWidget() {
    return widget;
  }

}
