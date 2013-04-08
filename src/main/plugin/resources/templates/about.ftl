<html>
<head>
    <title>About Database Query Plugin</title>
    <meta name="pageID" content="database-query-about"/>
    <content tag="pagetitle">About This Plugin</content>
    <content tag="pageID">database-query-about</content>
    <content tag="pagehelp">
        <h3>About the Plugin</h3>
        This page provides you with the information surrounding the database
        query plugin. This includes the author, bugs, source, etc.
    </content>
    <link rel='stylesheet' type='text/css'
          href="<@s.url value='/plugins/database-query-plugin/resources/styles/dbQuery.css'/>"/>
</head>
<body>
<div id="aboutTitle" class="center">
    <h2>Database Query Plugin</h2>
    <h4>Version 6.0-2</h4>
</div>

<div id="authorSection">
    <h4>Developed by:</h4>
    <ul>
        <li>Sean M. Staley, <a href="mailto:sean.staley@gmail.com">sean.staley@gmail.com</a></li>
        <li>Ben Walker, Jive Software</li>
    </ul>
</div>

<div>
    <h3>Source Code</h3>
    <a href="https://github.com/seanstaley/database-query-plugin">
        <img src="<@s.url value='/plugins/database-query-plugin/resources/images/github.png'/>"/>
    </a>
</div>

<div id="GitHubIssues">
    <h3>Opened Issues</h3>
    <ul id="issuesList">
        No issues are currently opened!
    </ul>
</div>

<script type="text/javascript"
        src="<@s.url value='/plugins/database-query-plugin/resources/script/vendor/jquery-1.9.1.min.js'/>"></script>
<script type="text/javascript">
    var gitHubIssuesUrl = 'https://api.github.com/repos/seanstaley/database-query-plugin/issues';

    $.getJSON(gitHubIssuesUrl, {
        state: 'opened',
        sort: 'created',
        direction: 'asc'
    })
            .done(function (data) {
                var openHtmlIssues = "";
                $.each(data, function (i, data) {
                    var issueTitle = data.title;
                    var issueId = data.number;
                    var issueLink = data.html_url;
                    openHtmlIssues = openHtmlIssues + "<p><a href=\"" + issueLink + "\">" + issueId + "</a>: " + issueTitle + "</p>";
                });
                $('#issuesList').html(openHtmlIssues);
            });
</script>
</body>
</html>