package com.sword.module.mybatis.dialect;

import com.sword.core.dto.PageInfo;
import com.sword.module.mybatis.common.DatabaseType;

public class MySQLDatabaseDialect implements DatabaseDialect {
    public MySQLDatabaseDialect() {
    }

    public String pageSql(PageInfo pageInfo, String originalSql) {
        int offset = pageInfo.getCountOfCurrentPage() * (pageInfo.getCurrentPage() - 1);
        if (offset < 0) {
            offset = 0;
        }

        String pageSql = originalSql + " limit " + offset + "," + pageInfo.getCountOfCurrentPage();
        return pageSql;
    }

    public DatabaseType getDatabaseType() {
        return DatabaseType.mysql;
    }
}