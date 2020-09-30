// Global variable for storing table name.
var tableName;



/** 
 * searchDropDown() used to filter based on drop downs.
 */
function searchDropDown(searchStateArray, serachDissertationModuleArray, dateArray, searchModuleArray, searchInput) {
    var table, tr, td, txtValue, toBeVisibleTr = [],
        toBeVisibleTrForModuleFilter = [],
        toBeVisibleTrForDateFilter = [],
        toBeVisibleTrForSearchBox = [],
        formattedDate = [],
        newDate = [];
    var upperCaseArray = changeArraysToUpperCase(searchStateArray, serachDissertationModuleArray, searchModuleArray);
    searchStateArray = upperCaseArray[0]
    serachDissertationModuleArray = upperCaseArray[1]
    searchModuleArray = upperCaseArray[2]

    //assigning table name if null.
    if (tableName == null || tableName == 'undefined') {
        tableName = 'projectTable';
    }

    //extracting rows in table.
    table = document.getElementById(tableName);
    tr = table.getElementsByTagName("tr");

    //filtering serachStateArray if length not 0
    if (searchStateArray.length != 0) {
        toBeVisibleTr = filterTableBasedOnState(tr, searchStateArray, toBeVisibleTr);
        //generating table with visible rows.
    } else {
        table = document.getElementById(tableName);
        tr = table.getElementsByTagName("tr");
        for (i = 0; i < tr.length; i++) {
            tr[i].style.display = "";
        } //redrawing entire table if no filters are selected.
    }

    //filtering serachDissertationModuleArray if length not 0
    if (serachDissertationModuleArray.length != 0) {
        toBeVisibleTrForDateFilter = filterBasedOnDissertationModule(tr, toBeVisibleTr,
            serachDissertationModuleArray, toBeVisibleTrForDateFilter);
    }
    //filtering dateArray if length not 0
    if (dateArray.length != 0) {
        for (i = 0; i < dateArray.length; i++) {
            newDate.push(dateArray[i].split(' '));
            formattedDate.push(newDate[i][0] + '-' + newDate[i][1]);
        } //splitting dateArray and formating it to MMMM-YYYY.
        for (j = 0; j < formattedDate.length; j++) {
            formattedDate[j] = formattedDate[j].toUpperCase();
        } //converting fromatted date to uppercase.
        toBeVisibleTrForModuleFilter = filterBasedOnDate(toBeVisibleTrForDateFilter, toBeVisibleTr, tr,
            formattedDate, toBeVisibleTrForModuleFilter);
    }

    //filtering searchModuleArray if length not 0
    if (searchModuleArray.length != 0) {
        toBeVisibleTrForSearchBox = filterBasedOnModules(toBeVisibleTrForModuleFilter,
            toBeVisibleTrForDateFilter, toBeVisibleTr, tr, searchModuleArray, toBeVisibleTrForSearchBox);
    }

    //filtering searchInput if not empty
    if (searchInput != "") {
        filterBasedOnSearchBoxInput(toBeVisibleTrForSearchBox, toBeVisibleTrForModuleFilter,
            toBeVisibleTrForDateFilter, toBeVisibleTr, tr, searchInput);
    } //filtering searchInput if length not 0 closing.
    if (searchStateArray.length == 0 && serachDissertationModuleArray.length == 0 && dateArray.length == 0 &&
        searchModuleArray.length == 0 && searchInput == "") {
        table = document.getElementById(tableName);
        tr = table.getElementsByTagName("tr");
        for (i = 0; i < tr.length; i++) {
            tr[i].style.display = "";
        }
    }
} // searchDropDown closing.

/**
 * function to convert input from drop downs in to upper case.
 * @param {*} searchStateArray input selected from state drop down
 * @param {*} serachDissertationModuleArray input selected from dissertation module drop down
 * @param {*} searchModuleArray input selected from module drop down
 */
