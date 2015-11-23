package org.ourproject.massmob.client.ui;

import java.util.Date;

import org.ourproject.massmob.client.CustomConstants;
import org.ourproject.massmob.client.StateManager;
import org.ourproject.massmob.client.actions.OptionsActions;
import org.ourproject.massmob.client.actions.UnconfirmedEvent;
import org.ourproject.massmob.client.actions.WithFieldEvent;
import org.ourproject.massmob.client.ui.EditEvent.EditHandler;
import org.ourproject.massmob.client.ui.date.DatePresenter;
import org.ourproject.massmob.client.ui.img.Images;

import com.google.gwt.core.client.GWT;
import com.google.gwt.core.client.Scheduler;
import com.google.gwt.core.client.Scheduler.ScheduledCommand;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiFactory;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.ui.AbstractImagePrototype;
import com.google.gwt.user.client.ui.CheckBox;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwt.user.client.ui.FlowPanel;
import com.google.gwt.user.client.ui.Image;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.PushButton;
import com.google.gwt.user.client.ui.ToggleButton;
import com.google.gwt.user.client.ui.Widget;
import com.google.inject.Inject;
import com.google.web.bindery.event.shared.EventBus;
import com.thezukunft.wave.connector.ModeChangeEvent;
import com.thezukunft.wave.connector.ModeChangeEventHandler;
import com.thezukunft.wave.connector.StateUpdateEvent;
import com.thezukunft.wave.connector.StateUpdateEventHandler;
import com.thezukunft.wave.connector.Wave;

import cc.kune.common.client.actions.gwtui.GwtGuiProvider;
import cc.kune.common.client.actions.ui.GuiProvider;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.client.resources.CommonResources;
import cc.kune.common.client.tooltip.Tooltip;
import cc.kune.common.client.utils.DateUtils;
import cc.kune.common.shared.i18n.I18nTranslationServiceMocked;
import cc.kune.common.shared.res.ICalConstants;

public class MassmobMainPanel extends Composite {

  interface MainPanelUiBinder extends UiBinder<Widget, MassmobMainPanel> {
  }

  private static final String FIELDLABEL_STYLE = "fieldlabel";

  private static MainPanelUiBinder uiBinder = GWT.create(MainPanelUiBinder.class);
  private final OptionsActions actions;
  @UiField
  EditableLabel appTitle;
  @UiField
  DescriptionField desc;
  private final EventBus eventBus;
  @UiField
  FlexTable fields;
  private final GuiProvider guiReg;
  private final Images img;
  @UiField
  FlowPanel mainPanel;
  @UiField
  OptionsPanel options;
  @UiField
  Assistance parts;
  private final StateManager stateManager;
  @UiField
  FlowPanel titlePanel;
  private final Wave wave;

