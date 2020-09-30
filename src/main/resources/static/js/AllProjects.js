var projectsVisibleForDissertationModules = [];
/**
 * method to implement read more and less. method referenced from
 * http://jsfiddle.net/X5r8r/1156/
 */
$(document).ready(function() {
    var numberOfCharactersToShow = 500;
    var ellipses = "...";
    var moreText = "Read more";
    var lessText = 'Read less'
    $('.projectDescription').each(function() {
        var content = $(this).html();
        if (content.length > numberOfCharactersToShow) {
            var contentToShow = content.substr(0, numberOfCharactersToShow);
            var contentToHide = content.substr(numberOfCharactersToShow, content.length - numberOfCharactersToShow);
            var html = contentToShow + '<span class="moreelipses">' + ellipses + '</span><span class="morecontent"><span>' + contentToHide + '</span>&nbsp;&nbsp;<a href="" class="moreLink">' + moreText + '</a></span>';
            $(this).html(html);
        }

    });
    $(".moreLink").click(function() {
        if ($(this).hasClass("less")) {
            $(this).removeClass("less");
            $(this).html(moreText);
        } else {
            $(this).addClass("less");
            $(this).html(lessText);
        }
        $(this).parent().prev().toggle();
        $(this).prev().toggle();
        return false;
    });
});
/**
 * setModules() function used to set values of modules dropdown dynamically.
 * @param {modules} modules selected by user
 */
function setModules(modules) {
    document.getElementById("selectedModule").value = modules;
    document.getElementById("getProject").submit();
}
/**
 * setDropdownValues() function used to set values of modules dropdown dynamically.
 * @param {modules} modules selected by user
 */
function setDropdownValues(modules) {
    document.getElementById("allFiltersButton").click();
    for (var option of document.getElementById('hiddenTopicsSelect').options) {
        if (option.text.toUpperCase().includes(modules.toUpperCase().trim(), 0)) {
            option.selected = true;
        }
    }
    hideAllFilters();
}
/**
 * function to display all filters.
 */
function displayAllFilters() {
    var allFilters = document.getElementById("allFilters");
    allFilters.style.display = "flex";
    var filters = document.getElementById("filters");
    filters.style.display = "none";
    var content = document.getElementsByClassName("allProjects");
    for (var i = 0; i < content.length; i++) {
        content[i].style.display = "none";
    }
}
/**
 * function to hide all filters.
 */
function hideAllFilters() {
    var allFilters = document.getElementById("allFilters");
    allFilters.style.display = "none";
    var filters = document.getElementById("filters");
    filters.style.display = "flex";
    getFilters();
    var content = document.getElementsByClassName("allProjects");
    emptyMainFilters();
    for (var i = 0; i < content.length; i++) {
        if (content[i].style.display == "") {
            content[i].style.display = "";
        } else {
            content[i].style.display = "none";
        }
    }
}

/**
 * function to get all values selected by user.
 */
function getFilters() {
    var dissertationCategories = getDissertationModules();
    var currentlyAvailable = isAvailable();
    var searchInput = document.getElementById("hiddenSearch");
    searchInput = searchInput.value.toUpperCase();
    var dateArray = getDate();
    var topics = getTopics();
    var relevantModules = getRelevantModules();
    var supervisors = getSupervisors();
    var currentlyHistoric = isHistoric();
    filterProjectsBasedOnAllFilters(dissertationCategories, currentlyAvailable, searchInput, dateArray, topics, relevantModules, supervisors, currentlyHistoric);
}
/**
 * function to get dissertation modules selected by user.
 */
function getDissertationModules() {
    var dissertationCategories = [];
    for (var option of document.getElementById('hiddenDissertationCategories').options) {
        if (option.selected) {
            dissertationCategories.push(option.text);
        }
    }
    if (dissertationCategories.length != 0) {
        for (i = 0; i < dissertationCategories.length; i++) {
            dissertationCategories[i] = dissertationCategories[i].toUpperCase();
        }
    }
    return dissertationCategories;
}

