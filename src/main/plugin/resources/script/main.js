/**
 * JavaScript functions to be used for the plugin.
 *
 * @author Sean M. Staley
 * @version 2.0
 */

/**
 * Function used to send the query form back to the application once the page number, or results to display has been
 * changed in the UI.
 */
function sendForm() {
    document.getElementById("queryForm").submit();
}

/**
 * Function that is called when the results per page selection is updated.
 */
function updateResultsPerPage() {
    var form = document.forms[0];
    form['currentPage'].value = 1;

    sendForm();
}

/**
 * Updates a selector option to a value provided. Makes the assumption that the value of the requested selector is
 * one (1) off of the actual index selected. eg. 40 = (4 - 1).
 *
 * @param node The DOM node that contains the select tag element.
 * @param value The value of the selector that will be selected.
 */
function updateSelectedOption(node, value) {
    if ((node && node != undefined) && (value && value != undefined)) {

        node.selectedIndex = value - 1;
    }
}

/**
 * On submitting a new query to the application, the page and results per page will need to be set back to the default
 * values of the first page, and 10 results.
 */
function submitNewQuery() {
    var form = document.forms[0];

    if (form['currentPage'] !== undefined && form['resultsPerPage'] !== undefined) {
        form['currentPage'].value = 1;
        form['resultsPerPage'].value = 10;
    }

    var _gaq = _gaq || [];
    _gaq.push(['_trackEvent', 'Query', 'First Query', form['databaseQuery'].value]);
}

function recordExport() {
    var form = document.forms[0];
    var query = form['databaseQuery'].value;

    var _gaq = _gaq || [];
    _gaq.push(['_trackEvent', 'Export', 'Export Featured Executed', query]);
}

// Start of on page load content.
var pageCountNode = document.getElementById("pageCount");
var resultPageCountNode = document.getElementById('resultPageCount');

if ((pageCountNode && pageCountNode !== undefined) && (resultPageCountNode && resultPageCountNode !== undefined)) {
    updateSelectedOption(document.getElementById("pageSelect"), pageCountNode.valueOf().innerHTML);
    updateSelectedOption(document.getElementById("resultPageSelect"), (resultPageCountNode.valueOf().innerHTML / 10));
}