  @Inject
  public MassmobMainPanel(final EventBus eventBus, final Wave wave, final StateManager stateManager,
      final DatePresenter startDate, final DatePresenter endDate, final Images img,
      final CommonResources res, final OptionsActions actions, final GuiProvider guiReg,
      final GwtGuiProvider guiProvider, final I18nTranslationServiceMocked i18n) {
    this.eventBus = eventBus;
    this.wave = wave;
    this.stateManager = stateManager;
    this.img = img;
    this.actions = actions;
    this.guiReg = guiReg;
    startDate.init(ICalConstants.DATE_TIME_START);
    endDate.init(ICalConstants.DATE_TIME_END);
    initWidget(uiBinder.createAndBindUi(this));
    this.setWidth("auto");
    Scheduler.get().scheduleDeferred(new ScheduledCommand() {
      @Override
      public void execute() {
        onModuleLoad();
      }

      private void onModuleLoad() {
        Resources.INSTANCE.style().ensureInjected();

        final TextField where = new TextField();
        final TextField who = new TextField();
        Tooltip.to(where, "Where the event will happens?");
        Tooltip.to(who, "Who's organizing the event?");
        img.css().ensureInjected();

        final Label whereLabel = new Label("Location:", true);
        final Label whoLabel = new Label("Who organize:", false);
        final Label allDayLabel = new Label("All day event:", false);
        final Label startLabel = new Label("Start:");
        final Label linkLabel = new Label("Keep the duration when changing dates");
        final Label endLabel = new Label("End:");
        final PushButton mapBtn = new PushButton(new Image(res.locationGrey()),
            new Image(res.locationBlack()));
        final ToggleButton keepDuration = new ToggleButton(new Image(res.chainOpenGrey()),
            new Image(res.chainClosedGrey()));
        keepDuration.setStyleName("k-nothing");
        keepDuration.addStyleName("k-pointer");
        Tooltip.to(keepDuration, "Click to change");
        new PushButton(AbstractImagePrototype.create(res.pictureGrey()).createImage(),
            AbstractImagePrototype.create(res.pictureBlack()).createImage());

        final CheckBox allDay = new CheckBox();

        allDay.setValue(Boolean.valueOf(wave.getState().get(ICalConstants._ALL_DAY)));
        keepDuration.setValue(Boolean.valueOf(wave.getState().get(CustomConstants.KEEP_DURATION)));
        Tooltip.to(mapBtn, "See location map");
        mapBtn.setStyleName("mapBtn");
        // keepDurationBtn.setStyleName("mapBtn");
        whereLabel.addStyleName(FIELDLABEL_STYLE);
        whoLabel.addStyleName(FIELDLABEL_STYLE);
        allDayLabel.addStyleName(FIELDLABEL_STYLE);
        startLabel.addStyleName(FIELDLABEL_STYLE);
        endLabel.addStyleName(FIELDLABEL_STYLE);
        keepDuration.addStyleName(FIELDLABEL_STYLE);

        fields.getColumnFormatter().setStyleName(1, "fieldboxes");
        fields.setWidget(0, 0, whereLabel);
        fields.setWidget(1, 0, whoLabel);
        fields.setWidget(2, 0, allDayLabel);
        fields.setWidget(4, 0, keepDuration);
        fields.setWidget(3, 0, startLabel);
        fields.setWidget(5, 0, endLabel);
        fields.setWidget(0, 1, where);
        fields.setWidget(1, 1, who);
        fields.setWidget(2, 1, allDay);
        fields.setWidget(3, 1, startDate.getView().asWidget());
        fields.setWidget(4, 1, linkLabel);
        fields.setWidget(5, 1, endDate.getView().asWidget());
        fields.setWidget(0, 2, mapBtn);
        allDay.addClickHandler(new ClickHandler() {
          @SuppressWarnings("deprecation")
          @Override
          public void onClick(final ClickEvent event) {
            final Boolean isAllDay = allDay.getValue();
            wave.getState().submitValue(ICalConstants._ALL_DAY, isAllDay.toString());
            if (isAllDay) {
              // We set all the hour to zero
              final Date start = DateUtils.toDate(wave.getState().get(ICalConstants.DATE_TIME_START));
              final Date end = DateUtils.toDate(wave.getState().get(ICalConstants.DATE_TIME_END));

              /*
               * See:
               * https://stackoverflow.com/questions/4170827/gwt-no-source-code-
               * is-available-for-type-java-util-calendar
               */
              start.setHours(0);
              start.setMinutes(0);
              start.setSeconds(0);
              end.setHours(23);
              end.setMinutes(59);
              end.setSeconds(59);

              wave.getState().submitValue(ICalConstants.DATE_TIME_START, DateUtils.toString(start));
              wave.getState().submitValue(ICalConstants.DATE_TIME_END, DateUtils.toString(start));
            }
          }
        });
        keepDuration.addClickHandler(new ClickHandler() {
          @Override
          public void onClick(final ClickEvent event) {
            wave.getState().submitValue(CustomConstants.KEEP_DURATION,
                keepDuration.getValue().toString());
          }
        });
        mapBtn.addClickHandler(new ClickHandler() {
          @Override
          public void onClick(final ClickEvent event) {
            final String whereS = where.getText();
            if (whereS.length() > 0) {
              if (Boolean.parseBoolean(stateManager.getValue(CustomConstants.USEGMAPS))) {
                Window.open("http://maps.google.com/maps?q=" + whereS, "_blank", "");
              } else {
                Window.open("http://nominatim.openstreetmap.org/search/" + whereS, "_blank", "");
              }
            } else {
              NotifyUser.info("Fill the location where the event will take place");
            }
          }
        });

        appTitle.addEditHandler(createHandler(ICalConstants.SUMMARY));
        where.addEditHandler(createHandler(ICalConstants.LOCATION));
        who.addEditHandler(createHandler(ICalConstants.ORGANIZER));
        eventBus.addHandler(StateUpdateEvent.TYPE, new StateUpdateEventHandler() {
          @Override
          public void onUpdate(final StateUpdateEvent event) {
            update(where, who, startDate, endDate, allDay, keepDuration);
            setEnabled(!wave.isPlayback(), stateManager, startDate, endDate, where, who);
          }
        });
        eventBus.addHandler(ModeChangeEvent.TYPE, new ModeChangeEventHandler() {
          @Override
          public void onUpdate(final ModeChangeEvent event) {
            setEnabled(!wave.isPlayback(), stateManager, startDate, endDate, where, who);
          }
        });
        setEnabled(!wave.isPlayback(), stateManager, startDate, endDate, where, who);
        update(where, who, startDate, endDate, allDay, keepDuration);
        eventBus.addHandler(ChangeBackgroundEvent.TYPE,
            new ChangeBackgroundEvent.ChangeBackgroundHandler() {
          @Override
          public void fire(final ChangeBackgroundEvent event) {
            setBackground(event.getBg());
          }
        });

        String withState = wave.getState().get(CustomConstants.WITHWHO);
        boolean visible = withState == null || Boolean.parseBoolean(withState);
        who.setVisible(visible);
        whoLabel.setVisible(visible);
        withState = wave.getState().get(CustomConstants.WITHASSISTANCE);
        visible = withState == null || Boolean.parseBoolean(withState);
        parts.setVisible(visible);

        eventBus.addHandler(WithFieldEvent.TYPE, new WithFieldEvent.WithFieldHandler() {
          @Override
          public void fire(final WithFieldEvent event) {
            final boolean with = event.getWith();
            final String field = event.getField();
            if (field.equals(CustomConstants.WITHWHO)) {
              who.setVisible(with);
              whoLabel.setVisible(with);
            }
            if (field.equals(CustomConstants.WITHASSISTANCE)) {
              parts.setVisible(with);
            }
          }
        });
        eventBus.addHandler(UnconfirmedEvent.TYPE, new UnconfirmedEvent.UnconfirmedHandler() {
          @Override
          public void fire(final UnconfirmedEvent event) {
            where.setText("Unconfirmed location, be attent to your mobile");
          }
        });
        final String bg = wave.getState().get(CustomConstants.BACKGROUND);
        if (bg != null) {
          setBackground(bg);
        }
      }
    });
  }

