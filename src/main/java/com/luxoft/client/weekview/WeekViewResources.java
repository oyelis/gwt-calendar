package com.luxoft.client.weekview;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

/**
 * Created by oieliseiev on 23.04.2014.
 */
public interface WeekViewResources extends ClientBundle {
    public interface MyWeekViewResources extends CssResource {

        @ClassName("today-area")
        String todayArea();

        String area();

        String label();
    }

    @Source("Week.css")
    MyWeekViewResources css();
}
