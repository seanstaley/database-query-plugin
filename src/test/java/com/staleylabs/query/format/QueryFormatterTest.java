package com.staleylabs.query.format;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertThat;

/**
 * StaleyLabs
 *
 * @author Sean M. Staley
 * @version 2.0 (9/27/13)
 */

public class QueryFormatterTest {

    private List<Map<String, Object>> queryResults = Lists.newArrayList();

    @Before
    public void setUp() throws Exception {
        queryResults = buildResultSet();
    }

    @Test
    public void testRetrieveColumnNames() throws Exception {
        List<String> result = QueryFormatter.retrieveColumnNames(queryResults);
        List<String> expected = Arrays.asList("column1", "column2", "column3", "column4", "column5");

        assertThat(result, is(expected));
    }

    @Test
    public void testRetrieveColumnNames_null() throws Exception {
        List<String> result = QueryFormatter.retrieveColumnNames(new ArrayList<Map<String, Object>>());

        assertNull(result);
    }

    @Test
    public void testFormatResults() throws Exception {
        List<List<String>> result = QueryFormatter.formatResults(queryResults);
        List<String> expectedColumns = Arrays.asList("column1", "column2", "column3", "column4", "column5");

        for (int i = 0; i < result.get(0).size(); i++) {
            assertThat(result.get(0).get(i), is(expectedColumns.get(i)));
        }

        for (int row = 1; row < result.size(); row++) {
            for (int column = 0; column < result.get(row).size(); column++) {
                assertThat(result.get(row).get(column), is(String.format("%s:%s", column+1, row-1)));
            }
        }
    }

    private List<Map<String, Object>> buildResultSet() {
        final List<Map<String, Object>> queryResults = Lists.newLinkedList();

        for (int i = 0; i < 5; i++) {
            final Map<String, Object> map = Maps.newLinkedHashMap();
            map.put("column1", "1:" + i);
            map.put("column2", "2:" + i);
            map.put("column3", "3:" + i);
            map.put("column4", "4:" + i);
            map.put("column5", "5:" + i);

            queryResults.add(map);
        }

        return queryResults;
    }
}
