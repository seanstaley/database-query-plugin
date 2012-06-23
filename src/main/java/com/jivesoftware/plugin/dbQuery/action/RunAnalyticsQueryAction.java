package com.jivesoftware.plugin.dbQuery.action;

import com.jivesoftware.community.analytics.action.AnalyticsActionSupport;
import com.jivesoftware.plugin.dbQuery.dao.AnalyticsQueryExecute;
import org.apache.log4j.Logger;
import org.springframework.jdbc.BadSqlGrammarException;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Sean M. Staley
 * Date: 5/15/12
 * Time: 7:24 PM
 */
public class RunAnalyticsQueryAction extends AnalyticsActionSupport {
    Logger log = Logger.getLogger(RunAnalyticsQueryAction.class);

    private String databaseQuery;
    private boolean isSelectQuery, isCleanQuery, isCompleted;
    private List<Map<String, Object>> queryResults;

    private AnalyticsQueryExecute analyticsQueryExecute;

    @Override
    public String execute() {
        setSelectQuery(true);
        setCleanQuery(true);
        setCompleted(true);

        if (getDatabaseQuery() == null){
            setCompleted(false);
            return INPUT;
        }
        else if (!analyticsQueryExecute.validateSelectQuery(databaseQuery)) {
            log.info("Query did not begin with a SELECT. Try again...");
            setSelectQuery(false);
            return INPUT;
        }

        try{
            queryResults = analyticsQueryExecute.runQuery(databaseQuery);
        } catch (BadSqlGrammarException e) {
            log.error("Database Query Plugin: Bad SQL grammar when querying the analytics database by " + getUser().getUsername(), e);
            setCompleted(false);
            setCleanQuery(false);
        }
        return SUCCESS;
    }

    public void setDatabaseQuery(String databaseQuery){
        this.databaseQuery = databaseQuery;
    }

    public String getDatabaseQuery(){
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

    public List<Map<String, Object>> getQueryResults(){
        return queryResults;
    }

    public void setQueryResults(List<Map<String, Object>> queryResults){
        this.queryResults = queryResults;
    }

    public AnalyticsQueryExecute getAnalyticsQueryExecute() {
        return analyticsQueryExecute;
    }

    public void setAnalyticsQueryExecute(AnalyticsQueryExecute analyticsQueryExecute) {
        this.analyticsQueryExecute = analyticsQueryExecute;
    }
}