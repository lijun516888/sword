//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sword.module.mybatis.page;

import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@NoArgsConstructor
public class MyBatisPage<E> extends ArrayList<E> {
    private static final long serialVersionUID = -1426723793570627510L;
    private long totalCount;
    private long totalPage;
    private int currentPage;
    private int countOfCurrentPage;

    public MyBatisPage(List<E> list, long totalCount, long totalPage) {
        this.addAll(list);
        this.totalCount = totalCount;
        this.totalPage = totalPage;
    }

    public long getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getTotalPage() {
        return this.totalPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getCountOfCurrentPage() {
        return this.countOfCurrentPage;
    }

    public void setCountOfCurrentPage(int countOfCurrentPage) {
        this.countOfCurrentPage = countOfCurrentPage;
    }
}
