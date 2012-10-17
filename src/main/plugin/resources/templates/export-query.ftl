<html>
<head>
    <title>Export Query Results</title>
    <meta name="pageID" content="database-query-page"/>
    <content tag="pagetitle">Export Query Results</content>
    <content tag="pageID">export-query-page</content>
    <content tag="pagehelp">
        <h3>Export Results</h3>
        This page provides you with the ability to export query results from the application database within the
        <i>Jive SBS Admin Console</i>. Please note that you can only perform <b>SELECT</b> queries
        from within this portion of the application. The results will be shown below once you submit the query
        to the application database.
    </content>

    <link rel='stylesheet' type='text/css'
          href="<@s.url value='/plugins/database-query-plugin/resources/styles/dbQuery.css'/>"/>

    <script type="text/javascript">
        function doExport() {
            $j('#export_all_form').submit();
            $j('#jive-link-memberExportStarted').show();
            $j('#jive-link-exportAllMembers').hide();
        }
    </script>

</head>

<body>
<p>
    This page provides you with the ability to directly query the application database from within the
    <i>Admin Console</i> of your Jive application. Please note that you can only perform <strong>SELECT</strong> queries
    from within this portion of the application. The results will be shown below once you submit the query.
    <br/>

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

<form id="export_all_form" method="post"
      action="<@s.url action='export-query-results'><@s.param name='databaseQuery' value='${databaseQuery}' /></@s.url>">
    <textarea name="databaseQuery" label="Database Query: " cols="80" rows="10"></textarea>
<@jive.token name="dbQuery.resultExport.${exportHash}" />
</form>

<@jive.renderActionSidebar 'database-query-actions' />
</p>
</body>
</html>