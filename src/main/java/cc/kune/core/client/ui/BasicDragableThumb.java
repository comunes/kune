package cc.kune.core.client.ui;

import cc.kune.common.client.ui.BasicThumb;
import cc.kune.core.shared.domain.utils.StateToken;

import com.allen_sauer.gwt.dnd.client.HasDragHandle;
import com.google.gwt.user.client.ui.Widget;

public class BasicDragableThumb extends BasicThumb implements HasDragHandle {

  private final StateToken token;

  public BasicDragableThumb(final Object imageRef, final int imgSize, final String text,
      final int textMaxLenght, final boolean crop, final StateToken token) {
    super(imageRef, imgSize, text, textMaxLenght, crop);
    this.token = token;
  }

  @Override
  public Widget getDragHandle() {
    return getImage();
  }

  public StateToken getToken() {
    return token;
  }

}
