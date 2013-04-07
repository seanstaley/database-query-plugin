package com.jivesoftware.plugin.dbQuery.action.query;

import com.jivesoftware.community.action.admin.AdminActionSupport;
import com.jivesoftware.plugin.dbQuery.dao.AbstractApplicationExecutionDao;
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
 * @version 2.0 (5/14/12)
 */
public class RunQueryAction extends AdminActionSupport {
    private static final long serialVersionUID = 7695055626966332768L;
    private static Logger log = Logger.getLogger(RunQueryAction.class);

    private String databaseQuery;
    private ArrayList<ArrayList<String>> queryResults;
    private boolean isSelectQuery = true;
    private boolean isCleanQuery = true;
    private boolean isCompleted = true;
    private boolean isResults = true;

    @Autowired
    private AbstractApplicationExecutionDao applicationExecutionDao;
    @Autowired
    private QueryValidationService validationService;
    @Autowired
    private QueryFormatService formatService;

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
        } catch (BadSqlGrammarException e) {
            log.error("Database Query Plugin: Bad SQL grammar when querying Application Database by " +
                    getUser().getUsername(), e);
            setCompleted(false);
            setIsCleanQuery(false);
            return INPUT;
        }

        if (queryResults.get(0).get(0).toString().equals(applicationExecutionDao.NO_RESULTS)) {
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

    public ArrayList<ArrayList<String>> getQueryResults() {
        return queryResults;
    }

    public void setQueryResults(ArrayList<ArrayList<String>> queryResults) {
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
}