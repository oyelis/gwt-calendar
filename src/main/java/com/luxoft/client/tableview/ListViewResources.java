package com.luxoft.client.tableview;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface ListViewResources extends ClientBundle {
    public interface MyList extends CssResource {
        String button();

        @ClassName("table-width")
        String tableWidth();

        @ClassName("table-scroll-height")
        String tableScrollHeight();
    }

    @Source("List.css")
    MyList css();
}
