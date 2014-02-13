package com.staleylabs.query.action.query;

import com.jivesoftware.community.JiveGlobals;
import com.jivesoftware.community.action.JiveActionSupport;
import com.jivesoftware.community.entitlements.authorization.RequiresRole;
import com.jivesoftware.util.StringUtils;
import com.staleylabs.query.dto.QueryResultTO;
import com.staleylabs.query.service.CsvBuildService;
import com.staleylabs.query.service.QueryService;
import com.staleylabs.query.validator.QueryValidator;

import java.io.InputStream;
import java.util.List;

/**
 * Base action support for the database query plugin where all types of query pass through.
 *
 * @author Sean M. Staley
 * @version 2.0 (5/14/12)
 */

@SuppressWarnings("FieldCanBeLocal")
@RequiresRole("Manage System")
public class RunQueryAction extends JiveActionSupport {

    /**
     * Constant that represents the number of maximum rows that a given CSV report can hold. A restart of the
     * application is required for this property to take effect, once changed.
     */
    private static final long MAX_ROWS = JiveGlobals.getJiveLongProperty("staleylabs.csv.limit", 1000);

    private QueryResultTO result;

    private String databaseQuery;

    private boolean isSelectQuery = true;

    private boolean isCompleted;

    private boolean isExport;

    private boolean isResults = true;

    private int currentPage;

    private int resultsPerPage;

    private InputStream csvStream;

    /**
     * Dynamic query service that is injected into the action from the sub actions.
     */
    protected QueryService queryService;

    private CsvBuildService csvBuildService;

    @Override
    public String execute() {

        // Parameter validation
        if (StringUtils.isBlank(databaseQuery)) {
            return INPUT;
        }

        if (QueryValidator.isNotSelectQuery(databaseQuery)) {
            isSelectQuery = false;
            return INPUT;
        }

        // Exporting a query? Return export.
        if (isExport) {
            return "export";
        }

        if (currentPage < 1) {
            currentPage = 1;
        }

        if (resultsPerPage < 10 || resultsPerPage > 50) {
            resultsPerPage = 10;
        }

        result = queryService.returnQueryResult(databaseQuery, currentPage, resultsPerPage);

        isCompleted = true;

        if (result.getTotalPages() < 1) {
            isResults = false;
        }

        return "query";
    }

    /**
     * Local method used to export a requested query.
     *
     * @param databaseQuery The query that is to be exported in CSV format.
     * @return {@link InputStream} that contains the CSV file.
     */
    private InputStream doExport(String databaseQuery) {
        List<List<String>> bulkResults = queryService.returnBulkQueryResults(databaseQuery, MAX_ROWS);

        return csvBuildService.createCsvStream(csvBuildService.generateCsv(bulkResults, MAX_ROWS));
    }

    public void setDatabaseQuery(String databaseQuery) {
        this.databaseQuery = databaseQuery.trim();
    }

    public boolean isSelectQuery() {
        return isSelectQuery;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public boolean isResults() {
        return isResults;
    }

    public void setCurrentPage(int currentPage) {
        this.currentPage = (currentPage >= 1) ? currentPage : 1;
    }

    public void setResultsPerPage(int resultsPerPage) {
        this.resultsPerPage = (resultsPerPage >= 10) ? resultsPerPage : 10;
    }

    public QueryResultTO getResult() {
        return result;
    }

    public void setResult(QueryResultTO result) {
        this.result = result;
    }

    public CsvBuildService getCsvBuildService() {
        return csvBuildService;
    }

    public void setCsvBuildService(CsvBuildService csvBuildService) {
        this.csvBuildService = csvBuildService;
    }

    public void setExport(String isExport) {
        this.isExport = true;
    }

    /**
     * Getter for csvStream member.
     *
     * @return the csvStream member of the class which may be null
     */
    public InputStream getCsvStream() {
        return doExport(databaseQuery);
    }

    /**
     * Generates the name of the CSV export file for the impl results, if any.
     *
     * @return the csv export filename for the current impl results
     */
    public String getDatabaseCSVFilename() {
        return StringUtils.makeURLSafe(getText("dbQuery.csv.filename")) + ".csv";
    }
}