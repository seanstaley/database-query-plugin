package com.staleylabs.query.validator;

import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * StaleyLabs
 *
 * @author Sean M. Staley
 * @version 2.0 (9/27/13)
 */

public class QueryValidatorTest {

    private static final String SELECT_QUERY = "SELECT * FROM JIVEUSER;";

    private static final String UPDATE_QUERY = "UPDATE JIVEUSER SET \"USERNAME\" = \"SEAN\";";

    private static final String DELETE_QUERY = "DELETE FROM JIVEUSER;";

    private static final String INSERT_QUERY = "INSERT INTO JIVEUSER VALUES (\"1\", \"SEAN\");";

    private static final String SELECT_INTO_QUERY = "SELECT * INTO JIVESTUFF FROM JIVEUSER;";

    @Test
    public void testIsNotSelectQuery_update() throws Exception {
        assertTrue(QueryValidator.isNotSelectQuery(UPDATE_QUERY));
    }

    @Test
    public void testIsNotSelectQuery_delete() throws Exception {
        assertTrue(QueryValidator.isNotSelectQuery(DELETE_QUERY));
    }

    @Test
    public void testIsNotSelectQuery_insert() throws Exception {
        assertTrue(QueryValidator.isNotSelectQuery(INSERT_QUERY));
    }

    @Test
    public void testIsNotSelectQuery_select() throws Exception {
        assertFalse(QueryValidator.isNotSelectQuery(SELECT_QUERY));
    }

    @Test
    public void testIsNotSelectQuery_select_into() throws Exception {
        assertTrue(QueryValidator.isNotSelectQuery(SELECT_INTO_QUERY));
    }

    @Test
    public void testIsSelectQuery_select() throws Exception {
        assertTrue(QueryValidator.isSelectQuery(SELECT_QUERY));
    }

    @Test
    public void testIsSelectQuery_delete() throws Exception {
        assertFalse(QueryValidator.isSelectQuery(DELETE_QUERY));
    }

    @Test
    public void testIsSelectQuery_insert() throws Exception {
        assertFalse(QueryValidator.isSelectQuery(INSERT_QUERY));
    }

    @Test
    public void testIsSelectQuery_update() throws Exception {
        assertFalse(QueryValidator.isSelectQuery(UPDATE_QUERY));
    }

    @Test
    public void testIsSelectQuery_select_into() throws Exception {
        assertFalse(QueryValidator.isSelectQuery(SELECT_INTO_QUERY));
    }
}
