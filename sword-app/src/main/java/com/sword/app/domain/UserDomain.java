package com.sword.app.domain;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Setter
@Getter
@Table(name = "SYS_USER")
public class UserDomain {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String userName;
    private String password;
    private String salt;
    private Integer status;
    @Column(
            name = "create_time",
            columnDefinition = " timestamp  DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间'"
    )
    private Date createTime = new Date();
    @Column(
            name = "update_time",
            columnDefinition = "timestamp  DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间'"
    )
    private Date updateTime = new Date();
}
