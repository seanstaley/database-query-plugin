package com.jivesoftware.plugin.dbQuery.action.query;

import com.jivesoftware.community.analytics.action.AnalyticsActionSupport;
import com.jivesoftware.plugin.dbQuery.dao.impl.ApplicationExecutionDaoImpl;
import com.jivesoftware.plugin.dbQuery.service.QueryFormatService;
import com.jivesoftware.plugin.dbQuery.service.QueryValidationService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;

import java.util.ArrayList;

/**
 * StaleyLabs
 *
 * @author Sean M. Staley
 * Date: 5/15/12
 * Time: 7:24 PM
 */
public class RunAnalyticsQueryAction extends AnalyticsActionSupport {
    private static final long serialVersionUID = 1L;
    private static Logger log = Logger.getLogger(RunAnalyticsQueryAction.class);

    private String databaseQuery;
    private ArrayList<ArrayList<String>> queryResults;
    private boolean isSelectQuery = true;
    private boolean isCleanQuery = true;
    private boolean isCompleted = true;
    private boolean isResults = true;

    @Autowired
    protected ApplicationExecutionDaoImpl analyticsExecutionDao;
    @Autowired
    protected QueryValidationService validationService;
    @Autowired
    protected QueryFormatService formatService;

    @Override
    public String execute() {

        //Is the box blank? Cereal?!
        if (getDatabaseQuery() == null) {
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
            queryResults = formatService.returnQueryResults(databaseQuery);
        }
        catch (BadSqlGrammarException e) {
            log.error("Database Query Plugin: Bad SQL grammar when querying Application Database by " +
                    getUser().getUsername(), e);
            setCompleted(false);
            setCleanQuery(false);
            return INPUT;
        }

        if (queryResults.get(0).get(0).equals(analyticsExecutionDao.NO_RESULTS)) {
            setIsResults(false);
        }
        return SUCCESS;
    }

    public void setDatabaseQuery(String databaseQuery) {
        this.databaseQuery = databaseQuery;
    }

    public String getDatabaseQuery() {
        return databaseQuery;
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

    public ArrayList<ArrayList<String>> getQueryResults() {
        return queryResults;
    }

    public void setQueryResults(ArrayList<ArrayList<String>> queryResults) {
        this.queryResults = queryResults;
    }

    public boolean isResults() {
        return this.isResults;
    }

    public void setIsResults(boolean results) {
        this.isResults = results;
    }
}