/**
 * function to check if available toggle button is on
 */
function isAvailable() {
    var available = document.getElementById('hiddenAvailable');
    var currentlyAvailable;
    if (available.checked == true) {
        currentlyAvailable = true;
    } else {
        currentlyAvailable = false;
    }
    return currentlyAvailable;
}
/**
 * functin to get date selected
 */
function getDate() {
    var dateArray = [];
    for (var option of document.getElementById('hiddenDateSelect').options) {
        if (option.selected) {
            dateArray.push(option.text);
        }
    }
    return dateArray;
}
/**
 * function to get topics selected by user.
 */
function getTopics() {
    var topics = [];
    for (var option of document.getElementById('hiddenTopicsSelect').options) {
        if (option.selected) {
            topics.push(option.text);
        }
    }
    if (topics.length != 0) {
        for (i = 0; i < topics.length; i++) {
            topics[i] = topics[i].toUpperCase();
        }
    }
    return topics;
}
/**
 * function to get relevant modules selected by user.
 */
function getRelevantModules() {
    var relevantModules = [];
    for (var option of document.getElementById('hiddenRelevantModules').options) {
        if (option.selected) {
            relevantModules.push(option.text);
        }
    }
    if (relevantModules.length != 0) {
        for (i = 0; i < relevantModules.length; i++) {
            relevantModules[i] = relevantModules[i].toUpperCase();
        }
    }
    return relevantModules;
}
/**
 * function to get supervisors selected by user.
 */
function getSupervisors() {
    var supervisors = [];
    for (var option of document.getElementById('hiddenSupervisors').options) {
        if (option.selected) {
            supervisors.push(option.text);
        }
    }
    if (supervisors.length != 0) {
        for (i = 0; i < supervisors.length; i++) {
            supervisors[i] = supervisors[i].toUpperCase();
        }
    }
    return supervisors;
}
/**
 * function to check if historic toggle button is on
 */
function isHistoric() {
    var historic = document.getElementById('hiddenHistoric');
    var currentlyHistoric;
    if (historic.checked == true) {
        currentlyHistoric = true;
    } else {
        currentlyHistoric = false;
    }
    return currentlyHistoric;
}
/**
 * function to filter projects based on all filters
 * @param {dissertationCategories} dissertationCategories array
 * @param {currentlyAvailable} currentlyAvailable boolean value 
 * @param {searchInput} searchInput is input to be searched
 * @param {dateArray} dateArray array of dates selected
 * @param {topics} topics array
 * @param {relevantModules} relevantModules array
 * @param {supervisors} supervisors array
 * @param {currentlyHistoric} currentlyHistoric boolean values
 */
