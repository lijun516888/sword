package com.sword.core.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Setter
@Getter
@NoArgsConstructor
public class JsonEntity<T> implements Serializable {

    private String message;
    private boolean success;
    private T entity;
}
