package com.luxoft.client.tableview;

import com.google.gwt.core.client.GWT;
import com.google.gwt.event.dom.client.ClickEvent;
import com.google.gwt.event.dom.client.DoubleClickEvent;
import com.google.gwt.event.dom.client.DoubleClickHandler;
import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.uibinder.client.UiBinder;
import com.google.gwt.uibinder.client.UiField;
import com.google.gwt.uibinder.client.UiHandler;
import com.google.gwt.uibinder.client.UiTemplate;
import com.google.gwt.user.cellview.client.ColumnSortEvent;
import com.google.gwt.user.client.Window;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.google.gwt.user.client.ui.Button;
import com.google.gwt.user.client.ui.Composite;
import com.google.gwt.user.client.ui.Widget;
import com.google.gwt.view.client.ListDataProvider;
import com.google.gwt.view.client.ProvidesKey;
import com.google.gwt.view.client.SingleSelectionModel;
import com.luxoft.client.deletedialogview.DeleteDialogView;
import com.luxoft.client.dialogview.DialogView;
import com.luxoft.client.events.*;
import com.luxoft.shared.EventModel;
import com.luxoft.shared.EventServiceAsync;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * List view which contains
 * create/edit/update buttons and table with events
 */
public class ListView extends Composite {

    private SingleSelectionModel<EventModel> selectionModel;
    private DialogView dialog;
    private DeleteDialogView deleteDialog;
    private SimpleEventBus eventBus;
    private List<EventModel> list;

    private static ListUiBinder uiBinder = GWT.create(ListUiBinder.class);

    @UiTemplate("ListView.ui.xml")
    interface ListUiBinder extends UiBinder<Widget, ListView> {
    }

    @UiField
    Table cellTable;

    @UiField
    Button buttonCreate;

    @UiField
    Button buttonEdit;

    @UiField
    Button buttonDelete;

    @UiField(provided = true)
    ListViewResources res;

    public ListView(final SimpleEventBus eventBus) {
        this.eventBus = eventBus;
        list = new ArrayList<EventModel>();
        res = GWT.create(ListViewResources.class);
        res.css().ensureInjected();
        initWidget(uiBinder.createAndBindUi(this));
        initializeSelectionModel();
        cellTable.setSelectionModel(selectionModel);
        sendFindAllRequest();
        eventBus.addHandler(CreateEvent.TYPE, new CreateEventHandler() {
            public void create(CreateEvent event) {
                list.add(event.getEvent());
                renewTable();
            }
        });
        eventBus.addHandler(UpdateEvent.TYPE, new UpdateEventHandler() {
            @Override
            public void onUpdate(UpdateEvent event) {
                list.set(list.indexOf(event.getEventModel()), event.getEventModel());
                renewTable();
            }
        });
        eventBus.addHandler(DeleteEvent.TYPE, new DeleteEventHandler() {
            public void onDelete(final DeleteEvent event) {
                list.remove(event.getEvent());
                selectionModel.setSelected(event.getEvent(), false);
                renewTable();
            }
        });
        dialog = new DialogView(eventBus);
        deleteDialog = new DeleteDialogView(eventBus);
        cellTable.addDomHandler(new DoubleClickHandler() {
            @Override
            public void onDoubleClick(DoubleClickEvent event) {
                EventModel selected = selectionModel.getSelectedObject();
                dialog.setSelected(selected, true);
                dialog.center();
            }
        }, DoubleClickEvent.getType());
    }

    /**
     * Initializes selection model for cell table
     */
    private void initializeSelectionModel() {
        ProvidesKey<EventModel> providesKey = new ProvidesKey<EventModel>() {
            public Object getKey(EventModel event) {
                return (event == null) ? null : event.getId();
            }
        };
        selectionModel = new SingleSelectionModel<EventModel>(providesKey);
    }

    /**
     * Create button handler
     * Shows dialog popup for new event
     *
     * @param event
     */
    @UiHandler("buttonCreate")
    void onCreateClick(ClickEvent event) {
        dialog.setSelected(new EventModel(), false);
        dialog.center();
    }

    /**
     * Edit button handler
     * Shows dialog popup for selected event from cell table
     *
     * @param event
     */
    @UiHandler("buttonEdit")
    public void onEditClick(ClickEvent event) {
        EventModel selected = selectionModel.getSelectedObject();
        if (selected != null) {
            dialog.setSelected(selected, true);
            dialog.center();
        } else {
            Window.alert("Выберите событие!");
        }
    }

    /**
     * Delete button handler
     * Shows confirm dialog for selected event
     *
     * @param event
     */
    @UiHandler("buttonDelete")
    public void onDeleteClick(ClickEvent event) {
        EventModel selected = selectionModel.getSelectedObject();
        if (selected != null) {
            deleteDialog.showConfirm(selected);
        } else {
            Window.alert("Выберите событие!");
        }
    }

    /**
     * Refills the table and fires new modify event to event bus
     */
    private void renewTable() {
        cellTable.setRowData(list);
        columnSorting();
        eventBus.fireEvent(new ModifiedEvent(list));
    }

    /**
     * RPC call for getting all events from DB and fill the table
     */
    private void sendFindAllRequest() {
        EventServiceAsync.Util.getInstance().findAll(new AsyncCallback<List<EventModel>>() {
            @Override
            public void onFailure(Throwable caught) {
                Window.alert("Error " + caught.getMessage());
            }

            @Override
            public void onSuccess(List<EventModel> result) {
                list.addAll(result);
                renewTable();
            }
        });
    }

    /**
     * Initializes column sorting
     */
    public void columnSorting() {
        cellTable.getColumn(0).setSortable(true);
        ListDataProvider<EventModel> dataProvider = new ListDataProvider<EventModel>();
        dataProvider.addDataDisplay(cellTable);
        List<EventModel> providerList = dataProvider.getList();
        for (EventModel e : list) {
            providerList.add(e);
        }
        ColumnSortEvent.ListHandler<EventModel> columnSortHandler = new ColumnSortEvent.ListHandler<EventModel>(providerList);
        columnSortHandler.setComparator(cellTable.getColumn(0), new Comparator<EventModel>() {
            @Override
            public int compare(EventModel o1, EventModel o2) {
                if (o1 != null) {
                    return (o2 != null) ? o1.getBeginDate().compareTo(o2.getBeginDate()) : 1;
                }
                return -1;
            }
        });
        cellTable.addColumnSortHandler(columnSortHandler);
        cellTable.getColumnSortList().push(cellTable.getColumn(0));
    }
}
