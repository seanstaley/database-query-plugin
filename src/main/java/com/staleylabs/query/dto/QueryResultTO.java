package com.staleylabs.query.dto;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.Collections;
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

    private long totalPages;

    private int currentResultsPerPage;

    private boolean badSyntax;

    private String badSyntaxDescription;

    public QueryResultTO() {
        this.resultSet = Collections.emptyList();
    }

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

    public long getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(long totalPages) {
        this.totalPages = totalPages;
    }

    public int getCurrentResultsPerPage() {
        return currentResultsPerPage;
    }

    public void setCurrentResultsPerPage(int currentResultsPerPage) {
        this.currentResultsPerPage = currentResultsPerPage;
    }

    public boolean isBadSyntax() {
        return badSyntax;
    }

    public void setBadSyntax(boolean badSyntax) {
        this.badSyntax = badSyntax;
    }

    public String getBadSyntaxDescription() {
        return badSyntaxDescription;
    }

    public void setBadSyntaxDescription(String badSyntaxDescription) {
        this.badSyntaxDescription = badSyntaxDescription;
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("query", query)
                .append("currentPage", currentPage)
                .append("totalPages", totalPages)
                .append("currentResultsPerPage", currentResultsPerPage)
                .append("badSyntax", badSyntax)
                .append("badSyntaxDescription", badSyntaxDescription)
                .toString();
    }
}
