package com.luxoft.client.deletedialogview;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.MouseDownEvent;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.*;
import com.luxoft.client.events.DeleteEvent;
import com.luxoft.shared.EventModel;
import com.luxoft.shared.EventServiceAsync;

/**
 * Dialog popup on delete operation
 */
public class DeleteDialogView extends DialogBox {

    @UiTemplate("DeleteDialogView.ui.xml")
    interface DeleteDialogViewUiBinder extends UiBinder<Widget, DeleteDialogView> {
    }

    private static DeleteDialogViewUiBinder ourUiBinder = GWT.create(DeleteDialogViewUiBinder.class);
    private SimpleEventBus eventBus;
    private EventModel eventModel;

    @UiField(provided = true)
    final DeleteDialogViewResources res;

    @UiField
    Label header;

    @UiField
    Button okDelete;

    @UiField
    Button cancelDelete;

    public DeleteDialogView(SimpleEventBus eventBus) {
        this.eventBus = eventBus;
        res = GWT.create(DeleteDialogViewResources.class);
        res.css().ensureInjected();
        setWidget(ourUiBinder.createAndBindUi(this));
        setGlassEnabled(true);
    }

    public void showConfirm(EventModel eventModel) {
        this.eventModel = eventModel;
        header.setText("Удалить событие: " + eventModel.getDescription() + "?");
        center();
    }

    /**
     * Ok delete button handler
     * sent rpc delete call on server
     *
     * @param event
     */
    @UiHandler("okDelete")
    public void onOk(ClickEvent event) {
        disableButtons();
        sendDeleteRequest(eventModel);
    }

    /**
     * cancel delete handler
     * hides delete popup
     *
     * @param event
     */
    @UiHandler("cancelDelete")
    public void onCancel(ClickEvent event) {
        eventModel = null;
        hide();
    }

    /**
     * RPC call to delete selected event and renew the table
     *
     * @param deleted event to delete
     */
    public void sendDeleteRequest(final EventModel deleted) {
        EventServiceAsync.Util.getInstance().delete(deleted.getId(), new AsyncCallback<Void>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Error " + caught.getMessage());
                enableButtons();
            }

            @Override
            public void onSuccess(Void result) {
                eventBus.fireEvent(new DeleteEvent(eventModel));
                hide();
                enableButtons();
            }
        });
    }

    /**
     * Disables ok/cancel buttons
     */
    public void disableButtons() {
        okDelete.setEnabled(false);
        cancelDelete.setEnabled(false);
    }

    /**
     * Enables ok/cancel buttons
     */
    public void enableButtons() {
        okDelete.setEnabled(true);
        cancelDelete.setEnabled(true);
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