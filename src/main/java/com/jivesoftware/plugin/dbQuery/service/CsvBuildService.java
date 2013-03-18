package com.jivesoftware.plugin.dbQuery.service;

import au.com.bytecode.opencsv.CSVWriter;
import com.jivesoftware.util.StringUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * StaleyLabs
 *
 * @author Sean M. Staley
 * @version 6.0-2 (3/8/13)
 */
public class CsvBuildService {
    private static Logger log = Logger.getLogger(CsvBuildService.class);

    /**
     * Creates and returns an InputStream to stream the given CSV file.
     *
     * @param csv a String representation of the CSV file to create an InputStream from
     * @return an InputStream for the given CSV contents
     */
    public InputStream createCsvStream(String csv) {
        ByteArrayOutputStream baos = null;
        Writer writer = null;
        InputStream csvStream = null;

        try {
            // need to encode the csv into UTF-8
            baos = new ByteArrayOutputStream();
            writer = new OutputStreamWriter(baos);
            IOUtils.copy(new ByteArrayInputStream(csv.getBytes("UTF-8")), writer, "UTF-8");
            writer.flush();
            csvStream = new ByteArrayInputStream(baos.toByteArray());
        }
        catch (IOException e) {
            log.warn("UTF-8 not supported for CSV encoding, using default encoding", e);
            csvStream = new ByteArrayInputStream(csv.getBytes());
        }
        finally {
            IOUtils.closeQuietly(baos);
            IOUtils.closeQuietly(writer);
        }

        return csvStream;
    }

    /**
     * Builds a String representation of the full CSV file contents the query's result.
     *
     * @param arraysOfRows an ArrayList of ArrayList rows that will be written to the CSV
     * @return the full CSV file contents for all results returned from the two input parameters
     */
    public String generateCsv(List<ArrayList<String>> arraysOfRows) {
        int numberOfColumns = arraysOfRows.get(0).size();
        // Allocate the line array once
        String[] line = new String[numberOfColumns];

        // Remember the 'Not Available' string
        StringWriter writer = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(writer);

        for (List<String> currentRow : arraysOfRows) {
            for (int i = 0; i < currentRow.size(); i++) {
                line[i] = currentRow.get(i).toString();
            }
            csvWriter.writeNext(line);
        }

        return writer.toString();
    }

    /**
     * Generates the name of the CSV export file for the query results, if any.
     *
     * @return the csv export filename for the current query results
     */
    public String generateCsvFilename() {
        return StringUtils.makeURLSafe("application.csv");
    }
}
