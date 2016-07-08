package com.luxoft.client.timepicker;

import com.google.gwt.core.client.GWT;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.ListBox;
import com.google.gwt.user.client.ui.Widget;
import com.luxoft.shared.Constants;

public class TimePicker extends Composite {
    interface TimePickerUiBinder extends UiBinder<Widget, TimePicker> {
    }

    private static TimePickerUiBinder ourUiBinder = GWT.create(TimePickerUiBinder.class);

    @UiField(provided = true)
    ListBox hours;

    @UiField(provided = true)
    ListBox minutes;

    public TimePicker() {
        initTime();
        initWidget(ourUiBinder.createAndBindUi(this));
    }

    /**
     * Initializes time
     */
    private void initTime() {
        String [] h = new String[]{"08", "09", "10", "11", "12", "13", "14", "15", "16", "17", "18", "19", "20"};
        String [] m = new String[]{"00", "05", "10", "15", "20", "25", "30", "35", "40", "45", "50", "55"};
        hours = new ListBox();
        minutes = new ListBox();
        for(int i = 0; i< h.length; i++){
            hours.addItem(h[i]);
        }
        for(int i = 0; i<m.length; i++){
            minutes.addItem(m[i]);
        }
        hours.setSelectedIndex(0);
        minutes.setSelectedIndex(0);
    }

    public long getTime(){
        return (Long.parseLong(hours.getValue(hours.getSelectedIndex())) * Constants.HOUR_MILS) + (Long.parseLong(minutes.getValue(minutes.getSelectedIndex())) * Constants.MINUTE_MILS);
    }

    public void setTime(long mils){
        int h = (int) ((mils / (Constants.HOUR_MILS)) % Constants.DAY_HOURS) - Constants.DIV;
        int m = (int) ((mils / (Constants.MINUTE_MILS)) % Constants.HOUR_MINUTES) / Constants.DIV;
        hours.setSelectedIndex(h);
        minutes.setSelectedIndex(m);
    }

    public void setEnabled(boolean enabled){
        hours.setEnabled(enabled);
        minutes.setEnabled(enabled);
    }

    public void resetTimePicker(){
        hours.setSelectedIndex(0);
        minutes.setSelectedIndex(0);
    }
}