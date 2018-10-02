package com.sword.module.mybatis.common.domain;

import javax.persistence.Id;
import java.util.Objects;

public class ShardingBaseDomain extends BaseDomain {

    @Id
    private Long tid;

    public Long getTid() {
        return tid;
    }

    public void setTid(Long tid) {
        if(Objects.isNull(this.tid)) {
            this.tid = tid;
        }
    }
}
