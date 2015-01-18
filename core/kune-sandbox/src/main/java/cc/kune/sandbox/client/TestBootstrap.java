package cc.kune.sandbox.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;

public class TestBootstrap extends Composite {

  interface TestBootstrapUiBinder extends UiBinder<Widget, TestBootstrap> {
  }

  private static TestBootstrapUiBinder uiBinder = GWT.create(TestBootstrapUiBinder.class);

  public TestBootstrap() {
    initWidget(uiBinder.createAndBindUi(this));
  }

}
