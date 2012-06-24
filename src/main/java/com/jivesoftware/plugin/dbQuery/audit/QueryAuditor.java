package com.jivesoftware.plugin.dbQuery.audit;

import com.jivesoftware.base.database.dao.JiveJdbcDaoSupport;
import org.apache.log4j.Logger;
import org.springframework.jdbc.BadSqlGrammarException;

/**
 * Created by IntelliJ IDEA.
 * User: Sean M. Staley
 * Date: 6/23/12
 * Time: 10:53 PM
 */
public class QueryAuditor extends JiveJdbcDaoSupport {
    Logger log = Logger.getLogger(QueryAuditor.class);

    public static String INSERT_AUDIT_STATEMENT = "INSERT INTO jiveDatabaseQueryAudit VALUES(";

    /**
     * Builds and validates SQL statement that goes to the application database table jiveDatabaseQueryAudit.
     * @param username Username of the user that performed the query at hand.
     * @param databaseUsed Name of the database that has been used to perform the query.
     * @param queryPerformed The query that was performed.
     * @param timePerformed The epoch time that the query was performed.
     * @return The SQL to be inserted into the Jive application.
     */
    public String buildEntry(String username, String databaseUsed, String queryPerformed, long timePerformed) {
        log.debug("Database Query Plugin: Inside of the buildEntry() method of Query Auditor....");
        String result;

        if(username == null)
        {
            log.error("Database Query Plugin: Missing username field when trying to add database audit log...");
            result = "ERROR";
            return result;
        }
        else if(databaseUsed == null) {
            log.error("Database Query Plugin: Missing databaseUsed field when trying to add database audit log...");
            result = "ERROR";
            return result;
        }
        else if(queryPerformed == null) {
            log.error("Database Query Plugin: Missing queryPerformed field when trying to add database audit log...");
            result = "ERROR";
            return result;
        }
        else if (timePerformed <= 0) {
            log.error("Database Query Plugin: Bad timePerformed field when trying to add database audit log...");
            result = "ERROR";
            return result;
        }

        result = INSERT_AUDIT_STATEMENT + "'" + username + "', '" + databaseUsed + "', '" + queryPerformed + "', " + timePerformed + ");";
        log.debug("Database Query Plugin: QueryAudit SQL is " + result + "...");
        return result;
    }

    /**
     * Runs the requested query against the application database
     * @param insert_query Query that will need to be performed into the application.
     * @return 1 if it has worked correctly or 0 if not.
     */
    public int addEntry(String insert_query) {
        log.debug("Database Query Plugin: Inside of addEntry() of QueryAuditor...");
        int result = 0;

        if(insert_query == null) {
            log.error("Database Query Plugin: insert_query was null.");
            return result;
        }

        try{
            result = getSimpleJdbcTemplate().update(insert_query);
        } catch (BadSqlGrammarException e) {
            log.error("Database Query Plugin: Bad SQL was almost inserted into the auditor table...", e);
        }

        return result;
    }
}
