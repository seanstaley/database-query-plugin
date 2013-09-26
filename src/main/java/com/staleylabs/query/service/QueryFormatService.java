package com.staleylabs.query.service;

import com.jivesoftware.util.CollectionUtils;
import com.staleylabs.query.beans.QueryPage;
import com.staleylabs.query.dao.ApplicationQueryExecutionDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * StaleyLabs
 *
 * @author Sean M. Staley
 * @version 2.0 (2/7/13)
 */

public class QueryFormatService {

    public static final String NO_RESULTS = "Your query did not return any results.";

    private static final Logger log = Logger.getLogger(QueryFormatService.class);

    @Autowired
    private ApplicationQueryExecutionDao applicationExecutionDao;

    private int totalQueryPages;

    /**
     * Provides us with a nice ArrayList of column names.
     *
     * @param rawResults The raw, ill formatted results that is returned by Spring.
     * @return {@link List} that contains all of the names of the columns in the table that has been queried.
     */
    private List<String> retrieveColumnNames(List<Map<String, Object>> rawResults) {
        List<String> columnNames = new ArrayList<String>();

        if (CollectionUtils.isEmpty(rawResults)) {
            return null;
        }

        for (String columnName : rawResults.get(0).keySet()) {
            columnNames.add(columnName);
        }
        return columnNames;
    }

    /**
     * This method is necessary to restructure the results into a nice array of row strings.
     *
     * @param rawResults Results that are contained in a list of maps.
     * @return Results that are formatted into a {@link List} for convention
     */
    private List<List<String>> formatResults(List<Map<String, Object>> rawResults) {

        final List<List<String>> allRows = new ArrayList<List<String>>();
        final List<String> columnNames = retrieveColumnNames(rawResults);

        if (CollectionUtils.isNotEmpty(columnNames)) {
            //Adding columnNames to provided list
            allRows.add(columnNames);

            // Iterate through the List of Maps. For each map, grab all of the columns and display.
            log.debug("Beginning to format " + rawResults.size() + " results.");
            for (int currentRow = 0; currentRow < rawResults.size(); ) {
                final Map<String, Object> vanillaMaps = rawResults.get(currentRow);
                final List<String> row = new ArrayList<String>();

                for (int index = 0; index <= columnNames.size() - 1; index++) {
                    final String columnKey = columnNames.get(index);
                    final Object columnEntry = vanillaMaps.get(columnKey);

                    if (columnEntry != null) {
                        row.add(vanillaMaps.get(columnKey).toString());
                    } else {
                        row.add(" ");
                    }
                }

                // Add the row to the the new results.
                allRows.add(row);

                // Memory Issues
                if (rawResults.size() != 1) {
                    rawResults.remove(currentRow);
                } else {
                    System.gc();
                    break;
                }
            }

            return allRows;

        } else {
            List<String> noResults = new ArrayList<String>();
            noResults.add(NO_RESULTS);
            allRows.add(0, noResults);
        }
        return allRows;
    }

    /**
     * The public facing method that provides the end user with the formatted query rows.
     *
     * @param inputQuery query that the user would like to run
     * @return formatted ArrayList of rows
     */
    public List<List<String>> returnQueryResults(String inputQuery, int pageNumber, int resultsPerPage) {
        final QueryPage<Map<String,Object>> mapQueryPage = applicationExecutionDao.retrieveResults(inputQuery, pageNumber, resultsPerPage);

        // Setting the number of query pages.
        totalQueryPages = mapQueryPage.getPagesAvailable();

        return formatResults(mapQueryPage.getPageItems());
    }

    public int getTotalQueryPages() {
        return totalQueryPages;
    }
}
