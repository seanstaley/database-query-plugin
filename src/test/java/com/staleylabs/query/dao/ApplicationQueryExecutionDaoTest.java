package com.staleylabs.query.dao;

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

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * StaleyLabs
 *
 * @author Sean M. Staley
 * @version 2.0 (3/19/13)
 */

public class ApplicationQueryExecutionDaoTest {
    private static final int RESULT_SIZE = 10;
    private static final String SELECT_JIVE_USER = "SELECT * FROM JIVEUSER;";
    private static final String SELECT_JIVE_USER_COUNT = "SELECT COUNT(1) FROM JIVEUSER;";

    @InjectMocks
    private ApplicationQueryExecutionDao classUnderTest;

    private List<Map<String, Object>> mockResultSet;

    @Mock
    private JiveJdbcDaoSupport jiveJdbcDaoSupport;

    @Before
    public void setUp() throws Exception {
        classUnderTest = new ApplicationQueryExecutionDao();
        mockResultSet = buildMockResultSet(RESULT_SIZE);

        initMocks(this);
    }

    @After
    public void tearDown() throws Exception {
        classUnderTest = null;
        mockResultSet = null;
    }

    @Test
    public void testIsOverResultLimit() throws Exception {

    }

    @Test
    public void testGenerateQuerySizeString() throws Exception {
        String query = "SELECT * FROM JIVEUSER;";

        String expected = "SELECT COUNT(1) FROM JIVEUSER;";
        String actual = classUnderTest.generateQuerySizeString(query);

        assertThat(actual, is(expected));
    }

    @Test
    public void testGenerateQuerySizeString_mixed_case() throws Exception {
        String query = "SELECT * From JIVEUSER;";

        String expected = "SELECT COUNT(1) FROM JIVEUSER;";
        String actual = classUnderTest.generateQuerySizeString(query);

        assertThat(actual, is(expected));
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
