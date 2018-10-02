package com.sword.app.domain;

import com.sword.module.mybatis.common.domain.SimpleBaseDomain;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Table;

@Setter
@Getter
@ToString
@Table(name = "SYS_USER")
public class UserDomain extends SimpleBaseDomain {

    private String userName;
    private String password;
    private String salt;
    private Integer status;
}
