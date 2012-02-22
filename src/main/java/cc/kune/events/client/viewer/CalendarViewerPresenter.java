package cc.kune.events.client.viewer;

import java.util.Date;

import javax.annotation.Nonnull;

import cc.kune.common.client.actions.ui.descrip.Position;
import cc.kune.common.client.notify.NotifyUser;
import cc.kune.common.shared.i18n.I18nTranslationService;
import cc.kune.core.client.state.Session;
import cc.kune.core.client.state.StateManager;
import cc.kune.core.shared.dto.HasContent;
import cc.kune.events.client.actions.CalendarOnOverMenu;
import cc.kune.gspace.client.viewers.AbstractFolderViewerView;
import cc.kune.gspace.client.viewers.FolderViewerUtils;

import com.bradrydzewski.gwt.calendar.client.Appointment;
import com.bradrydzewski.gwt.calendar.client.CalendarViews;
import com.bradrydzewski.gwt.calendar.client.HasAppointments;
import com.bradrydzewski.gwt.calendar.client.HasLayout;
import com.bradrydzewski.gwt.calendar.client.event.CreateHandler;
import com.bradrydzewski.gwt.calendar.client.event.HasDateRequestHandlers;
import com.bradrydzewski.gwt.calendar.client.event.HasDeleteHandlers;
import com.bradrydzewski.gwt.calendar.client.event.HasMouseOverHandlers;
import com.bradrydzewski.gwt.calendar.client.event.HasTimeBlockClickHandlers;
import com.bradrydzewski.gwt.calendar.client.event.HasUpdateHandlers;
import com.bradrydzewski.gwt.calendar.client.event.MouseOverEvent;
import com.bradrydzewski.gwt.calendar.client.event.MouseOverHandler;
import com.bradrydzewski.gwt.calendar.client.event.TimeBlockClickEvent;
import com.bradrydzewski.gwt.calendar.client.event.TimeBlockClickHandler;
import com.bradrydzewski.gwt.calendar.client.event.UpdateEvent;
import com.bradrydzewski.gwt.calendar.client.event.UpdateHandler;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.logical.shared.HasOpenHandlers;
import com.google.gwt.event.logical.shared.HasSelectionHandlers;
import com.google.gwt.event.logical.shared.OpenEvent;
import com.google.gwt.event.logical.shared.OpenHandler;
import com.google.gwt.event.logical.shared.SelectionEvent;
import com.google.gwt.event.logical.shared.SelectionHandler;
import com.google.gwt.event.shared.EventBus;
import com.google.gwt.event.shared.HandlerRegistration;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.google.inject.Inject;
import com.gwtplatform.mvp.client.Presenter;
import com.gwtplatform.mvp.client.annotations.ProxyCodeSplit;
import com.gwtplatform.mvp.client.proxy.Proxy;
import com.gwtplatform.mvp.client.proxy.RevealRootContentEvent;

