//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sword.module.mybatis.common;

import org.springframework.core.convert.support.DefaultConversionService;

public class EnhanceDefaultConversionService extends DefaultConversionService {
    public static EnhanceDefaultConversionService INSTANCE = new EnhanceDefaultConversionService();

    private EnhanceDefaultConversionService() {
        // this.addConverter(new StringToDateConverter());
        // this.addConverter(new StringToMoneyConverter());
    }
}