function filterProjectsBasedOnAllFilters(dissertationCategories, currentlyAvailable, searchInput, dateArray,
    topics, relevantModules, supervisors, currentlyHistoric) {
    var allStates = [],
        projects = [],
        projectsForCurrentlyAvailable = [],
        projectsForsearchInput = [],
        projectsForDate = [],
        projectsForTopics = [],
        projectsForRelevantModules = [],
        projectsForSupervisors = [],
        projectsForHiddenHistoric = [],
        projectsForRecentlyAdded = [];
    projects = document.getElementsByClassName('allProjects');
    if (dissertationCategories.length != 0) {
        projectsForCurrentlyAvailable = filterBasedOnHiddenDissertationModules(dissertationCategories, projects, projectsForCurrentlyAvailable);
    }
    if (currentlyAvailable == true) {
        projectsForsearchInput = filterBasedOnHiddenAvailable(projectsForsearchInput, projects, projectsForCurrentlyAvailable);
    }
    if (searchInput != "") {
        projectsForDate = filterBasedOnSearchInput(projectsForsearchInput, projectsForCurrentlyAvailable, projects, projectsForDate, searchInput);
    }
    if (dateArray.length != 0) {
        projectsForTopics = filterBasedOnHiddenDate(projectsForDate, projectsForsearchInput, projectsForCurrentlyAvailable, projects, projectsForTopics, dateArray);
    }
    if (topics.length != 0) {
        projectsForRelevantModules = filterBasedOnHiddenTopics(projectsForTopics, projectsForDate, projectsForsearchInput, projectsForCurrentlyAvailable,
            projects, topics, projectsForRelevantModules);
    }
    if (relevantModules.length != 0) {
        projectsForSupervisors = filterBasedOnRelevantModules(projectsForRelevantModules, projectsForTopics, projectsForDate, projectsForsearchInput, projectsForCurrentlyAvailable,
            projects, relevantModules, projectsForSupervisors);
    }
    if (supervisors.length != 0) {
        projectsForHiddenHistoric = filterBasedOnSupervisors(projectsForSupervisors, projectsForRelevantModules, projectsForTopics, projectsForDate,
            projectsForsearchInput, projectsForCurrentlyAvailable, projects, supervisors, projectsForHiddenHistoric);
    }
    if (currentlyHistoric == true) {
        projectsForRecentlyAdded = filterBasedOnHiddenHistoric(projectsForHiddenHistoric, projectsForSupervisors, projectsForRelevantModules, projectsForTopics, projectsForDate,
            projectsForsearchInput, projectsForCurrentlyAvailable, projects, projectsForRecentlyAdded);
    }
    if (dissertationCategories.length == 0 && currentlyAvailable == false && searchInput == "" &&
        dateArray.length == 0 && topics.length == 0 && relevantModules.length == 0 &&
        supervisors.length == 0 && currentlyHistoric == false) {
        for (i = 0; i < projects.length; i++) {
            projects[i].style.display = "";
        }
    }

}
/**
 * filters projects based on hidden dissertation module filter.
 * @param {dissertationCategories}  dissertationCategories array
 * @param {projects} projects array
 * @param {projectsForCurrentlyAvailable} projectsForCurrentlyAvailable array 
 */
function filterBasedOnHiddenDissertationModules(dissertationCategories, projects, projectsForCurrentlyAvailable) {
    for (i = 0; i < projects.length; i++) {
        var pTags = projects[i].getElementsByClassName('dissertationModules');
        if (pTags) {
            for (j = 0; j < pTags.length; j++) {
                var span = pTags[j].getElementsByTagName("span");
                if (span) {
                    for (s = 0; s < span.length; s++) {
                        txtValue = span[s].textContent || span[s].innerText;
                        txtValueArray = txtValue.split(',');
                        txtValue = txtValueArray[0];
                        if (dissertationCategories.includes(txtValue.toUpperCase().trim(), 0)) {
                            projects[i].style.display = "";
                            break;
                        } else {
                            projects[i].style.display = "none";
                        }
                    }
                }
                if (projects[i].style.display == "") {
                    break;
                }
            }
        }
    }
    for (i = 0; i < projects.length; i++) {
        if (projects[i].style.display == "") {
            projectsForCurrentlyAvailable.push(projects[i]);
        }
    }
    return projectsForCurrentlyAvailable;
}
/**
 * filter projects based on hidden available filter
 * @param {projectsForsearchInput} projectsForsearchInput array
 * @param {projects} projects array 
 * @param {projectsForCurrentlyAvailable} projectsForCurrentlyAvailable array
 */
