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
    private static Logger log = Logger.getLogger(QueryFormatService.class);

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
     * @param unformattedResults results that are contained in a list of maps.
     * @return results that are formatted into an ArrayList for convention
     */
    private ArrayList<ArrayList<String>> formatResults(List<Map<String, Object>> unformattedResults) {
        ArrayList<ArrayList<String>> allRows = new ArrayList<ArrayList<String>>();
        ArrayList<String> columnNames = retrieveColumnNames(unformattedResults);

        if (columnNames != null) {
            int currentList = 0;

            //Adding columnNames to provided list
            allRows.add(columnNames);

            // Iterate through the List of Maps. For each map, grab all of the columns and display.
            for (Map<String, Object> vanillaMaps : unformattedResults) {
                currentList++;
                ArrayList<String> row = new ArrayList<String>();

                for (int index = 0; index <= columnNames.size() - 1; index++) {
                    try {
                        row.add(vanillaMaps.get(columnNames.get(index)).toString());
                    } catch (NullPointerException npe) {
                        row.add(" ");
                        continue;
                    } finally {
                        unformattedResults.remove(currentList);

                        if(currentList % 10 == 0) {
                            log.debug("Requesting a garbage collection...");
                            System.gc();
                        }
                    }
                }
                // Add the row to the new ArrayList.
                allRows.add(row);
            }
            return allRows;
        }

        if (columnNames == null) {
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
