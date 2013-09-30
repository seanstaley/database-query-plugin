package com.staleylabs.query.action.csv;

import com.jivesoftware.community.JiveGlobals;
import com.jivesoftware.community.action.admin.AdminActionSupport;
import com.jivesoftware.util.StringUtils;
import com.staleylabs.query.service.CsvBuildService;
import com.staleylabs.query.service.QueryService;
import com.staleylabs.query.validator.QueryValidator;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.BadSqlGrammarException;

import java.io.InputStream;
import java.util.Date;
import java.util.List;

/**
 * StaleyLabs
 *
 * @author Sean M. Staley
 * @version 2.0
 * @since 1.0 (9/17/12)
 */

public class QueryCsvAction extends AdminActionSupport {

    private static final Logger log = Logger.getLogger(QueryCsvAction.class);

    private static final int MAX_ROWS = JiveGlobals.getJiveIntProperty("staleylabs.csv.limit", 1000);

    private final Date date = new Date();

    private InputStream csvStream;

    private String databaseQuery;

    private boolean selectQuery = true;

    private boolean cleanQuery = true;

    @Autowired
    private QueryService queryService;

    @Autowired
    private CsvBuildService csvBuildService;

    @Override
    public String execute() {

        if (StringUtils.isBlank(databaseQuery)) {
            return INPUT;
        }

        //Is the query NOT a SELECT query?
        if (QueryValidator.isNotSelectQuery(databaseQuery)) {
            selectQuery = false;

            return INPUT;
        }

        final List<List<String>> arrayOfRows;
        try {
            arrayOfRows = queryService.returnBulkQueryResults(databaseQuery, MAX_ROWS);
            response.setHeader("Cache-Control", "private");

            String csv = csvBuildService.generateCsv(arrayOfRows);
            csvStream = csvBuildService.createCsvStream(csv);

        } catch (BadSqlGrammarException bse) {
            log.error("Bad SQL grammar when generating a CSV in the Application Database.\n" + databaseQuery);
            cleanQuery = false;

            return INPUT;
        }

        return "export";
    }

    public InputStream getCsvStream() {
        return csvStream;
    }

    /**
     * Generates the name of the CSV export file for the query results, if any.
     *
     * @return the csv export filename for the current query results
     */
    public String getDatabaseCSVFilename() {
        return StringUtils.makeURLSafe(getText("dbQuery.csv.filename")) + ".csv";
    }

    public String getDatabaseQuery() {
        return (databaseQuery == null) ? databaseQuery = " " : databaseQuery;
    }

    public void setDatabaseQuery(String databaseQuery) {
        this.databaseQuery = databaseQuery;
    }

    public boolean getSelectQuery() {
        return selectQuery;
    }

    public String getExportHash() {
        return Long.toString(Math.abs((getDatabaseQuery() + getUser().getID() + date.getTime()).hashCode()));
    }

    public boolean isCleanQuery() {
        return cleanQuery;
    }
}
