package com.jivesoftware.plugin.dbQuery.action;

import com.jivesoftware.community.JiveGlobals;
import com.jivesoftware.community.action.admin.AdminActionSupport;
import com.jivesoftware.plugin.dbQuery.dao.AnalyticsQueryExecute;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Sean M. Staley
 * Date: 5/15/12
 * Time: 7:24 PM
 */
public class RunAnalyticsQueryAction extends AdminActionSupport {

    Logger log = Logger.getLogger(RunAnalyticsQueryAction.class);

    public static final String ANALYTICS_ENABLED = "__jive.analytics.enabled";

    private String databaseQuery;
    private boolean isAnalyticsEnabled;
    private List<Map<String, Object>> queryResults;
    private AnalyticsQueryExecute analyticsQueryExecute;

    @Override
    public String execute() {
        if (isAnalyticsEnabled){
            setIsAnalyticsEnabled(true);
            if (getDatabaseQuery() == null){
                return INPUT;
            }

            if (!analyticsQueryExecute.validateSelectQuery(databaseQuery)) {
                log.info("Query did not begin with a SELECT. Try again...");
                return INPUT;
            }

            queryResults = analyticsQueryExecute.runQuery(databaseQuery);
        }

        return SUCCESS;
    }

    public void setDatabaseQuery(String databaseQuery){
        this.databaseQuery = databaseQuery;
    }

    public String getDatabaseQuery(){
        return databaseQuery;
    }

    public List<Map<String, Object>> getQueryResults(){
        return queryResults;
    }

    public void setQueryResults(List<Map<String, Object>> queryResults){
        this.queryResults = queryResults;
    }

    public boolean isAnalyticsEnabled() {
        return "true".equals(JiveGlobals.getJiveBooleanProperty(ANALYTICS_ENABLED));
    }

    public void setIsAnalyticsEnabled(boolean isAnalyticsEnabled) {
        this.isAnalyticsEnabled = isAnalyticsEnabled;
    }

    public AnalyticsQueryExecute getAnalyticsQueryExecute() {
        return analyticsQueryExecute;
    }

    public void setAnalyticsQueryExecute(AnalyticsQueryExecute analyticsQueryExecute) {
        this.analyticsQueryExecute = analyticsQueryExecute;
    }
}