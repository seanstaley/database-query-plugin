package com.staleylabs.query.service;

import org.apache.log4j.Logger;

/**
 * StaleyLabs
 *
 * @author Sean M. Stlaey
 * @version 2.0 (2/7/13)
 */
public class QueryValidationService {

    private static final Logger log = Logger.getLogger(QueryValidationService.class);

    /**
     * Method to validate the query to make sure that you can not use any other functions but SELECT.
     *
     * @param defendingQuery The query that is being passed to the application database.
     * @return <code>true</code> if the query is a <code>SELECT</code> statement, <code>false</code> if anything else
     */
    public boolean validateSelectQuery(String defendingQuery) {
        log.debug("Database Query Plugin: Inside of validateSelectQuery() of ApplicationQueryExecutionDaoImpl class...");
        boolean validated = false;

        //Trying to catch some null pointers!
        try {
            String query = defendingQuery.trim().toUpperCase();

            //Make sure the string starts with SELECT and does not have the word INTO.
            if (query.startsWith("SELECT") && !query.toUpperCase().contains("INTO")) {
                log.info("Database Query Plugin: Query, " + defendingQuery + ", is a SELECT query and has been validated.");
                validated = true;
            }
        } catch (NullPointerException e) {
            log.error("Database Query Plugin: Validating the query has generated a NullPointerException.", e);
            validated = true;
        }

        return validated;
    }
}
