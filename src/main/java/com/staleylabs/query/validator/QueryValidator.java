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
     * Validates that a query is not a <strong>SELECT</strong> statement only.
     *
     * @param defendingQuery Query that requires validation.
     * @return {@code true} if the query is not a <strong>SELECT</strong> statement.
     */
    public static boolean isNotSelectQuery(String defendingQuery) {
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

        return !validated;
    }

    /**
     * Validates that a given query is in fact only a {@code SELECT} query.
     *
     * @param defendingQuery Query that needs to be validated.
     * @return {@code true} if the query is a <strong>SELECT</strong> statement.
     */
    public static boolean isSelectQuery(String defendingQuery) {
        return !isNotSelectQuery(defendingQuery);
    }
}