function filterBasedOnHiddenAvailable(projectsForsearchInput, projects, projectsForCurrentlyAvailable) {
    var allStates = [];
    if (projectsForCurrentlyAvailable != 0) {
        currentProjects = projectsForCurrentlyAvailable;
        console.log(currentProjects.length);
    } else if (projects.length != 0) {
        currentProjects = projects;
        console.log(currentProjects.length);
    }
    for (j = 0; j < currentProjects.length; j++) {
        console.log(currentProjects[j]);
        allStates = currentProjects[j].getElementsByClassName('stateCircle');
        for (i = 0; i < allStates.length; i++) {
            if (allStates[i].style.color == "green") {
                currentProjects[j].style.display = "";
            } else {
                currentProjects[j].style.display = "none";
            }
        }
    }
    for (i = 0; i < currentProjects.length; i++) {
        if (currentProjects[i].style.display == "") {
            projectsForsearchInput.push(currentProjects[i]);
        }
    }
    return projectsForsearchInput;
}
/**
 * function to filter projects based on search box
 * @param {projectsForsearchInput} projectsForsearchInput array
 * @param {projectsForCurrentlyAvailable} projectsForCurrentlyAvailable array
 * @param {projects} projects array
 * @param {projectsForDate} projectsForDate array
 * @param {searchInput} searchInput array
 */
function filterBasedOnSearchInput(projectsForsearchInput, projectsForCurrentlyAvailable, projects, projectsForDate, searchInput) {
    if (projectsForsearchInput != 0) {
        currentProjects = projectsForsearchInput.concat();
    } else if (projectsForCurrentlyAvailable != 0) {
        currentProjects = projectsForCurrentlyAvailable.concat();
    } else if (projects.length != 0) {
        currentProjects = projects;
    }
    for (i = 0; i < currentProjects.length; i++) {
        allTitles = currentProjects[i].getElementsByClassName("projectTitle");
        for (j = 0; j < allTitles.length; j++) {
            txtValue = allTitles[j].textContent || allTitles[j].innerText;
            if (txtValue.toUpperCase().indexOf(searchInput) > -1) {
                currentProjects[i].style.display = "";
            } else {
                currentProjects[i].style.display = "none";
            }
        }
    }
    for (i = 0; i < currentProjects.length; i++) {
        if (currentProjects.style.display == "") {
            projectsForDate.push(currentProjects[i]);
        }
    }
    return projectsForDate;
}
/**
 * Function to filter projects based on hidden date
 * @param {projectsForDate} projectsForDate array 
 * @param {projectsForsearchInput} projectsForsearchInput array
 * @param {projectsForCurrentlyAvailable} projectsForCurrentlyAvailable array
 * @param {projects} projects array
 * @param {projectsForTopics} projectsForTopics array
 * @param {dateArray} dateArray array 
 */
function filterBasedOnHiddenDate(projectsForDate, projectsForsearchInput, projectsForCurrentlyAvailable, projects, projectsForTopics, dateArray) {
    var newDate = [],
        formattedDate = [];
    if (projectsForDate.length != 0) {
        currentProjects = projectsForDate.concat();
    } else if (projectsForsearchInput != 0) {
        currentProjects = projectsForsearchInput.concat();
    } else if (projectsForCurrentlyAvailable != 0) {
        currentProjects = projectsForCurrentlyAvailable.concat();
    } else if (projects.length != 0) {
        currentProjects = projects;
    }
    for (i = 0; i < dateArray.length; i++) {
        newDate.push(dateArray[i].split(' '));
        formattedDate.push(newDate[i][0] + '-' + newDate[i][1]);
    } // splitting dateArray and formating it to MMMM-YYYY.
    for (j = 0; j < formattedDate.length; j++) {
        formattedDate[j] = formattedDate[j].toUpperCase();
    } // converting fromatted date to uppercase.
    for (i = 0; i < currentProjects.length; i++) {
        allDates = currentProjects[i].getElementsByClassName("datePosted");
        for (j = 0; j < allDates.length; j++) {
            txtValue = allDates[j].textContent || allDates[j].innerText;
            splittedArray = [];
            splittedArray = txtValue.split('-');
            txtValue = splittedArray[1] + '-' + splittedArray[2];
            if (formattedDate.includes(txtValue.toUpperCase())) {
                currentProjects[i].style.display = "";
            } else {
                currentProjects[i].style.display = "none";
            }
        }
    }
    for (i = 0; i < currentProjects.length; i++) {
        if (currentProjects[i].style.display == "") {
            projectsForTopics.push(currentProjects[i]);
        }
    }
    return projectsForTopics;
}
/**
 * function to filter projects based on hidden topics filter.
 * @param {projectsForTopics} projectsForTopics array
 * @param {projectsForDate} projectsForDate array
 * @param {projectsForsearchInput} projectsForsearchInput array
 * @param {projectsForCurrentlyAvailable} projectsForCurrentlyAvailable array
 * @param {projects} projects array
 * @param {topics} topics array
 * @param {projectsForRelevantModules} projectsForRelevantModules array
 */
