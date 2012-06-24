package com.jivesoftware.plugin.dbQuery.audit;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Created by IntelliJ IDEA.
 * User: Sean M. Staley
 * Date: 6/23/12
 * Time: 11:47 PM
 */
public class QueryAuditorTest {
    static int WORKS = 1;
    static int NO_WORK = 0;

    static String USER_NAME = "Test User";
    static String GOOD_DATABASE_USED = "ANALYTICS";
    static String BAD_DATABASE_USED = "WHATEVER";
    static String QUERY_PERFORMED = "SELECT * FROM JIVEUSER;";
    static String EMPTY_STRING_FIELD = null;
    static long EMPTY_LONG_FIELD;

    static long TIME_PERFORMED = 1234567890;

    QueryAuditor queryAuditor = new QueryAuditor();

    @Test
    public void addEntry_works_with_all_required_fields_non_null(){
        assertEquals(WORKS, queryAuditor.addEntry(USER_NAME, GOOD_DATABASE_USED, QUERY_PERFORMED, TIME_PERFORMED));
    }

    @Test
    public void addEntry_does_not_work_without_username_field() {
        assertEquals(NO_WORK, queryAuditor.addEntry(EMPTY_STRING_FIELD, GOOD_DATABASE_USED, QUERY_PERFORMED, TIME_PERFORMED));
    }

    @Test
    public void addEntry_does_not_work_without_query_field() {
        assertEquals(NO_WORK, queryAuditor.addEntry(USER_NAME, GOOD_DATABASE_USED, EMPTY_STRING_FIELD, TIME_PERFORMED));
    }

    @Test
    public void addEntry_does_not_work_without_time_field() {
        assertEquals(NO_WORK, queryAuditor.addEntry(USER_NAME, GOOD_DATABASE_USED, QUERY_PERFORMED, EMPTY_LONG_FIELD));
    }

    @Test
    public void addEntry_does_not_work_without_database_used() {
        assertEquals(NO_WORK, queryAuditor.addEntry(USER_NAME, EMPTY_STRING_FIELD, QUERY_PERFORMED, TIME_PERFORMED));
    }

    @Test
    public void validateInformation_bad_date() {
        assertEquals(NO_WORK, queryAuditor.addEntry(USER_NAME, GOOD_DATABASE_USED, QUERY_PERFORMED, EMPTY_LONG_FIELD));
    }
}
