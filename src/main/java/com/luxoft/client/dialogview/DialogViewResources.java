package com.luxoft.client.dialogview;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

/**
 * Created by oieliseiev on 18.04.2014.
 */
public interface DialogViewResources extends ClientBundle {
    public interface MyDialog extends CssResource {

        String button();

        String label();

        @ClassName("header-label")
        String headerLabel();

        @ClassName("error-label")
        String errorLabel();

        String field();

        @ClassName("button-align")
        String buttonAlign();

        String date();
    }

    @Source("Dialog.css")
    MyDialog css();
}