function filterBasedOnHiddenTopics(projectsForTopics, projectsForDate, projectsForsearchInput, projectsForCurrentlyAvailable, projects, topics, projectsForRelevantModules) {
    if (projectsForTopics.length != 0) {
        currentProjects = projectsForTopics.concat();
    } else if (projectsForDate.length != 0) {
        currentProjects = projectsForDate.concat();
    } else if (projectsForsearchInput != 0) {
        currentProjects = projectsForsearchInput.concat();
    } else if (projectsForCurrentlyAvailable != 0) {
        currentProjects = projectsForCurrentlyAvailable.concat();
    } else if (projects.length != 0) {
        currentProjects = projects;
    }
    for (i = 0; i < currentProjects.length; i++) {
        var pTags = currentProjects[i].getElementsByClassName('topicAreas');
        if (pTags) {
            for (j = 0; j < pTags.length; j++) {
                var span = pTags[j].getElementsByTagName("span");
                if (span) {
                    for (s = 0; s < span.length; s++) {
                        txtValue = span[s].textContent || span[s].innerText;
                        txtValueArray = txtValue.split(',');
                        txtValue = txtValueArray[0];
                        if (topics.includes(txtValue.toUpperCase().trim(), 0)) {
                            currentProjects[i].style.display = "";
                            break;
                        } else {
                            currentProjects[i].style.display = "none";
                        }
                    }
                }
                if (currentProjects[i].style.display == "") {
                    break;
                }
            }
        }
    }
    for (i = 0; i < currentProjects.length; i++) {
        if (currentProjects[i].style.display == "") {
            projectsForRelevantModules.push(currentProjects[i]);
        }
    }
    return projectsForRelevantModules;
}
/**
 * function to filter projects based on revelant modules filter
 * @param {projectsForRelevantModules} projectsForRelevantModules array
 * @param {projectsForTopics} projectsForTopics array
 * @param {projectsForDate} projectsForDate array
 * @param {projectsForsearchInput} projectsForsearchInput array
 * @param {projectsForCurrentlyAvailable} projectsForCurrentlyAvailable array
 * @param {projects} projects array
 * @param {relevantModules} relevantModules array
 * @param {projectsForSupervisors} projectsForSupervisors array
 */
