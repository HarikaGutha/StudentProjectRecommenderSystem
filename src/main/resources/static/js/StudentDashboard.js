var currentPage = 0;
var currentSupervisor;
/**
 * function to display get projects based on supervisor filter.
 */
function displayProjects() {
	var projectSupervisor;
    document.getElementById("projects").innerHTML = "";
    document.getElementById("customPagination").innerHTML = "";
    var supervisors = [];
    for (var option of document.getElementById('supervisorsDropdown').options) {
        if (option.selected) {
            supervisors.push(option.value);
            projectSupervisor = option.value;
        }
    }
    if (supervisors.length != 0) {
        var projects = {
            methods: 'GET',
        };
        if(projectSupervisor == currentSupervisor){
        	 fetch('/filterBySupervisor?supervisors=' + supervisors + '&' + 'page=' + currentPage, projects).then(displayJsonProjects);
        } else{
        	 fetch('/filterBySupervisor?supervisors=' + supervisors + '&' + 'page=' + 0, projects).then(displayJsonProjects);
        }
       
    }

    /**
     * function to display projects.
     * @param {response} response Object
     */
    async function displayJsonProjects(response) {
        var json = await response.json();
        clearDiv(json);
        if (json.totalPages > 1) {
            displayPagination(json, supervisors);
        }
    }
}

/**
 * function to clear div elements.
 * @param {json} json object
 */
function clearDiv(json) {
    if (json.content.length == 0) {
        document.getElementById("projects").innerText = "No projects uploaded";
        document.getElementById("projects").classList.add("noProjects", "mb-3");
    } else {
        addChildNodes(json);
    }
}

/**
 * function to create nodes and add to filter by supervisor div.
 * @param {json} json object
 */
function addChildNodes(json) {
    for (i = 0; i < json.content.length; i++) {
        var parentDiv = document.createElement("div");
        parentDiv.classList.add("row", "mx-1", "my-3");
        parentDiv.style.cssText = 'background:#A9CCE3;border-radius:10px !Important;';
        var leftDiv = document.createElement("div");
        leftDiv.classList.add("col-1", "col-sm-1", "col-lg-1");
        var rightDiv = document.createElement("div");
        rightDiv.classList.add("col-11", "col-sm-11", "col-lg-11");
        parentDiv.appendChild(leftDiv);
        parentDiv.appendChild(rightDiv);
        createSaveIcon(leftDiv, json);
        createTitle(rightDiv, json);
        createDateAndSupervisor(rightDiv, json);
        document.getElementById("projects").appendChild(parentDiv);
    }
}

/**
 * function to create save icon div.
 * @param {leftDiv} leftDiv child div
 * @param {json} json object
 */
function createSaveIcon(leftDiv, json) {
    var saveIconSpan = document.createElement("span");
    var image = document.createElement("i");
    image.id = "save" + json.content[i].projectId;
    image.classList.add("fa", "fa-heart", "fa-lg", "pl-1", "pt-2", "saveSymbol");
    image.setAttribute("onclick", "save(" + json.content[i].projectId + ")");
    leftDiv.appendChild(saveIconSpan);
    saveIconSpan.appendChild(image);
    if (json.content[i].isSavedProject == true) {
        image.style.cssText = 'color:orange';
    } else {
        image.style.cssText = 'color:white';
    }
}

/**
 * function to create title div
 * @param {rightDiv} rightDiv child div 
 * @param {*} json object
 */
function createTitle(rightDiv, json) {
    var title = document.createElement("a");
    var node = document.createTextNode(json.content[i].title);
    title.appendChild(node);
    title.href = "/individualProjectDescription?id=" + json.content[i].projectId;
    title.classList.add("projectTitle");
    rightDiv.appendChild(title);
}

/**
 * function to create date and supervisor div
 * @param {*} rightDiv chile div
 * @param {*} json object
 */
function createDateAndSupervisor(rightDiv, json) {
    var supervisor;
    var para = document.createElement("p");
    para.classList.add("row");
    var calendarIconSpan = document.createElement("span");
    calendarIconSpan.classList.add('col-12', 'col-sm-6', 'col-lg-6', 'col-xl-6')
    var calendar = document.createElement("i");
    calendar.classList.add("fas", "fa-calendar-times", "fa-lg", "pr-2");
    calendarIconSpan.appendChild(calendar);
    var datePosted = new Date(json.content[i].datePosted);
    var date = document.createTextNode(datePosted.getDate() + '.' + datePosted.getMonth() + 1 + '.' +
        datePosted.getFullYear());
    calendarIconSpan.appendChild(date);
    para.appendChild(calendarIconSpan);
    rightDiv.appendChild(para);
    var userIconSpan = document.createElement("span");
    userIconSpan.classList.add('col-12', 'col-sm-6', 'col-lg-6', 'col-xl-6')
    var user = document.createElement("i");
    user.classList.add("fas", "fa-user-alt", "fa-lg", "pr-2");
    userIconSpan.appendChild(user);
    for (var option of document.getElementById('supervisorsDropdown').options) {
        if (option.selected) {
            supervisor = option.text;
        }
    }
    var supervisorName = document.createTextNode(supervisor);
    userIconSpan.appendChild(supervisorName);
    para.appendChild(userIconSpan);
}

/**
 * function to display pagination
 * @param {json} json object
 * @param {supervisors} supervisors array
 */
function displayPagination(json, supervisors) {
    var ul = document.createElement("ul");
    ul.id="ulPagination"
    ul.classList.add("pl-3", "pr-3", "pb-3", "pt-3", "pagination");
    ul.style.cssText = 'justify-content:flex-end;';
    var prev = document.createElement("li");
    prev.id=("prev");
    var prevButton = document.createElement("button");
    prevButton.id=("prevButton");
    var prevNode = document.createTextNode("<<");
    prevButton.appendChild(prevNode);
    prev.appendChild(prevButton);
    ul.appendChild(prev);
    prev.style.cssText = "display:none"
    prevButton.classList.add("page-link");
    prevButton.setAttribute("onclick", "displayLeftPagination()");
    for (i = 1; i <= json.totalPages; i++) {
        var li = document.createElement("li");
        li.classList.add("page-item","customLink");
        if (i == json.number + 1) {
            li.classList.add("active");
        }
        var a = document.createElement("button");
        a.classList.add("page-link");
        a.setAttribute("onclick", "displayPaginationProjects(" + i + ',' + supervisors + ")");
        var node = document.createTextNode(i);
        a.appendChild(node);
        li.appendChild(a);
        ul.appendChild(li);
    }
    document.getElementById("customPagination").appendChild(ul);
    customisePagination();
    checkActiveClass();
}

/**
 * function to display paginated projects.
 * @param {jsonSize} jsonSize size in json object
 * @param {i} i index value
 */
function displayPaginationProjects(i,supervisors) {
		currentSupervisor = supervisors;
    currentPage = i - 1;
    displayProjects();
}
