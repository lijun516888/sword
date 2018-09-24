//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sword.module.mybatis.common;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import org.apache.commons.lang3.StringUtils;
import org.springframework.util.Assert;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class SearchFilter {
    public String fieldName;
    public Object value;
    public SearchFilter.Operator operator;

    public SearchFilter(String fieldName, SearchFilter.Operator operator, Object value) {
        this.fieldName = fieldName;
        this.value = value;
        this.operator = operator;
    }

    public static List<SearchFilter> parse(Map<String, Object> searchParams) {
        List<SearchFilter> filters = Lists.newArrayList();
        Iterator var2 = searchParams.entrySet().iterator();

        while(var2.hasNext()) {
            Entry<String, Object> entry = (Entry)var2.next();
            SearchFilter searchFilter = parse((String)entry.getKey(), entry.getValue());
            if (searchFilter != null) {
                filters.add(searchFilter);
            }
        }

        return filters;
    }

    public static SearchFilter parse(String param, Object value) {
        Assert.hasText(param);
        String[] names = StringUtils.split(param, "_");
        if (names.length != 2) {
            throw new IllegalArgumentException(param + " is not a valid search filter name");
        } else {
            SearchFilter.Operator op = SearchFilter.Operator.valueOf(names[0].toUpperCase());
            return (value == null || Strings.isNullOrEmpty(value.toString())) && op != SearchFilter.Operator.NULL && op != SearchFilter.Operator.NOTNULL ? null : new SearchFilter(names[1], op, value);
        }
    }

    public static enum Operator {
        EQ,
        NEQ,
        LIKE,
        LLIKE,
        RLIKE,
        GT,
        LT,
        GTE,
        LTE,
        IN,
        NOTIN,
        NULL,
        NOTNULL;

        private Operator() {
        }
    }
}
