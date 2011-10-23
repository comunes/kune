package cc.kune.common.client.ui.dialogs.tabbed;

import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.client.ui.IconLabel;
import cc.kune.common.client.utils.TextUtils;

import com.google.gwt.resources.client.ImageResource;

public class TabTitleGenerator {

  private static String format(final String title, final int maxLength) {
    return TextUtils.ellipsis(title, maxLength);
  }

  public static IconLabel generate(final ImageResource res, final String title) {
    final IconLabel tabTitle = new IconLabel(res, title);
    return tabTitle;
  }

  public static IconLabel generate(final ImageResource res, final String title, final int maxLength) {
    final IconLabel tabTitle = new IconLabel(res, format(title, maxLength));
    setTooltip(title, maxLength, tabTitle);
    return tabTitle;
  }

  public static void setText(final IconLabel tabTitle, final String title, final int maxLength) {
    tabTitle.setText(format(title, maxLength));
    setTooltip(title, maxLength, tabTitle);
  }

  private static void setTooltip(final String title, final int maxLength, final IconLabel tabTitle) {
    if (title.length() > maxLength) {
      Tooltip.to(tabTitle, title);
    }
  }

}
