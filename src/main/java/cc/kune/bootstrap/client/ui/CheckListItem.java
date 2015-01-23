package cc.kune.bootstrap.client.ui;

import org.gwtbootstrap3.client.ui.constants.IconType;

public class CheckListItem extends AbstractCheckListItem {

  private static final IconType CHECKED_ICON = IconType.CHECK_SQUARE_O;
  private static final IconType UNCHECKED_ICON = IconType.SQUARE_O;

  public CheckListItem() {
    super(CHECKED_ICON, UNCHECKED_ICON);
  }

  public CheckListItem(final String text) {
    super(CHECKED_ICON, UNCHECKED_ICON);
    setText(text);
  }
}
