package com.jivesoftware.plugin.dbQuery.service;

import com.jivesoftware.plugin.dbQuery.dao.AbstractApplicationExecutionDao;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.Iterator;
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
    private AbstractApplicationExecutionDao applicationExecutionDao;

    /**
     * Provides us with a nice ArrayList of column names.
     *
     * @param unformattedResults the raw unformatted results that is returned by Spring.
     * @return an ArrayList that contains all of the names of the columns in the table that has been queried.
     */
    private ArrayList<String> retrieveColumnNames(List<Map<String, Object>> unformattedResults) {
        // Get Column Names from Map and place into ArrayList
        ArrayList<String> columnNames = new ArrayList<String>();

        if (unformattedResults.isEmpty()) {
            return null;
        }
        Map<String, Object> firstRow = unformattedResults.get(0);

        Iterator keyIterator = firstRow.entrySet().iterator();
        while (keyIterator.hasNext()) {
            Map.Entry entry = (Map.Entry) keyIterator.next();
            columnNames.add(entry.getKey().toString());
        }
        return columnNames;
    }

    /**
     * This method is necessary to restructure the results into a nice array of row strings.
     *
     * @param rawResults results that are contained in a list of maps.
     * @return results that are formatted into an ArrayList for convention
     */
    private ArrayList<ArrayList<String>> formatResults(List<Map<String, Object>> rawResults) {
        ArrayList<ArrayList<String>> allRows = new ArrayList<ArrayList<String>>();
        ArrayList<String> columnNames = retrieveColumnNames(rawResults);

        if (columnNames != null) {
            //Adding columnNames to provided list
            allRows.add(columnNames);

            // Iterate through the List of Maps. For each map, grab all of the columns and display.
            log.debug("Beginning to format " + rawResults.size() + " results.");
            for (int currentRow = 0; currentRow < rawResults.size();) {
                Map<String, Object> vanillaMaps = rawResults.get(currentRow);
                ArrayList<String> row = new ArrayList<String>();

                for (int index = 0; index <= columnNames.size() - 1; index++) {
                    String columnKey = columnNames.get(index);
                    String columnEntry = (String) vanillaMaps.get(columnKey);

                    if(columnEntry != null) {
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
            ArrayList<String> noResults = new ArrayList<String>();
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
    public ArrayList<ArrayList<String>> returnQueryResults(String inputQuery) {
        return formatResults(applicationExecutionDao.retrieveResults(inputQuery));
    }
}
