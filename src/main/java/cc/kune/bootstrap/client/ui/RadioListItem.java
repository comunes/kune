package cc.kune.bootstrap.client.ui;

import org.gwtbootstrap3.client.ui.constants.IconType;

public class RadioListItem extends AbstractCheckListItem {

  private static final IconType CHECKED_ICON = IconType.DOT_CIRCLE_O;
  private static final IconType UNCHECKED_ICON = IconType.CIRCLE_O;

  public RadioListItem() {
    super(CHECKED_ICON, UNCHECKED_ICON);
  }

  public RadioListItem(final String text) {
    super(CHECKED_ICON, UNCHECKED_ICON);
    setText(text);
  }
}
