package com.luxoft.client.weekview;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.luxoft.client.cellevent.CellEvent;
import com.luxoft.client.events.*;
import com.luxoft.shared.Constants;
import com.luxoft.shared.EventModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Week view
 * Contains grid for 7 days with events
 */
public class WeekView extends Composite {

    final WeekViewResources res;
    private FlexTable flexTable;
    private Date currentDate;
    private Date today;
    private List<EventModel> list;
    private List<CellEvent> events;
    private DateTimeFormat f;
    private SimpleEventBus eventBus;

    public WeekView(SimpleEventBus eventBus) {
        today = new Date();
        events = new ArrayList<CellEvent>();
        list = new ArrayList<EventModel>();
        this.eventBus = eventBus;
        res = GWT.create(WeekViewResources.class);
        res.css().ensureInjected();
        initializeTable();
        f = DateTimeFormat.getFormat("E, dd/MM/yy");
        eventBus.addHandler(ModifiedEvent.TYPE, new ModifiedEventHandler() {
            @Override
            public void onModify(ModifiedEvent event) {
                list = event.getTable();
                draw(currentDate);
            }
        });
    }

    private void initializeTable() {
        flexTable = new FlexTable();
        initWidget(flexTable);
    }

    /**
     * Fills table by time
     */
    private void fillTimeTable() {
        for (int i = Constants.MIN_SELECT_HOUR, j = 1; i <= Constants.MAX_SELECT_HOUR; i++) {
            Label label = new Label(i + ":00");
            label.addStyleName(res.css().label());
            flexTable.setWidget(j, 0, label);
            j += 2;
        }
    }

    /**
     * Fill column with name of column
     *
     * @param date to fill
     */
    public void draw(Date date) {
        currentDate = CalendarUtil.copyDate(date);
        fillTimeTable();
        for (int i = 1; i <= Constants.WEEK_DAYS; i++) {
            Label label = new Label(f.format(date));
            label.addStyleName(res.css().label());
            flexTable.setWidget(0, i, label);
            fillColumn(i, date);
            CalendarUtil.addDaysToDate(date, 1);
        }
    }

    /**
     * Fill column with events
     *
     * @param column to fill
     */
    private void fillColumn(int column, Date date) {
        List<EventModel> dayevents = getEventsByDate(date);
        for (int i = Constants.MIN_SELECT_HOUR, j = 1; i <= Constants.MAX_SELECT_HOUR; i++) {
            CellEvent event = new CellEvent(eventBus);
            CellEvent event30 = new CellEvent(eventBus);
            if (CalendarUtil.isSameDate(date, today)) {
                event.setToday(true);
                event30.setToday(true);
            }
            events.add(event);
            events.add(event30);
            for (EventModel e : dayevents) {
                if (e.getBeginDate().getHours() == i) {
                    if (e.getBeginDate().getMinutes() < Constants.HALF_AN_HOUR) {
                        event.addEvent(e);
                    } else {
                        event30.addEvent(e);
                    }
                }
            }
            flexTable.setWidget(j, column, event);
            j++;
            flexTable.setWidget(j, column, event30);
            j++;
        }
        repaint();
    }

    /**
     * Increments day date and redraw week widget
     */
    public Date incrementDayAndRedraw() {
        CalendarUtil.addDaysToDate(currentDate, 1);
        flexTable.clear();
        events.clear();
        draw(currentDate);
        return (Date) currentDate.clone();
    }

    /**
     * Decrements day date and redraw week widget
     */
    public Date decrementDayAndRedraw() {
        CalendarUtil.addDaysToDate(currentDate, -1);
        flexTable.clear();
        events.clear();
        draw(currentDate);
        return (Date) currentDate.clone();
    }

    /**
     * Gets list of events by date(no time)
     *
     * @param date to equal
     * @return list of events
     */
    public List<EventModel> getEventsByDate(Date date) {
        List<EventModel> eventsByDate = new ArrayList<EventModel>();
        for (EventModel e : list) {
            if (CalendarUtil.isSameDate(date, e.getBeginDate())) {
                eventsByDate.add(e);
            }
        }
        return eventsByDate;
    }

    /**
     * Paints grid
     */
    public void repaint() {
        for (CellEvent cell : events) {
            if (cell.isToday()) {
                cell.setStyleName(res.css().todayArea());
            } else {
                cell.setStyleName(res.css().area());
            }
        }
    }
}
