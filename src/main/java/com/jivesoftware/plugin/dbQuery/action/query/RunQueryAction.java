package com.jivesoftware.plugin.dbQuery.action.query;

import com.jivesoftware.community.action.admin.AdminActionSupport;
import com.jivesoftware.plugin.dbQuery.dao.query.QueryExecute;
import org.apache.log4j.Logger;
import org.springframework.jdbc.BadSqlGrammarException;

import java.util.ArrayList;

/**
 * Created by IntelliJ IDEA.
 * User: Sean M. Staley
 * Date: 5/14/12
 * Time: 11:24 AM
 */
public class RunQueryAction extends AdminActionSupport {
    private static final long serialVersionUID = 7695055626966332768L;
    Logger log = Logger.getLogger(RunQueryAction.class);

    private String databaseQuery;
    private ArrayList<ArrayList<String>> queryResults;
    private QueryExecute queryExecute;
    private boolean isSelectQuery = true;
    private boolean isCleanQuery = true;
    private boolean isCompleted = true;
    private boolean isResults = true;

    @Override
    public String execute() {
        //Is the box blank? Cereal?!
        if (getDatabaseQuery() == null) {
            setCompleted(false);
            return INPUT;
        }

        //Is the query NOT a SELECT query?
        else if (!queryExecute.validateSelectQuery(databaseQuery)) {
            setSelectQuery(false);
            setCompleted(false);
            return INPUT;
        }

        //Catching dirty SQL talk and running a nice query.
        try {
            queryResults = queryExecute.returnQueryResults(databaseQuery);
        }
        catch (BadSqlGrammarException e) {
            log.error("Database Query Plugin: Bad SQL grammar when querying Application Database by " +
                    getUser().getUsername(), e);
            setCompleted(false);
            setIsCleanQuery(false);
            return INPUT;
        }

        if (queryResults.get(0).get(0).toString().equals(queryExecute.NO_RESULTS)) {
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

    public ArrayList<ArrayList<String>> getQueryResults() {
        return queryResults;
    }

    public void setQueryResults(ArrayList<ArrayList<String>> queryResults) {
        this.queryResults = queryResults;
    }

    public QueryExecute getQueryExecute() {
        return queryExecute;
    }

    public void setQueryExecute(QueryExecute queryExecute) {
        this.queryExecute = queryExecute;
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