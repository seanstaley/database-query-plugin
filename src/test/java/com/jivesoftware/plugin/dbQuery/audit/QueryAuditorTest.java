package com.jivesoftware.plugin.dbQuery.audit;

/**
 * Created by IntelliJ IDEA.
 * User: Sean M. Staley
 * Date: 6/23/12
 * Time: 11:47 PM
 */
public class QueryAuditorTest {/*
    static String ERROR = "ERROR";

    static String USER_NAME = "admin";
    static String GOOD_DATABASE_USED = "APPLICATION";
    static String BAD_DATABASE_USED = "WHATEVER";
    static String QUERY_PERFORMED = "SELECT * FROM JIVEUSER;";
    static String EMPTY_STRING_FIELD = null;
    static long EMPTY_LONG_FIELD;

    static String INSERT_QUERY = "INSERT INTO jiveDatabaseQueryAudit VALUES('admin', 'APPLICATION', 'SELECT * FROM JIVEUSER;', 1234567890);";

    static long TIME_PERFORMED = 1234567890;

    QueryAuditor queryAuditor = new QueryAuditor();

    @Test
    public void buildEntry_works_with_all_required_fields_non_null(){
        assertEquals(INSERT_QUERY, queryAuditor.buildEntry(USER_NAME, GOOD_DATABASE_USED, QUERY_PERFORMED, TIME_PERFORMED));
    }

    @Test
    public void buildEntry_does_not_work_without_username_field() {
        assertEquals(ERROR, queryAuditor.buildEntry(EMPTY_STRING_FIELD, GOOD_DATABASE_USED, QUERY_PERFORMED, TIME_PERFORMED));
    }

    @Test
    public void buildEntry_does_not_work_without_query_field() {
        assertEquals(ERROR, queryAuditor.buildEntry(USER_NAME, GOOD_DATABASE_USED, EMPTY_STRING_FIELD, TIME_PERFORMED));
    }

    @Test
    public void buildEntry_does_not_work_without_time_field() {
        assertEquals(ERROR, queryAuditor.buildEntry(USER_NAME, GOOD_DATABASE_USED, QUERY_PERFORMED, EMPTY_LONG_FIELD));
    }

    @Test
    public void buildEntry_does_not_work_without_database_used() {
        assertEquals(ERROR, queryAuditor.buildEntry(USER_NAME, EMPTY_STRING_FIELD, QUERY_PERFORMED, TIME_PERFORMED));
    }*/
}
