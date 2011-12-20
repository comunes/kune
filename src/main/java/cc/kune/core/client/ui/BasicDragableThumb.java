package cc.kune.core.client.ui;

import cc.kune.common.client.ui.BasicThumb;

import com.allen_sauer.gwt.dnd.client.HasDragHandle;
import com.google.gwt.user.client.ui.Widget;

public class BasicDragableThumb extends BasicThumb implements HasDragHandle {

  public BasicDragableThumb(final Object imageRef, final int imgSize, final String text,
      final int textMaxLenght, final boolean crop) {
    super(imageRef, imgSize, text, textMaxLenght, crop);
  }

  @Override
  public Widget getDragHandle() {
    return getImage();
  }

}