function filterBasedOnRelevantModules(projectsForRelevantModules, projectsForTopics, projectsForDate, projectsForsearchInput, projectsForCurrentlyAvailable,
    projects, relevantModules, projectsForSupervisors) {
    if (projectsForRelevantModules.length != 0) {
        currentProjects = projectsForRelevantModules.concat();
    } else if (projectsForTopics.length != 0) {
        currentProjects = projectsForTopics.concat();
    } else if (projectsForDate.length != 0) {
        currentProjects = projectsForDate.concat();
    } else if (projectsForsearchInput != 0) {
        currentProjects = projectsForsearchInput.concat();
    } else if (projectsForCurrentlyAvailable != 0) {
        currentProjects = projectsForCurrentlyAvailable.concat();
    } else if (projects.length != 0) {
        currentProjects = projects;
    }
    for (i = 0; i < currentProjects.length; i++) {
        var pTags = currentProjects[i].getElementsByClassName('relevantModules');
        if (pTags) {
            for (j = 0; j < pTags.length; j++) {
                var span = pTags[j].getElementsByTagName("span");
                if (span) {
                    for (s = 0; s < span.length; s++) {
                        txtValue = span[s].textContent || span[s].innerText;
                        txtValueArray = txtValue.split(',');
                        txtValue = txtValueArray[0];
                        if (relevantModules.includes(txtValue.toUpperCase().trim(), 0)) {
                            currentProjects[i].style.display = "";
                            break;
                        } else {
                            currentProjects[i].style.display = "none";
                        }
                    }
                }
                if (currentProjects[i].style.display == "") {
                    break;
                }
            }
        }
    }
    for (i = 0; i < currentProjects.length; i++) {
        if (currentProjects[i].style.display == "") {
            projectsForSupervisors.push(currentProjects[i]);
        }
    }
    return projectsForSupervisors;
}
/**
 * Function to filter projects based on supervisors.
 * @param {projectsForSupervisors} projectsForSupervisors array
 * @param {projectsForRelevantModules} projectsForRelevantModules array
 * @param {projectsForTopics} projectsForTopics array
 * @param {projectsForDate} projectsForDate array
 * @param {projectsForsearchInput} projectsForsearchInput array
 * @param {projectsForCurrentlyAvailable} projectsForCurrentlyAvailable array
 * @param {projects} projects array
 * @param {supervisors} supervisors array
 * @param {projectsForHiddenHistoric} projectsForHiddenHistoric array
 */
function filterBasedOnSupervisors(projectsForSupervisors, projectsForRelevantModules, projectsForTopics, projectsForDate,
    projectsForsearchInput, projectsForCurrentlyAvailable, projects, supervisors, projectsForHiddenHistoric) {
    if (projectsForSupervisors.length != 0) {
        currentProjects = projectsForSupervisors.concat();
    } else if (projectsForRelevantModules.length != 0) {
        currentProjects = projectsForRelevantModules.concat();
    } else if (projectsForTopics.length != 0) {
        currentProjects = projectsForTopics.concat();
    } else if (projectsForDate.length != 0) {
        currentProjects = projectsForDate.concat();
    } else if (projectsForsearchInput != 0) {
        currentProjects = projectsForsearchInput.concat();
    } else if (projectsForCurrentlyAvailable != 0) {
        currentProjects = projectsForCurrentlyAvailable.concat();
    } else if (projects.length != 0) {
        currentProjects = projects;
    }
    for (i = 0; i < currentProjects.length; i++) {
        supervisorsList = currentProjects[i].getElementsByClassName("nameButton");
        for (j = 0; j < supervisorsList.length; j++) {
            txtValue = supervisorsList[j].textContent || supervisorsList[j].innerText;
            if (supervisors.includes(txtValue.toUpperCase().trim(), 0)) {
                currentProjects[i].style.display = "";
            } else {
                currentProjects[i].style.display = "none";
            }
        }
    }
    for (i = 0; i < currentProjects.length; i++) {
        if (currentProjects[i].style.display == "") {
            projectsForHiddenHistoric.push(currentProjects[i]);
        }
    }
    return projectsForHiddenHistoric;
}
/**
 * Function to filter projects based on hidden historic filter
 * @param {projectsForHiddenHistoric} projectsForHiddenHistoric array
 * @param {projectsForSupervisors} projectsForSupervisors array
 * @param {projectsForRelevantModules} projectsForRelevantModules array
 * @param {projectsForTopics} projectsForTopics array
 * @param {projectsForDate} projectsForDate array
 * @param {projectsForsearchInput} projectsForsearchInput array
 * @param {projectsForCurrentlyAvailable} projectsForCurrentlyAvailable array
 * @param {projects} projects array
 * @param {projectsForRecentlyAdded} projectsForRecentlyAdded array
 */
