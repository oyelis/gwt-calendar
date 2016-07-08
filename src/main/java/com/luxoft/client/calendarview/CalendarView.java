package com.luxoft.client.calendarview;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DatePicker;
import com.luxoft.client.dialogview.DialogView;
import com.luxoft.client.events.SelectEvent;
import com.luxoft.client.events.SelectEventHandler;
import com.luxoft.client.monthview.MonthView;
import com.luxoft.client.weekview.WeekView;
import com.luxoft.shared.EventModel;

import java.util.Date;

/**
 * Calendar view
 * Consists month view, week view and task field
 */
public class CalendarView extends Composite {
    private static CalendarUiBinder uiBinder = GWT.create(CalendarUiBinder.class);

    @UiTemplate("CalendarView.ui.xml")
    interface CalendarUiBinder extends UiBinder<Widget, CalendarView> {
    }

    @UiField(provided = true)
    final CalendarViewResources res;

    @UiField(provided = true)
    DatePicker picker;

    @UiField
    ScrollPanel panel;

    @UiField
    Label date;

    @UiField
    TextArea tasks;

    private DateTimeFormat f;
    private boolean weekActive;
    private MonthView monthtable;
    private WeekView weektable;
    DialogView dialog;

    public CalendarView(final SimpleEventBus eventBus) {
        res = GWT.create(CalendarViewResources.class);
        res.css().ensureInjected();
        picker = new DatePicker();
        picker.setValue(new Date());
        initWidget(uiBinder.createAndBindUi(this));
        monthtable = new MonthView(eventBus);
        weektable = new WeekView(eventBus);
        f = DateTimeFormat.getFormat("MMM, yyyy");
        dialog = new DialogView(eventBus);
        panel.add(monthtable);
        monthtable.draw(new Date());
        weektable.draw(new Date());
        date.setText(f.format(new Date()));
        tasks.setReadOnly(true);
        tasks.setEnabled(false);
        eventBus.addHandler(SelectEvent.TYPE, new SelectHandler());
    }

    /**
     * Create button handler
     * Show popup dialog for new event
     *
     * @param event event
     */
    @UiHandler("createEvent")
    public void onCreateEventClick(ClickEvent event) {
        EventModel newEvent = new EventModel();
        newEvent.setBeginDate(picker.getValue());
        newEvent.setEndDate(picker.getValue());
        dialog.setSelected(newEvent, false);
        dialog.center();
    }

    /**
     * Reset button handler
     * Resets date picker
     *
     * @param event
     */
    @UiHandler("reset")
    public void onResetDate(ClickEvent event) {
        picker.setCurrentMonth(new Date());
    }

    /**
     * Today button handler
     * sets to current date and redraw monthview/weekview
     *
     * @param event
     */
    @UiHandler("today")
    public void onTodayClick(ClickEvent event) {
        if (!weekActive) {
            monthtable.draw(new Date());
        } else {
            weektable.draw(new Date());
        }
        date.setText(f.format(new Date()));
    }

    /**
     * Left button handler
     * decrements on day/month and redraw weekview/monthview
     *
     * @param event
     */
    @UiHandler("left")
    public void onLeftClick(ClickEvent event) {
        tasks.setText("");
        if (!weekActive) {
            date.setText(f.format(monthtable.decrementMonthAndReDraw()));
        } else {
            date.setText(f.format(weektable.decrementDayAndRedraw()));
        }
    }

    /**
     * Right button handler
     * increments on day/month and redraw weekview/monthview
     *
     * @param event
     */
    @UiHandler("right")
    public void onRightClick(ClickEvent event) {
        tasks.setText("");
        if (!weekActive) {
            date.setText(f.format(monthtable.incrementMonthAndReDraw()));
        } else {
            date.setText(f.format(weektable.incrementDayAndRedraw()));
        }
    }

    /**
     * Month button handler
     * adds month view to panel
     *
     * @param event
     */
    @UiHandler("month")
    public void onMonthClick(ClickEvent event) {
        weekActive = false;
        panel.clear();
        panel.add(monthtable);
    }

    /**
     * Week button handler
     * adds week view to panel
     *
     * @param event
     */
    @UiHandler("week")
    public void onWeekClick(ClickEvent event) {
        weekActive = true;
        panel.clear();
        panel.add(weektable);
    }

    /**
     * Select handler
     * Fills task field for selected date/time
     */
    private class SelectHandler implements SelectEventHandler {
        private DateTimeFormat selectedFormatter = DateTimeFormat.getFormat("dd MMM yyyy, HH:mm");

        @Override
        public void onSelect(SelectEvent event) {
            monthtable.repaint();
            weektable.repaint();
            StringBuilder builder = new StringBuilder();
            for (EventModel e : event.getSet()) {
                builder.append(e.getId())
                        .append(".")
                        .append("(")
                        .append(selectedFormatter.format(e.getBeginDate()))
                        .append("-")
                        .append(selectedFormatter.format(e.getEndDate()))
                        .append(") ")
                        .append(e.getPlace())
                        .append(", ")
                        .append(e.getType())
                        .append(", ")
                        .append(e.getDescription())
                        .append("\n");
            }
            tasks.setText(builder.toString());
        }
    }
}
