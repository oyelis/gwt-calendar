package com.luxoft.client.cellevent;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.ClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.FocusPanel;
import com.google.gwt.user.client.ui.Label;
import com.google.gwt.user.client.ui.VerticalPanel;
import com.luxoft.client.events.SelectEvent;
import com.luxoft.shared.EventModel;

import java.util.HashSet;
import java.util.Set;

public class CellEvent extends Composite {

    private Set<EventModel> set;
    private DateTimeFormat f;
    private VerticalPanel panel;
    private CellEventResources res;
    private boolean today;

    public CellEvent(final SimpleEventBus eventBus) {
        res = GWT.create(CellEventResources.class);
        res.css().ensureInjected();
        set = new HashSet<EventModel>();
        f = DateTimeFormat.getFormat("H:mm");
        FocusPanel wrapper = new FocusPanel();
        panel = new VerticalPanel();
        wrapper.add(panel);
        wrapper.addClickHandler(new ClickHandler() {
            @Override
            public void onClick(ClickEvent event) {
                eventBus.fireEvent(new SelectEvent(set));
                addStyleName(res.css().areaSelected());
            }
        });
        initWidget(wrapper);
    }

    /**
     * Adds event to cell set
     *
     * @param eventModel to add
     */
    public void addEvent(EventModel eventModel) {
        set.add(eventModel);
        Label label = new Label();
        label.setText(f.format(eventModel.getBeginDate()) + " - " + f.format(eventModel.getEndDate()) + " " + eventModel.getDescription());
        label.addStyleName(res.css().label());
        panel.add(label);
    }

    public boolean isToday() {
        return today;
    }

    public void setToday(boolean today) {
        this.today = today;
    }
}
