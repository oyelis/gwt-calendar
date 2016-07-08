package com.luxoft.client.dialogview;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.i18n.client.DateTimeFormat;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.google.gwt.user.datepicker.client.DateBox;
import com.luxoft.client.events.CreateEvent;
import com.luxoft.client.events.UpdateEvent;
import com.luxoft.client.timepicker.TimePicker;
import com.luxoft.shared.EventModel;
import com.luxoft.shared.EventServiceAsync;
import com.luxoft.shared.Type;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.groups.Default;
import java.util.Date;
import java.util.Set;

/**
 * Dialog popup on edit/create operation
 */
public class DialogView extends DialogBox {

    @UiTemplate("DialogView.ui.xml")
    interface DialogViewUiBinder extends UiBinder<Widget, DialogView> {
    }

    private static DialogViewUiBinder ourUiBinder = GWT.create(DialogViewUiBinder.class);

    @UiField(provided = true)
    final DialogViewResources res;

    @UiField
    DateBox beginDate;

    @UiField
    TimePicker beginTime;

    @UiField
    DateBox endDate;

    @UiField
    TimePicker endTime;

    @UiField
    TextBox place;

    @UiField
    ListBox type;

    @UiField
    TextBox description;

    @UiField
    Label errorDate;

    @UiField
    Button save;

    @UiField
    Button cancel;

    private EventModel selected;
    private boolean editMode;
    private SimpleEventBus eventBus;

    public DialogView(SimpleEventBus eventBus) {
        this.eventBus = eventBus;
        res = GWT.create(DialogViewResources.class);
        res.css().ensureInjected();
        setWidget(ourUiBinder.createAndBindUi(this));
        for (Type t : Type.values()) {
            type.addItem(t.name());
        }
        beginDate.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("MM/dd/yyyy")));
        endDate.setFormat(new DateBox.DefaultFormat(DateTimeFormat.getFormat("MM/dd/yyyy")));
        setGlassEnabled(true);
    }

    /**
     * Fills dialog field from selected or new event
     *
     * @param selected event
     * @param editMode if false new event mode
     */
    public void setSelected(EventModel selected, boolean editMode) {
        this.selected = selected;
        this.editMode = editMode;
        errorDate.setText("");
        beginDate.setValue(selected.getBeginDate());
        endDate.setValue(selected.getEndDate());
        beginTime.resetTimePicker();
        endTime.resetTimePicker();
        place.setText("");
        type.setSelectedIndex(0);
        description.setText("");
        if (editMode) {
            beginTime.setTime(selected.getBeginDate().getTime());
            endTime.setTime(selected.getEndDate().getTime());
            place.setText(selected.getPlace());
            type.setSelectedIndex(selected.getType().ordinal());
            description.setText(selected.getDescription());
        }
    }

    /**
     * Save button handler
     * send create or update rpc call to server depends on edit or new event mode
     *
     * @param event
     */
    @UiHandler("save")
    public void onSaveClick(ClickEvent event) {
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        disableDialog();
        if (beginDate.getValue() != null) {
            selected.setBeginDate(new Date(beginDate.getValue().getTime() + beginTime.getTime()));
        }
        if (endDate.getValue() != null) {
            selected.setEndDate(new Date(endDate.getValue().getTime() + endTime.getTime()));
        }
        selected.setPlace(place.getValue());
        selected.setType(Type.values()[type.getSelectedIndex()]);
        selected.setDescription(description.getValue());
        Set<ConstraintViolation<EventModel>> violations = validator.validate(selected, Default.class);
        if(!violations.isEmpty()){
            StringBuilder builder = new StringBuilder();
            for (ConstraintViolation<EventModel> violation : violations) {
                builder.append(violation.getMessage());
                builder.append("\n");
            }
            errorDate.setText(builder.toString());
            enableDialog();
        }else {
            if (!editMode) {
                sendCreateRequest(selected);
            } else {
                sendUpdateRequest(selected);
            }
        }
    }

    @UiHandler("cancel")
    public void onCancelClick(ClickEvent event) {
        hide();
    }

    /**
     * RPC call for creating new event
     *
     * @param created event to create
     */
    public void sendCreateRequest(EventModel created) {
        EventServiceAsync.Util.getInstance().save(created, new AsyncCallback<EventModel>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Error " + caught.getMessage());
                enableDialog();
            }

            @Override
            public void onSuccess(EventModel result) {
                eventBus.fireEvent(new CreateEvent(result));
                hide();
                enableDialog();
            }
        });
    }

    /**
     * RPC call for updating existing event
     *
     * @param update event to update
     */
    public void sendUpdateRequest(EventModel update) {
        EventServiceAsync.Util.getInstance().update(update, new AsyncCallback<EventModel>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Error " + caught.getMessage());
                enableDialog();
            }

            @Override
            public void onSuccess(EventModel result) {
                eventBus.fireEvent(new UpdateEvent(result));
                hide();
                enableDialog();
            }
        });
    }

    /**
     * Disables dialog inputs and buttons
     */
    public void disableDialog() {
        beginDate.setEnabled(false);
        beginTime.setEnabled(false);
        endDate.setEnabled(false);
        endTime.setEnabled(false);
        place.setEnabled(false);
        type.setEnabled(false);
        description.setEnabled(false);
        save.setEnabled(false);
        cancel.setEnabled(false);
    }

    /**
     * Enables dialog inputs and buttons
     */
    public void enableDialog() {
        beginDate.setEnabled(true);
        beginTime.setEnabled(true);
        endDate.setEnabled(true);
        endTime.setEnabled(true);
        place.setEnabled(true);
        type.setEnabled(true);
        description.setEnabled(true);
        save.setEnabled(true);
        cancel.setEnabled(true);
    }

    /**
     * Disables window drugging
     *
     * @param e
     */
    @Override
    protected void beginDragging(MouseDownEvent e) {
        e.preventDefault();
    }
}