package cc.kune.events.client.viewer;

import java.util.Date;

import javax.annotation.Nonnull;

import cc.kune.common.client.actions.ui.descrip.Position;
import cc.kune.common.client.notify.NotifyUser;
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
import com.google.gwt.event.dom.client.ClickEvent;
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

    void addAppointment(String title, Date date);

    HandlerRegistration addClickHandler(ClickHandler clickHandler);

    HandlerRegistration addCreateHandler(CreateHandler<Appointment> handler);

    Date getDate();

    void goToday();

    void setDate(Date date);

    void setView(CalendarViews view);

    void setView(CalendarViews view, int days);

  }

  private static final CalendarViews DEF_VIEW = CalendarViews.DAY;
  public Appointment appToEdit = NO_APPOINT;

  private CalendarViews currentCalView;
  private int currentDaysView = 7;
  private final FolderViewerUtils folderViewerUtils;
  private Date onOverDate;
  private final CalendarOnOverMenu onOverMenu;

  @Inject
  public CalendarViewerPresenter(final EventBus eventBus, final CalendarViewerView view,
      final CalendarViewerProxy proxy, final FolderViewerUtils folderViewerUtils,
      final CalendarOnOverMenu onOverMenu) {
    super(eventBus, view, proxy);
    this.folderViewerUtils = folderViewerUtils;
    this.onOverMenu = onOverMenu;
    addListeners();
    setViewImpl(DEF_VIEW, currentDaysView);
  }

  @Override
  public void addAppointment(final String description, final Date onDate) {
    getView().addAppointment(description, onDate);
  }

  private void addListeners() {
    getView().addTimeBlockClickHandler(new TimeBlockClickHandler<Date>() {
      @Override
      public void onTimeBlockClick(final TimeBlockClickEvent<Date> event) {
        NotifyUser.info("on time block");
        appToEdit = NO_APPOINT;
        onOverDate = event.getTarget();
        updateMenuItems();
      }
    });
    getView().addClickHandler(new ClickHandler() {
      @Override
      public void onClick(final ClickEvent event) {
        onOverMenu.get().show(new Position(event.getClientX(), event.getClientY()));
      }
    });
    getView().addMouseOverHandler(new MouseOverHandler<Appointment>() {
      @Override
      public void onMouseOver(final MouseOverEvent<Appointment> event) {
        NotifyUser.info("on mouse over");
        // Tooltip.to((Widget) event.getSource(), "kk");
        // final Tooltip tooltip = new Tooltip();
        // tooltip.setText("lalala");
        // tooltip.setPopupPosition(, currentDaysView)
        // tooltip.showRelativeTo((UIObject) event.getElement());
      }
    });
    getView().addUpdateHandler(new UpdateHandler<Appointment>() {
      @Override
      public void onUpdate(final UpdateEvent<Appointment> event) {
        NotifyUser.info("updated handler");
      }
    });
    getView().addOpenHandler(new OpenHandler<Appointment>() {
      @Override
      public void onOpen(final OpenEvent<Appointment> event) {
        NotifyUser.info("open handler");
      }
    });
    getView().addSelectionHandler(new SelectionHandler<Appointment>() {
      @Override
      public void onSelection(final SelectionEvent<Appointment> event) {
        appToEdit = event.getSelectedItem();
        onOverDate = event.getSelectedItem().getStart();
        NotifyUser.info("on selection");
        updateMenuItems();
        // getView().removeAppointment(event.getSelectedItem());
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
  protected void revealInParent() {
    RevealRootContentEvent.fire(this, this);
  }

  @Override
  public void setContent(@Nonnull final HasContent state) {
    folderViewerUtils.setContent(getView(), state);
  }

  @Override
  public void setDate(final Date date) {
    getView().setDate(date);
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

  private void updateMenuItems() {
    CalendarStateChangeEvent.fire(getEventBus());
  }
}
