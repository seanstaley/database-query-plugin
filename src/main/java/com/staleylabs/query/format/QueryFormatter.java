package com.staleylabs.query.format;

import com.jivesoftware.util.CollectionUtils;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Utility class used for formatting query results from a collection of maps to a nice list of rows to present to the
 * User Interface.
 *
 * @author Sean M. Staley
 * @version 2.0 (9/26/13)
 */

public class QueryFormatter {

    public static final String NO_RESULTS = "Your query did not return any results.";

    private static final Logger log = Logger.getLogger(QueryFormatter.class);

    /**
     * Provides us with a nice ArrayList of column names.
     *
     * @param rawResults The raw, ill formatted results that is returned by Spring.
     * @return {@link List} that contains all of the names of the columns in the table that has been queried.
     */
    private static List<String> retrieveColumnNames(List<Map<String, Object>> rawResults) {
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
     * @return Results that are formatted into a {@link java.util.List} for convention
     */
    public static List<List<String>> formatResults(List<Map<String, Object>> rawResults) {
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

}
