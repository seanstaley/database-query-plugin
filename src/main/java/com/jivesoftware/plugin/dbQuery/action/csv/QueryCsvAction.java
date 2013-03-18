package com.jivesoftware.plugin.dbQuery.action.csv;

import au.com.bytecode.opencsv.CSVWriter;
import com.jivesoftware.community.action.admin.AdminActionSupport;
import com.jivesoftware.plugin.dbQuery.service.QueryFormatService;
import com.jivesoftware.plugin.dbQuery.service.QueryValidationService;
import com.jivesoftware.util.StringUtils;
import org.apache.commons.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;

import java.io.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;

/**
 * StaleyLabs
 *
 * @author Sean M. Staley
 * @version 1.0 (9/17/12)
 */
public class QueryCsvAction extends AdminActionSupport {
    private static Logger log = Logger.getLogger(QueryCsvAction.class);
    Date date = new Date();
    private InputStream csvStream;
    private String databaseQuery;
    private boolean selectQuery = true;
    private boolean cleanQuery = true;

    @Autowired
    private QueryValidationService validationService;
    @Autowired
    private QueryFormatService formatService;

    public String execute() {
        ArrayList<ArrayList<String>> arrayOfRows;

        if (databaseQuery == null || databaseQuery.equals(" ")) {
            return INPUT;
        }

        //Is the query NOT a SELECT query?
        if (!validationService.validateSelectQuery(databaseQuery)) {
            setSelectQuery(false);
            return INPUT;
        }

        try {
            arrayOfRows = formatService.returnQueryResults(databaseQuery);
            response.setHeader("Cache-Control", "private");
            String csv = generateCsv(arrayOfRows);
            InputStream stream = createCsvStream(csv);
            setCsvStream(stream);

        } catch (BadSqlGrammarException bse) {
            log.error("Database Query Plugin: Bad SQL grammar when querying Application Database by " +
                    getUser().getUsername());
            setCleanQuery(false);
            return INPUT;

        }
        return "export";
    }

    /**
     * Generates the name of the CSV export file for the query results, if any.
     *
     * @return the csv export filename for the current query results
     */
    public String getDatabaseCSVFilename() {
        return StringUtils.makeURLSafe(getText("dbQuery.csv.filename")) + ".csv";
    }

    /**
     * Builds a String representation of the full CSV file contents the query's result.
     *
     * @param arraysOfRows an ArrayList of ArrayList rows that will be written to the CSV
     * @return the full CSV file contents for all results returned from the two input parameters
     */
    protected String generateCsv(ArrayList<ArrayList<String>> arraysOfRows) {
        int numberOfColumns = arraysOfRows.get(0).size();
        // Allocate the line array once
        String[] line = new String[numberOfColumns];

        // Remember the 'Not Available' string
        String notAvailable = getText("dbQuery.csv.notavailable");
        StringWriter writer = new StringWriter();
        CSVWriter csvWriter = new CSVWriter(writer);

        for (ArrayList<String> currentRow : arraysOfRows) {
            Iterator currentRowIterator = currentRow.iterator();
            for (int i = 0; i < currentRow.size(); i++) {
                line[i] = currentRow.get(i).toString();
            }
            csvWriter.writeNext(line);
        }

        return writer.toString();
    }

    /**
     * Creates and returns an InputStream to stream the given CSV file.
     *
     * @param csv a String representation of the CSV file to create an InputStream from
     * @return an InputStream for the given CSV contents
     */
    protected InputStream createCsvStream(String csv) {
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
     * Getter for csvStream member.
     *
     * @return the csvStream member of the class which may be null
     */
    public InputStream getCsvStream() {
        return csvStream;
    }

    /**
     * Setter for csvStream member.
     *
     * @param csvStream the InputStream that csvStream should reference
     */
    public void setCsvStream(InputStream csvStream) {
        this.csvStream = csvStream;
    }

    public String getDatabaseQuery() {
        return (databaseQuery == null) ? databaseQuery = " " : databaseQuery;
    }

    public void setDatabaseQuery(String databaseQuery) {
        if (databaseQuery == null) {
            this.databaseQuery = " ";
        }
        this.databaseQuery = databaseQuery;
    }

    public boolean getSelectQuery() {
        return selectQuery;
    }

    public void setSelectQuery(boolean selectQuery) {
        this.selectQuery = selectQuery;
    }

    public String getExportHash() {
        return Long.toString(Math.abs((getDatabaseQuery() + getUser().getID() + date.getTime()).hashCode()));
    }

    public boolean isCleanQuery() {
        return cleanQuery;
    }

    public void setCleanQuery(boolean cleanQuery) {
        this.cleanQuery = cleanQuery;
    }
}
