<html>
<head>
    <title>About Database Query Plugin</title>
    <meta name="pageID" content="database-query-about"/>
    <content tag="pagetitle">Database Query Plugin</content>
    <content tag="pageID">database-query-about</content>
    <content tag="pagehelp">
        <h3>About the Plugin</h3>
        This page provides you with the information surrounding the database
        query plugin. This includes the author, bugs, source, etc.
    </content>
    <link rel='stylesheet' type='text/css'
          href="<@s.url value='/plugins/database-query-plugin/resources/styles/about.css'/>"/>
    <script type="text/javascript"
            src="<@s.url value='/plugins/database-query-plugin/resources/script/vendor/jquery-1.9.1.min.js'/>"></script>
    <script type="text/javascript"
            src="<@s.url value='/plugins/database-query-plugin/resources/script/query-js.js'/>"></script>
</head>
<body>
<div id="authorSection">
    <h4>Developed by:</h4>
    <ul>
        <li>Sean M. Staley</li>
        <li>Ben Walker</li>
    </ul>
</div>
<div id="hostedBy">
    <h4>Open Source Project</h4>
    <a href="https://github.com/seanstaley/database-query-plugin">
        <img class="text-center-image"
             src="<@s.url value='/plugins/database-query-plugin/resources/images/github.png'/>"/>
    </a>
    Source control and issue tracking provided by GitHub.
</div>
<div id="feedback">
    <h4>Feedback, Issue Reporting</h4>

    <p>
        Have an issue that you would like to have reported? Have feedback about this
        plugin? Feel free to contact me using this <a href="mailto:sean.staley@gmail.com">
        link</a>.
    </p>
</div>

<div id="GitHubIssues">
    <h3>Opened Bugs and Enhancements</h3>
    <table id="githubTable" class="bordered">
        <tr>
            <td></td>
            <td>No issues are currently opened!</td>
        </tr>
    </table>
</div>
</body>
</html>