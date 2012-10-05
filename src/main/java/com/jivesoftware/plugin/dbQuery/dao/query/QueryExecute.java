package com.jivesoftware.plugin.dbQuery.dao.query;

import com.jivesoftware.base.database.dao.JiveJdbcDaoSupport;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by IntelliJ IDEA.
 * User: Sean M. Staley
 * Date: 5/14/12
 * Time: 5:11 PM
 * public ArrayList<String> returnQueryResults(String inputQuery)
 */
public class QueryExecute extends JiveJdbcDaoSupport {
    Logger log = Logger.getLogger(QueryExecute.class);

    /**
     * Method to validate the query to make sure that you can not use any other functions but SELECT.
     *
     * @param defendingQuery The query that is being passed to the application database.
     * @return TRUE is the query is a SELECT statement, FALSE if anything else
     */
    public boolean validateSelectQuery(String defendingQuery) {
        log.debug("Database Query Plugin: Inside of validateSelectQuery() of QueryExecute class...");
        boolean validated = false;

        //Trying to catch some null pointers!
        try {
            //Make sure the string starts with SELECT and does not have the word INTO.
            if (defendingQuery.toUpperCase().startsWith("SELECT") && !defendingQuery.toUpperCase().contains("INTO")) {
                log.info("Database Query Plugin: Query, " + defendingQuery +
                        ", is a SELECT query and has been validated.");
                validated = true;
            }
        }
        catch (NullPointerException e) {
            log.error("Database Query Plugin: Validating the query has generated a NullPointerException.", e);
            validated = true;
        }
        return validated;
    }

    /**
     * Performs the inputted query against the requested database.
     *
     * @param query Query to execute against the database
     * @return The dirty results of the query that was performed against the application database.
     */
    public List
            <Map<String, Object>> retrieveResults(String query) {
        return getSimpleJdbcTemplate().queryForList(query);
    }

    /**
     * Provides us with a nice ArrayList of column names.
     *
     * @param unformattedResults the raw unformatted results that is returned by Spring.
     * @return an ArrayList that contains all of the names of the columns in the table that has been queried.
     */
    private ArrayList<String> retrieveColumnNames(List<Map<String, Object>> unformattedResults) {
        // Get Column Names from Map and place into ArrayList
        ArrayList<String> columnNames = new ArrayList<String>();

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

        //Adding columnNames to provided list
        allRows.add(columnNames);

        // Iterate through the List of Maps. For each map, grab all of the columns and display.
        for (Map<String, Object> vanillaMaps : unformattedResults) {
            ArrayList<String> row = new ArrayList<String>();
            for (int index = 0; index <= columnNames.size() - 1; index++) {
                try {
                row.add(vanillaMaps.get(columnNames.get(index)).toString());
                } catch (NullPointerException npe) {
                    row.add(" ");
                    continue;
                }
            }
            allRows.add(row);
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
        return formatResults(retrieveResults(inputQuery));
    }
}