package com.luxoft.client.calendarview;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface CalendarViewResources extends ClientBundle {
    public interface MyCalendar extends CssResource {
        String customeDatePicker();

        String button();

        String label();

        String devider();

        String tasksfield();

        String tasklabel();

        @ClassName("panel-scroll")
        String panelScroll();
    }

    @Source("Calendar.css")
    MyCalendar css();
}