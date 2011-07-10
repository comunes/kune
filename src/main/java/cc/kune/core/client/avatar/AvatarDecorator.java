package cc.kune.core.client.avatar;

import com.google.gwt.user.client.ui.IsWidget;

public interface AvatarDecorator {

  void setDecoratorVisible(boolean visible);

  void setItem(String name);

  void setWidget(IsWidget widget);

}
