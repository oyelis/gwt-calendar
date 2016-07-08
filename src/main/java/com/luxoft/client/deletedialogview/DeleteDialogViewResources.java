package com.luxoft.client.deletedialogview;

import com.google.gwt.resources.client.ClientBundle;
import com.google.gwt.resources.client.CssResource;

public interface DeleteDialogViewResources extends ClientBundle {
    public interface MyDeleteDialog extends CssResource {

        String button();

        @ClassName("header-label")
        String headerLabel();

        String dialog();

        @ClassName("button-align")
        String buttonAlign();
    }

    @Source("DeleteDialog.css")
    MyDeleteDialog css();
}
