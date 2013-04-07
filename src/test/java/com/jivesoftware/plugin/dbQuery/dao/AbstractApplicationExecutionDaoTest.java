package com.jivesoftware.plugin.dbQuery.dao;

import com.jivesoftware.base.database.dao.JiveJdbcDaoSupport;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * StaleyLabs
 *
 * @author Sean M. Staley
 * @version 2.0 (3/19/13)
 */

public class AbstractApplicationExecutionDaoTest {
    private static final int RESULT_SIZE = 10;
    private static final String SELECT_JIVE_USER = "SELECT * FROM JIVEUSER;";
    private static final String SELECT_JIVE_USER_COUNT = "SELECT COUNT(*) FROM JIVEUSER;";

    @InjectMocks
    private MockExecutionDao classUnderTest;
    private List<Map<String, Object>> mockResultSet;

    @Mock
    private JiveJdbcDaoSupport jiveJdbcDaoSupport;

    @Before
    public void setUp() throws Exception {
        classUnderTest = new MockExecutionDao();
        mockResultSet = buildMockResultSet(RESULT_SIZE);

        initMocks(this);
        when(jiveJdbcDaoSupport.getSimpleJdbcTemplate().queryForList(SELECT_JIVE_USER)).thenReturn(mockResultSet);
        when(jiveJdbcDaoSupport.getSimpleJdbcTemplate().queryForInt(SELECT_JIVE_USER_COUNT)).thenReturn(RESULT_SIZE);
    }

    @After
    public void tearDown() throws Exception {
        classUnderTest = null;
        mockResultSet = null;
    }

    @Test
    public void testRetrieveResults() throws Exception {
        List<Map<String, Object>> result = classUnderTest.retrieveResults(SELECT_JIVE_USER);

        verify(jiveJdbcDaoSupport.getSimpleJdbcTemplate().queryForList(SELECT_JIVE_USER));
        assertEquals(mockResultSet, result);
    }

    @Test
    public void testGetQueryResultSize() throws Exception {
        int expected = RESULT_SIZE;
        int actual = classUnderTest.getQueryResultSize(SELECT_JIVE_USER);
        assertEquals(expected, actual);
    }

    @Test
    public void testIsOverResultLimit() throws Exception {

    }

    private List<Map<String, Object>> buildMockResultSet(int resultSize) {
        List<Map<String, Object>> resultList = new ArrayList<Map<String, Object>>(resultSize);

        for(int count = 0; count<resultSize; count++)  {
            Map<String, Object> resultMap = new ConcurrentHashMap<String, Object>();
            resultMap.put("Username", "user"+count);
            resultMap.put("Password", "wazup"+count);
            resultList.add(resultMap);
        }
        return resultList;
    }
}
class MockExecutionDao extends AbstractApplicationExecutionDao{};
