package com.luxoft.client.deletedialogview;

import com.google.gwt.event.shared.SimpleEventBus;
import com.google.gwt.user.client.rpc.AsyncCallback;
import com.googlecode.gwt.test.GwtModule;
import com.googlecode.gwt.test.GwtTestWithMockito;
import com.googlecode.gwt.test.utils.GwtReflectionUtils;
import com.luxoft.client.events.DeleteEvent;
import com.luxoft.runner.MyGwtRunner;
import com.luxoft.shared.EventModel;
import com.luxoft.shared.EventServiceAsync;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;

import static org.mockito.Matchers.*;
import static org.mockito.Mockito.verify;

@GwtModule("com/luxoft/CalendarModule")
@RunWith(MyGwtRunner.class)
public class DeleteDialogViewTest extends GwtTestWithMockito {
    @Mock
    private EventServiceAsync serviceAsync;

    @Mock
    private EventModel model;

    @Mock
    private SimpleEventBus eventBus;

    private DeleteDialogView deleteDialogView;

    @Before
    public void init() {
        deleteDialogView = new DeleteDialogView(eventBus);
        deleteDialogView.show();
    }

    @Test
    public void testOkDelete() {
        EventModel eventModel = new EventModel();
        eventModel.setId(1);
        GwtReflectionUtils.setPrivateFieldValue(deleteDialogView, "eventModel", eventModel);
        doSuccessCallback(null).when(serviceAsync).delete(anyInt(), any(AsyncCallback.class));
        deleteDialogView.okDelete.click();
        verify(serviceAsync).delete(eq(1), any(AsyncCallback.class));
        verify(eventBus).fireEvent(any(DeleteEvent.class));
    }

}
