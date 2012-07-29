package com.jivesoftware.plugin.dbQuery.dao;

import static org.junit.Assert.*;

import com.jivesoftware.plugin.dbQuery.dao.query.QueryExecute;
import org.junit.Test;

/**
 * Created by IntelliJ IDEA.
 * User: Sean M. Staley
 * Date: 5/14/12
 * Time: 5:10 PM
 */
public class QueryExecuteTest {
    private QueryExecute queryExecute = new QueryExecute();

    public static String LOWERCASE_SELECT = "select * from jiveuser;";
    public static String INSERT_QUERY = "INSERT INTO jiveUser;";
    public static String DELETE_QUERY = "DELETE FROM jiveUser WHERE userid = 1;";
    public static String UPDATE_QUERY = "UPDATE jiveUser SET status = 1;";
    public static String SELECT_QUERY = "SELECT * FROM jiveUser LIMIT 2;";
    public static String CREATE_QUERY = "CREATE OR REPLACE FUNCTION xyz;";
    public static String SELECT_INTO_QUERY = "SELECT * INTO jiveUser_Backup FROM jiveUser;";
    public static String NESTED_SELECT_QUERY = "SELECT * FROM jiveUser WHERE userID IN (SELECT userID FROM jiveMessage);";

    @Test
    public void checkQuery_does_not_allow_anything_except_selects(){
        boolean expected = false;
        assertEquals(expected, queryExecute.validateSelectQuery(INSERT_QUERY));
        assertEquals(expected, queryExecute.validateSelectQuery(DELETE_QUERY));
        assertEquals(expected, queryExecute.validateSelectQuery(UPDATE_QUERY));
        assertEquals(expected, queryExecute.validateSelectQuery(CREATE_QUERY));
        assertEquals(expected, queryExecute.validateSelectQuery(SELECT_INTO_QUERY));
    }

    @Test
    public void validateQuery_allows_select_statements_or_null(){
        boolean expected = true;
        assertEquals(expected, queryExecute.validateSelectQuery(SELECT_QUERY));
        assertEquals(expected, queryExecute.validateSelectQuery(null));
        assertEquals(expected, queryExecute.validateSelectQuery(NESTED_SELECT_QUERY));
    }

    @Test
    public void validateQuery_allows_lowercase_queries(){
        boolean expected = true;
        assertEquals(expected, queryExecute.validateSelectQuery(LOWERCASE_SELECT));
    }

}