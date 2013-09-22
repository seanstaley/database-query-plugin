package com.staleylabs.query.action.query;

import com.jivesoftware.community.analytics.action.AnalyticsActionSupport;
import com.jivesoftware.util.StringUtils;
import com.staleylabs.query.dao.ApplicationQueryExecutionDao;
import com.staleylabs.query.dao.impl.ApplicationQueryExecutionDaoImpl;
import com.staleylabs.query.service.QueryFormatService;
import com.staleylabs.query.service.QueryValidationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;

import java.util.List;

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
    protected ApplicationQueryExecutionDaoImpl analyticsExecutionDao;

    @Autowired
    protected QueryValidationService validationService;

    @Autowired
    protected QueryFormatService formatService;

    private String databaseQuery;

    private List<List<String>> queryResults;

    private boolean isSelectQuery = true;

    private boolean isCleanQuery = true;

    private boolean isCompleted = true;

    private boolean isResults = true;

    private int currentPage;

    private int resultsPerPage;

    @Override
    public String execute() {

        //Is the box blank? Cereal?!
        if (StringUtils.isBlank(databaseQuery)) {
            setCompleted(false);
            return INPUT;
        }

        //Is the query NOT a SELECT query?
        else if (!validationService.validateSelectQuery(databaseQuery)) {
            setSelectQuery(false);
            setCompleted(false);
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

            queryResults = formatService.returnQueryResults(databaseQuery, getCurrentPage(), getResultsPerPage());
        } catch (BadSqlGrammarException e) {
            log.error("Database Query Plugin: Bad SQL grammar when querying Application Database by " + getUser().getUsername(), e);
            setCompleted(false);
            setCleanQuery(false);

            return INPUT;
        }

        if (queryResults.get(0).get(0).equals(ApplicationQueryExecutionDao.NO_RESULTS)) {
            setIsResults(false);
        }

        return SUCCESS;
    }

    public String getDatabaseQuery() {
        return databaseQuery;
    }

    public void setDatabaseQuery(String databaseQuery) {
        this.databaseQuery = databaseQuery;
    }

    public boolean isSelectQuery() {
        return isSelectQuery;
    }

    public void setSelectQuery(boolean selectQuery) {
        isSelectQuery = selectQuery;
    }

    public boolean isCleanQuery() {
        return isCleanQuery;
    }

    public void setCleanQuery(boolean cleanQuery) {
        isCleanQuery = cleanQuery;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

    public List<List<String>> getQueryResults() {
        return queryResults;
    }

    public void setQueryResults(List<List<String>> queryResults) {
        this.queryResults = queryResults;
    }

    public boolean isResults() {
        return this.isResults;
    }

    public void setIsResults(boolean results) {
        this.isResults = results;
    }

    public int getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = (currentPage >= 1) ? currentPage : 1;
    }

    public int getResultsPerPage() {
        return resultsPerPage;
    }

    public void setResultsPerPage(int resultsPerPage) {
        this.resultsPerPage = (resultsPerPage >= 10) ? resultsPerPage : 10;
    }
}