package com.luxoft.client.dialogview;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.gwt.test.GwtModule;
import com.googlecode.gwt.test.GwtTestWithMockito;
import com.googlecode.gwt.test.utils.GwtReflectionUtils;
import com.luxoft.client.events.CreateEvent;
import com.luxoft.client.events.UpdateEvent;
import com.luxoft.runner.MyGwtRunner;
import com.luxoft.shared.EventModel;
import com.luxoft.shared.EventServiceAsync;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import java.util.Date;

import static org.mockito.Matchers.any;
import static org.mockito.Mockito.verify;

@Ignore
@GwtModule("com/luxoft/CalendarModule")
@RunWith(MyGwtRunner.class)
public class DialogViewTest extends GwtTestWithMockito {
    @Mock
    private EventModel model;

    @Mock
    private SimpleEventBus eventBus;

    @Mock
    private EventServiceAsync serviceAsync;

    private DialogView dialog;

    @Before
    public void initWidget() {
        dialog = new DialogView(eventBus);
        dialog.show();
        dialog.beginDate.setValue(new Date());
        dialog.beginTime.setTime(28800000);
        dialog.endDate.setValue(new Date());
        dialog.endTime.setTime(32400000);
        dialog.place.setText("test");
        dialog.type.setSelectedIndex(1);
        dialog.description.setText("test");
    }

    @Test
    public void testOnSaveNewEventClick() {
        GwtReflectionUtils.setPrivateFieldValue(dialog, "editMode", false);
        GwtReflectionUtils.setPrivateFieldValue(dialog, "selected", new EventModel());
        doSuccessCallback(model).when(serviceAsync).save(any(EventModel.class), any(AsyncCallback.class));
        dialog.save.click();
        verify(serviceAsync).save(any(EventModel.class), any(AsyncCallback.class));
        verify(eventBus).fireEvent(any(CreateEvent.class));
    }

    @Test
    public void testOnSaveUpdateClick() {
        GwtReflectionUtils.setPrivateFieldValue(dialog, "selected", new EventModel());
        GwtReflectionUtils.setPrivateFieldValue(dialog, "editMode", true);
        doSuccessCallback(model).when(serviceAsync).update(any(EventModel.class), any(AsyncCallback.class));
        dialog.save.click();
        verify(serviceAsync).update(any(EventModel.class), any(AsyncCallback.class));
        verify(eventBus).fireEvent(any(UpdateEvent.class));
    }
}
