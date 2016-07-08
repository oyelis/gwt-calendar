package com.luxoft.client.tableview;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.view.client.SingleSelectionModel;
import com.googlecode.gwt.test.GwtModule;
import com.googlecode.gwt.test.GwtTestWithMockito;
import com.googlecode.gwt.test.utils.GwtReflectionUtils;
import com.luxoft.client.deletedialogview.DeleteDialogView;
import com.luxoft.client.dialogview.DialogView;
import com.luxoft.runner.MyGwtRunner;
import com.luxoft.shared.EventModel;
import com.luxoft.shared.EventServiceAsync;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.junit.Assert.*;

@GwtModule("com/luxoft/CalendarModule")
@RunWith(MyGwtRunner.class)
public class ListViewTest extends GwtTestWithMockito {
    @Mock
    private EventServiceAsync serviceAsync;

    @Mock
    private EventModel eventModel;

    @Mock
    private SimpleEventBus eventBus;

    private ListView listView;

    @Before
    public void init() {
        listView = new ListView(eventBus);
    }

    @Test
    public void testCreateClick() {
        listView.buttonCreate.click();
        DialogView dialogView = GwtReflectionUtils.getPrivateFieldValue(listView, "dialog");
        assertFalse((Boolean) GwtReflectionUtils.getPrivateFieldValue(dialogView, "editMode"));
        assertNotNull(GwtReflectionUtils.getPrivateFieldValue(dialogView, "selected"));
        assertTrue(dialogView.isShowing());
    }

    @Test
    public void testDeleteClick() {
        SingleSelectionModel<EventModel> selectionModel = GwtReflectionUtils.getPrivateFieldValue(listView, "selectionModel");
        selectionModel.setSelected(eventModel, true);
        listView.buttonDelete.click();
        DeleteDialogView deleteDialogView = GwtReflectionUtils.getPrivateFieldValue(listView, "deleteDialog");
        assertNotNull(GwtReflectionUtils.getPrivateFieldValue(deleteDialogView, "eventModel"));
        assertTrue(deleteDialogView.isShowing());
    }

}
