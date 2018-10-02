package com.sword.module.mybatis.mapper;

import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.UpdateProvider;
import org.apache.ibatis.mapping.MappedStatement;
import tk.mybatis.mapper.mapperhelper.MapperHelper;
import tk.mybatis.mapper.mapperhelper.SqlHelper;
import tk.mybatis.mapper.provider.base.BaseDeleteProvider;

import java.io.Serializable;

public interface RemoveMapper<T> {

    @UpdateProvider(
            type = RemoveMapper.RemoveProvider.class,
            method = "dynamicSQL"
    )
    void remove(T o);

    @UpdateProvider(
            type = RemoveMapper.RemoveProvider.class,
            method = "dynamicSQL"
    )
    void removeById(Serializable id);

    @UpdateProvider(
            type = RemoveMapper.RemoveProvider.class,
            method = "dynamicSQL"
    )
    void removeShardingById(@Param("id") Serializable id, @Param("tid") Serializable tid);

    @UpdateProvider(
            type = RemoveMapper.RemoveProvider.class,
            method = "dynamicSQL"
    )
    void removes(Serializable... ids);

    @UpdateProvider(
            type = RemoveMapper.RemoveProvider.class,
            method = "dynamicSQL"
    )
    void removesSharding(@Param("tid") Serializable tid, @Param("array") Serializable... ids);


    class RemoveProvider extends BaseDeleteProvider {
        public RemoveProvider(Class<?> mapperClass, MapperHelper mapperHelper) {
            super(mapperClass, mapperHelper);
        }

        public String removeById(MappedStatement ms) {
            return super.deleteByPrimaryKey(ms);
        }

        public String removeShardingById(MappedStatement ms) {
            return super.deleteByPrimaryKey(ms);
        }

        public String remove(MappedStatement ms) {
            return super.delete(ms);
        }

        public String removes(MappedStatement ms) {
            Class<?> entityClass = this.getEntityClass(ms);
            StringBuilder sql = new StringBuilder();
            sql.append(SqlHelper.deleteFromTable(entityClass, this.tableName(entityClass)));
            sql.append(" WHERE ID IN");
            sql.append("<foreach item=\"item\" index=\"index\" collection=\"array\" open=\"(\" separator=\",\" close=\")\">  \n #{item}  \n</foreach>  ");
            return sql.toString();
        }

        public String removesSharding(MappedStatement ms) {
            Class<?> entityClass = this.getEntityClass(ms);
            StringBuilder sql = new StringBuilder();
            sql.append(SqlHelper.deleteFromTable(entityClass, this.tableName(entityClass)));
            sql.append(" WHERE TID = #{tid}");
            sql.append(" AND ID IN");
            sql.append("<foreach item=\"item\" index=\"(index+1)\" collection=\"array\" open=\"(\" separator=\",\" close=\")\">  \n #{item}  \n</foreach>  ");
            return sql.toString();
        }
    }
}
