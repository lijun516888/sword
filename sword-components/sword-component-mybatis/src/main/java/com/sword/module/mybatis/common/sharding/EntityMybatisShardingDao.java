package com.sword.module.mybatis.common.sharding;

import com.sword.module.mybatis.common.EntityDao;
import com.sword.module.mybatis.mapper.*;
import tk.mybatis.mapper.common.Marker;

public interface EntityMybatisShardingDao<T> extends EntityDao<T>, Marker, CreateMapper<T>, GetShardingMapper<T>, UpdateMapper<T>, RemoveMapper<T>, FindMapper<T>, ListMapper<T> {

}
