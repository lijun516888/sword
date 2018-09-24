//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sword.module.mybatis.dialect;

import com.sword.core.dto.PageInfo;
import com.sword.module.mybatis.common.DatabaseType;

public interface DatabaseDialect {

    String pageSql(PageInfo pageInfo, String originalSql);
    DatabaseType getDatabaseType();
}
