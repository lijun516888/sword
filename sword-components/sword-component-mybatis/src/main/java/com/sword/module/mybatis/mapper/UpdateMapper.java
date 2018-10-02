package com.sword.module.mybatis.mapper;

import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.SqlHelper;
import tk.mybatis.mapper.provider.base.BaseUpdateProvider;

import java.util.Iterator;
import java.util.Set;

public interface UpdateMapper<T> {

    @UpdateProvider(
            type = UpdateMapper.UpdateByPrimaryKeySelectiveProvider.class,
            method = "dynamicSQL"
    )
    void update(T o);

    class UpdateByPrimaryKeySelectiveProvider extends BaseUpdateProvider {
        public UpdateByPrimaryKeySelectiveProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
            super(mapperClass, mapperHelper);
        }

        public static String updateSetColumns(Class<?> entityClass, String entityName, boolean notNull, boolean notEmpty) {
            StringBuilder sql = new StringBuilder();
            sql.append("<set>");
            Set<EntityColumn> columnList = EntityHelper.getColumns(entityClass);
            Iterator var6 = columnList.iterator();

            while(var6.hasNext()) {
                EntityColumn column = (EntityColumn)var6.next();
                if (!column.isId() && column.isUpdatable() && !column.getProperty().equals("createTime")) {
                    if (notNull) {
                        sql.append(SqlHelper.getIfNotNull(entityName, column, column.getColumnEqualsHolder(entityName) + ",", notEmpty));
                    } else {
                        sql.append(column.getColumnEqualsHolder(entityName) + ",");
                    }
                }
            }

            sql.append("</set>");
            return sql.toString();
        }

        public String update(MappedStatement ms) {
            Class<?> entityClass = this.getEntityClass(ms);
            StringBuilder sql = new StringBuilder();
            sql.append(SqlHelper.updateTable(entityClass, this.tableName(entityClass)));
            sql.append(updateSetColumns(entityClass, (String)null, true, this.isNotEmpty()));
            sql.append(SqlHelper.wherePKColumns(entityClass));
            return sql.toString();
        }
    }
}
