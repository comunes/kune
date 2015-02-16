package cc.kune.common.client.notify;

import org.gwtbootstrap3.client.ui.Container;
import org.gwtbootstrap3.client.ui.constants.IconSize;
import org.gwtbootstrap3.client.ui.constants.IconType;
import org.gwtbootstrap3.client.ui.constants.Styles;
import org.gwtbootstrap3.extras.growl.client.ui.Growl;
import org.gwtbootstrap3.extras.growl.client.ui.GrowlHelper;
import org.gwtbootstrap3.extras.growl.client.ui.GrowlOptions;
import org.gwtbootstrap3.extras.growl.client.ui.GrowlPosition;
import org.gwtbootstrap3.extras.growl.client.ui.GrowlTemplate;

import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.user.client.ui.HTML;
import com.google.gwt.user.client.ui.Image;
import com.google.inject.Inject;
import com.google.inject.Singleton;
import com.google.web.bindery.event.shared.EventBus;

/**
 * The Class UserNotifierGrowl.
 *
 * http://bootstrap-growl.remabledesigns.com/
 */
@Singleton
public class UserNotifierGrowl {
  /** The Constant AVATAR_SIZE. */
  private static final String AVATAR_SIZE = "40px";
  private static final String SEPARATOR = " ";

  /**
   * Instantiates a new user notifier growl.
   *
   * @param eventBus
   *          the event bus
   */
  @Inject
  public UserNotifierGrowl(final EventBus eventBus) {

    eventBus.addHandler(UserNotifyEvent.getType(), new UserNotifyEvent.UserNotifyHandler() {

      @Override
      public void onUserNotify(final UserNotifyEvent event) {

        final GrowlOptions options = GrowlHelper.getNewOptions();

        final GrowlPosition position = GrowlHelper.getNewPosition();
        position.setCenter();
        position.setTop(false);
        options.setGrowlPosition(position);

        final GrowlTemplate gt = GrowlHelper.getNewTemplate();
        gt.setTitleDivider("<br>");
        options.setTemplateObject(gt);

        // TODO event.getId()

        final Boolean closeable = event.getCloseable();
        if (closeable) {
          options.setDelay(0);
        }
        options.setAllowDismiss(closeable);
        options.setPauseOnMouseOver(true);

        final String message = event.getMessage();
        String icon = "";
        final String iconStyleBase = Styles.FONT_AWESOME_BASE + SEPARATOR + IconSize.TIMES2.getCssName()
            + " growl-icon-margin ";

        final NotifyLevel level = event.getLevel();
        switch (level) {
        case error:
          options.setDangerType();
          icon = iconStyleBase + IconType.EXCLAMATION_CIRCLE.getCssName();
          break;
        case avatar:
          final ClickHandler clickHandler = event.getClickHandler();
          final Container container = new Container();
          container.setFluid(true);
          final Image avatar = new Image(event.getLevel().getUrl());
          avatar.setSize(AVATAR_SIZE, AVATAR_SIZE);
          avatar.addStyleName("k-fl");
          avatar.addStyleName("growl-icon-margin");
          container.add(avatar);
          container.add(new HTML(message));
          container.addDomHandler(clickHandler, ClickEvent.getType());
          Growl.growl(container.getElement().getInnerHTML(), options);
          return;
        case veryImportant:
        case important:
          options.setWarningType();
          icon = iconStyleBase + IconType.WARNING.getCssName();
          break;
        case success:
          options.setSuccessType();
          icon = iconStyleBase + IconType.CHECK_CIRCLE.getCssName();
          break;
        case info:
          options.setInfoType();
          icon = iconStyleBase + IconType.INFO_CIRCLE.getCssName();
          break;
        case log:
          // Do nothing with this level
          return;
        default:
          break;
        }

        Growl.growl(event.getTitle(), message, icon, options);
      }
    });
  }
}
