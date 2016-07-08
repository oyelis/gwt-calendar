package com.luxoft.client.mainview;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface MainViewResources extends ClientBundle {
    public interface MyMain extends CssResource {
        String space();

        String view();
    }

    @Source("Main.css")
    MyMain css();
}