function filterBasedOnHiddenHistoric(projectsForHiddenHistoric, projectsForSupervisors, projectsForRelevantModules, projectsForTopics, projectsForDate,
    projectsForsearchInput, projectsForCurrentlyAvailable, projects, projectsForRecentlyAdded) {
    if (projectsForHiddenHistoric.length != 0) {
        currentProjects = projectsForHiddenHistoric.concat();
    } else if (projectsForSupervisors.length != 0) {
        currentProjects = projectsForSupervisors.concat();
    } else if (projectsForRelevantModules.length != 0) {
        currentProjects = projectsForRelevantModules.concat();
    } else if (projectsForTopics.length != 0) {
        currentProjects = projectsForTopics.concat();
    } else if (projectsForDate.length != 0) {
        currentProjects = projectsForDate.concat();
    } else if (projectsForsearchInput != 0) {
        currentProjects = projectsForsearchInput.concat();
    } else if (projectsForCurrentlyAvailable != 0) {
        currentProjects = projectsForCurrentlyAvailable.concat();
    } else if (projects.length != 0) {
        currentProjects = projects;
    }
    for (j = 0; j < currentProjects.length; j++) {
        allStates = currentProjects[j].getElementsByClassName('stateCircle');
        if (allStates) {
            for (i = 0; i < allStates.length; i++) {
                if (allStates[i].style.color == "gray") {
                    currentProjects[j].style.display = "";
                } else {
                    currentProjects[j].style.display = "none";
                }
            }
        }
    }
    for (i = 0; i < currentProjects.length; i++) {
        if (currentProjects[i].style.display == "") {
            projectsForRecentlyAdded.push(currentProjects[i]);
        }
    }
    return projectsForRecentlyAdded;
}


/**
 * function to disable historic toggle when available toggle is checked.
 */
function available() {
    if ($(this).prop('checked')) {
        $('#hiddenHistoric').bootstrapToggle('disable');
        $('#historicInfo').css('display', 'inline');
    } else {
        $('#hiddenHistoric').bootstrapToggle('enable');
        $('#historicInfo').css('display', 'none');
    }
}
/**
 * function to disable available toggle when historic toggle is checked.
 */
function historic() {
    if ($(this).prop('checked')) {
        $('#hiddenAvailable').bootstrapToggle('disable');
        $('#availableInfo').css('display', 'inline');
    } else {
        $('#hiddenHistoric').bootstrapToggle('enable');
        $('#availableInfo').css('display', 'none');
    }
}

/**
 * function to disable visibleHistoric toggle when visibleAvailable toggle is
 * checked.
 */
$(function() {
    $('#visibleAvailable').change(function() {
        if ($(this).prop('checked')) {
            $('#visibleHistoric').bootstrapToggle('off');
            filterBasedOnVisibleAvailableAndHistoric('visibleAvailable');
        } else {
            filterBasedOnVisibleAvailableAndHistoric('notChecked');
        }
    })
})

/**
 * function to disable visibleAvailable toggle when visibleHistoric toggle is
 * checked.
 */
$(function() {
    $('#visibleHistoric').change(function() {
        if ($(this).prop('checked')) {
            // $('#visibleAvailable').prop('checked', false);
            $('#visibleAvailable').bootstrapToggle('off');
            filterBasedOnVisibleAvailableAndHistoric('visibleHistoric');
        } else {
            filterBasedOnVisibleAvailableAndHistoric('notChecked');
        }
    })
})

/**
 * function to disable hiddenHistoric toggle when hiddenAvailable toggle is
 * checked.
 */
$(function() {
    $('#hiddenAvailable').change(function() {
        if ($(this).prop('checked')) {
            $('#hiddenHistoric').bootstrapToggle('off');
        }
    })
})

/**
 * function to disable hiddenAvailable toggle when hiddenHistoric toggle is
 * checked.
 */

$(function() {
    $('#hiddenHistoric').change(function() {
        if ($(this).prop('checked')) {
            $('#hiddenAvailable').bootstrapToggle('off');
        }
    })
})

