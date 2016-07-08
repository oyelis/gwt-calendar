package com.luxoft.client.cellevent;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface CellEventResources extends ClientBundle {
    public interface MyCell extends CssResource {
        String label();

        @ClassName("area-selected")
        String areaSelected();
    }

    @Source("cell.css")
    MyCell css();
}
