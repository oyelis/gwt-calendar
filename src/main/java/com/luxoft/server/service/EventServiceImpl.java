package com.luxoft.server.service;

import com.luxoft.shared.EventService;
import com.luxoft.server.repository.EventRepository;
import com.luxoft.server.entity.EventEntity;
import com.luxoft.shared.EventModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@Service("eventService")
public class EventServiceImpl implements EventService {

    @Autowired
    private EventRepository eventRepository;

    @Override
    @Transactional(readOnly = true)
    public List<EventModel> findAll() {
        List<EventModel> events = new ArrayList<EventModel>();
        for (EventEntity entity : eventRepository.findAll()) {
            events.add(entity.toDto());
        }
        return events;
    }

    @Override
    @Transactional
    public EventModel save(EventModel event) {
        return eventRepository.save(new EventEntity(event)).toDto();
    }

    @Override
    @Transactional
    public EventModel update(EventModel updated) {
        EventEntity entity = eventRepository.findOne(updated.getId());
        if (entity == null) {
            return null;
        }
        entity.update(updated.getBeginDate(), updated.getEndDate(), updated.getPlace(), updated.getType(), updated.getDescription());
        return entity.toDto();
    }

    @Override
    @Transactional
    public void delete(int id) {
        eventRepository.delete(id);
    }
}