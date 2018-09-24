package com.sword.module.mybatis.interceptor;

import com.github.pagehelper.parser.CountSqlParser;
import com.google.common.base.Strings;
import com.google.common.collect.Maps;
import com.sword.core.dto.PageInfo;
import com.sword.module.mybatis.common.CountSql;
import com.sword.module.mybatis.dialect.DatabaseDialectManager;
import com.sword.module.mybatis.page.MyBatisPage;
import org.apache.ibatis.executor.Executor;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.MappedStatement.Builder;
import org.apache.ibatis.mapping.ParameterMapping;
import org.apache.ibatis.plugin.*;
import org.apache.ibatis.scripting.defaults.DefaultParameterHandler;
import org.apache.ibatis.session.ResultHandler;
import org.apache.ibatis.session.RowBounds;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.*;

@Intercepts({@Signature(
        type = Executor.class,
        method = "query",
        args = {MappedStatement.class, Object.class, RowBounds.class, ResultHandler.class}
)})
public class PageExecutorInterceptor implements Interceptor {
    private static Map<String, String> countSqlMap = Maps.newConcurrentMap();
    CountSqlParser countSqlParser = new CountSqlParser();

    public Object intercept(Invocation invocation) throws Throwable {
        PageInfo pageInfo = this.havePageInfoArg(invocation);
        if (pageInfo == null) {
            return invocation.proceed();
        } else {
            MappedStatement mappedStatement = (MappedStatement)invocation.getArgs()[0];
            Object parameter = invocation.getArgs()[1];
            BoundSql boundSql = mappedStatement.getBoundSql(parameter);
            String originalSql = boundSql.getSql().trim();
            Object parameterObject = boundSql.getParameterObject();
            long totalCount = this.getCount(mappedStatement, originalSql, boundSql, parameterObject);
            long totalPage = totalCount % (long)pageInfo.getCountOfCurrentPage() == 0L ? totalCount / (long)pageInfo.getCountOfCurrentPage() : totalCount / (long)pageInfo.getCountOfCurrentPage() + 1L;
            pageInfo.setTotalCount(totalCount);
            pageInfo.setTotalPage(totalPage);
            String pageSql = this.getPageSql(mappedStatement, pageInfo, originalSql);
            BoundSql newBoundSql = this.copyFromBoundSql(mappedStatement, boundSql, pageSql);
            MappedStatement newMapperStmt = this.copyFromMappedStatement(mappedStatement, newBoundSql);
            invocation.getArgs()[0] = newMapperStmt;
            List result = (List)invocation.proceed();
            if (result == null) {
                pageInfo.setPageResults(Collections.emptyList());
            } else {
                pageInfo.setPageResults(result);
            }

            MyBatisPage page = new MyBatisPage(result, totalCount, totalPage);
            page.setCurrentPage(pageInfo.getCurrentPage());
            page.setCountOfCurrentPage(pageInfo.getCountOfCurrentPage());
            return page;
        }
    }

    public Object plugin(Object arg0) {
        return Plugin.wrap(arg0, this);
    }

    public void setProperties(Properties arg0) {
    }

    private String getPageSql(MappedStatement mappedStatement, PageInfo pageInfo, String originalSql) throws Throwable {
        Connection connection = mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection();
        return DatabaseDialectManager.pageSql(connection, pageInfo, originalSql);
    }

    private long getCount(MappedStatement mappedStatement, String originalSql, BoundSql boundSql, Object parameterObject) throws Throwable {
        String countSql = this.getCountSql(mappedStatement, originalSql);
        Connection connection = null;
        long totpage = 0L;

        try {
            connection = mappedStatement.getConfiguration().getEnvironment().getDataSource().getConnection();
            PreparedStatement countStmt = connection.prepareStatement(countSql);
            BoundSql countBS = this.copyFromBoundSql(mappedStatement, boundSql, countSql);
            DefaultParameterHandler parameterHandler = new DefaultParameterHandler(mappedStatement, parameterObject, countBS);
            parameterHandler.setParameters(countStmt);
            ResultSet rs = countStmt.executeQuery();
            totpage = 0L;
            if (rs.next()) {
                totpage = rs.getLong(1);
            }

            rs.close();
            countStmt.close();
        } finally {
            if (connection != null) {
                connection.close();
            }

        }

        return totpage;
    }

