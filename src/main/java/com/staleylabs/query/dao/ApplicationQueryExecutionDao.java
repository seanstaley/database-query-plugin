package com.staleylabs.query.dao;

import com.jivesoftware.base.database.dao.JiveJdbcDaoSupport;
import com.jivesoftware.util.StringUtils;
import com.staleylabs.query.beans.QueryPage;
import com.staleylabs.query.utils.PaginationUtils;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;

/**
 * StaleyLabs
 *
 * @author Sean Staley
 * @version 2.0 (2/7/13)
 */

public class ApplicationQueryExecutionDao extends JiveJdbcDaoSupport {

    public static final String NO_RESULTS = "Your query did not return any results.";

    private static final Logger log = Logger.getLogger(ApplicationQueryExecutionDao.class);

    /**
     * Performs the inputted query against the requested database.
     *
     * @param query Query to execute against the database
     * @return The raw results of the query that was performed against the application database.
     */
    public List<Map<String, Object>> retrieveResults(final String query, final int pageNumber, final int resultsPerPage) {
        log.debug("Database Query Plugin: Running the following query. \n" + query);
        QueryPage<Map<String, Object>> results = getResults(query, pageNumber, resultsPerPage);

        return results.getPageItems();
    }

    /**
     * Method used to get a page of results using {@link PaginationUtils}.
     *
     * @param query The original query inputted by the user.
     * @return {@link QueryPage} instance that contains the results from the data source.
     */
    private QueryPage<Map<String, Object>> getResults(String query, int pageNumber, int resultsPerPage) {
        PaginationUtils<Map<String, Object>> page = new PaginationUtils<Map<String, Object>>();

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
