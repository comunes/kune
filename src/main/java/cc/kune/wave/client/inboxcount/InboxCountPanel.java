package cc.kune.wave.client.inboxcount;

import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.client.ui.BlinkAnimation;
import cc.kune.core.client.sitebar.spaces.Space;
import cc.kune.core.client.sitebar.spaces.SpaceSelectEvent;
import cc.kune.core.shared.i18n.I18nTranslationService;
import cc.kune.wave.client.inboxcount.InboxCountPresenter.InboxCountView;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.user.client.ui.InlineLabel;
import com.google.gwt.user.client.ui.PopupPanel;
import com.google.inject.Inject;

public class InboxCountPanel extends PopupPanel implements InboxCountView {

  private final BlinkAnimation blinkAnimation;
  private final InlineLabel count;
  private final I18nTranslationService i18n;
  private final Tooltip tooltip;

  @Inject
  public InboxCountPanel(final I18nTranslationService i18n, final EventBus eventBus) {
    this.i18n = i18n;
    count = new InlineLabel();
    setStylePrimaryName("k-space-sel-inbox-count");
    super.setWidget(count);
    blinkAnimation = new BlinkAnimation(this, 400);
    tooltip = Tooltip.to(this, "Nothing");
    addDomHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        SpaceSelectEvent.fire(eventBus, Space.userSpace, true);
      }
    }, ClickEvent.getType());

  }

  @Override
  public void blink() {
    blinkAnimation.animate(4);
  }

  @Override
  public void setTotal(final int total) {
    count.setText(String.valueOf(total));
    tooltip.setText(total == 1 ? i18n.t("One recent conversation unread") : i18n.t(
        "[%d] recent conversations unread", total));
  }

  @Override
  public void showCount(final boolean show) {
    if (show) {
      super.setPopupPositionAndShow(new PositionCallback() {
        @Override
        public void setPosition(final int offsetWidth, final int offsetHeight) {
          // user inbox icon is at 38px to the right, then we center the popup
          // down the icon
          setPopupPosition(38 - offsetWidth / 2, 20);
        }
      });
    } else {
      super.hide();
    }
  }
}
