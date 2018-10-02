package com.sword;

import com.sword.app.Application;
import com.sword.app.domain.ItemDomain;
import com.sword.app.service.ItemService;
import org.junit.After;
import org.junit.Before;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class TestItemService {

    @Autowired
    private ItemService itemService;

    @Before
    public void before() {

    }

    @After
    public void after() {

    }

    @Test
    public void testA() throws Exception {
        ItemDomain itemDomain = new ItemDomain();
        itemDomain.setName("分类1");
        itemDomain.setTid(0L);
        itemService.save(itemDomain);
    }

    @Test
    public void testB() throws Exception {
        itemService.get("");
    }

    @Test
    public void testC() throws Exception {

    }

    @Test
    public void testD() throws Exception {

    }

    @Test
    public void testE() throws Exception {

    }

    @Test
    public void testF() throws Exception {

    }

    @Test
    public void testG() throws Exception {

    }

    @Test
    public void testH() throws Exception {

    }

    @Test
    public void testI() throws Exception {

    }

}
