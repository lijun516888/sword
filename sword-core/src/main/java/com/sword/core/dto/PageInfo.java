//
// Source code recreated from a .class file by IntelliJ IDEA
// (powered by Fernflower decompiler)
//

package com.sword.core.dto;

import com.google.common.collect.Lists;
import com.sword.core.utils.bean.BeanCopier;

import java.io.Serializable;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class PageInfo<T> implements Serializable {
    private static final long serialVersionUID = -168321987075635774L;
    private List<T> pageResults;
    private int countOfCurrentPage = 10;
    private int currentPage;
    private long totalCount;
    private long totalPage;

    public PageInfo() {
        this.currentPage = 1;
        this.countOfCurrentPage = 10;
        this.totalCount = 0L;
        this.totalPage = 0L;
    }

    public PageInfo(int countOfCurrentPage, int currentPage) {
        this.countOfCurrentPage = countOfCurrentPage;
        this.currentPage = currentPage;
    }

    public boolean hasNext() {
        return (long)this.currentPage < this.totalPage;
    }

    public boolean isNext() {
        return this.hasNext();
    }

    public boolean hasPrevious() {
        return this.currentPage != 1;
    }

    public boolean isPrevious() {
        return this.hasPrevious();
    }

    public int getCountOfCurrentPage() {
        return this.countOfCurrentPage;
    }

    public void setCountOfCurrentPage(int countOfCurrentPage) {
        this.countOfCurrentPage = countOfCurrentPage;
    }

    public int getCurrentPage() {
        return this.currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public List<T> getPageResults() {
        return this.pageResults;
    }

    public void setPageResults(List<T> pageResults) {
        this.pageResults = pageResults;
    }

    public long getTotalCount() {
        return this.totalCount;
    }

    public void setTotalCount(long totalCount) {
        this.totalCount = totalCount;
    }

    public long getTotalPage() {
        return (this.totalCount + (long)this.countOfCurrentPage - 1L) / (long)this.countOfCurrentPage;
    }

    public void setTotalPage(long totalPage) {
        this.totalPage = totalPage;
    }

    public void calPageResults(List<T> results) {
        if (results == null) {
            throw new IllegalArgumentException("null argument!");
        } else {
            int iDatasSize = results.size();
            int iOffset;
            if (iDatasSize >= this.countOfCurrentPage * this.currentPage) {
                iOffset = this.countOfCurrentPage;
            } else {
                iOffset = iDatasSize - this.countOfCurrentPage * (this.currentPage - 1);
            }

            int iStart = this.countOfCurrentPage * (this.currentPage - 1);
            this.pageResults = results.subList(iStart, iStart + iOffset);
        }
    }

    public String toString() {
        return String.format("{pageResults:%s, countOfCurrentPage:%s, totalCount:%s}", this.pageResults, this.countOfCurrentPage, this.totalCount);
    }

    public <E> PageInfo<E> to(Class<E> clazz) {
        PageInfo<E> info = new PageInfo();
        info.setTotalPage(this.totalPage);
        info.setTotalCount(this.totalCount);
        info.setCurrentPage(this.currentPage);
        info.setCountOfCurrentPage(this.countOfCurrentPage);
        if (this.pageResults != null && !this.pageResults.isEmpty()) {
            List<E> list = Lists.newArrayListWithCapacity(this.pageResults.size());
            Iterator var4 = this.pageResults.iterator();

            while(var4.hasNext()) {
                T pageResult = (T) var4.next();
                list.add(BeanCopier.copy(pageResult, clazz, BeanCopier.CopyStrategy.IGNORE_NULL, new String[0]));
            }

            info.setPageResults(list);
        } else {
            info.setPageResults(Collections.emptyList());
        }

        return info;
    }
}
