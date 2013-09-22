package com.staleylabs.query.beans;

import com.google.common.collect.Lists;

import java.util.List;

/**
 * Bean used to represent a page of database query results.
 *
 * @author Sean M. Staley
 * @version 2.0 (9/21/13)
 */

public class QueryPage<E> {

    private int pageNumber;

    private int pagesAvailable;

    private List<E> pageItems = Lists.newLinkedList();

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }

    public int getPagesAvailable() {
        return pagesAvailable;
    }

    public void setPagesAvailable(int pagesAvailable) {
        this.pagesAvailable = pagesAvailable;
    }

    public List<E> getPageItems() {
        return pageItems;
    }

    public void setPageItems(List<E> pageItems) {
        this.pageItems = pageItems;
    }

    @Override
    public String toString() {
        return "QueryPage{" +
                "pageNumber=" + pageNumber +
                ", pagesAvailable=" + pagesAvailable +
                ", pageItems=" + pageItems +
                '}';
    }
}
