package com.sword.module.mybatis.mapper;

import com.google.common.collect.Maps;
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

public interface FindMapper<T> {

    @SelectProvider(
            type = FindMapper.FindProvider.class,
            method = "dynamicSQL"
    )
    List<T> find(String property, Object value);

    @SelectProvider(
            type = FindMapper.FindProvider.class,
            method = "dynamicSQL"
    )
    T findUniqu(String property, Object value);

    @SelectProvider(
            type = FindMapper.FindProvider.class,
            method = "dynamicSQL"
    )
    List<T> getShardingAll(String property, Object value);

    class FindSqlSource implements SqlSource {
        private MappedStatement ms;
        private Class<?> entityClass;
        private String sql;
        private Map<String, Class<?>> fieldsTypes;
        private boolean unique;

        public FindSqlSource(MappedStatement ms, Class<?> entityClass, String sql, Map<String, Class<?>> fieldsTypes, boolean unique) {
            this.ms = ms;
            this.entityClass = entityClass;
            this.sql = sql;
            this.fieldsTypes = fieldsTypes;
            this.unique = unique;
        }

        public BoundSql getBoundSql(Object parameterObject) {
            Assert.isInstanceOf(MapperMethod.ParamMap.class, parameterObject);
            MapperMethod.ParamMap paramMap = (MapperMethod.ParamMap)parameterObject;
            String property = (String)paramMap.get("param1");
            SearchFilter searchFilter = SearchFilter.parse(property, paramMap.get("param2"));
            StringBuilder sqlResult = new StringBuilder();
            sqlResult.append(this.sql);
            sqlResult.append(" WHERE ");
            Class proType = this.fieldsTypes.get(searchFilter.fieldName);
            Assert.notNull(proType, "属性[" + searchFilter.fieldName + "]不存在");
            sqlResult.append(SearchFilterParser.parseSqlField(searchFilter, proType));
            if (this.unique) {
                sqlResult.append(" limit 1");
            }

            BoundSql boundSql = new BoundSql(this.ms.getConfiguration(), sqlResult.toString(), null, null);
            return boundSql;
        }
    }

    class FindProvider extends MapperTemplate {
        public FindProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
            super(mapperClass, mapperHelper);
        }

        public void find(MappedStatement ms) {
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

            FindMapper.FindSqlSource findSqlSource = new FindMapper.FindSqlSource(ms, entityClass, sql.toString(), fieldsTypes, false);
            this.setSqlSource(ms, findSqlSource);
        }

        public void findUniqu(MappedStatement ms) {
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

            FindMapper.FindSqlSource findSqlSource = new FindMapper.FindSqlSource(ms, entityClass, sql.toString(), fieldsTypes, true);
            this.setSqlSource(ms, findSqlSource);
        }

        public void getShardingAll(MappedStatement ms) {
            Class<?> entityClass = this.getEntityClass(ms);
            System.out.println("");
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
            FindMapper.FindSqlSource findSqlSource = new FindMapper.FindSqlSource(ms, entityClass, sql.toString(), fieldsTypes, false);
            this.setSqlSource(ms, findSqlSource);
        }
    }
}
