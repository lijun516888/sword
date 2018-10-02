package com.sword.module.mybatis.common.simple;

import com.sword.module.mybatis.common.EntityDao;
import com.sword.module.mybatis.mapper.*;
import tk.mybatis.mapper.common.Marker;

public interface EntityMybatisDao<T> extends EntityDao<T>, Marker, GetMapper<T>, UpdateMapper<T>, RemoveMapper<T>, FindMapper<T>, ListMapper<T> {

}
