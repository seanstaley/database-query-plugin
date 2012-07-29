package com.jivesoftware.plugin.dbQuery.dao.audit;

import com.jivesoftware.base.database.dao.JiveJdbcDaoSupport;
import com.jivesoftware.plugin.dbQuery.audit.QueryAuditor;
import org.apache.log4j.Logger;
import org.springframework.jdbc.BadSqlGrammarException;
import org.springframework.jdbc.core.simple.ParameterizedBeanPropertyRowMapper;

import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Sean M. Staley
 * Date: 6/23/12
 * Time: 10:53 PM
 */
public class QueryAuditorDao extends JiveJdbcDaoSupport {
    Logger log = Logger.getLogger(QueryAuditorDao.class);

    public final static String INSERT_AUDIT_STATEMENT = "INSERT INTO jiveDatabaseQueryAudit VALUES(";
    public final static String GET_AUDIT_RECORDS = "SELECT * FROM jiveDatabaseQueryAudit;";

    public final static int FAILED_TO_INSERT = 0;

    /**
     * Builds and validates SQL statement that goes to the application database table jiveDatabaseQueryAudit.
     * @param username Username of the user that performed the query at hand.
     * @param databaseUsed Name of the database that has been used to perform the query.
     * @param queryPerformed The query that was performed.
     * @param timePerformed The epoch time that the query was performed.
     * @return The SQL to be inserted into the Jive application.
     */
    public int addAuditStatement(String username, String databaseUsed, String queryPerformed, long timePerformed) {
        log.debug("Database Query Plugin: Inside of the addAuditStatement() method of Query Auditor DAO....");
        String insertStatement;

        if(username == null)
        {
            log.error("Database Query Plugin: Missing username field when trying to add database audit log...");
            return FAILED_TO_INSERT;
        }
        else if(databaseUsed == null) {
            log.error("Database Query Plugin: Missing databaseUsed field when trying to add database audit log...");
            return FAILED_TO_INSERT;
        }
        else if(queryPerformed == null) {
            log.error("Database Query Plugin: Missing queryPerformed field when trying to add database audit log...");
            return FAILED_TO_INSERT;
        }
        else if (timePerformed <= 0) {
            log.error("Database Query Plugin: Bad timePerformed field when trying to add database audit log...");
            return FAILED_TO_INSERT;
        }

        insertStatement = INSERT_AUDIT_STATEMENT + "'" + username + "', '" + databaseUsed + "', '" + queryPerformed + "', " + timePerformed + ");";
        log.debug("Database Query Plugin: QueryAudit SQL is " + insertStatement + "...");

        int insertedQuerySuccess = insertIntoDatabase(insertStatement);

        return insertedQuerySuccess;
    }

    /**
     * Runs the requested query against the application database
     * @param insert_query Query that will need to be performed into the application.
     * @return 1 if it has worked correctly or 0 if not.
     */
    private int insertIntoDatabase(String insert_query) {
        log.debug("Database Query Plugin: Inside of insertIntoDatabase() of QueryAuditor...");
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

        log.debug("Database Query Plugin: The query " + insert_query + "returned " + result + " when inserted into the database.");
        return result;
    }

    //TODO: Need to implement returning records and storing into object.
    public List<QueryAuditor> returnAllAuditRecords(){

        List<QueryAuditor> results = getSimpleJdbcTemplate().query(
                GET_AUDIT_RECORDS, ParameterizedBeanPropertyRowMapper.newInstance(QueryAuditor.class));

        log.debug("Database Query Plugin: Query auditor results: \n" + results);

        return results;
    }
}
