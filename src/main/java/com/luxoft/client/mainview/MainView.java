package com.luxoft.client.mainview;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.TabLayoutPanel;
import com.google.gwt.user.client.ui.Widget;
import com.luxoft.client.calendarview.CalendarView;
import com.luxoft.client.tableview.ListView;
import com.luxoft.shared.Constants;

/**
 * Main view
 * Tab Layout panel with tabs list view and calendar view
 */
public class MainView extends Composite {

    private static MainUiBinder uiBinder = GWT.create(MainUiBinder.class);

    @UiTemplate("MainView.ui.xml")
    interface MainUiBinder extends UiBinder<Widget, MainView> {
    }

    @UiField(provided = true)
    final MainViewResources res;

    @UiField(provided = true)
    ListView listView;

    @UiField(provided = true)
    CalendarView calendarView;

    @UiField
    TabLayoutPanel tabPanel;

    public MainView() {
        res = GWT.create(MainViewResources.class);
        res.css().ensureInjected();
        SimpleEventBus eventBus = new SimpleEventBus();
        listView = new ListView(eventBus);
        calendarView = new CalendarView(eventBus);
        initWidget(uiBinder.createAndBindUi(this));
        tabPanel.selectTab(0);
        tabPanel.setAnimationDuration(Constants.ANIMATION_DURATION);
    }
}
