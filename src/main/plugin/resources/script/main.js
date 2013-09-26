/*
 * Function used to send the query form back to the application once the page number, or results to display has been
 * changed in the UI.
 */
function sendForm() {
    document.getElementById("queryForm").submit();
}

/**
 * Updates a selector option to a value provided. Makes the assumption that the value of the requested selector is
 * one (1) off of the actual index selected. eg. 40 = (4 - 1).
 *
 * @param node The DOM node that contains the select tag element.
 * @param value The value of the selector that will be selected.
 */
function updateSelectedOption(node, value) {
    console.log("Updating node " + node + " with " + value);
    node.selectedIndex = value - 1;
}

updateSelectedOption(document.getElementById("pageSelect"), document.getElementById("pageCount").valueOf().innerHTML);
updateSelectedOption(document.getElementById("resultPageSelect"), (document.getElementById('resultPageCount').valueOf().innerHTML / 10));

