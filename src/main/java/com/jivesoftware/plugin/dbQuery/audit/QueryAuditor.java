package com.jivesoftware.plugin.dbQuery.audit;

import com.jivesoftware.base.database.dao.JiveJdbcDaoSupport;
import org.apache.log4j.Logger;

/**
 * Created by IntelliJ IDEA.
 * User: Sean M. Staley
 * Date: 6/23/12
 * Time: 10:53 PM
 */
public class QueryAuditor extends JiveJdbcDaoSupport {
    Logger log = Logger.getLogger(QueryAuditor.class);
    public static String INSERT_AUDIT_STATEMENT = "INSERT INTO jiveDatabaseQueryAudit VALUES (?,?,?,?);";

    /**
     * Adds Entry to the application database table jiveDatabaseQueryAudit.
     * @param username Username of the user that performed the query at hand.
     * @param databaseUsed Name of the database that has been used to perform the query.
     * @param queryPerformed The query that was performed.
     * @param timePerformed The epoch time that the query was performed.
     * @return 1 if the query was logged correctly, 0 otherwise.
     */
    public int addEntry(String username, String databaseUsed, String queryPerformed, long timePerformed) {
        if(username == null || databaseUsed == null || queryPerformed == null || timePerformed == 0)
        {
            log.error("Database Query Plugin: Missing field when trying to add database audit log...");
            return 0;
        }

        return 1;
    }
}
