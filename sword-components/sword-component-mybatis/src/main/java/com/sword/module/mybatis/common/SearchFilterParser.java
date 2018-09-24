package com.sword.module.mybatis.common;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.sword.core.utils.Dates;
import com.sword.core.utils.Strings;
import org.springframework.core.convert.ConversionService;
import org.springframework.util.CollectionUtils;

import java.util.Date;
import java.util.Iterator;
import java.util.List;

public class SearchFilterParser {
    private static final ConversionService conversionService;
    private static final char UNDERLINE = '_';

    public static String parseSqlField(SearchFilter searchFilter, Class proType) {
        StringBuilder sb = new StringBuilder();
        sb.append(" ");
        sb.append(Strings.camelToUnderline(searchFilter.fieldName));
        sb.append(" ");
        Object value = convert(searchFilter, proType);
        if (value instanceof Date) {
            value = Dates.format((Date)value);
        }

        switch(searchFilter.operator) {
            case EQ:
                if (value instanceof String) {
                    sb.append(" = '" + value + "'");
                } else {
                    sb.append(" = " + value);
                }
                break;
            case NEQ:
                if (value instanceof String) {
                    sb.append(" != '" + value + "'");
                } else {
                    sb.append(" != " + value);
                }
                break;
            case LIKE:
                sb.append(" like '%" + value + "%'");
                break;
            case LLIKE:
                sb.append(" like '%" + value + "'");
                break;
            case RLIKE:
                sb.append(" like '" + value + "%'");
                break;
            case GT:
                if (value instanceof String) {
                    sb.append(" > '" + value + "'");
                } else {
                    sb.append(" > " + value);
                }
                break;
            case LT:
                if (value instanceof String) {
                    sb.append(" < '" + value + "'");
                } else {
                    sb.append(" < " + value);
                }
                break;
            case GTE:
                if (value instanceof String) {
                    sb.append(" >= '" + value + "'");
                } else {
                    sb.append(" >= " + value);
                }
                break;
            case LTE:
                if (value instanceof String) {
                    sb.append(" <= '" + value + "'");
                } else {
                    sb.append(" <= " + value);
                }
                break;
            case NULL:
                sb.append(" is null ");
                break;
            case NOTNULL:
                sb.append(" is not null ");
                break;
            case IN:
                sb.append(" in (");
                genInClause(proType, sb, (List)value);
                break;
            case NOTIN:
                sb.append(" not in (");
                genInClause(proType, sb, (List)value);
        }

        return sb.toString();
    }

    private static void genInClause(Class proType, StringBuilder sb, List value) {
        StringBuilder tmp;
        Iterator var4;
        Object o;
        if (Number.class.isAssignableFrom(proType)) {
            tmp = new StringBuilder();
            var4 = value.iterator();

            while(var4.hasNext()) {
                o = var4.next();
                tmp.append(o + ",");
            }

            tmp.deleteCharAt(tmp.length() - 1);
            sb.append(tmp.toString()).append(")");
        } else {
            tmp = new StringBuilder();
            var4 = value.iterator();

            while(var4.hasNext()) {
                o = var4.next();
                tmp.append("'" + o + "',");
            }

            tmp.deleteCharAt(tmp.length() - 1);
            sb.append(tmp.toString()).append(")");
        }

    }

    private static Object convert(SearchFilter searchFilter, Class proType) {
        if (proType.isEnum()) {
            if (searchFilter.value instanceof String) {
                return searchFilter.value;
            } else {
                return searchFilter.value instanceof Enum ? ((Enum)searchFilter.value).name() : searchFilter.value;
            }
        } else {
            Object value = null;
            if (searchFilter.operator != SearchFilter.Operator.IN && searchFilter.operator != SearchFilter.Operator.NOTIN) {
                if (searchFilter.value != null) {
                    if (!proType.isAssignableFrom(searchFilter.value.getClass())) {
                        if (searchFilter.operator == SearchFilter.Operator.LTE && proType.isAssignableFrom(java.sql.Date.class)) {
                            String oriValue = (String)searchFilter.value;
                            if (oriValue.length() == "yyyy-MM-dd".length()) {
                                searchFilter.value = Dates.addDay(Dates.parse(oriValue));
                            }
                        }

                        value = conversionService.convert(searchFilter.value, proType);
                    } else {
                        value = searchFilter.value;
                    }
                } else if (searchFilter.operator != SearchFilter.Operator.NULL && searchFilter.operator != SearchFilter.Operator.NOTNULL) {
                    throw new RuntimeException("操作符[" + searchFilter.operator + "]时，值不能为null");
                }
            } else {
                if (searchFilter.value == null) {
                    throw new RuntimeException("操作符[" + searchFilter.operator + "]时，值不能为null");
                }

                if (!Number.class.isAssignableFrom(proType) && !String.class.isAssignableFrom(proType)) {
                    throw new RuntimeException("操作符[" + searchFilter.operator + "]时，支持属性为String或者数字");
                }

                Object tmp;
                if (searchFilter.value.getClass().isArray()) {
                    tmp = CollectionUtils.arrayToList(searchFilter.value);
                } else if (List.class.isAssignableFrom(searchFilter.value.getClass())) {
                    tmp = (List)searchFilter.value;
                } else {
                    if (!String.class.isAssignableFrom(searchFilter.value.getClass())) {
                        throw new RuntimeException("操作符[" + searchFilter.operator + "]时，支持参数类型为数组，list，String，value=" + searchFilter.value);
                    }

                    tmp = Lists.newArrayList(Splitter.on(",").trimResults().splitToList((String)searchFilter.value));
                }

                if (((List)tmp).isEmpty()) {
                    throw new RuntimeException("操作符[" + searchFilter.operator + "]时，集合不能为空，value=" + searchFilter.value);
                }

                List result = Lists.newArrayList();

                for(int i = 0; i < ((List)tmp).size(); ++i) {
                    Object cur = ((List)tmp).get(i);
                    if (!proType.isAssignableFrom(cur.getClass())) {
                        result.add(conversionService.convert(cur, proType));
                    } else {
                        result.add(cur);
                    }
                }

                value = result;
            }

            return value;
        }
    }

    static {
        conversionService = EnhanceDefaultConversionService.INSTANCE;
    }
}
