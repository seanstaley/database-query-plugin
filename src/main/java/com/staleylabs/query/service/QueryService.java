package com.staleylabs.query.service;

import com.jivesoftware.community.JiveGlobals;
import com.staleylabs.query.beans.QueryPage;
import com.staleylabs.query.dao.ApplicationQueryExecutionDao;
import com.staleylabs.query.dto.QueryResultTO;
import com.staleylabs.query.format.QueryFormatter;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * Service used for querying the application data source and returning nice looking results to the controller layer of
 * the plugin.
 *
 * @author Sean M. Staley
 * @version 2.0 (2/7/13)
 */

@Service
public class QueryService {

    private static final Logger log = Logger.getLogger(QueryService.class);

    private static final int FIRST_PAGE = 1;

    @Autowired
    private ApplicationQueryExecutionDao applicationExecutionDao;

    @Autowired
    private ApplicationQueryExecutionDao analyticsExecutionDao;

    /**
     * Method used to simply map the simple DTO object from the results provided by the application.
     *
     * @param query          The query performed on the data source.
     * @param pageNumber     The current page that is being viewed in the application.
     * @param resultsPerPage The number of results displayed per page.
     * @param mapQueryPage   {@link QueryPage} object that will be translated to the DTO.
     * @return {@link QueryResultTO} object that will contain all information needed for the UI.
     */
    protected static QueryResultTO mapQueryResults(String query, int pageNumber, int resultsPerPage, QueryPage<Map<String, Object>> mapQueryPage) {
        final QueryResultTO resultTO = new QueryResultTO();

        log.debug("Found " + mapQueryPage.getPagesAvailable() + " pages for performed query.");

        // If there is not at least one page, just return the result set.
        if (mapQueryPage.getPagesAvailable() >= 1) {
            resultTO.setQuery(query.toUpperCase());
            resultTO.setCurrentPage(pageNumber);
            resultTO.setCurrentResultsPerPage(resultsPerPage);

            resultTO.setTotalPages(mapQueryPage.getPagesAvailable());
            resultTO.setResultSet(QueryFormatter.formatResults(mapQueryPage.getPageItems()));
        }
        return resultTO;
    }

    /**
     * Used to get a bulk set of results from the application data source. By default, the number of results to be
     * returned is <strong>1,000</strong>. This can be overridden by applying a change to the system property
     * {@code staleylabs.csv.limit}. A restart is <strong>not required</strong> for this change to take effect.
     *
     * @param inputQuery Query that has been requested to be ran against the application data source.
     * @return {@link List} of {@link List} that contains the string values of the results.
     */
    public List<List<String>> returnBulkQueryResults(String inputQuery) {
        final QueryPage<Map<String, Object>> mapQueryPage = applicationExecutionDao.retrieveResults(
                inputQuery, FIRST_PAGE, JiveGlobals.getJiveIntProperty("staleylabs.csv.limit", 1000));

        return QueryFormatter.formatResults(mapQueryPage.getPageItems());
    }

    /**
     * Used to provide a single page of results from the application database.
     *
     * @param query Query that has been requested to be ran against the application data source.
     * @return {@link QueryResultTO} object that will contain all information needed for the UI.
     */
    public QueryResultTO returnQueryResult(String query, int pageNumber, int resultsPerPage) {
        final QueryPage<Map<String, Object>> mapQueryPage = applicationExecutionDao.retrieveResults(query, pageNumber, resultsPerPage);

        return mapQueryResults(query, pageNumber, resultsPerPage, mapQueryPage);
    }

    /**
     * Used to provide a single page of results from the analytics database.
     *
     * @param query SQL query that is being requested to run.
     * @return {@link QueryResultTO} object that will contain all information needed for the UI.
     */
    public QueryResultTO returnAnalyticQueryResult(String query, int pageNumber, int resultsPerPage) {
        final QueryPage<Map<String, Object>> mapQueryPage = analyticsExecutionDao.retrieveResults(query, pageNumber, resultsPerPage);

        return mapQueryResults(query, pageNumber, resultsPerPage, mapQueryPage);
    }
}
