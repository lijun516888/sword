package com.sword;

import com.google.common.collect.Maps;
import com.sword.app.Application;
import com.sword.app.domain.OrderDomain;
import com.sword.app.service.OrderService;
import com.sword.core.dto.PageInfo;
import org.junit.*;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@SpringBootTest(classes = Application.class)
@RunWith(SpringJUnit4ClassRunner.class)
@FixMethodOrder(value = MethodSorters.NAME_ASCENDING)
public class TestOrderService {

    @Autowired
    private OrderService orderService;

    @Before
    public void before() {

    }

    @After
    public void after() {

    }

    @Test
    public void testA() {
        Arrays.asList(0L, 1L).forEach(t -> {
            try {
                List<OrderDomain> all = orderService.getAll(t);
                all.forEach(v -> {
                    try {
                        orderService.remove(v);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        });
    }

    @Test
    public void testB() throws Exception {
        OrderDomain var0 = new OrderDomain();
        var0.setTid(0L);
        var0.setName("订单表0");
        orderService.save(var0);
        Assert.assertTrue(!Objects.isNull(var0.getId()));
        OrderDomain var1 = new OrderDomain();
        var1.setTid(1L);
        var1.setName("订单表1");
        orderService.save(var1);
        Assert.assertTrue(!Objects.isNull(var1.getId()));
    }

    @Test
    public void testC() throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        map.put("RLIKE_name", "订单表");
        List<OrderDomain> orderDomains = orderService.query(0L, map, null);
        Assert.assertEquals(1, orderDomains.size());
        Assert.assertEquals("订单表0", orderDomains.get(0).getName());
        map.put("RLIKE_name", "订单表");
        orderDomains = orderService.query(1L, map, null);
        Assert.assertEquals(1, orderDomains.size());
        Assert.assertEquals("订单表1", orderDomains.get(0).getName());
    }

    @Test
    public void testD() throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        map.put("RLIKE_name", "订单表");
        List<OrderDomain> orderDomains = orderService.query(0L, map, null);
        orderDomains.get(0).setName("订单表0-修改");
        orderService.update(orderDomains.get(0));
        orderDomains = orderService.query(0L, map, null);
        Assert.assertEquals("订单表0-修改", orderDomains.get(0).getName());
    }

    @Test
    public void testE() throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        map.put("RLIKE_name", "订单表");
        List<OrderDomain> orderDomains = orderService.query(0L, map, null);
        orderService.remove(orderDomains.get(0));
        orderDomains = orderService.getAll(0L);
        Assert.assertEquals(0, orderDomains.size());
    }

    @Test
    public void testF() throws Exception {
        Map<String, Object> map = Maps.newHashMap();
        map.put("RLIKE_name", "订单表");
        List<OrderDomain> orderDomains = orderService.query(1L, map, null);
        orderService.removeById(orderDomains.get(0).getId(), orderDomains.get(0).getTid());
        map.put("RLIKE_name", "订单表");
        orderDomains = orderService.query(1L, map, null);
        Assert.assertEquals(0, orderDomains.size());
    }

    @Test
    public void testG() throws Exception {
        OrderDomain var0 = new OrderDomain();
        var0.setTid(0L);
        var0.setName("订单表0");
        orderService.save(var0);
        Map<String, Object> map = Maps.newHashMap();
        map.put("RLIKE_name", "订单表");
        List<OrderDomain> orderDomains = orderService.query(0L, map, null);
        orderService.removes(orderDomains.get(0).getTid(), orderDomains.get(0).getId());
        map.put("RLIKE_name", "订单表");
        orderDomains = orderService.query(0L, map, null);
        Assert.assertEquals(0, orderDomains.size());
    }

    @Test
    public void testH() throws Exception {
        OrderDomain var0 = new OrderDomain();
        var0.setTid(0L);
        var0.setName("订单表0");
        orderService.save(var0);
        Map<String, Object> map = Maps.newHashMap();
        map.put("RLIKE_name", "订单表");
        List<OrderDomain> query = orderService.query(0L, map, null);
        Assert.assertEquals(1, query.size());
    }

    @Test
    public void testI() throws Exception {
        PageInfo<OrderDomain> pageInfo = new PageInfo<>();
        pageInfo.setCountOfCurrentPage(10);
        pageInfo.setCurrentPage(0);
        Map<String, Object> map = Maps.newHashMap();
        map.put("RLIKE_name", "订单表");
        PageInfo<OrderDomain> query = orderService.query(0L, pageInfo, map);
        Assert.assertEquals(1, query.getPageResults().size());
    }

}
