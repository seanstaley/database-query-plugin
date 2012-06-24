<html>
    <head>
        <title>Activity Database Querying</title>
        <meta name="pageID" content="database-query-page"/>
        <content tag="pagetitle">Activity Database Querying</content>
        <content tag="pageID">activity-query-page</content>
        <content tag="pagehelp">
            <h3>Query the Activity Database</h3>
            This page provides you with the ability to directly query the activity engine database from within the
            <i>Admin Console</i> of your Jive application. Please note that you can only perform <b>SELECT</b> queries
            from within this portion of the application. The results will be shown below once you submit the query
            to the activity engine database.
        </content>
    </head>

    <body>
        <p>
            This page provides you with the ability to directly query the activity engine database from within the
            <i>Admin Console</i> of your Jive application. Please note that you can only perform <strong>SELECT</strong> queries
            from within this portion of the application. The results will be shown below once you submit the query
            to the activity engine database.
            <br>
            <br>
            To view the schema of the activity database, follow this <a href=<@s.text name="dbQuery.link.database.activity" /> target="_blank" title="Jive 5.0 Activity Engine Database Schema">link</a>.
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

        <form action="activity-query-page.jspa" method="POST">
            <textarea name="databaseQuery" label="Database Query: " cols="80" rows="10"></textarea>
            <br>
            <input type="submit" value="Submit" /> <input type="reset" value="Clear" />
        </form>

            <#if completed>
            <br>
            <i><@s.text name="dbQuery.query.success.analyticsQuery.message" /> ${databaseQuery}</i>
            <br><br>

                <#if !results>
                <div id="jive-info-box" class="jive-info-box" style>
                    <span class="jive-icon-med jive-icon-info"></span>
                    <@s.text name="dbQuery.query.error.databaseQuery.noResults" />
                </div>
                </#if>

                <#list queryResults as queryResults>
                ${queryResults?replace(',',' | ')?replace('{','')?replace('}','')?replace('=',' = ')}<br>
                <hr size="2"/><br>
                </#list>
            </#if>
        </p>
    </body>
</html>