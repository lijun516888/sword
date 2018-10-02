package com.sword.app.domain;

import com.sword.module.mybatis.common.domain.ShardingBaseDomain;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;

@Setter
@Getter
@ToString
@Table(name = "t_item")
public class ItemDomain extends ShardingBaseDomain {

    private String name;
}
