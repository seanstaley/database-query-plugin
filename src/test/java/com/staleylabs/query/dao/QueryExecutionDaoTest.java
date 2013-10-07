package com.staleylabs.query.dao;

import org.junit.Test;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;

/**
 * StaleyLabs
 *
 * @author Sean M. Staley
 * @version 2.0 (3/19/13)
 */

public class QueryExecutionDaoTest {

    private static final String SELECT_JIVE_USER = "SELECT * FROM JIVEUSER;";

    private static final String SELECT_JIVE_USER_COUNT = "SELECT COUNT(1) FROM JIVEUSER;";

    private static final String SELECT_JIVE_USER_SUB_SELECT = "SELECT USER FROM (SELECT USER FROM JIVEUSER);";

    private static final String SELECT_JIVE_USER_SUB_SELECT_COUNT = "SELECT COUNT(1) FROM (SELECT USER FROM JIVEUSER);";

    private static final String SELECT_JIVE_USER_INNER_JOIN = "SELECT * FROM JIVEUSER INNER JOIN JIVEPROPERTY ON NOTHING = NOTHING;";

    private static final String SELECT_JIVE_USER_INNER_JOIN_COUNT = "SELECT COUNT(1) FROM JIVEUSER INNER JOIN JIVEPROPERTY ON NOTHING = NOTHING;";

    private final QueryExecutionDao classUnderTest = new QueryExecutionDao();

    @Test
    public void testGenerateQuerySizeString() throws Exception {
        String actual = classUnderTest.generateQuerySizeString(SELECT_JIVE_USER);

        assertThat(actual, is(SELECT_JIVE_USER_COUNT));
    }

    @Test
    public void testGenerateQuerySizeString_mixed_case() throws Exception {
        String query = "SELECT * From JIVEUSER;";

        String expected = "SELECT COUNT(1) FROM JIVEUSER;";
        String actual = classUnderTest.generateQuerySizeString(query);

        assertThat(actual, is(expected));
    }

    @Test
    public void testGenerateQuerySizeString_sub_select() throws Exception {
        String actual = classUnderTest.generateQuerySizeString(SELECT_JIVE_USER_SUB_SELECT);

        assertThat(actual, is(SELECT_JIVE_USER_SUB_SELECT_COUNT));
    }

    @Test
    public void testGenerateQuerySizeString_inner_join() throws Exception {
        String actual = classUnderTest.generateQuerySizeString(SELECT_JIVE_USER_INNER_JOIN);

        assertThat(actual, is(SELECT_JIVE_USER_INNER_JOIN_COUNT));
    }
}
