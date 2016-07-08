package com.luxoft.shared;

import com.google.gwt.user.client.rpc.RemoteService;
import com.google.gwt.user.client.rpc.RemoteServiceRelativePath;

import java.util.List;

@RemoteServiceRelativePath("springGwtServices/eventService")
public interface EventService extends RemoteService {

    List<EventModel> findAll();

    EventModel save(EventModel event);

    EventModel update(EventModel event);

    void delete(int id);

}
