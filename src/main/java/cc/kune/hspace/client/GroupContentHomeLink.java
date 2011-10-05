package cc.kune.hspace.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Hyperlink;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Widget;

public class GroupContentHomeLink extends Composite {

  interface GroupContentHomeLinkUiBinder extends UiBinder<Widget, GroupContentHomeLink> {
  }

  private static GroupContentHomeLinkUiBinder uiBinder = GWT.create(GroupContentHomeLinkUiBinder.class);

  @UiField
  Image icon;

  @UiField
  Hyperlink link;

  public GroupContentHomeLink() {
    initWidget(uiBinder.createAndBindUi(this));
  }

  public void setValues(final String logoImageUrl, final String text, final String historyToken) {
    icon.setUrl(logoImageUrl);
    link.setText(text);
    link.setTargetHistoryToken(historyToken);
  }

}
