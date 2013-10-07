package com.staleylabs.query.dao;

import com.jivesoftware.base.database.dao.JiveJdbcDaoSupport;
import com.jivesoftware.util.StringUtils;
import com.staleylabs.query.beans.QueryPage;
import com.staleylabs.query.utils.PaginationUtils;
import org.apache.log4j.Logger;
import org.springframework.jdbc.BadSqlGrammarException;

import java.util.Map;

/**
 * DAO layer of the plugin that performs all of the interactions with the data source.
 *
 * @author Sean Staley
 * @version 2.0 (2/7/13)
 */

public class QueryExecutionDao extends JiveJdbcDaoSupport {

    private static final Logger log = Logger.getLogger(QueryExecutionDao.class);

    private final PaginationUtils<Map<String, Object>> page = new PaginationUtils<Map<String, Object>>();

    /**
     * Performs the inputted query against the requested database.
     *
     * @param query Query to execute against the database
     * @return The raw results of the query that was performed against the application database.
     */
    public QueryPage<Map<String, Object>> retrieveResults(final String query,
                                                          final int pageNumber,
                                                          final long resultsPerPage) throws BadSqlGrammarException {
        log.debug("Database Query Plugin: Running the following query. \n" + query);

        return page.fetchPage(getJdbcTemplate(), generateQuerySizeString(query), query, pageNumber, resultsPerPage);
    }

    /**
     * Obtains the result size of the query that the user wants to perform.
     *
     * @param incomingQuery The query that the user wants to run against the database.
     * @return <code>int</code> representing the number of rows that will be returned.
     */
    protected static String generateQuerySizeString(final String incomingQuery) {
        final StringBuilder countQuery = new StringBuilder("SELECT COUNT(1) FROM");

        return countQuery.append(StringUtils.substringAfter(incomingQuery.toUpperCase(), "FROM")).toString();
    }
}
