package com.staleylabs.query.action.query;

import com.jivesoftware.community.analytics.action.AnalyticsActionSupport;
import com.jivesoftware.util.StringUtils;
import com.staleylabs.query.dto.QueryResultTO;
import com.staleylabs.query.service.QueryService;
import com.staleylabs.query.validator.QueryValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;

/**
 * StaleyLabs
 *
 * @author Sean M. Staley
 * @version 2.0
 * @since 1.0 (5/15/12)
 */

public class RunAnalyticsQueryAction extends AnalyticsActionSupport {

    private static final Logger log = Logger.getLogger(RunAnalyticsQueryAction.class);

    @Autowired
    protected QueryService queryService;

    private String databaseQuery;

    private QueryResultTO result;

    private boolean isSelectQuery = true;

    private boolean isCleanQuery = true;

    private boolean isCompleted;

    private boolean isResults = true;

    private int currentPage;

    private int resultsPerPage;

    @Override
    public String execute() {

        //Is the box blank? Cereal?!
        if (StringUtils.isBlank(databaseQuery)) {
            return INPUT;
        }

        //Is the query NOT a SELECT query?
        else if (QueryValidator.isNotSelectQuery(databaseQuery)) {
            isSelectQuery = false;
            return INPUT;
        }

        //Catching dirty SQL talk and running a nice query.
        try {
            if (currentPage < 1) {
                setCurrentPage(1);
            }

            if (resultsPerPage < 1) {
                setResultsPerPage(10);
            }

            result = queryService.returnAnalyticQueryResult(databaseQuery, currentPage, resultsPerPage);
            isCompleted = true;
        } catch (BadSqlGrammarException e) {
            log.error("Bad SQL grammar when querying Analytics Database:\n" + databaseQuery, e);
            isCleanQuery = false;

            return INPUT;
        }

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

    public boolean isCleanQuery() {
        return isCleanQuery;
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