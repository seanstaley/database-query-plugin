<html xmlns="http://www.w3.org/1999/html">
<head>
    <title>Analytic Database Querying</title>
    <meta name="pageID" content="analytics-query-page"/>
    <content tag="pagetitle">Analytic Database Querying</content>
    <content tag="pageID">analytics-query-page</content>
    <content tag="pagehelp">
        <h3>Query the Analytics Database</h3>
        This page provides you with the ability to directly query the analytics database from within the
        <i>Admin Console</i> of your Jive application. Please note that you can only perform <b>SELECT</b> queries
        from within this portion of the application. The results will be shown below once you submit the query
        to the application database.
    </content>

    <link rel='stylesheet' type='text/css'
          href="<@s.url value='/plugins/database-query-plugin/resources/styles/dbQuery.css'/>"
</head>

<body>
<#if analyticsModuleAvailable && analyticsAvailable>
<p>
    This page provides you with the ability to directly query the analytics database from within the
    <i>Admin Console</i> of your Jive application. Please note that you can only perform <strong>SELECT</strong> queries
    from within this portion of the application. The results will be shown below once you submit the query
    to the application database.
    <br>
    <br>
    To view the schema of the analytics database, follow this <a
        href=<@s.text name="dbQuery.link.database.analytics" /> target="_blank"
    title="<@.text name="dbQuery.hover.database.analytic"/>">link</a>.
    <br>
    <br>
</p>
    <#if !selectQuery>
    <div id="jive-error-box" class="jive-error-box" style>
        <span class="jive-icon-med jive-icon-redalert"></span>
        <@s.text name="dbQuery.query.error.databaseQuery.notSelect" />
    </div>
    </#if>

    <#if !cleanQuery>
    <div id="jive-error-box" class="jive-error-box" style>
        <span class="jive-icon-med jive-icon-redalert"></span>
        <@s.text name="dbQuery.query.error.databaseQuery.dirtyQuery" />
    </div>
    </#if>

<form id="queryForm" action="analytics-query-page.jspa" method="POST">
    <textarea name="databaseQuery" label="Database Query: " cols="80" rows="10">
        <#if result?? && result.query??>${result.query}</#if>
    </textarea>
    <br>
    <input type="submit" value="Submit"/> <input type="reset" value="Clear"/>
</form>

    <#if completed>
    <br>
    <i><@s.text name="dbQuery.query.success.analyticsQuery.message" /> ${result.databaseQuery}</i>
    <br><br>

        <#if !results>
        <div id="jive-info-box" class="jive-info-box" style>
            <span class="jive-icon-med jive-icon-info"></span>
            <@s.text name="dbQuery.query.error.databaseQuery.noResults" />
        </div>
        </#if>

    <div id="resultDiv">
        <table id="resultTable" border="0">
            <#list result.queryResults as arraylists>
                <tr>
                    <#list arraylists as rowEntry>
                        <td>${rowEntry}</td>
                    </#list>
                </tr>
            </#list>
        </table>
    </div>

    <div id="pageOptions" style="padding: 5px">
        <div id="resultPerPageDiv" style="align: left">
            <span style="display: none" id="resultPageCount">${result.currentResultsPerPage}</span>

            <strong>Results Per Page:</strong>
            <select id="resultPageSelect" name="resultsPerPage" class="selection" form="queryForm">
                <option value="10">10</option>
                <option value="20">20</option>
                <option value="30">30</option>
                <option value="40">40</option>
                <option value="50">50</option>
            </select>
        </div>
        <div id="queryPagination">
            <span style="display: none" id="pageCount">${result.currentPage}</span>

            <strong>Select Page:</strong>
            <select name="currentPage" id="pageSelect" form="queryForm" class="selection" onchange="sendForm()">
                <#assign x = result.totalPages>
                <#list 1..x as i>
                    <option value="${i}">${i}</option>
                </#list>
            </select>
        </div>
    </div>
    </#if>
<script type="text/javascript" src='<@s.url value="/plugins/database-query-plugin/resources/script/main.js"/>'></script>
<#else>
    <@s.text name="dbQuery.analytics.module.disabled" />
</#if>
</body>
</html>