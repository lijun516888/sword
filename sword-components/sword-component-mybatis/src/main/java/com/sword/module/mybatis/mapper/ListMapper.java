package com.sword.module.mybatis.mapper;

import com.google.common.collect.Maps;
import com.sword.core.dto.PageInfo;
import com.sword.core.utils.Strings;
import com.sword.module.mybatis.common.SearchFilter;
import com.sword.module.mybatis.common.SearchFilterParser;
import org.apache.ibatis.annotations.SelectProvider;
import org.apache.ibatis.binding.MapperMethod;
import org.apache.ibatis.mapping.BoundSql;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlSource;
import org.springframework.util.Assert;
import tk.mybatis.mapper.entity.EntityColumn;
import tk.mybatis.mapper.entity.EntityTable;
import tk.mybatis.mapper.mapperhelper.EntityHelper;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.MapperTemplate;
import tk.mybatis.mapper.mapperhelper.SqlHelper;

import java.util.Iterator;
import java.util.List;
import java.util.Map;

public interface ListMapper<T> {

    @SelectProvider(
            type = ListMapper.ListProvider.class,
            method = "dynamicSQL"
    )
    List<T> list(Map<String, Object> map, Map<String, Boolean> sort);

    @SelectProvider(
            type = ListMapper.ListProvider.class,
            method = "dynamicSQL"
    )
    PageInfo<T> query(PageInfo<T> pageInfo, Map<String, Object> map, Map<String, Boolean> sortMap);

    class ListSqlSource implements SqlSource {
        private MappedStatement ms;
        private String sql;
        private Map<String, Class<?>> fieldsTypes;
        private int index;

        public ListSqlSource(MappedStatement ms, String sql, Map<String, Class<?>> fieldsTypes, int index) {
            this.ms = ms;
            this.sql = sql;
            this.fieldsTypes = fieldsTypes;
            this.index = index;
        }

        public BoundSql getBoundSql(Object parameterObject) {
            Assert.isInstanceOf(MapperMethod.ParamMap.class, parameterObject);
            MapperMethod.ParamMap paramMap = (MapperMethod.ParamMap)parameterObject;
            Map<String, Object> map = (Map)paramMap.get("param" + this.index);
            Map<String, Boolean> sortMap = (Map)paramMap.get("param" + (this.index + 1));
            StringBuilder sqlResult = new StringBuilder();
            sqlResult.append(this.sql);
            Iterator var6;
            Map.Entry entry;
            if (map != null && !map.isEmpty()) {
                sqlResult.append(" WHERE ");
                var6 = map.entrySet().iterator();

                while(var6.hasNext()) {
                    entry = (Map.Entry)var6.next();
                    SearchFilter searchFilter = SearchFilter.parse((String)entry.getKey(), entry.getValue());
                    if (searchFilter != null) {
                        Class proType = this.fieldsTypes.get(searchFilter.fieldName);
                        Assert.notNull(proType, "属性[" + searchFilter.fieldName + "]不存在");
                        sqlResult.append(SearchFilterParser.parseSqlField(searchFilter, proType));
                        sqlResult.append(" AND ");
                    }
                }

                sqlResult.delete(sqlResult.length() - 4, sqlResult.length() - 1);
            }

            sqlResult.append("order by ");
            if (sortMap != null && !sortMap.isEmpty()) {
                var6 = sortMap.entrySet().iterator();

                while(var6.hasNext()) {
                    entry = (Map.Entry)var6.next();
                    sqlResult.append(Strings.camelToUnderline((String)entry.getKey()) + "  " + ((Boolean)entry.getValue() ? "ASC" : "DESC"));
                    sqlResult.append(",");
                }

                sqlResult.deleteCharAt(sqlResult.length() - 1);
            } else {
                sqlResult.append(" id desc");
            }

            BoundSql boundSql = new BoundSql(this.ms.getConfiguration(), sqlResult.toString(), (List)null, (Object)null);
            return boundSql;
        }
    }

    class ListProvider extends MapperTemplate {
        public ListProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
            super(mapperClass, mapperHelper);
        }

        public void list(MappedStatement ms) {
            Class<?> entityClass = this.getEntityClass(ms);
            this.setResultType(ms, entityClass);
            StringBuilder sql = new StringBuilder();
            sql.append(SqlHelper.selectAllColumns(entityClass));
            sql.append(SqlHelper.fromTable(entityClass, this.tableName(entityClass)));
            Map<String, Class<?>> fieldsTypes = Maps.newHashMap();
            EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
            Iterator var6 = entityTable.getEntityClassColumns().iterator();

            while(var6.hasNext()) {
                EntityColumn column = (EntityColumn)var6.next();
                fieldsTypes.put(column.getProperty(), column.getJavaType());
            }

            ListMapper.ListSqlSource findSqlSource = new ListMapper.ListSqlSource(ms, sql.toString(), fieldsTypes, 1);
            this.setSqlSource(ms, findSqlSource);
        }

        public void query(MappedStatement ms) {
            Class<?> entityClass = this.getEntityClass(ms);
            this.setResultType(ms, entityClass);
            StringBuilder sql = new StringBuilder();
            sql.append(SqlHelper.selectAllColumns(entityClass));
            sql.append(SqlHelper.fromTable(entityClass, this.tableName(entityClass)));
            Map<String, Class<?>> fieldsTypes = Maps.newHashMap();
            EntityTable entityTable = EntityHelper.getEntityTable(entityClass);
            Iterator var6 = entityTable.getEntityClassColumns().iterator();

            while(var6.hasNext()) {
                EntityColumn column = (EntityColumn)var6.next();
                fieldsTypes.put(column.getProperty(), column.getJavaType());
            }

            ListMapper.ListSqlSource findSqlSource = new ListMapper.ListSqlSource(ms, sql.toString(), fieldsTypes, 2);
            this.setSqlSource(ms, findSqlSource);
        }
    }
}
