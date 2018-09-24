package com.sword.module.mybatis.page;

import com.google.common.collect.Lists;
import com.sword.core.dto.PageInfo;
import org.apache.ibatis.reflection.MetaObject;
import org.apache.ibatis.reflection.factory.ObjectFactory;
import org.apache.ibatis.reflection.property.PropertyTokenizer;
import org.apache.ibatis.reflection.wrapper.ObjectWrapper;

import java.util.List;

public class PageObjectWrapper implements ObjectWrapper {
    private PageInfo pageInfo;

    public PageObjectWrapper(PageInfo pageInfo) {
        this.pageInfo = pageInfo;
    }

    public Object get(PropertyTokenizer prop) {
        return null;
    }

    public <E> void addAll(List<E> element) {
        MyBatisPage page = (MyBatisPage)element;
        this.pageInfo.setTotalCount(page.getTotalCount());
        this.pageInfo.setTotalPage(page.getTotalPage());
        this.pageInfo.setCountOfCurrentPage(page.getCountOfCurrentPage());
        this.pageInfo.setCurrentPage(page.getCurrentPage());
        this.pageInfo.setPageResults(Lists.newArrayList(page));
    }

    public void set(PropertyTokenizer prop, Object value) {
    }

    public String findProperty(String name, boolean useCamelCaseMapping) {
        return null;
    }

    public String[] getGetterNames() {
        return new String[0];
    }

    public String[] getSetterNames() {
        return new String[0];
    }

    public Class<?> getSetterType(String name) {
        return null;
    }

    public Class<?> getGetterType(String name) {
        return null;
    }

    public boolean hasSetter(String name) {
        return false;
    }

    public boolean hasGetter(String name) {
        return false;
    }

    public MetaObject instantiatePropertyValue(String name, PropertyTokenizer prop, ObjectFactory objectFactory) {
        return null;
    }

    public boolean isCollection() {
        return false;
    }

    public void add(Object element) {
    }
}