    private String getCountSql(MappedStatement mappedStatement, String sql) {
        String countSqlByAnnotation = this.getCountSqlByAnnotation(mappedStatement);
        return !Strings.isNullOrEmpty(countSqlByAnnotation) ? countSqlByAnnotation : this.countSqlParser.getSmartCountSql(sql);
    }

    private String getCountSqlByAnnotation(MappedStatement mappedStatement) {
        String id = mappedStatement.getId();
        String cached = countSqlMap.get(id);
        if (cached == null) {
            String countSql = "";

            try {
                if (!Strings.isNullOrEmpty(id)) {
                    int idx = id.lastIndexOf(46);
                    String className = id.substring(0, idx);
                    String methodName = id.substring(idx + 1);
                    Method[] methods = Class.forName(className).getMethods();
                    Method[] var9 = methods;
                    int var10 = methods.length;

                    for(int var11 = 0; var11 < var10; ++var11) {
                        Method method = var9[var11];
                        if (method.getName().equals(methodName)) {
                            CountSql annotation = (CountSql)method.getAnnotation(CountSql.class);
                            if (annotation != null) {
                                countSql = annotation.value();
                                break;
                            }
                        }
                    }
                }
            } catch (Exception var14) {
                ;
            }

            countSqlMap.put(id, countSql);
            cached = countSql;
        }

        return cached;
    }

    private MappedStatement copyFromMappedStatement(MappedStatement ms, final BoundSql newBoundSql) {
        Builder builder = new Builder(ms.getConfiguration(), ms.getId(), (parameterObject) -> {
            return newBoundSql;
        }, ms.getSqlCommandType());
        builder.resource(ms.getResource());
        builder.fetchSize(ms.getFetchSize());
        builder.statementType(ms.getStatementType());
        builder.keyGenerator(ms.getKeyGenerator());
        builder.timeout(ms.getTimeout());
        builder.parameterMap(ms.getParameterMap());
        builder.resultMaps(ms.getResultMaps());
        builder.resultSetType(ms.getResultSetType());
        builder.cache(ms.getCache());
        builder.flushCacheRequired(ms.isFlushCacheRequired());
        builder.useCache(ms.isUseCache());
        return builder.build();
    }

    private BoundSql copyFromBoundSql(MappedStatement ms, BoundSql boundSql, String sql) {
        BoundSql newBoundSql = new BoundSql(ms.getConfiguration(), sql, boundSql.getParameterMappings(), boundSql.getParameterObject());
        Iterator var5 = boundSql.getParameterMappings().iterator();

        while(var5.hasNext()) {
            ParameterMapping mapping = (ParameterMapping)var5.next();
            String prop = mapping.getProperty();
            if (boundSql.hasAdditionalParameter(prop)) {
                newBoundSql.setAdditionalParameter(prop, boundSql.getAdditionalParameter(prop));
            }
        }

        return newBoundSql;
    }

    private PageInfo havePageInfoArg(Invocation invocation) {
        Object parameter = invocation.getArgs()[1];
        if (parameter == null) {
            return null;
        } else {
            if (Map.class.isAssignableFrom(parameter.getClass())) {
                Iterator var3 = ((Map)parameter).values().iterator();

                while(var3.hasNext()) {
                    Object v = var3.next();
                    if (v instanceof PageInfo) {
                        return (PageInfo)v;
                    }
                }
            } else if (parameter instanceof PageInfo) {
                return (PageInfo)parameter;
            }

            return null;
        }
    }
}
