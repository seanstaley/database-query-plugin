var gitHubIssuesUrl = 'https://api.github.com/repos/seanstaley/database-query-plugin/issues';

/* jQuery Starts up once the page loads */
$(document).ready(function () {

    /* AJAX call for the current issue list */
    $.getJSON(gitHubIssuesUrl, {
        state: 'opened',
        sort: 'created',
        direction: 'asc'
    }).done(function (data) {
            var openHtmlIssues = "<tr class=\"highlight\"><th>Issue ID</th><th>Issue Description</th><th>Issue State</th></tr>";

            $.each(data, function (i, data) {
                var issueTitle = data.title;
                var issueId = data.number;
                var issueLink = data.html_url;
                var issueState = data.state;
                openHtmlIssues = openHtmlIssues + "<tr class=\"hover\"><td>" + issueId + "</td><td>" + issueTitle + "</td><td>" + issueState + "</td></tr>";
            });
            $('#githubTable').html(openHtmlIssues);
        });
});