public class CalendarViewerPresenter extends
    Presenter<CalendarViewerPresenter.CalendarViewerView, CalendarViewerPresenter.CalendarViewerProxy>
    implements CalendarViewer {

  @ProxyCodeSplit
  public interface CalendarViewerProxy extends Proxy<CalendarViewerPresenter> {
  }

  public interface CalendarViewerView extends HasSelectionHandlers<Appointment>,
      HasDeleteHandlers<Appointment>, HasOpenHandlers<Appointment>, HasTimeBlockClickHandlers<Date>,
      HasUpdateHandlers<Appointment>, HasDateRequestHandlers<Date>, HasMouseOverHandlers<Appointment>,
      HasLayout, HasAppointments, AbstractFolderViewerView {

    @Override
    void addAppointment(Appointment app);

    HandlerRegistration addClickHandler(ClickHandler clickHandler);

    HandlerRegistration addCreateHandler(CreateHandler<Appointment> handler);

    int getClientX();

    int getClientY();

    Date getDate();

    void goToday();

    @Override
    void removeAppointment(Appointment app);

    void setDate(Date date);

    void setView(CalendarViews view);

    void setView(CalendarViews view, int days);
  }

  private static final CalendarViews DEF_VIEW = CalendarViews.DAY;
  public Appointment appToEdit = NO_APPOINT;

  private CalendarViews currentCalView;
  private int currentDaysView = 7;
  private final FolderViewerUtils folderViewerUtils;
  private final I18nTranslationService i18n;
  private Date onOverDate;
  private final CalendarOnOverMenu onOverMenu;
  private final Session session;
  private final StateManager stateManager;

  @Inject
  public CalendarViewerPresenter(final EventBus eventBus, final CalendarViewerView view,
      final CalendarViewerProxy proxy, final FolderViewerUtils folderViewerUtils,
      final CalendarOnOverMenu onOverMenu, final Session session, final StateManager stateManager,
      final I18nTranslationService i18n) {
    super(eventBus, view, proxy);
    this.folderViewerUtils = folderViewerUtils;
    this.onOverMenu = onOverMenu;
    this.session = session;
    this.stateManager = stateManager;
    this.i18n = i18n;
    addListeners();
    setViewImpl(DEF_VIEW, currentDaysView);
  }

  @Override
  public void addAppointment(final Appointment app) {
    getView().addAppointment(app);
  }

  private void addListeners() {
    getView().addTimeBlockClickHandler(new TimeBlockClickHandler<Date>() {
      @Override
      public void onTimeBlockClick(final TimeBlockClickEvent<Date> event) {
        // NotifyUser.info("on time block");
        appToEdit = NO_APPOINT;
        onOverDate = event.getTarget();
        updateMenuItems();
        showMenu();
      }
    });
    getView().addMouseOverHandler(new MouseOverHandler<Appointment>() {
      @Override
      public void onMouseOver(final MouseOverEvent<Appointment> event) {
        // NotifyUser.info("on mouse over");
      }
    });
    getView().addUpdateHandler(new UpdateHandler<Appointment>() {
      @Override
      public void onUpdate(final UpdateEvent<Appointment> event) {
        final boolean editable = session.getCurrentState().getGroupRights().isEditable();
        if (!editable) {
          NotifyUser.error(i18n.t("Only members can update events"));
        }
        event.setCancelled(!editable);
        // event.setCancelled(true);
        // NotifyUser.info("updated handler");
        hideMenu();
      }
    });
    getView().addOpenHandler(new OpenHandler<Appointment>() {
      @Override
      public void onOpen(final OpenEvent<Appointment> event) {
        // NotifyUser.info("open handler");
        updateMenuItems();
        showMenu();
      }
    });
    getView().addSelectionHandler(new SelectionHandler<Appointment>() {
      @Override
      public void onSelection(final SelectionEvent<Appointment> event) {
        appToEdit = event.getSelectedItem();
        onOverDate = event.getSelectedItem().getStart();
        // NotifyUser.info("on selection");

        // This is not very usable:
        // updateMenuItems();
        // showMenu();
      }
    });
  }

  @Override
  public void attach() {
    getView().attach();
  }

  @Override
  public void decrement() {
    incrementDate(false);
  }

  @Override
  public void detach() {
    getView().detach();
  }

  @Override
  public Appointment getAppToEdit() {
    return appToEdit;
  }

  @Override
  public Date getDate() {
    return getView().getDate();
  }

  @Override
  public Date getOnOverDate() {
    return onOverDate;
  }

  @Override
  public void goToday() {
    getView().goToday();
  }

  private void hideMenu() {
    onOverMenu.get().hide();
  }

  @Override
  public void increment() {
    incrementDate(true);
  }

  private void incrementDate(final boolean positive) {
    final Date date = getDate();
    switch (currentCalView) {
    case DAY:
    case AGENDA:
      CalendarUtil.addDaysToDate(date, currentDaysView * (positive ? 1 : -1));
      break;
    case MONTH:
      CalendarUtil.addMonthsToDate(date, (positive ? 1 : -1));
      break;
    }
    setDate(date);
  }

  @Override
  public void removeAppointment(final Appointment app) {
    getView().removeAppointment(app);
  }

  @Override
  protected void revealInParent() {
    RevealRootContentEvent.fire(this, this);
  }

  @Override
  public void setContent(@Nonnull final HasContent state) {
    folderViewerUtils.setContent(getView(), state);
    updateMenuItems();
  }

  @Override
  public void setDate(final Date date) {
    getView().setDate(date);
  }

  private void setMenuPosition(final int x, final int y) {
    onOverMenu.get().setMenuPosition(new Position(x, y));
  }

  @Override
  public void setView(final CalendarViews calView) {
    setViewImpl(calView);
  }

  @Override
  public void setView(final CalendarViews calView, final int days) {
    setViewImpl(calView, days);
  }

  private void setViewImpl(final CalendarViews calView) {
    this.currentCalView = calView;
    this.currentDaysView = 1;
    getView().setView(calView);
  }

  private void setViewImpl(final CalendarViews calView, final int days) {
    this.currentCalView = calView;
    this.currentDaysView = days;
    getView().setView(calView, days);
  }

  private void showMenu() {
    setMenuPosition(getView().getClientX(), getView().getClientY());
    onOverMenu.get().show();
  }

  private void updateMenuItems() {
    CalendarStateChangeEvent.fire(getEventBus());
  }
}
