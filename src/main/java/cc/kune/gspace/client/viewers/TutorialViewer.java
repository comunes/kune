package cc.kune.gspace.client.viewers;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Frame;
import com.google.gwt.user.client.ui.Widget;

public class TutorialViewer extends Composite {

  interface TutorialViewerUiBinder extends UiBinder<Widget, TutorialViewer> {
  }

  private static TutorialViewerUiBinder uiBinder = GWT.create(TutorialViewerUiBinder.class);

  @UiField
  Frame frame;

  public TutorialViewer() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  public Widget setUrl(final String url) {
    frame.setUrl(url);
    return this;
  }

}
