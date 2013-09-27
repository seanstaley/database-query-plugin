<html>
<head>
    <title>Application Database Querying</title>
    <meta name="pageID" content="database-query-page"/>
    <content tag="pagetitle">Application Database Querying</content>
    <content tag="pageID">database-query-page</content>
    <content tag="pagehelp">
        <h3>Query the Application Database</h3>

        <p>
            This page provides you with the ability to directly query the application database from within the
            <i>Admin Console</i> of your Jive application. Please note that you can only perform <b>SELECT</b> queries
            from within this portion of the application. The results will be shown below once you submit the query
            to the application database.
        </p>
    </content>

    <link rel='stylesheet' type='text/css'
          href='<@s.url value="/plugins/database-query-plugin/resources/styles/dbQuery.css"/>'/>
</head>
<body>
<div id="queryInformation">
    <p>
        This page provides you with the ability to directly query the application database from within the
        <i>Admin Console</i> of your Jive application. Please note that you can only perform <strong>SELECT</strong>
        queries from within this portion of the application. The results will be shown below once you submit the query
        to the application database.
        <br>
        <br>
        To view the schema of the application database, follow this <a
            href="<@s.text name="dbQuery.link.database.application" />"
            title="<@s.text name="dbQuery.hover.database.application" />"
            target="_blank">link</a>.
    </p>
</div>

<div id="messagesArea">
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
</div>

<form id="queryForm" action="database-query-page.jspa" method="POST">
    <textarea name="databaseQuery" label="Database Query: " cols="80" rows="10">
    <#if result?? && result.query??>${result.query}</#if>
    </textarea>
    <br>

    <div style="padding: 5px">
        <input type="submit" value="Submit"/> <input type="reset" value="Cancel"/>
    </div>
</form>

<#if completed>
<br>
<strong>
    <@s.text name="dbQuery.query.success.databaseQuery.message" />
</strong>
<br>
<br>
    <#if !results>
    <div id="jive-info-box" class="jive-info-box" style>
        <span class="jive-icon-med jive-icon-info"></span>
        <@s.text name="dbQuery.query.error.databaseQuery.noResults" />
    </div>
    </#if>

<div id="resultDiv">
    <table id="resultTable" border="0">
        <#list result.resultSet as arraylists>
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

<#-- Load scripts at the bottom of the page for better performance. -->
<script type="text/javascript" src='<@s.url value="/plugins/database-query-plugin/resources/script/main.js"/>'></script>
<script type="text/javascript">
    (function (i, s, o, g, r, a, m) {
        i['GoogleAnalyticsObject'] = r;
        i[r] = i[r] || function () {
            (i[r].q = i[r].q || []).push(arguments)
        }, i[r].l = 1 * new Date();
        a = s.createElement(o),
                m = s.getElementsByTagName(o)[0];
        a.async = 1;
        a.src = g;
        m.parentNode.insertBefore(a, m)
    })(window, document, 'script', '//www.google-analytics.com/analytics.js', 'ga');

    ga('create', 'UA-40493136-1', 'staleylabs.com');
    ga('send', 'pageview');
</script>
</body>
</html>