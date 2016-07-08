package com.luxoft.client.monthview;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.CalendarUtil;
import com.luxoft.client.cellevent.CellEvent;
import com.luxoft.client.events.ModifiedEvent;
import com.luxoft.client.events.ModifiedEventHandler;
import com.luxoft.shared.Constants;
import com.luxoft.shared.EventModel;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Month view shows month grid with events
 */
public class MonthView extends Composite {

    final MonthViewResources res;
    private static final int ROWS = 7;
    private static final int COLS = 7;
    private Grid grid;
    private Date currentDate;
    private Date today;
    private SimpleEventBus eventBus;
    private List<EventModel> list;
    private List<CellEvent> events;
    private static final List<Label> DAYS = new ArrayList<Label>(7);

    public MonthView(SimpleEventBus eventBus) {
        this.eventBus = eventBus;
        res = GWT.create(MonthViewResources.class);
        res.css().ensureInjected();
        grid = new Grid(ROWS, COLS);
        list = new ArrayList<EventModel>();
        events = new ArrayList<CellEvent>();
        initDayConstants();
        fillDaysNames();
        eventBus.addHandler(ModifiedEvent.TYPE, new ModifiedEventHandler() {
            @Override
            public void onModify(ModifiedEvent event) {
                list = event.getTable();
                draw(currentDate);
            }
        });
        today = new Date();
        initWidget(grid);
    }

    /**
     * Fills table by events for date
     *
     * @param date to fill table
     */
    public void draw(Date date) {
        grid.clear();
        events.clear();
        fillDaysNames();
        currentDate = CalendarUtil.copyDate(date);
        CalendarUtil.setToFirstDayOfMonth(date);
        int startDay = date.getDay();
        if (startDay == 0) {
            startDay = Constants.START_DAY;
        } else {
            startDay--;
        }
        Date copyDate = CalendarUtil.copyDate(date);
        CalendarUtil.addMonthsToDate(copyDate, 1);
        int daysOfMonth = CalendarUtil.getDaysBetween(date, copyDate);
        for (int row = 1; row < ROWS; row++) {
            if (row > 1) {
                startDay = 0;
            }
            for (int col = startDay; col < COLS; col++) {
                if (daysOfMonth != 0) {
                    CellEvent cellEvent = new CellEvent(eventBus);
                    if (CalendarUtil.isSameDate(date, today)) {
                        cellEvent.setToday(true);
                    }
                    events.add(cellEvent);
                    for (EventModel e : list) {
                        if (CalendarUtil.isSameDate(date, e.getBeginDate())) {
                            cellEvent.addEvent(e);
                        }
                    }
                    VerticalPanel vp = new VerticalPanel();
                    Label day = new Label(DateTimeFormat.getFormat("d, MMM, yyyy").format(date));
                    day.addStyleName(res.css().label());
                    vp.add(day);
                    vp.add(cellEvent);
                    grid.setWidget(row, col, vp);
                    CalendarUtil.addDaysToDate(date, 1);
                    daysOfMonth--;
                }
            }
        }
        repaint();
    }

    /**
     * Increment month and redraw grid
     *
     * @return current date
     */
    public Date incrementMonthAndReDraw() {
        CalendarUtil.setToFirstDayOfMonth(currentDate);
        CalendarUtil.addMonthsToDate(currentDate, 1);
        draw(currentDate);
        return (Date) currentDate.clone();
    }

    /**
     * Decrement month and redraw grid
     *
     * @return current date
     */
    public Date decrementMonthAndReDraw() {
        CalendarUtil.addMonthsToDate(currentDate, -1);
        draw(currentDate);
        return (Date) currentDate.clone();
    }

    /**
     * Fills table headers
     */
    private void fillDaysNames() {
        for (int i = 0; i < DAYS.size(); i++) {
            grid.setWidget(0, i, DAYS.get(i));
        }
    }

    /**
     * Paints grid, if today paints different
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

    /**
     * Initializes day constants
     */
    private void initDayConstants() {
        String[] strDays = new String[]{"Понедельник", "Вторник", "Среда", "Четверг", "Пятница", "Суббота", "Воскресенье"};
        for (String strDay : strDays) {
            Label label = new Label(strDay);
            label.addStyleName(res.css().dayHeader());
            DAYS.add(label);
        }
    }
}