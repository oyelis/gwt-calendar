package com.luxoft.client.monthview;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface MonthViewResources extends ClientBundle {
    public interface MyMonthViewResources extends CssResource {

        @ClassName("today-area")
        String todayArea();

        String area();

        String label();

        @ClassName("day-header")
        String dayHeader();
    }

    @Source("Month.css")
    MyMonthViewResources css();
}
