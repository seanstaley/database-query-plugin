package com.staleylabs.query.service;

import au.com.bytecode.opencsv.CSVWriter;
import com.jivesoftware.community.JiveGlobals;
import com.jivesoftware.community.audit.aop.Audit;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import java.io.*;
import java.util.List;

/**
 * Service layer for exporting queries to CSV format instead of a List of List of List of List format.
 *
 * @author Sean M. Staley
 * @version 2.0
 */

@Service
public class CsvBuildService {

    private static final Logger log = Logger.getLogger(CsvBuildService.class);

    protected static final String RESULT_SET_TOO_LARGE_TO_COMPLETE = "Result Set Too Large to Complete.";

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

            if (JiveGlobals.getJiveBooleanProperty("staleylabs.csv.writeBOM")) {
                writer.write('\ufeff');
            }

            IOUtils.copy(new ByteArrayInputStream(csv.getBytes("UTF-8")), writer, "UTF-8");
            writer.flush();

            csvStream = new ByteArrayInputStream(baos.toByteArray());
        } catch (IOException e) {
            log.warn("UTF-8 not supported for CSV encoding, using default encoding", e);

            csvStream = new ByteArrayInputStream(csv.getBytes());
        } finally {
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
    @Audit
    public String generateCsv(List<List<String>> arraysOfRows, long maxRows) {
        int numberOfColumns = arraysOfRows.get(0).size();

        // Allocate the line array once
        String[] line = new String[numberOfColumns];

        // Remember the 'Not Available' string
        StringWriter writer = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(writer);

        for (List<String> currentRow : arraysOfRows) {
            for (int i = 0; i < currentRow.size(); i++) {
                line[i] = currentRow.get(i);
            }
            csvWriter.writeNext(line);
        }

        if (arraysOfRows.size() >= maxRows) {
            line = new String[1];
            line[0] = RESULT_SET_TOO_LARGE_TO_COMPLETE;

            csvWriter.writeNext(line);
        }

        return writer.toString();
    }
}
