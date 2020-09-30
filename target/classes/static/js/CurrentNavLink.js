/**
 * This js file has the code to highlight current page nav link.
 */
var page = document.getElementById("headers").innerText.toUpperCase();
if (page.indexOf("WELCOME") > -1) {
    document.getElementById("home").style.color = "#ff6666";
} else if (page.indexOf("SAVED") > -1) {
    document.getElementById("heart").style.color = "#ff6666";
} else if (page.indexOf("AVAILABLE") > -1) {
    document.getElementById("list").style.color = "#ff6666";
} else if (page.indexOf("DESCRIPTION") > -1) {
    document.getElementById("list").style.color = "#ff6666";
} else if (page.indexOf("SUPERVISORS") > -1) {
    document.getElementById("users").style.color = "#ff6666";
} else if (page.indexOf("SUPERVISOR") > -1) {
    document.getElementById("users").style.color = "#ff6666";
} else if (page.indexOf("OWN") > -1) {
    document.getElementById("pencil").style.color = "#ff6666";
} else if (page.indexOf("CREATION") > -1) {
    document.getElementById("faEdit").style.color = "#ff6666";
} else if (page.indexOf("MANAGEMENT") > -1) {
    document.getElementById("folder").style.color = "#ff6666";
}