package com.sword.app.jsoup;

import com.alibaba.druid.pool.DruidDataSource;
import com.google.common.collect.Lists;
import com.google.common.io.CharStreams;
import org.apache.commons.lang3.StringUtils;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.util.ClassUtils;

import javax.sql.DataSource;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.SQLException;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class PressureTest {

    public static List<TestResponse> requestList = Collections.synchronizedList(new ArrayList<>());

    public static void main(String[] args) {
        List<String> urls = Lists.newArrayList();
        String str = "";
        try {
            InputStream inputStream = new FileInputStream(new File("d:/req.txt"));
            str = CharStreams.toString(new InputStreamReader(inputStream));
            String[] strs = str.split("\n");
            urls = Stream.of(strs).collect(Collectors.toList());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if(StringUtils.isBlank(str)) {
            return;
        }
        JdbcTemplate jdbcTemplate = new JdbcTemplate(createDataSource());
        List<Map<String, Object>> maps = jdbcTemplate.queryForList("select code as code from plt_employee " +
                "where cid = 1000 and `STATUS` = 1");
        List<String> finalUrls = urls;
        List<AgentClient> agentClients = maps.parallelStream().map(a -> new AgentClient(a.get("code").toString(),
                "ks12qwaszx~", finalUrls)).collect(Collectors.toList());
        ExecutorService loginExecutorService = Executors.newFixedThreadPool(100);
        CompletableFuture[] cfs = agentClients.parallelStream().map(a -> CompletableFuture.supplyAsync(a::intoSystem,
                loginExecutorService)).toArray(CompletableFuture[]::new);
        CompletableFuture.allOf(cfs).join();
        loginExecutorService.shutdown();

        List<Object[]> params = requestList.stream().map(a -> new Object[] {
                a.getEmpcode(),
                a.getRequestUrl(),
                a.getResult(),
                a.getStartTime(),
                a.getEndTime()})
                .collect(Collectors.toList());
        jdbcTemplate.batchUpdate("INSERT INTO ks_pressure_info_lj(empcode,requesturl,result,start_time,end_time) values" +
                "(?,?,?,?,?)", params);
    }

    public static DataSource createDataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassLoader(ClassUtils.getDefaultClassLoader());
        dataSource.setUrl("jdbc:mysql://192.168.1.250:3305/ljtest");
        dataSource.setUsername("root");
        dataSource.setPassword("111111");
        dataSource.setInitialSize(1);
        dataSource.setMinIdle(1);
        dataSource.setMaxActive(1);
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
        properties.put("druid.stat.slowSqlMillis", "1000");
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
