package com.staleylabs.query.service;

import com.staleylabs.query.beans.QueryPage;
import com.staleylabs.query.dao.QueryExecutionDao;
import com.staleylabs.query.dto.QueryResultTO;
import org.junit.Before;
import org.junit.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.jdbc.BadSqlGrammarException;

import java.sql.SQLException;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertTrue;
import static org.mockito.MockitoAnnotations.initMocks;

/**
 * StaleyLabs
 *
 * @author Sean M. Staley
 * @version 2.0 (10/6/13)
 */

public class QueryServiceTest {

    private static final String GOOD_QUERY = "SELECT * FROM JIVEUSER;";

    private static final String BAD_QUERY = "SELECT * FROM JIVEPIZZA";

    QueryPage<Map<String, Object>> mapQueryPage;

    @InjectMocks
    private QueryService queryService;

    @Mock
    private QueryExecutionDao mockExecutionDao;

    @Before
    public void setUp() throws Exception {
        queryService = new QueryService(mockExecutionDao);
        mapQueryPage = buildQueryPage();

        initMocks(this);
    }

    @Test
    public void testMapQueryResults() throws Exception {
        QueryResultTO actual = QueryService.mapQueryResults(GOOD_QUERY, 1, 10, mapQueryPage);

        assertEquals(5, actual.getTotalPages(), 0);
        assertEquals(1, actual.getCurrentPage(), 0);

        assertEquals("userId", actual.getResultSet().get(0).get(0));
    }

    @Test
    public void testMapBadQueryResults() throws Exception {
        BadSqlGrammarException exception = new BadSqlGrammarException("Table did not exist", BAD_QUERY, new SQLException());
        final QueryResultTO resultTO = QueryService.mapBadQueryResults(BAD_QUERY, exception);

        assertEquals("Table did not exist; bad SQL grammar [SELECT * FROM JIVEPIZZA]; nested exception is java.sql.SQLException", resultTO.getBadSyntaxDescription());
        assertEquals(BAD_QUERY, resultTO.getQuery());
        assertTrue(resultTO.isBadSyntax());
    }

    private QueryPage<Map<String, Object>> buildQueryPage() {
        QueryPage<Map<String, Object>> result = new QueryPage<Map<String, Object>>();
        List<Map<String, Object>> resultSet = new LinkedList<Map<String, Object>>();


        for(int i=1; i<3; i++) {
            Map<String, Object> row = new LinkedHashMap<String, Object>();
            row.put("userId", i);
            row.put("name", "sean"+i);

            resultSet.add(row);
        }

        result.setPageItems(resultSet);
        result.setPageNumber(1);
        result.setPagesAvailable(5);

        return result;
    }
}
