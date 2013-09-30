<html>
<head>
    <title>Analytics Database Querying</title>
    <meta name="pageID" content="analytics-query-page"/>
    <content tag="pagetitle">Analytics Database Querying</content>
    <content tag="pageID">analytics-query-page</content>
    <content tag="pagehelp"></content>

    <link rel='stylesheet' type='text/css'
          href='<@s.url value="/plugins/database-query-plugin/resources/styles/dbQuery.css"/>'/>
</head>
<body>
<div id="queryInformation" class="dbqInformation">
    <p>
        This page provides you with the ability to directly query the analytics database from within the
        <i>Admin Console</i> of your Jive application. Please note that you can only perform <strong>SELECT</strong>
        queries from within this portion of the application. The results will be shown below once you submit the query
        to the application database.
        <br>
        <br>
        To view the schema of the application database, follow this <a
            href="<@s.text name="dbQuery.link.database.analytics" />"
            title="<@s.text name="dbQuery.hover.database.analytics" />"
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

<#if result?? && result.badSyntax>
    <div id="jive-error-box" class="jive-error-box" style>
        <span class="jive-icon-med jive-icon-redalert"></span>
        <@s.text name="dbQuery.query.error.databaseQuery.dirtyQuery" />
    </div>
</#if>
</div>

<#-- Form Beginning -->
<form id="queryForm" action="analytics-query-page.jspa" method="POST">
    <textarea name="databaseQuery" cols="80" rows="10">
    <#if result?? && result.query??>${result.query}</#if>
    </textarea>
    <br>

    <div style="padding: 5px">
        <input type="submit" value="Submit" onclick="submitNewQuery()"/> <input type="reset" value="Cancel"/>
    </div>
</form>
<#-- Form Ending -->

<#-- Completed Query Start -->
<#if completed && result?? && !result.badSyntax>
<br>
<strong>
    <@s.text name="dbQuery.query.success.databaseQuery.message" />
</strong>
<br>
<br>

<div id="resultDiv">
    <#if result?? && result.resultSet??>
        <#if !result.resultSet?has_content>
            <@s.text name="dbQuery.query.error.databaseQuery.noResults" />
        <#else>
            <table id="resultTable" border="0">
                <#list result.resultSet as arraylists>
                    <tr>
                        <#list arraylists as rowEntry>
                            <td>${rowEntry}</td>
                        </#list>
                    </tr>
                </#list>
            </table>
        </#if>
    </#if>
</div>

<div id="pageOptions" class="resultOptions">
    <span style="display: none" id="resultPageCount">${result.currentResultsPerPage}</span>
    <span style="display: none" id="pageCount">${result.currentPage}</span>

    <strong>Results Per Page:</strong>
    <select id="resultPageSelect" name="resultsPerPage" form="queryForm" onchange="updateResultsPerPage()">
        <option value="10">10</option>
        <option value="20">20</option>
        <option value="30">30</option>
        <option value="40">40</option>
        <option value="50">50</option>
    </select>

    <strong>Select Page:</strong>
    <select name="currentPage" id="pageSelect" form="queryForm" onchange="sendForm()">
        <#assign x = result.totalPages>
        <#list 1..x as i>
            <option value="${i}">${i}</option>
        </#list>
    </select>
</div>
</#if>
<#-- Completed Query Ending -->

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

    ga('create', 'UA-40493136-1');
    ga('send', 'pageview');
</script>
</body>
</html>