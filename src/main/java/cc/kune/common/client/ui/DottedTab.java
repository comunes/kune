package cc.kune.common.client.ui;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class DottedTab extends Composite {

  interface DottedTabUiBinder extends UiBinder<Widget, DottedTab> {
  }

  private static DottedTabUiBinder uiBinder = GWT.create(DottedTabUiBinder.class);

  public DottedTab() {
    initWidget(uiBinder.createAndBindUi(this));
  }

}
