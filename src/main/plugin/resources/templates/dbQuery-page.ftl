<html>
<head>
    <title>Application Database Querying</title>
    <meta name="pageID" content="database-query-page"/>
    <content tag="pagetitle">Application Database Querying</content>
    <content tag="pageID">database-query-page</content>
    <content tag="pagehelp">
        <h3>Query the Application Database</h3>
        This page provides you with the ability to directly query the application database from within the
        <i>Admin Console</i> of your Jive application. Please note that you can only perform <b>SELECT</b> queries
        from within this portion of the application. The results will be shown below once you submit the query
        to the application database.
    </content>

    <link rel='stylesheet' type='text/css'
          href="<@s.url value='/plugins/database-query-plugin/resources/styles/dbQuery.css'/>"/>

</head>

<body>
<p>
    This page provides you with the ability to directly query the application database from within the
    <i>Admin Console</i> of your Jive application. Please note that you can only perform <strong>SELECT</strong> queries
    from within this portion of the application. The results will be shown below once you submit the query
    to the application database.
    <br>
    <br>
    To view the schema of the application database, follow this <a
        href="<@s.text name="dbQuery.link.database.application" />" title="Jive 5.0 Application Database Schema"
        target="_blank">link</a>.
    <br>
    <br>
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

<form action="database-query-page.jspa" method="POST">
    <textarea name="databaseQuery" label="Database Query: " cols="80" rows="10"></textarea>
    <br>
    <input type="submit" value="Submit"/> <input type="reset" value="Cancel"/>
</form>

<#if completed>
<br>
<i><@s.text name="dbQuery.query.success.databaseQuery.message" /> ${databaseQuery}</i>
<br><br>

    <#if !results>
    <div id="jive-info-box" class="jive-info-box" style>
        <span class="jive-icon-med jive-icon-info"></span>
        <@s.text name="dbQuery.query.error.databaseQuery.noResults" />
    </div>
    </#if>

<div id="resultDiv">
    <table id="resultTable" border="0">
        <#list queryResults as arraylists>
            <tr>
                <#list arraylists as rowEntry>
                    <td>${rowEntry}</td>
                </#list>
            </tr>
        </#list>
    </table>
</div>

</#if>
</p>

<script type="text/javascript">
    (function(i,s,o,g,r,a,m){i['GoogleAnalyticsObject']=r;i[r]=i[r]||function(){
        (i[r].q=i[r].q||[]).push(arguments)},i[r].l=1*new Date();a=s.createElement(o),
            m=s.getElementsByTagName(o)[0];a.async=1;a.src=g;m.parentNode.insertBefore(a,m)
    })(window,document,'script','//www.google-analytics.com/analytics.js','ga');

    ga('create', 'UA-40493136-1', 'staleylabs.com');
    ga('send', 'pageview');

</script>
</body>
</html>