function changeArraysToUpperCase(searchStateArray, serachDissertationModuleArray, searchModuleArray) {
    for (j = 0; j < searchStateArray.length; j++) {
        searchStateArray[j] = searchStateArray[j].toUpperCase();
    } // searchStateArray upper case for closing.
    for (j = 0; j < serachDissertationModuleArray.length; j++) {
        serachDissertationModuleArray[j] = serachDissertationModuleArray[j].toUpperCase();
    } // serachDissertationModuleArray upper case for closing.
    for (j = 0; j < searchModuleArray.length; j++) {
        searchModuleArray[j] = searchModuleArray[j].toUpperCase();
    } // searchModuleArray upper case for closing.
    return [searchStateArray, serachDissertationModuleArray, searchModuleArray]
}

/**
 * function to filter based on state in drop down.
 * @param {tr} tr all rows of table
 * @param {searchStateArray} searchStateArray input selected from drop down
 * @param {toBeVisibleTr} toBeVisibleTr used to store filtered rows
 */
function filterTableBasedOnState(tr, searchStateArray, toBeVisibleTr) {
    for (i = 0; i < tr.length; i++) {
        if (tableName == 'projectTable' || tableName == 'allProjectsTable') {
            td = tr[i].getElementsByTagName("td")[2];
        } else if (tableName == 'collaborationTable') {
            td = tr[i].getElementsByTagName("td")[3];
        }
        if (td) {
            txtValue = td.textContent || td.innerText;
            if (searchStateArray.includes(txtValue.toUpperCase().trim(), 0)) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
    for (q = 0; q < tr.length; q++) {
        if (tr[q].style.display == "") {
            toBeVisibleTr.push(tr[q]);
        }
    }
    return toBeVisibleTr;
}

/**
 * function to filter based on dissertation modules in drop down.
 * @param {tr} tr all rows of table
 * @param {toBeVisibleTr} toBeVisibleTr rows filtered from state
 * @param {serachDissertationModuleArray} serachDissertationModuleArray input selected from drop down
 * @param {toBeVisibleTrForDateFilter} toBeVisibleTrForDateFilter used to store filtered rows
 */
function filterBasedOnDissertationModule(tr, toBeVisibleTr, serachDissertationModuleArray, toBeVisibleTrForDateFilter) {
    if (toBeVisibleTr.length == 0) {
        toBeVisibleTr = tr;
    }
    for (i = 0; i < toBeVisibleTr.length; i++) {
        if (tableName == 'projectTable' || tableName == 'allProjectsTable') {
            td = toBeVisibleTr[i].getElementsByTagName("td")[7];
        } else if (tableName == 'collaborationTable') {
            td = toBeVisibleTr[i].getElementsByTagName("td")[8];
        }
        if (td) {
            var p = td.getElementsByTagName("p");
            for (y = 0; y < p.length; y++) {
                var span = p[y].getElementsByTagName("span");
                if (span) {
                    for (s = 0; s < span.length; s++) {
                        txtValue = span[s].textContent || span[s].innerText;
                        if (serachDissertationModuleArray.includes(txtValue.toUpperCase().trim(), 0)) {
                            toBeVisibleTr[i].style.display = "";
                            break;
                        } else {
                            toBeVisibleTr[i].style.display = "none";
                        }
                    }
                }
                if (toBeVisibleTr[i].style.display == "") {
                    break;
                }
            }
        }
    }
    for (i = 0; i < toBeVisibleTr.length; i++) {
        if (toBeVisibleTr[i].style.display == "") {
            toBeVisibleTrForDateFilter.push(toBeVisibleTr[i]);
        } //generating table with visible rows.
    }
    return toBeVisibleTrForDateFilter;
}

/**
 * function to filter based on dates in drop down.
 * @param {toBeVisibleTrForDateFilter} toBeVisibleTrForDateFilter rows filtered from dissetation modules
 * @param {toBeVisibleTr} toBeVisibleTr rows filtered from state
 * @param {tr} tr all rows of table
 * @param {formattedDate} formattedDate input selected from drop down
 * @param {toBeVisibleTrForModuleFilter} toBeVisibleTrForModuleFilter used to store filtered rows
 */
function filterBasedOnDate(toBeVisibleTrForDateFilter, toBeVisibleTr, tr, formattedDate,
    toBeVisibleTrForModuleFilter) {
    if (toBeVisibleTrForDateFilter.length != 0) {
        currentTr = toBeVisibleTrForDateFilter.concat();
    } else if (toBeVisibleTr != 0) {
        currentTr = toBeVisibleTr.concat();
    } else {
        currentTr = tr;
    }
    for (i = 0; i < currentTr.length; i++) {
        if (tableName == 'projectTable' || tableName == 'allProjectsTable') {
            td = currentTr[i].getElementsByTagName("td")[4];
        } else if (tableName == 'collaborationTable') {
            td = currentTr[i].getElementsByTagName("td")[5];
        }
        if (td) {
            txtValue = td.textContent || td.innerText;
            splittedArray = [];
            splittedArray = txtValue.split('-');
            txtValue = splittedArray[1] + '-' + splittedArray[2];
            if (formattedDate.includes(txtValue.toUpperCase())) {
                currentTr[i].style.display = "";
            } else {
                currentTr[i].style.display = "none";
            }
        }
    }
    for (i = 0; i < currentTr.length; i++) {
        if (currentTr[i].style.display == "") {
            toBeVisibleTrForModuleFilter.push(currentTr[i]);
        } //redrawing table for visible rows.
    }
    return toBeVisibleTrForModuleFilter;
}

/**
 * function to filter based on modules in drop down.
 * @param {toBeVisibleTrForModuleFilter} toBeVisibleTrForModuleFilter rows filtered from date
 * @param {toBeVisibleTrForDateFilter} toBeVisibleTrForDateFilter rows filtered from dissetation modules
 * @param {toBeVisibleTr} toBeVisibleTr rows filtered from state
 * @param {tr} tr all rows of table
 * @param {searchModuleArray} searchModuleArray input selected from drop down
 * @param {toBeVisibleTrForSearchBox} toBeVisibleTrForSearchBox used to store filtered rows
 */
function filterBasedOnModules(toBeVisibleTrForModuleFilter,
    toBeVisibleTrForDateFilter, toBeVisibleTr, tr, searchModuleArray, toBeVisibleTrForSearchBox) {
    if (toBeVisibleTrForModuleFilter.length != 0) {
        currentTr = toBeVisibleTrForModuleFilter.concat();
    } else if (toBeVisibleTrForDateFilter.length != 0) {
        currentTr = toBeVisibleTrForDateFilter.concat();
    } else if (toBeVisibleTr != 0) {
        currentTr = toBeVisibleTr.concat();
    } else {
        currentTr = tr;
    }

    for (i = 0; i < currentTr.length; i++) {
        if (tableName == 'projectTable' || tableName == 'allProjectsTable') {
            td = currentTr[i].getElementsByTagName("td")[6];
        } else if (tableName == 'collaborationTable') {
            td = currentTr[i].getElementsByTagName("td")[7];
        }
        if (td) {
            var p = td.getElementsByTagName("p");
            for (y = 0; y < p.length; y++) {
                var span = p[y].getElementsByTagName("span");
                if (span) {
                    for (s = 0; s < span.length; s++) {
                        txtValue = span[s].textContent || span[s].innerText;
                        if (searchModuleArray.includes(txtValue.toUpperCase().trim(), 0)) {
                            currentTr[i].style.display = "";
                            break;
                        } else {
                            currentTr[i].style.display = "none";
                        }
                    }
                }
                if (currentTr[i].style.display == "") {
                    break;
                }
            }
        }
    }
    for (i = 0; i < currentTr.length; i++) {
        if (currentTr[i].style.display == "") {
            toBeVisibleTrForSearchBox.push(currentTr[i]);
        } //redrawing table for visible rows.
    }
    return toBeVisibleTrForSearchBox;
}
/**
 * function to filter based on input in serachbox.
 * @param {toBeVisibleTrForSearchBox} toBeVisibleTrForSearchBox rows filterd from modules
 * @param {toBeVisibleTrForModuleFilter} toBeVisibleTrForModuleFilter rows filtered from date
 * @param {toBeVisibleTrForDateFilter} toBeVisibleTrForDateFilter rows filtered from dissetation modules
 * @param {toBeVisibleTr} toBeVisibleTr rows filtered from state
 * @param {tr} tr all rows of table
 * @param {searchInput} searchInput input to be searched
 */
function filterBasedOnSearchBoxInput(toBeVisibleTrForSearchBox, toBeVisibleTrForModuleFilter,
    toBeVisibleTrForDateFilter, toBeVisibleTr, tr, searchInput) {
    if (toBeVisibleTrForSearchBox.length != 0) {
        currentTr = toBeVisibleTrForSearchBox.concat();
    } else if (toBeVisibleTrForModuleFilter.length != 0) {
        currentTr = toBeVisibleTrForModuleFilter.concat();
    } else if (toBeVisibleTrForDateFilter.length != 0) {
        currentTr = toBeVisibleTrForDateFilter.concat();
    } else if (toBeVisibleTr.length != 0) {
        currentTr = toBeVisibleTr.concat();
    } else {
        currentTr = tr;
    }
    for (i = 0; i < currentTr.length; i++) {
        if (tableName == 'projectTable' || tableName == 'collaborationTable') {
            td = currentTr[i].getElementsByTagName("td")[1];
        } else if (tableName == 'allProjectsTable') {
            td = currentTr[i].getElementsByTagName("td")[0];
        }
        if (td) {
            txtValue = td.textContent || td.innerText;
            if (txtValue.toUpperCase().indexOf(searchInput) > -1) {
                currentTr[i].style.display = "";
            } else {
                currentTr[i].style.display = "none";
            }
        }

    }
}
/**
 * filterDropDowns invoked when on change event occurs on any filter
 */
function filterDropDowns() {
    //getting selected values from state drop down.
    var selected = [];
    for (var option of document.getElementById('supervisorProjectState').options) {
        if (option.selected) {
            selected.push(option.value);
        }
    }
    //getting selected values from dissertationModules drop down.
    var dissertationModulesSelected = [];
    for (var option of document.getElementById('dissertationModules').options) {
        if (option.selected) {
            dissertationModulesSelected.push(option.text);
        }
    }
    //getting selected values from dateSelect drop down.
    var dateArray = [];
    for (var option of document.getElementById('dateSelect').options) {
        if (option.selected) {
            dateArray.push(option.text);
        }
    }
    //getting selected values from modules drop down.
    var modulesSelected = [];
    for (var option of document.getElementById('modules').options) {
        if (option.selected) {
            modulesSelected.push(option.text);
        }
    }
    //getting input value from search box.
    var search = document.getElementById("search");
    searchInput = search.value.toUpperCase();
    searchDropDown(selected, dissertationModulesSelected, dateArray, modulesSelected, searchInput);
} //filterDropDowns() closing
/**
 * function invoked when drop downs are selected.
 */
function filterStudentProposalDropDowns() {
    var studentName = [];
    for (var option of document.getElementById('studentName').options) {
        if (option.selected) {
            studentName.push(option.text);
        }
    }
    var dateArray = [];
    for (var option of document.getElementById('StudentProposaldateSelect').options) {
        if (option.selected) {
            dateArray.push(option.text);
        }
    }
    var topicsSelected = [];
    for (var option of document.getElementById('topicsDropDown').options) {
        if (option.selected) {
            topicsSelected.push(option.text);
        }
    }
    var selectedState = [];
    for (var option of document.getElementById('studentProjectState').options) {
        if (option.selected) {
            selectedState.push(option.text);
        }
    }
    var searchInput = document.getElementById("searchStudentProposals");
    searchInput = searchInput.value.toUpperCase();
    filterStudentProposal(studentName, dateArray, topicsSelected, selectedState, searchInput);
}
/**
 * function called in filterStudentProposalDropDowns().
 * @param {studentName} studentName values selected in the filter student name
 * @param {dateArray} dateArray values selected in the filter date
 * @param {topicsSelected} topicsSelected values selected in the filter topics
 * @param {selectedState} selectedState values selected in the filter state
 * @param {searchInput} searchInput text values search box 
 */
function filterStudentProposal(studentName, dateArray, topicsSelected, selectedState, searchInput) {
    var table, proposalsUpperCaseArray = [],
        trForDate = [],
        trForTopics = [],
        formattedDate = [],
        newDate = [],
        trForState = [],
        trForSearch = [];
    var proposalsUpperCaseArray = changeProposalFiltersToUpperCase(studentName, topicsSelected, selectedState);
    studentName = proposalsUpperCaseArray[0];
    topicsSelected = proposalsUpperCaseArray[1];
    selectedState = proposalsUpperCaseArray[2];
    table = document.getElementById("studentProposalsTable");
    tr = table.getElementsByTagName("tr");
    if (studentName.length != 0) {
        trForDate = filterBasedOnStudentName(tr, studentName, trForDate);
    } else {
        for (i = 0; i < tr.length; i++) {
            tr[i].style.display = "";
        }
    }
    if (dateArray.length != 0) {
        for (i = 0; i < dateArray.length; i++) {
            newDate.push(dateArray[i].split(' '));
            formattedDate.push(newDate[i][0] + '-' + newDate[i][1]);
        } //splitting dateArray and formating it to MMMM-YYYY.
        for (j = 0; j < formattedDate.length; j++) {
            formattedDate[j] = formattedDate[j].toUpperCase();
        } //converting fromatted date to uppercase.
        trForTopics = filterProposalsBasedOnDate(trForDate, tr, formattedDate, trForTopics);
    }
    if (topicsSelected.length != 0) {
        trForState = filterBasedOnTopics(trForTopics, trForDate, tr, topicsSelected, trForState);
    }
    if (selectedState.length != 0) {
        trForSearch = filterBasedOnStudentProposalState(trForState, trForTopics, trForDate, tr, selectedState, trForSearch);
    }
    if (searchInput != "") {
        filterBasedOnStudentProposalSearchInput(trForSearch, trForState, trForTopics, trForDate, tr, searchInput);
    }
}
/**
 * function to convert drop down value sto upper case.
 * @param {studentName} studentName values selected in the filter student name.
 * @param {topicsSelected} topicsSelected values selected in the filter topics
 * @param {selectedState} selectedState values selected in the filter state
 */
function changeProposalFiltersToUpperCase(studentName, topicsSelected, selectedState) {
    for (j = 0; j < studentName.length; j++) {
        studentName[j] = studentName[j].toUpperCase();
    } // studentName upper case for closing.
    for (j = 0; j < topicsSelected.length; j++) {
        topicsSelected[j] = topicsSelected[j].toUpperCase();
    } // topicsSelected upper case for closing.
    for (j = 0; j < selectedState.length; j++) {
        selectedState[j] = selectedState[j].toUpperCase();
    } // selectedState upper case for closing.
    return [studentName, topicsSelected, selectedState]
}
/**
 * function to filter based on student name.
 * @param {tr} tr all tr of table
 * @param {studentName} studentName values selected in the filter student name
 * @param {trForDate} trForDate tr that are visible after filtering.
 */
function filterBasedOnStudentName(tr, studentName, trForDate) {
    for (i = 0; i < tr.length; i++) {
        var td = tr[i].getElementsByTagName("td")[2];
        if (td) {
            txtValue = td.textContent || td.innerText;
            if (studentName.includes(txtValue.toUpperCase().trim(), 0)) {
                tr[i].style.display = "";
            } else {
                tr[i].style.display = "none";
            }
        }
    }
    for (q = 0; q < tr.length; q++) {
        if (tr[q].style.display == "") {
            trForDate.push(tr[q]);
        }
    }
    return trForDate;
}
/**
 * function to filter based on date.
 * @param {trForDate} trForDate tr resulting from student name filter
 * @param {tr} tr all tr of table
 * @param {formattedDate} formattedDate date formatted to MMMM-yyyy
 * @param {trForTopics} trForTopics tr that are visible after filtering.
 */
function filterProposalsBasedOnDate(trForDate, tr, formattedDate, trForTopics) {
    if (trForDate.length != 0) {
        currentTr = trForDate.concat();
    } else {
        currentTr = tr;
    }
    for (i = 0; i < currentTr.length; i++) {
        td = currentTr[i].getElementsByTagName("td")[3];
        if (td) {
            txtValue = td.textContent || td.innerText;
            splittedArray = [];
            splittedArray = txtValue.split('-');
            txtValue = splittedArray[1] + '-' + splittedArray[2];
            if (formattedDate.includes(txtValue.toUpperCase())) {
                currentTr[i].style.display = "";
            } else {
                currentTr[i].style.display = "none";
            }
        }
    }
    for (i = 0; i < currentTr.length; i++) {
        if (currentTr[i].style.display == "") {
            trForTopics.push(currentTr[i]);
        } //redrawing table for visible rows.
    }
    return trForTopics;
}

/**
 * function to filter based on topics
 * @param {trForTopics} trForTopics tr after filtering by date.
 * @param {trForDate} trForDate tr after filtering by student name
 * @param {tr} tr all tr of table
 * @param {topicsSelected} topicsSelected values selected in the filter topics 
 * @param {trForState} trForState tr for visible rows
 */
function filterBasedOnTopics(trForTopics, trForDate, tr, topicsSelected, trForState) {
    if (trForTopics.length != 0) {
        currentTr = trForTopics.concat()
    } else if (trForDate.length != 0) {
        currentTr = trForDate.concat();
    } else {
        currentTr = tr;
    }
    for (i = 0; i < currentTr.length; i++) {
        td = currentTr[i].getElementsByTagName("td")[4];
        if (td) {
            var p = td.getElementsByTagName("p");
            for (y = 0; y < p.length; y++) {
                var span = p[y].getElementsByTagName("span");
                if (span) {
                    for (s = 0; s < span.length; s++) {
                        txtValue = span[s].textContent || span[s].innerText;
                        console.log(txtValue)
                        if (topicsSelected.includes(txtValue.toUpperCase().trim(), 0)) {
                            currentTr[i].style.display = "";
                            break;
                        } else {
                            currentTr[i].style.display = "none";
                        }
                    }
                }
                if (currentTr[i].style.display == "") {
                    break;
                }
            }
        }
    }
    for (i = 0; i < currentTr.length; i++) {
        if (currentTr[i].style.display == "") {
            trForState.push(currentTr[i]);
        } //redrawing table for visible rows.
    }
    return trForState;
}

/**
 * function to filter based on state.
 * @param {trForState} trForState tr after filtering by topics.
 * @param {trForTopics} trForTopics tr after filtering by date
 * @param {trForDate} trForDate tr after filtering by student name
 * @param {tr} tr all tr of table
 * @param {selectedState} selectedState values selected in the filter state
 * @param {trForSearch} trForSearch tr for visible rows
 */
function filterBasedOnStudentProposalState(trForState, trForTopics, trForDate, tr, selectedState, trForSearch) {
    if (trForState.length != 0) {
        currentTr = trForState.concat();
    } else if (trForTopics.length != 0) {
        currentTr = trForTopics.concat()
    } else if (trForDate.length != 0) {
        currentTr = trForDate.concat();
    } else {
        currentTr = tr;
    }
    for (i = 0; i < currentTr.length; i++) {
        td = currentTr[i].getElementsByTagName("td")[5];
        if (td) {
            txtValue = td.textContent || td.innerText;
            if (selectedState.includes(txtValue.toUpperCase().trim(), 0)) {
                currentTr[i].style.display = "";
            } else {
                currentTr[i].style.display = "none";
            }
        }
    }
    for (i = 0; i < currentTr.length; i++) {
        if (currentTr[i].style.display == "") {
            trForSearch.push(currentTr[i]);
        } //redrawing table for visible rows.
    }
    return trForSearch;
}

/**
 * function to filter based on input text in search box.
 * @param {trForSearch} trForSearch tr after filtering by state
 * @param {trForState} trForState tr after filtering by topics
 * @param {trForTopics} trForTopics tr after filtering by date
 * @param {trForDate} trForDate tr after filtering by student name
 * @param {tr} tr all tr of table
 * @param {searchInput} searchInput input in the serach box
 */
function filterBasedOnStudentProposalSearchInput(trForSearch, trForState, trForTopics, trForDate, tr, searchInput) {
    if (trForSearch.length != 0) {
        currentTr = trForSearch.concat();
    }
    if (trForState.length != 0) {
        currentTr = trForState.concat();
    } else if (trForTopics.length != 0) {
        currentTr = trForTopics.concat()
    } else if (trForDate.length != 0) {
        currentTr = trForDate.concat();
    } else {
        currentTr = tr;
    }
    for (i = 0; i < currentTr.length; i++) {
        td = currentTr[i].getElementsByTagName("td")[1];
        if (td) {
            txtValue = td.textContent || td.innerText;
            if (txtValue.toUpperCase().indexOf(searchInput) > -1) {
                currentTr[i].style.display = "";
            } else {
                currentTr[i].style.display = "none";
            }
        }

    }
}
/**
 * method to clear drop down data.
 */
function clearDropDownData() {
    $("#supervisorProjectState").val('default');
    $("#supervisorProjectState").selectpicker("refresh");
    $("#dissertationModules").val('default');
    $("#dissertationModules").selectpicker("refresh");
    $("#dateSelect").val('default');
    $("#dateSelect").selectpicker("refresh");
    $("#modules").val('default');
    $("#modules").selectpicker("refresh");
    $("#studentName").val('default');
    $("#studentName").selectpicker("refresh");
    $("#StudentProposaldateSelect").val('default');
    $("#StudentProposaldateSelect").selectpicker("refresh");
    $("#topicsDropDown").val('default');
    $("#topicsDropDown").selectpicker("refresh");
    $("#studentProjectState").val('default');
    $("#studentProjectState").selectpicker("refresh");
}

/**
 * method invoked on clicking collaboration button.
 */
function displayCollaboration() {
    clearDropDownData();
    document.getElementById("projectTable").style.display = 'none';
    if(document.getElementById("noMyProjects")){
    	 document.getElementById("noMyProjects").style.display = 'none';
    }   
    tableName = 'collaborationTable';
    filterDropDowns();
    document.getElementById("collaborationTable").style.display = 'table';
    if(document.getElementById("noCollaborationProjects")){
    	document.getElementById("noCollaborationProjects").style.display = "";
    }
    document.getElementById("allProjectsTable").style.display = 'none';
    if(document.getElementById("noAllProjects")){
        document.getElementById("noAllProjects").style.display = 'none';    	
    }
    document.getElementById("studentProposalsTable").style.display = 'none';
    if(document.getElementById("noSudentProposals")){
        document.getElementById("noSudentProposals").style.display = 'none';
    }
    document.getElementById("studentProposalFilters").style.display = 'none';
    document.getElementById("commonFilters").style.display = 'flex';
}

/**
 * method invoked on clicking my projects button.
 */
//$(document).ready(function() {
function displayMyProjects() {
    clearDropDownData();
    document.getElementById("projectTable").style.display = 'table';
    if(document.getElementById("noMyProjects")){
   	 document.getElementById("noMyProjects").style.display = '';
   }
    tableName = 'projectTable';
    filterDropDowns();
    document.getElementById("collaborationTable").style.display = 'none';
    if(document.getElementById("noCollaborationProjects")){
    	document.getElementById("noCollaborationProjects").style.display = "none";
    }
    document.getElementById("allProjectsTable").style.display = 'none';
    if(document.getElementById("noAllProjects")){
        document.getElementById("noAllProjects").style.display = 'none';    	
    }
    document.getElementById("studentProposalsTable").style.display = 'none';
    if(document.getElementById("noSudentProposals")){
        document.getElementById("noSudentProposals").style.display = 'none';
    }
    document.getElementById("studentProposalFilters").style.display = 'none';
    document.getElementById("commonFilters").style.display = 'flex';
}

/**
 * method invoked on clicking all projects button.
 */
function displayAllProjects() {
    clearDropDownData();
    document.getElementById("projectTable").style.display = 'none';
    if(document.getElementById("noMyProjects")){
   	 document.getElementById("noMyProjects").style.display = 'none';
   }
    tableName = 'allProjectsTable';
    filterDropDowns();
    document.getElementById("collaborationTable").style.display = 'none';
    if(document.getElementById("noCollaborationProjects")){
    	document.getElementById("noCollaborationProjects").style.display = "none";
    };
    document.getElementById("allProjectsTable").style.display = 'table';
    if(document.getElementById("noAllProjects")){
        document.getElementById("noAllProjects").style.display = '';    	
    }
    document.getElementById("studentProposalsTable").style.display = 'none';
    if(document.getElementById("noSudentProposals")){
        document.getElementById("noSudentProposals").style.display = 'none';
    }
    document.getElementById("studentProposalFilters").style.display = 'none';
    document.getElementById("commonFilters").style.display = 'flex';
}

/**
 * method invoked on clicking student proposals button.
 */
function displayStudentProposals() {
    clearDropDownData();
    filterStudentProposalDropDowns();
    document.getElementById("projectTable").style.display = 'none';
    if(document.getElementById("noMyProjects")){
   	 document.getElementById("noMyProjects").style.display = 'none';
   }
    document.getElementById("collaborationTable").style.display = 'none';
    if(document.getElementById("noCollaborationProjects")){
    	document.getElementById("noCollaborationProjects").style.display = "none";
    };
    document.getElementById("allProjectsTable").style.display = 'none';
    if(document.getElementById("noAllProjects")){
        document.getElementById("noAllProjects").style.display = 'none';    	
    }
    document.getElementById("studentProposalsTable").style.display = 'table';
    if(document.getElementById("noSudentProposals")){
        document.getElementById("noSudentProposals").style.display = '';
    }
    document.getElementById("studentProposalFilters").style.display = 'flex';
    document.getElementById("commonFilters").style.display = 'none';
}

function changeStudentSubmittedProjectState(projectId) {
    var statusTd = document.getElementById("statusTd" + projectId);
    if (statusTd.innerHTML.toUpperCase().indexOf("PENDING") > -1) {
        statusTd.innerHTML = 'Responded';
        var state = {
            methods: 'GET',
        };
        fetch('/changeStudentSubmittedProjectState?id=' + projectId + '&state=Responded', state)
    } else if (statusTd.innerHTML.toUpperCase().indexOf('RESPONDED') > -1) {
        statusTd.innerHTML = 'Confirmed';
        var state = {
            methods: 'GET',
        };
        fetch('/changeStudentSubmittedProjectState?id=' + projectId + '&state=Confirmed', state)
    } else if (statusTd.innerHTML.toUpperCase().indexOf('CONFIRMED') > -1) {
        statusTd.innerHTML = 'Responded';
        var state = {
            methods: 'GET',
        };
        fetch('/changeStudentSubmittedProjectState?id=' + projectId + '&state=Responded', state)
    }
}