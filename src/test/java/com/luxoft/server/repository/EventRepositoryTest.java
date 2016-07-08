package com.luxoft.server.repository;

import com.luxoft.server.config.RootConfig;
import com.luxoft.server.entity.EventEntity;
import com.luxoft.shared.EventModel;
import com.luxoft.shared.Type;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import javax.annotation.Resource;
import java.util.Date;

import static org.junit.Assert.assertEquals;

/**
 * Created by oieliseiev on 07.05.2014.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RootConfig.class)
public class EventRepositoryTest {

    @Resource
    private EventRepository eventRepository;

    EventEntity entity1;
    EventEntity entity2;
    int id1;
    int id2;

    @Before
    public void initData() {
        Date begin = new Date(0);
        Date end = new Date(1000);
        EventModel model1 = new EventModel(begin, end, "testPlace1", Type.DEMO, "testDescription1");
        EventModel model2 = new EventModel(begin, end, "testPlace2", Type.MEETING, "testDescription2");
        entity1 = new EventEntity(model1);
        entity2 = new EventEntity(model2);
        id1 = eventRepository.save(entity1).getId();
        id2 = eventRepository.save(entity2).getId();
    }

    @Test
    public void testFindAll() {
        assertEquals(2, eventRepository.findAll().size());
    }

    @Test
    public void testSave() {
        EventModel model3 = new EventModel(new Date(0), new Date(1000), "testPlace3", Type.MEETING, "testDescription3");
        final EventEntity entity = new EventEntity(model3);
        int id = eventRepository.save(entity).getId();
        assertEquals(entity, eventRepository.findOne(id));
        assertEquals(3, eventRepository.findAll().size());
    }

    @Test
    public void testDelete() {
        eventRepository.delete(id1);
        assertEquals(1, eventRepository.findAll().size());
        eventRepository.delete(id2);
        assertEquals(0, eventRepository.findAll().size());
    }

    @After
    public void removeData() {
        eventRepository.deleteAll();
    }
}
