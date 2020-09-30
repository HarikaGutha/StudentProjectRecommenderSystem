/**
 * changeState() used to change the state project
 * @param projectId is the id of the project for which the state is to be changed
 */
function changeState(projectId) {
    var availableSpan = document.getElementById("availableSpan" + projectId);
    var takenSpan = document.getElementById("takenSpan" + projectId);
    var historicSpan = document.getElementById("historicSpan" + projectId);
    if (availableSpan != null) {
        changeStateInAvailableSpan(availableSpan, projectId);
    } //availableSpan if closing
    else if (takenSpan != null) {
        changeStateInTakenSpan(takenSpan, projectId);
    } //takenSpan else if closing
    else if (historicSpan != null) {
        changeStateInHistoricSpan(historicSpan, projectId)
    } //  historicSpan else if closing.
} // changeState() closing.

/**
 * refactored function called in availableSpan.
 * @param availableSpan is the element with the id availableSpan
 * @param projectId is the id of the project for which the state is to be changed
 */
function changeStateInAvailableSpan(availableSpan, projectId) {
    if (availableSpan.innerHTML.toUpperCase().indexOf('AVAILABLE') > -1) {
        if (changeStateToTaken(projectId)) {
            availableSpan.innerHTML = ' <i class="fas fa-circle" style="color:red"></i> Taken';
        }
    } else if (availableSpan.innerHTML.toUpperCase().indexOf('TAKEN') > -1) {
        if (changeStateToHisoric(projectId)) {
            availableSpan.innerHTML = '<i class="fas fa-circle" style="color:gray"></i> Historic';
        }
    } else if (availableSpan.innerHTML.toUpperCase().indexOf('HISTORIC') > -1) {
        if (changeStateToAvailable(projectId)) {
            availableSpan.innerHTML = '<i class="fas fa-circle" style="color:green"></i> Available';
        }
    }
}

/**
 * refactored function called in takenSpan. 
 * @param takenSpan is the element with the id takenSpan
 * @param projectId is the id of the project for which the state is to be changed
 */
function changeStateInTakenSpan(takenSpan, projectId) {
    if (takenSpan.innerHTML.toUpperCase().indexOf('TAKEN') > -1) {
        if (changeStateToHisoric(projectId)) {
            takenSpan.innerHTML = '<i class="fas fa-circle" style="color:gray"></i> Historic';
        }
    } else if (takenSpan.innerHTML.toUpperCase().indexOf('HISTORIC') > -1) {
        if (changeStateToAvailable(projectId)) {
            takenSpan.innerHTML = '<i class="fas fa-circle" style="color:green"></i> Available';
        }
    } else if (takenSpan.innerHTML.toUpperCase().indexOf('AVAILABLE') > -1) {
        if (changeStateToTaken(projectId)) {
            takenSpan.innerHTML = '<i class="fas fa-circle" style="color:red"></i> Taken';
        }
    }
}

/**
 * refactored function called in historicSpan. 
 * @param historicSpan is the element with the id historicSpan
 * @param projectId is the id of the project for which the state is to be changed
 */
function changeStateInHistoricSpan(historicSpan, projectId) {
    if (historicSpan.innerHTML.toUpperCase().indexOf('HISTORIC') > -1) {
        if (changeStateToAvailable(projectId)) {
            historicSpan.innerHTML = '<i class="fas fa-circle" style="color:green"></i> Available';
        }
    } else if (historicSpan.innerHTML.toUpperCase().indexOf('TAKEN') > -1) {
        if (changeStateToHisoric(projectId)) {
            historicSpan.innerHTML = '<i class="fas fa-circle" style="color:gray"></i> Historic';
        }
    } else if (historicSpan.innerHTML.toUpperCase().indexOf('AVAILABLE') > -1) {
        if (changeStateToTaken(projectId)) {
            historicSpan.innerHTML = '<i class="fas fa-circle" style="color:red"></i> Taken';
        }
    }
}

/**
 * changeStateToTaken method  called when the state should be changed to taken 
 * @param projectId is the id of the project for which the state is to be changed
 */
function changeStateToTaken(projectId) {
    var status = new Boolean(0);
    var state = {
        methods: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    };
    fetch('/changeProjectState?id=' + projectId + '&state=Taken', state).then(response => {
        if (response.status == 200) {
            status = true;
        }
    })
    return status;
}

/**
 * changeStateToHisoric method  called when the state should be changed to taken 
 * @param projectId is the id of the project for which the state is to be changed
 */
function changeStateToHisoric(projectId) {
    var status = new Boolean(0);
    var state = {
        methods: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    };
    fetch('/changeProjectState?id=' + projectId + '&state=Historic', state)
        .then(response => {
            if (response.status == 200) {
                status = true;
            }
        })
    return status;
}

/**
 * changeStateToAvailable method  called when the state should be changed to taken 
 * @param projectId is the id of the project for which the state is to be changed
 */
function changeStateToAvailable(projectId) {
    var status = new Boolean(0);
    var state = {
        methods: 'GET',
        headers: {
            'Content-Type': 'application/json',
            'Accept': 'application/json'
        }
    };
    fetch('/changeProjectState?id=' + projectId + '&state=Available', state)
        .then(response => {
            if (response.status == 200) {
                status = true;
            }
        })
    return status;
}