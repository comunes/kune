package cc.kune.bootstrap.client;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

public interface BootstrapBundle extends ClientBundle {

  static final BootstrapBundle INSTANCE = GWT.create(BootstrapBundle.class);

  @Source("../resource/js/theme.js")
  TextResource theme();
}
