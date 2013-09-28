package com.staleylabs.query.dao;

import com.jivesoftware.base.database.dao.JiveJdbcDaoSupport;
import com.jivesoftware.util.StringUtils;
import com.staleylabs.query.beans.QueryPage;
import com.staleylabs.query.utils.PaginationUtils;
import org.apache.log4j.Logger;

import java.util.Map;

/**
 * StaleyLabs
 *
 * @author Sean Staley
 * @version 2.0 (2/7/13)
 */

public class ApplicationQueryExecutionDao extends JiveJdbcDaoSupport {

    private static final Logger log = Logger.getLogger(ApplicationQueryExecutionDao.class);

    private final PaginationUtils<Map<String, Object>> page = new PaginationUtils<Map<String, Object>>();

    /**
     * Performs the inputted query against the requested database.
     *
     * @param query Query to execute against the database
     * @return The raw results of the query that was performed against the application database.
     */
    public QueryPage<Map<String, Object>> retrieveResults(final String query, final int pageNumber, final int resultsPerPage) {
        log.debug("Database Query Plugin: Running the following query. \n" + query);

        return page.fetchPage(getJdbcTemplate(), generateQuerySizeString(query), query, pageNumber, resultsPerPage);
    }

    /**
     * Obtains the result size of the query that the user wants to perform.
     *
     * @param incomingQuery The query that the user wants to run against the database.
     * @return <code>int</code> representing the number of rows that will be returned.
     */
    protected String generateQuerySizeString(String incomingQuery) {
        final StringBuilder countQuery = new StringBuilder("SELECT COUNT(1) FROM");
        return countQuery.append(StringUtils.substringAfter(incomingQuery.toUpperCase(), "FROM")).toString();
    }
}
