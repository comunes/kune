package org.ourproject.massmob.client.actions;

import org.ourproject.massmob.client.CustomConstants;
import org.ourproject.massmob.client.StateManager;
import org.ourproject.massmob.client.actions.PromptTopDialog.Builder;
import org.ourproject.massmob.client.actions.PromptTopDialog.OnEnter;
import org.ourproject.massmob.client.ui.ChangeBackgroundEvent;
import org.ourproject.massmob.client.ui.img.Images;

import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.thezukunft.wave.connector.GadgetUpdateEvent;
import com.thezukunft.wave.connector.StateUpdateEvent;
import com.thezukunft.wave.connector.StateUpdateEventHandler;
import com.thezukunft.wave.connector.Wave;

import cc.kune.common.client.actions.AbstractExtendedAction;
import cc.kune.common.client.actions.Action;
import cc.kune.common.client.actions.ActionEvent;
import cc.kune.common.client.actions.PropertyChangeEvent;
import cc.kune.common.client.actions.PropertyChangeListener;
import cc.kune.common.client.actions.ui.descrip.GuiActionDescCollection;
import cc.kune.common.client.actions.ui.descrip.MenuDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuItemDescriptor;
import cc.kune.common.client.actions.ui.descrip.MenuSeparatorDescriptor;
import cc.kune.common.client.resources.CommonResources;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.common.shared.utils.TextUtils;

public class OptionsActions {
  public abstract class AbstractWith extends AbstractExtendedAction {
    private final String constant;
    private final EventBus eventBus;
    private final Wave wave;
    private boolean with;
    private final String withoutText;
    private final String withText;

    public AbstractWith(final EventBus eventBus, final Wave wave, final String constant,
        final String withText, final String withoutText) {
      this.eventBus = eventBus;
      this.constant = constant;
      this.wave = wave;
      this.withText = withText;
      this.withoutText = withoutText;
      eventBus.addHandler(StateUpdateEvent.TYPE, new StateUpdateEventHandler() {
        @Override
        public void onUpdate(final StateUpdateEvent event) {
          update();
        }
      });
      update();
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
      wave.getState().submitValue(constant, Boolean.toString(!with));
    }

    private void setTexts() {
      if (with) {
        super.putValue(Action.NAME, withoutText);
      } else {
        super.putValue(Action.NAME, withText);
      }
    }

    private void update() {
      final String withState = wave.getState().get(constant);
      if (withState == null || Boolean.parseBoolean(withState)) {
        with = true;
      } else {
        with = false;
      }
      eventBus.fireEvent(new WithFieldEvent(with, constant));
      setTexts();
    }
  }
  public class ChangeMapServer extends AbstractExtendedAction {
    private final StateManager stateManager;

    public ChangeMapServer(final EventBus eventBus, final StateManager stateManager) {
      this.stateManager = stateManager;
      eventBus.addHandler(StateUpdateEvent.TYPE, new StateUpdateEventHandler() {
        @Override
        public void onUpdate(final StateUpdateEvent event) {
          update();
        }
      });
      update();
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
      stateManager.setValue(CustomConstants.USEGMAPS, Boolean.toString(!getCurrent()));
    }

    private boolean getCurrent() {
      return Boolean.parseBoolean(stateManager.getValue(CustomConstants.USEGMAPS));
    }

    private void update() {
      final boolean value = getCurrent();
      if (value) {
        super.putValue(Action.NAME, "Use Openstreet Maps");
      } else {
        super.putValue(Action.NAME, "Use Google Maps");
      }
    }
  }

  public class SetBack extends AbstractExtendedAction {

    public static final String OK_ID = "k-bck-ok-btn";
    private static final String SET_THE_IMAGE_BACKGROUND_URL = "Set the image background URL";
    private PromptTopDialog dialog;
    private final EventBus eventBus;
    private final Wave wave;

