package com.luxoft.client;

import com.google.gwt.core.client.EntryPoint;

import com.google.gwt.user.client.ui.*;
import com.luxoft.client.mainview.MainView;

/**
 * Entry point
 */
public class CalendarModule implements EntryPoint {

    public void onModuleLoad() {
        MainView mainView = new MainView();
        RootLayoutPanel.get().add(mainView);
    }
}
