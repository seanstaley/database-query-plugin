package com.jivesoftware.plugin.dbQuery.dao;

import com.jivesoftware.base.database.dao.JiveJdbcDaoSupport;
import com.jivesoftware.util.StringUtils;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 *
 * @author Sean
 * @version 2.0 (2/7/13)
 */
public abstract class ApplicationExecutionDao extends JiveJdbcDaoSupport {
    private static Logger log = Logger.getLogger(ApplicationExecutionDao.class);
    public static final String NO_RESULTS = "Your query did not return any results.";

    private static final int RESULT_SET_LIMIT = 100000;

    /**
     * Performs the inputted query against the requested database.
     *
     * @param query Query to execute against the database
     * @return The dirty results of the query that was performed against the application database.
     */
    public List<Map<String, Object>> retrieveResults(String query) {
        log.debug("Database Query Plugin: Running the following query. \n" + query);
        return getSimpleJdbcTemplate().queryForList(query);
    }

    /**
     * Obtains the result size of the query that the user wants to perform.
     * @param query The query that the user wants to run against the database.
     * @return <code>int</code> representing the number of rows that will be returned.
     */
    private int getQueryResultSize(String query) {
        StringBuilder countQuery = new StringBuilder("SELECT COUNT(*) FROM ");
        countQuery.append(StringUtils.substringAfter(query, "FROM"));

        return getSimpleJdbcTemplate().queryForInt(countQuery.toString());
    }

    protected boolean isOverResultLimit(String query){
        boolean overLimit = false;

        if(getQueryResultSize(query) >= RESULT_SET_LIMIT) {
            overLimit = true;
        }
        return overLimit;
    }
}
