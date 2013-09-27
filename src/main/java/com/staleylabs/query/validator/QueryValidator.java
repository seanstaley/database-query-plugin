package com.staleylabs.query.validator;

import com.jivesoftware.util.StringUtils;
import org.apache.log4j.Logger;

/**
 * StaleyLabs
 *
 * @author Sean M. Stlaey
 * @version 2.0 (2/7/13)
 */

public final class QueryValidator {

    private static final Logger log = Logger.getLogger(QueryValidator.class);

    /**
     * Method to validate the query to make sure that you can not use any other functions but SELECT.
     *
     * @param defendingQuery The query that is being passed to the application database.
     * @return <code>true</code> if the query is a <code>SELECT</code> statement, <code>false</code> if anything else
     */
    public static boolean validateSelectQuery(String defendingQuery) {
        log.debug("Inside of validateSelectQuery() of QueryValidation; validateSelectQuery method...");
        boolean validated = false;

        if (StringUtils.isNotBlank(defendingQuery)) {
            String query = defendingQuery.trim().toUpperCase();

            //Make sure the string starts with SELECT and does not have the word INTO.
            if (query.startsWith("SELECT") && !query.toUpperCase().contains("INTO")) {
                log.info("Query, " + defendingQuery + ", is a SELECT query and has been validated.");
                validated = true;
            }
        } else {
            log.info("Query was empty and therefore validated.");
            validated = true;
        }

        return validated;
    }
}
