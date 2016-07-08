package com.luxoft.server.service;

import com.luxoft.server.config.RootConfig;
import com.luxoft.server.repository.EventRepository;
import com.luxoft.shared.EventModel;
import com.luxoft.shared.EventService;
import com.luxoft.shared.Type;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by oieliseiev on 07.05.2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootConfig.class)
public class EventServiceImplTest {

    @Autowired
    private EventService eventService;

    @Autowired
    private EventRepository repository;

    private EventModel model1;
    private EventModel model2;
    private int id1;
    private int id2;
    Date begin;
    Date end;

    @Before
    public void initData() {
        begin = new Date(0);
        end = new Date(1000);
        model1 = new EventModel(begin, end, "testPlace1", Type.DEMO, "testDescription1");
        model2 = new EventModel(begin, end, "testPlace2", Type.MEETING, "testDescription2");
        id1 = eventService.save(model1).getId();
        id2 = eventService.save(model2).getId();
    }

    @Test
    public void testFindAll() {
        assertEquals(2, eventService.findAll().size());
        EventModel e1 = eventService.findAll().get(0);
        EventModel e2 = eventService.findAll().get(1);
        assertEquals("testPlace1", e1.getPlace());
        assertEquals(Type.DEMO, e1.getType());
        assertEquals("testDescription1", e1.getDescription());
        assertEquals("testPlace2", e2.getPlace());
        assertEquals(Type.MEETING, e2.getType());
        assertEquals("testDescription2", e2.getDescription());
    }

    @Test
    public void testSave() {
        EventModel model3 = new EventModel(begin, end, "testPlace3", Type.STAND_UP, "testDescription3");
        assertEquals(model3.getDescription(), eventService.save(model3).getDescription());
        assertEquals(3, eventService.findAll().size());
    }

    @Test
    public void testUpdate() {
        EventModel model4 = eventService.findAll().get(0);
        model4.setPlace("updatedPlace");
        model4.setDescription("updatedDesc");
        model4.setType(Type.RETROSPECTIVE);
        EventModel updated = eventService.update(model4);
        assertEquals("updatedPlace", updated.getPlace());
        assertEquals("updatedDesc", updated.getDescription());
        assertEquals(Type.RETROSPECTIVE, updated.getType());
    }

    @Test
    public void testDelete() {
        eventService.delete(id1);
        assertEquals(1, eventService.findAll().size());
        eventService.delete(id2);
        assertEquals(0, eventService.findAll().size());
    }

    @After
    public void removeData() {
        repository.deleteAll();
    }
}
