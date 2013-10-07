package com.staleylabs.query.service;

import com.jivesoftware.util.StringUtils;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * StaleyLabs
 *
 * @author Sean M. Staley
 * @version 2.0 (10/6/13)
 */

public class CsvBuildServiceTest {

    private CsvBuildService csvBuildService;

    private List<List<String>> arraysOfRows;

    @Before
    public void setUp() throws Exception {
        csvBuildService = new CsvBuildService();
    }

    @After
    public void tearDown() throws Exception {
        csvBuildService = null;
        arraysOfRows = null;
    }

    @Test
    public void testGenerateCsv_works() throws Exception {
        arraysOfRows = buildArrayOfRows(1);

        String actual = csvBuildService.generateCsv(arraysOfRows, 1000);

        System.out.println("CSV generated array:" + actual);

        assertTrue(StringUtils.startsWith(actual, "\"00\",\"01\",\"02\",\"03\",\"04\"\n"));
    }

    @Test
    public void testGenerateCsv_overflow() throws Exception {
        arraysOfRows = buildArrayOfRows(1001);

        String actual = csvBuildService.generateCsv(arraysOfRows, 1000);

        System.out.println("CSV generated array:" + actual);

        assertTrue(StringUtils.startsWith(actual, "\"00\",\"01\",\"02\",\"03\",\"04\"\n"));
        assertTrue(StringUtils.endsWith(actual, "\"" + CsvBuildService.JIVE_SUPPORT_TEXT + "\"\n"));
    }

    private List<List<String>> buildArrayOfRows(int rowCount) {
        List<List<String>> result = new LinkedList<List<String>>();

        for (int i = 0; i < rowCount; i++) {
            List<String> row = new LinkedList<String>();

            for (int j = 0; j < 5; j++) {
                row.add(String.format("%d%d", i, j));
            }

            result.add(row);
        }

        return result;
    }
}