/**
 * function to clear filters
 */
function emptyMainFilters() {
    if ($('#visibleAvailable').prop('checked')) {
        $('#visibleAvailable').bootstrapToggle('off');
    }
    if ($('#visibleHistoric').prop('checked')) {
        $('#visibleHistoric').bootstrapToggle('off');
        filterBasedOnVisibleAvailableAndHistoric('visibleAvailable');
    }
    $('visibleDissertaionModules').val('default');
    $('visibleDissertaionModules').selectpicker("refresh");
}
/**
 * function to filter projects based on available and historic project state.
 * @param {state} state the project state
 */
function filterBasedOnVisibleAvailableAndHistoric(state) {
    var allStates = [],
        projects = [];
    projects = document.getElementsByClassName('allProjects');
    allStates = document.getElementsByClassName('stateCircle');
    if (state == 'visibleAvailable') {
        projectsVisibleForDissertationModules = [];
        for (i = 0; i < projects.length; i++) {
            if (allStates[i].style.color == "green") {
                projects[i].style.display = "";
                projectsVisibleForDissertationModules.push(projects[i]);
            } else {
                projects[i].style.display = "none";
            }
        }
        filterBasedOnVisibleDissertationModules();
    } else if (state == "visibleHistoric") {
        projectsVisibleForDissertationModules = [];
        for (i = 0; i < projects.length; i++) {
            if (allStates[i].style.color == "gray") {
                projects[i].style.display = "";
                projectsVisibleForDissertationModules.push(projects[i]);
            } else {
                projects[i].style.display = "none";
            }
        }
        filterBasedOnVisibleDissertationModules();
    } else if (state == 'notChecked') {
        projectsVisibleForDissertationModules = [];
        console.log(projectsVisibleForDissertationModules.length);
        for (i = 0; i < projects.length; i++) {
            projects[i].style.display = "";
            projectsVisibleForDissertationModules.push(projects[i]);
        }
        filterBasedOnVisibleDissertationModules();
    }
}

/**
 * function to get values in vissible dissertation modules filter.
 */
function filterBasedOnVisibleDissertationModules() {
    var selected = [];
    for (var option of document.getElementById('visibleDissertaionModules').options) {
        if (option.selected) {
            selected.push(option.text);
        }
    }
    for (i = 0; i < selected.length; i++) {
        selected[i] = selected[i].toUpperCase();
    }
    filterBasedOnVisibleDissertationModulesRefactoredMethod(selected);
}

/**
 * function to filter project based on dissertation modules
 * @param {selected} dissertation modules array. 
 */
function filterBasedOnVisibleDissertationModulesRefactoredMethod(selected) {
    if (selected.length != 0) {
        if (projectsVisibleForDissertationModules.length == 0) {
            projectsVisibleForDissertationModules = document.getElementsByClassName('allProjects');
        }
        for (i = 0; i < projectsVisibleForDissertationModules.length; i++) {
            var pTags = projectsVisibleForDissertationModules[i].getElementsByClassName('dissertationModules');
            if (pTags) {
                for (j = 0; j < pTags.length; j++) {
                    var span = pTags[j].getElementsByTagName("span");
                    if (span) {
                        for (s = 0; s < span.length; s++) {
                            txtValue = span[s].textContent || span[s].innerText;
                            txtValueArray = txtValue.split(',');
                            txtValue = txtValueArray[0];
                            if (selected.includes(txtValue.toUpperCase().trim(), 0)) {
                                projectsVisibleForDissertationModules[i].style.display = "";
                                break;
                            } else {
                                projectsVisibleForDissertationModules[i].style.display = "none";
                            }
                        }
                    }
                    if (projectsVisibleForDissertationModules[i].style.display == "") {
                        break;
                    }
                }
            }
        }
    } else {
        for (i = 0; i < projectsVisibleForDissertationModules.length; i++) {
            projectsVisibleForDissertationModules[i].style.display = "";
        }
    }
}