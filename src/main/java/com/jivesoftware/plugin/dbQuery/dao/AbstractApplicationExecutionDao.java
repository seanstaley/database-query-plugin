package com.jivesoftware.plugin.dbQuery.dao;

import com.jivesoftware.base.database.dao.JiveJdbcDaoSupport;
import com.jivesoftware.community.JiveGlobals;
import com.jivesoftware.util.StringUtils;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * StaleyLabs
 *
 * @author Sean
 * @version 2.0 (2/7/13)
 */
public abstract class AbstractApplicationExecutionDao extends JiveJdbcDaoSupport {
    private static final Logger log = Logger.getLogger(AbstractApplicationExecutionDao.class);

    public static final String NO_RESULTS = "Your query did not return any results.";
    private static final String RESULT_LIMIT_PROPERTY = "staleylabs.dbquery.limit";
    private static final String DEFAULT_RESULT_LIMIT_SET = "100000";

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
    protected int getQueryResultSize(String query) {
        StringBuilder countQuery = new StringBuilder("SELECT COUNT(*) FROM ");
        countQuery.append(StringUtils.substringAfter(query, "FROM"));

        return getSimpleJdbcTemplate().queryForInt(countQuery.toString());
    }

    /**
     * Method to determine if query will return too many results.
     *
     * @param query The query that is going to be executed against the database
     * @return <code>true</code> if the limit is over what has been set, <code>false</code> if the limit is set to <code>0</code> or under the set limit.
     */
    protected boolean isOverResultLimit(String query){
        int resultSizeLimit = Integer.parseInt(
                StringUtils.defaultIfEmpty(JiveGlobals.getJiveProperty(RESULT_LIMIT_PROPERTY), DEFAULT_RESULT_LIMIT_SET));
        boolean overLimit = false;

        if(resultSizeLimit <= 0) {
             overLimit = false;
        } else if(getQueryResultSize(query) >= resultSizeLimit) {
            overLimit = true;
        }
        return overLimit;
    }
}
