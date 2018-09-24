package com.sword.module.mybatis.dialect;

import com.google.common.collect.Maps;
import com.sword.core.dto.PageInfo;
import com.sword.module.mybatis.common.DatabaseType;
import org.apache.commons.lang3.StringUtils;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Map;

public final class DatabaseDialectManager {
    private static Map<DatabaseType, DatabaseDialect> dialects = Maps.newHashMap();

    private DatabaseDialectManager() {
    }

    public static String pageSql(Connection connection, PageInfo pageInfo, String sql) {
        DatabaseType dbType = getDatabaseType(connection);
        return (dialects.get(dbType)).pageSql(pageInfo, sql);
    }

    public static DatabaseType getDatabaseType(Connection connection) {
        String jdbcUrl = getJdbcUrlFromDataSource(connection);
        if (StringUtils.contains(jdbcUrl, ":mysql:")) {
            return DatabaseType.mysql;
        } else if (StringUtils.contains(jdbcUrl, ":oracle:")) {
            return DatabaseType.oracle;
        } else {
            throw new IllegalArgumentException("Unknown Database of " + jdbcUrl);
        }
    }

    public static String getJdbcUrlFromDataSource(Connection connection) {
        String var1;
        try {
            if (connection == null) {
                throw new IllegalStateException("Connection was null");
            }

            var1 = connection.getMetaData().getURL();
        } catch (SQLException var10) {
            throw new RuntimeException("Could not get database url", var10);
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException var9) {
                    ;
                }
            }

        }

        return var1;
    }

    private static void register(DatabaseDialect dialect) {
        dialects.put(dialect.getDatabaseType(), dialect);
    }

    static {
        register(new MySQLDatabaseDialect());
    }
}
