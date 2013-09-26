package com.staleylabs.query.action.query;

import com.jivesoftware.community.action.admin.AdminActionSupport;
import com.jivesoftware.util.StringUtils;
import com.staleylabs.query.dao.ApplicationQueryExecutionDao;
import com.staleylabs.query.service.QueryFormatService;
import com.staleylabs.query.service.QueryValidationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;

import java.util.List;

/**
 * Controller used for Running an application database query against the Jive application.
 *
 * @author Sean M. Staley
 * @version 2.0 (5/14/12)
 */
public class RunQueryAction extends AdminActionSupport {

    private static final Logger log = Logger.getLogger(RunQueryAction.class);

    private String databaseQuery;

    private List<List<String>> queryResults;

    private boolean isSelectQuery = true;

    private boolean isCleanQuery = true;

    private boolean isCompleted = true;

    private boolean isResults = true;

    private int currentPage;

    private int resultsPerPage;

    private int totalPages = 1;

    @Autowired
    private QueryValidationService validationService;

    @Autowired
    private QueryFormatService formatService;

    @Override
    public String execute() {
        if (StringUtils.isBlank(getDatabaseQuery())) {
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
            setDatabaseQuery(databaseQuery.toUpperCase());
            setTotalPages(formatService.getTotalQueryPages());
        } catch (BadSqlGrammarException e) {
            log.error("Database Query Plugin: Bad SQL grammar when querying Application Database by " + getUser().getUsername(), e);
            setCompleted(false);
            setIsCleanQuery(false);

            return INPUT;
        }

        if (queryResults.get(0).get(0).equals(ApplicationQueryExecutionDao.NO_RESULTS)) {
            setIsResults(false);
        }

        return SUCCESS;
    }

    public String getDatabaseQuery() {
        return StringUtils.trimToEmpty(databaseQuery);
    }

    public void setDatabaseQuery(String databaseQuery) {
        this.databaseQuery = databaseQuery;
    }

    public List<List<String>> getQueryResults() {
        return queryResults;
    }

    public void setQueryResults(List<List<String>> queryResults) {
        this.queryResults = queryResults;
    }

    public boolean isSelectQuery() {
        return isSelectQuery;
    }

    public void setSelectQuery(boolean selectQuery) {
        isSelectQuery = selectQuery;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        this.isCompleted = completed;
    }

    public boolean isCleanQuery() {
        return isCleanQuery;
    }

    public void setIsCleanQuery(boolean isCleanQuery) {
        this.isCleanQuery = isCleanQuery;
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

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }
}