package com.sword.cloud.user.domain;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@NoArgsConstructor
@Data
@ToString
@EqualsAndHashCode
public class UserDomain implements Serializable {

    private Long id;
    private String name;
    private Integer age;
}
