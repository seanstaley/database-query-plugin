package com.staleylabs.query.dto;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.List;

/**
 * A DTO object that is used to represent a given result set provided back from the user and data source.
 *
 * @author Sean M. Staley
 * @version 2.0 (9/26/13)
 */

public class QueryResultTO {

    private String query;

    private List<List<String>> resultSet;

    private int currentPage;

    private int totalPages;

    private int currentResultsPerPage;

    public String getQuery() {
        return query;
    }

    public void setQuery(String query) {
        this.query = query;
    }

    public List<List<String>> getResultSet() {
        return resultSet;
    }

    public void setResultSet(List<List<String>> resultSet) {
        this.resultSet = resultSet;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentResultsPerPage() {
        return currentResultsPerPage;
    }

    public void setCurrentResultsPerPage(int currentResultsPerPage) {
        this.currentResultsPerPage = currentResultsPerPage;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("query", query)
                .append("currentPage", currentPage)
                .append("totalPages", totalPages)
                .append("currentResultsPerPage", currentResultsPerPage)
                .toString();
    }
}