    public SetBack(final EventBus eventBus, final Wave wave, final CommonResources res) {
      super("Set background image", i18n.t(SET_THE_IMAGE_BACKGROUND_URL), "FIXME");
      super.putValue(Action.SMALL_ICON, res.pictureWhite());
      this.eventBus = eventBus;
      this.wave = wave;
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
      final String bg = wave.getState().get(CustomConstants.BACKGROUND);
      if (dialog == null) {
        final Builder builder = new PromptTopDialog.Builder("k-back-btn",
            i18n.t(SET_THE_IMAGE_BACKGROUND_URL), false, true, i18n.getDirection(), new OnEnter() {
              @Override
              public void onEnter() {
                doAction();
              }
            });
        // Title == prompt text nowadays :-/
        // builder.title(i18n.t("Image background URL"));
        builder.width(280).height(50).firstButtonTitle(i18n.t("Set")).sndButtonTitle(
            i18n.t("Cancel")).firstButtonId(OK_ID).sndButtonId(CANCEL_ID);

        builder.textFieldWidth(240).regex(TextUtils.URL_REGEXP).regexText(
            i18n.t("This should be something like 'http://example.org/image.jpg'")).textboxId(
                TEXTBOX_ID);
        dialog = builder.build();
        dialog.focusOnTextBox();
        dialog.getSecondBtn().addClickHandler(new ClickHandler() {
          @Override
          public void onClick(final ClickEvent event) {
            dialog.hide();
          }
        });
        dialog.getFirstBtn().addClickHandler(new ClickHandler() {
          @Override
          public void onClick(final ClickEvent event) {
            doAction();
          }
        });
      }

      dialog.showCentered();
      dialog.focusOnTextBox();
      dialog.setTextFieldValue(bg == null ? DEF_BG : bg);
    }

    private void doAction() {
      if (dialog.isValid()) {
        final String newBg = dialog.getTextFieldValue();
        if (newBg != null) {
          eventBus.fireEvent(new ChangeBackgroundEvent(newBg));
        }
        dialog.hide();
      }
    }
  }

  public class UnconfirmedLocation extends AbstractExtendedAction {

    private final EventBus eventBus;

    public UnconfirmedLocation(final EventBus eventBus) {
      super("Unconfirmed location");
      this.eventBus = eventBus;
    }

    @Override
    public void actionPerformed(final ActionEvent event) {
      eventBus.fireEvent(new UnconfirmedEvent());
    }

  }

  public class WithAssistance extends AbstractWith {

    public WithAssistance(final EventBus eventBus, final Wave wave) {
      super(eventBus, wave, CustomConstants.WITHASSISTANCE, "Use confirmation",
          "Don't use confirmation");
    }
  }

  public class WithWho extends AbstractWith {
    public WithWho(final EventBus eventBus, final Wave wave) {
      super(eventBus, wave, CustomConstants.WITHWHO, "Use 'who' field", "Don't use 'who' field");
    }
  }

  public static final String CANCEL_ID = "k-bck-cancel-btn";
  private static final String DEF_BG = "http://mass-nob.appspot.com/massmob/images/bg.png";
  // private static final String DEF_BG = "http://lorempixel.com/1000/1000";
  public static final String TEXTBOX_ID = "k-back-form-textbox";
  private final GuiActionDescCollection actions;
  private final I18nTranslationService i18n;

  private final MenuDescriptor menu;

  @Inject
  public OptionsActions(final EventBus eventBus, final Images img, final Wave wave,
      final StateManager stateManager, final GuiActionDescCollection actions, final CommonResources res,
      final I18nTranslationService i18n) {
    this.actions = actions;
    this.i18n = i18n;

    menu = new MenuDescriptor("", "Options", res.prefGrey());
    menu.addPropertyChangeListener(new PropertyChangeListener() {
      @Override
      public void propertyChange(final PropertyChangeEvent event) {
        if (event.getPropertyName().equals(MenuDescriptor.MENU_ONSHOW)) {
          Scheduler.get().scheduleDeferred(new ScheduledCommand() {

            @Override
            public void execute() {
              eventBus.fireEvent(new GadgetUpdateEvent());
            }
          });
        }
      }
    });
    new MenuItemDescriptor(menu, new SetBack(eventBus, wave, res));
    new MenuSeparatorDescriptor(menu);
    new MenuItemDescriptor(menu, new WithWho(eventBus, wave));
    new MenuItemDescriptor(menu, new WithAssistance(eventBus, wave));
    new MenuItemDescriptor(menu, new UnconfirmedLocation(eventBus));
    new MenuItemDescriptor(menu, new ChangeMapServer(eventBus, stateManager));
    actions.add(menu);
  }

  public GuiActionDescCollection getActions() {
    return actions;
  }

  public MenuDescriptor getMenu() {
    return menu;
  }

}
