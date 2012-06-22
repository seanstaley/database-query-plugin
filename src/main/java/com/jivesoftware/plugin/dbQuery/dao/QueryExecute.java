package com.jivesoftware.plugin.dbQuery.dao;

import com.jivesoftware.base.database.dao.JiveJdbcDaoSupport;
import com.jivesoftware.community.audit.aop.Audit;
import org.apache.log4j.Logger;

import javax.management.timer.Timer;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Sean M. Staley
 * Date: 5/14/12
 * Time: 5:11 PM
 */
public class QueryExecute extends JiveJdbcDaoSupport {
    Logger log = Logger.getLogger(QueryExecute.class);
    Timer timer = new Timer();

    /**
     * Method to validate the query to make sure that you can not use any other functions but SELECT.
     * @param queryInput The query that is being passed to the application database.
     * @return TRUE is the query is a SELECT statement, FALSE if anything else
     */
    public boolean validateSelectQuery(String queryInput){
        //Initially validation is FALSE.
        boolean validated = false;

        //Trying to catch some null pointers!
        try{
            //Make sure the string starts with SELECT and does not have the word INTO.
            if(queryInput.toUpperCase().startsWith("SELECT") && !queryInput.toUpperCase().contains("INTO")){
                log.info("Query, " + queryInput + ", is a SELECT query and has been validated.");
                validated = true;
            }
        } catch(NullPointerException e){
            log.error("Validating the query has generated a NullPointerException. Stack: " + e);
            validated = true;
        }
        return validated;
    }

    /**
     * Performs the inputted query against the requested database. Also this is logged in the jiveAuditLog table for viewing who performed a query.
     * @param queryInput Query to execute against the database
     * @return The results of the query that was performed against the application database.
     */
    @Audit
    public List<Map<String, Object>> runQuery(String queryInput){
        log.debug("Inside of the QueryExecute.runQuery method.");

        List<Map<String, Object>> results;

        //Querying the database for a list of mapped results and setting that to results.
        results = getSimpleJdbcTemplate().queryForList(queryInput);
        log.info("runQuery method in Database Query Plugin provided these results for the " + queryInput + " query:" +
                '\n' + results);

        return results;
    }
}