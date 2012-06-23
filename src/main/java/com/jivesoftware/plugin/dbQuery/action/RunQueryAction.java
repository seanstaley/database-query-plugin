package com.jivesoftware.plugin.dbQuery.action;

import com.jivesoftware.community.action.admin.AdminActionSupport;
import com.jivesoftware.plugin.dbQuery.dao.QueryExecute;
import org.apache.log4j.Logger;
import org.springframework.jdbc.BadSqlGrammarException;

import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Sean M. Staley
 * Date: 5/14/12
 * Time: 11:24 AM
 */
public class RunQueryAction extends AdminActionSupport {
    Logger log = Logger.getLogger(RunQueryAction.class);

    private String databaseQuery;
    private List<Map<String, Object>> queryResults;
    private boolean isSelectQuery, isCleanQuery, isCompleted;
    private QueryExecute queryExecute;

    @Override
    public String execute() {
        log.debug("Database Query Plugin: Inside of execute() of RunQueryAction...");

        //Setting selectQuery and assuming query is clean, so to true for now...
        setSelectQuery(true);
        setIsCleanQuery(true);
        setCompleted(true);

        //Is the box blank? Cereal?!
        if (getDatabaseQuery() == null){
            log.info("Database Query Plugin: Application query was blank.");
            setCompleted(false);
            return INPUT;
        }

        //Is the query NOT a SELECT query?
        else if (!queryExecute.validateSelectQuery(databaseQuery)) {
            log.info("Database Query Plugin: Query did not begin with a SELECT! Try again...");
            setSelectQuery(false);
            setCompleted(false);
            return INPUT;
        }

        //Catching dirty SQL talk and running a nice query.
        try{
            queryResults = queryExecute.runQuery(databaseQuery);
            log.info("Database Query Plugin: Query Results: " + queryResults);
        } catch (BadSqlGrammarException e) {
            log.error("Database Query Plugin: Bad SQL grammar when querying Application Database by " + getUser().getUsername(), e);
            setCompleted(false);
            setIsCleanQuery(false);
            return INPUT;
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

    public QueryExecute getQueryExecute(){
        return queryExecute;
    }

    public void setQueryExecute(QueryExecute queryExecute){
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
}