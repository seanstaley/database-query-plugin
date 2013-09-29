package com.staleylabs.query.action.query;

import com.jivesoftware.community.action.admin.AdminActionSupport;
import com.jivesoftware.util.StringUtils;
import com.staleylabs.query.dto.QueryResultTO;
import com.staleylabs.query.service.QueryService;
import com.staleylabs.query.validator.QueryValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Controller used for Running an application database query against the Jive application.
 *
 * @author Sean M. Staley
 * @version 2.0 (5/14/12)
 */

@SuppressWarnings("FieldCanBeLocal")
public class RunQueryAction extends AdminActionSupport {

    private static final Logger log = Logger.getLogger(RunQueryAction.class);

    private QueryResultTO result;

    private String databaseQuery;

    private boolean isSelectQuery = true;

    private boolean isCleanQuery = true;

    private boolean isCompleted;

    private boolean isResults = true;

    private int currentPage;

    private int resultsPerPage;

    @Autowired
    private QueryService queryService;

    @Override
    public String execute() {
        if (StringUtils.isBlank(databaseQuery)) {
            return INPUT;
        }

        //Is the query NOT a SELECT query?
        else if (QueryValidator.isNotSelectQuery(databaseQuery)) {
            isSelectQuery = false;
            return INPUT;
        }

        if (currentPage < 1) {
            currentPage = 1;
        }

        if (resultsPerPage < 1) {
            resultsPerPage = 10;
        }

        result = queryService.returnQueryResult(databaseQuery, currentPage, resultsPerPage);

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