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

    <link rel='stylesheet' type='text/css' href="<@s.url value='/plugins/database-query-plugin/resources/styles/dbQuery.css'/>"
</head>

<body>
    <p>
        This page provides you with the ability to directly query the application database from within the
        <i>Admin Console</i> of your Jive application. Please note that you can only perform <strong>SELECT</strong> queries
        from within this portion of the application. The results will be shown below once you submit the query
        to the application database.
        <br>
        <br>
        To view the schema of the application database, follow this <a href=<@s.text name="dbQuery.link.database.application" /> title="Jive 5.0 Application Database Schema" target="_blank">link</a>.
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
            <input type="submit" value="Submit" /> <input type="reset" value="Cancel" />
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

            <div style="border: 2px ridge;  background-color: #FFFFFF; overflow: scroll; width:800px; height: 200px;">
                <#list queryResults as queryResults>
                    <p class="results">
                        ${queryResults?replace(',',' | ')?replace('{','')?replace('}','')?replace('=',' = ')}<br>
                    </p>
                    <hr size="2"/>
                </#list>
            </div>
        </#if>
    </p>
</body>
</html>