  private EditHandler createHandler(final String key) {
    return new EditHandler() {
      @Override
      public void fire(final EditEvent event) {
        stateManager.setValue(key, event.getText());
      }
    };
  }

  @UiFactory
  OptionsPanel createOptionsPanel() {
    return new OptionsPanel(guiReg, actions);
  }

  @UiFactory
  @Inject
  DescriptionField makeDescriptionField() {
    return new DescriptionField(eventBus, stateManager, img);
  }

  @UiFactory
  @Inject
  Assistance makeParts() {
    return new Assistance(eventBus, wave, stateManager, img);
  }

  @UiFactory
  CustomRoundedLinePanel makeRD() {
    return new CustomRoundedLinePanel("#808080", "#f4e7ee", "#f4e7ee");
  }

  private void setBackground(final String bg) {
    final String bodyProp = "#f4e7ee url('" + bg + "') fixed no-repeat top left";
    mainPanel.getElement().getStyle().setProperty("background", bodyProp);
    mainPanel.getElement().getStyle().setProperty("backgroundAttachment", "inherit");
    wave.getState().submitValue(CustomConstants.BACKGROUND, bg);
  }

  private void setEnabled(final boolean enabled, final StateManager stateManager,
      final DatePresenter startDatePresenter, final DatePresenter endDatePresenter,
      final TextField where, final TextField who) {
    startDatePresenter.setEnabled(enabled);
    endDatePresenter.setEnabled(enabled);
    who.textbox.setEnabled(enabled);
    where.textbox.setEnabled(enabled);
    desc.setEnabled(enabled);
    parts.setEnabled(enabled);
    appTitle.setEnabled(enabled);
    stateManager.setEnabled(enabled);
    options.setEnabled(enabled);
  }

  private void update(final TextField where, final TextField who, final DatePresenter startDate,
      final DatePresenter endDate, final CheckBox allDay, final ToggleButton keepDuration) {
    final String summary = stateManager.getValue(ICalConstants.SUMMARY);
    final String location = stateManager.getValue(ICalConstants.LOCATION);
    final String organizer = stateManager.getValue(ICalConstants.ORGANIZER);
    final boolean allDayValue = Boolean.parseBoolean(stateManager.getValue(ICalConstants._ALL_DAY));
    final boolean keepDurationValue = Boolean.parseBoolean(
        stateManager.getValue(CustomConstants.KEEP_DURATION));
    if (summary != null) {
      appTitle.setText(summary);
    }
    if (location != null) {
      where.setText(location);
    }
    if (organizer != null) {
      who.setText(organizer);
    }
    startDate.updateView(wave.getState());
    endDate.updateView(wave.getState());
    allDay.setValue(allDayValue);
    keepDuration.setValue(keepDurationValue);
  }
}
