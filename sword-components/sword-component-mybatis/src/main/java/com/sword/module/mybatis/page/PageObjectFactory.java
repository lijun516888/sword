//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sword.module.mybatis.page;

import com.sword.core.dto.PageInfo;
import lombok.NoArgsConstructor;
import org.apache.ibatis.reflection.factory.DefaultObjectFactory;

@NoArgsConstructor
public class PageObjectFactory extends DefaultObjectFactory {
    private static final long serialVersionUID = -1082960721558661578L;

    public <T> T create(Class<T> type) {
        return type == PageInfo.class ? (T) new PageInfo() : this.create(type, null, null);
    }

    public <T> boolean isCollection(Class<T> type) {
        return type == PageInfo.class ? true : super.isCollection(type);
    }
}
