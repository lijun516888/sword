package com.sword;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.fastjson.JSONObject;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ClassUtils;

import javax.sql.DataSource;
import java.sql.SQLException;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicInteger;

public class SyncBillHandle {

    public static void main(String[] args) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate(getDataSource());
        String billsql = "select * from bx_bill_state s where s.YT_STATE <> 0 or s.TB_STATE <> 0 or SX_STATE <> 0 or FX_STATE <> 0";
        List<Map<String, Object>> listmap = jdbcTemplate.queryForList(billsql);
        System.out.println("处理总数:"+listmap.size());
        AtomicInteger index = new AtomicInteger(1);
        listmap.forEach(a -> {
            System.out.println("处理当前数:" + index.getAndIncrement());
            String billid = a.get("BILLID").toString();
            String billstatesql = "select * from bx_billstate_recored where type = 3 and billid = " + billid + " order by id asc";
            List<Map<String, Object>> billstatemap = jdbcTemplate.queryForList(billstatesql);
            billstatemap.forEach(b -> {
                String resultXml = b.get("RESULT_XML").toString();
                JSONObject object = (JSONObject) JSONObject.parse(resultXml);
                String polstate = object.getString("POLSTATE");
                String constate = object.getString("CONSTATE");
                String changetime = object.getString("CHANGETIME");
                changetime += " 23:59:59";
                asyncBill(polstate, constate, changetime, billid, jdbcTemplate);
            });
        });
    }

    public static void asyncBill(String polstate, String constate, String changetime, String billid, JdbcTemplate jdbcTemplate) {
        if("11".equals(polstate) && "114".equals(constate)) { // 犹豫期退保
            String modifly = "UPDATE BX_BILL_STATE SET YT_TIME = ? WHERE BILLID = ?";
            jdbcTemplate.update(modifly, changetime, billid);
            //System.out.println("犹豫期退保:"+modifly);
        } else if("11".equals(polstate) && "113".equals(constate)) { // 退保
            String modifly = "UPDATE BX_BILL_STATE SET TB_TIME = ? WHERE BILLID = ?";
            jdbcTemplate.update(modifly, changetime, billid);
            //System.out.println("退保:"+modifly);
        } else if("17".equals(polstate)) { // 失效
            String modifly = "UPDATE BX_BILL_STATE SET SX_TIME = ? WHERE BILLID = ?";
            jdbcTemplate.update(modifly, changetime, billid);
            //System.out.println("失效:"+modifly);
        } else if("9".equals(polstate) && "101".equals(constate)) { // 复效
            String modifly = "UPDATE BX_BILL_STATE SET FX_TIME = ? WHERE BILLID = ?";
            jdbcTemplate.update(modifly, changetime, billid);
            //System.out.println("复效:"+modifly);
        }
    }

    public static DataSource getDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassLoader(ClassUtils.getDefaultClassLoader());
        dataSource.setUrl("jdbc:mysql://localhost:3306/ljtest");
        dataSource.setUsername("root");
        dataSource.setPassword("12qwaszx!");
        dataSource.setInitialSize(5);
        dataSource.setMinIdle(20);
        dataSource.setMaxActive(300);
        dataSource.setMaxWait(10000);
        dataSource.setTimeBetweenEvictionRunsMillis(300000L);
        dataSource.setMinEvictableIdleTimeMillis(3600000L);
        dataSource.setTestWhileIdle(true);
        dataSource.setTestOnBorrow(false);
        dataSource.setTestOnReturn(false);
        dataSource.setValidationQueryTimeout(5);
        dataSource.setRemoveAbandoned(true);
        dataSource.setRemoveAbandonedTimeout(3600);
        dataSource.setLogAbandoned(true);
        dataSource.setValidationQuery("select 'x'");
        dataSource.setValidationQueryTimeout(2);
        Properties properties = new Properties();
        properties.put("druid.stat.slowSqlMillis", "0");
        properties.put("druid.stat.logSlowSql", Boolean.toString(true));
        dataSource.setConnectProperties(properties);
        try {
            dataSource.setFilters("stat");
            dataSource.init();
        } catch (SQLException var4) {
            throw new RuntimeException("druid连接池初始化失败", var4);
        }
        return dataSource;
    }

}
