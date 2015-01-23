package cc.kune.bootstrap.client.smartmenus;

import com.google.gwt.core.client.GWT;
import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.TextResource;

public interface SmartMenusBundle extends ClientBundle {

  static final SmartMenusBundle INSTANCE = GWT.create(SmartMenusBundle.class);

  // @Source("resource/jquery.smartmenus.min.js")
  @Source("resource/jquery.smartmenus.js")
  TextResource smartmenus();

  // Warning: min.js has not the same modified options (subIndicators, etc)
  // @Source("resource/addons/bootstrap/jquery.smartmenus.bootstrap.min.js")
  @Source("resource/addons/bootstrap/jquery.smartmenus.bootstrap.js")
  TextResource smartmenusBootstrap();
}
