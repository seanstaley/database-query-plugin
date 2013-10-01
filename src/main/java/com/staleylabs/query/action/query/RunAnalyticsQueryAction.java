package com.staleylabs.query.action.query;

import com.jivesoftware.community.analytics.action.AnalyticsActionSupport;
import com.jivesoftware.community.entitlements.authorization.RequiresRole;
import com.jivesoftware.util.StringUtils;
import com.staleylabs.query.dto.QueryResultTO;
import com.staleylabs.query.service.QueryService;
import com.staleylabs.query.validator.QueryValidator;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * StaleyLabs
 *
 * @author Sean M. Staley
 * @version 2.0
 * @since 1.0 (5/15/12)
 */

@RequiresRole("Manage System")
public class RunAnalyticsQueryAction extends AnalyticsActionSupport {

    private QueryResultTO result;

    private String databaseQuery;

    private boolean isSelectQuery = true;

    private boolean isCompleted;

    private boolean isResults = true;

    private int currentPage;

    private int resultsPerPage;

    @Autowired
    private QueryService queryService;

    @Override
    public String execute() {
        // Parameter validation
        if (StringUtils.isBlank(databaseQuery)) {
            return INPUT;
        }

        if (QueryValidator.isNotSelectQuery(databaseQuery)) {
            isSelectQuery = false;
            return INPUT;
        }

        if (currentPage < 1) {
            currentPage = 1;
        }

        if (resultsPerPage < 10 || resultsPerPage > 50) {
            resultsPerPage = 10;
        }

        result = queryService.returnAnalyticQueryResult(databaseQuery, currentPage, resultsPerPage);
        isCompleted = true;

        if (result.getTotalPages() < 1) {
            isResults = false;
        }

        return SUCCESS;
    }

    public void setDatabaseQuery(String databaseQuery) {
        this.databaseQuery = databaseQuery.trim();
    }

    public boolean isSelectQuery() {
        return isSelectQuery;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public boolean isResults() {
        return this.isResults;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = (currentPage >= 1) ? currentPage : 1;
    }

    public void setResultsPerPage(int resultsPerPage) {
        this.resultsPerPage = (resultsPerPage >= 10) ? resultsPerPage : 10;
    }

    public QueryResultTO getResult() {
        return result;
    }

    public void setResult(QueryResultTO result) {
        this.result = result;
    }
}