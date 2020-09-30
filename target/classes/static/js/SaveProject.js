/**
 * method to save and unsave a project.
 * 
 * @param {projectId} projectId id of the project to be saved or unsaved
 */
function save(projectId) {
    console.log("func called");
    var saveIcon = document.getElementById("save" + projectId);
    if (saveIcon.style.color == "white") {
        saveIcon.style.color = "orange";
        var save = {
            methods: 'GET',
        };
        fetch('/saveProject?id=' + projectId, save)
    } else {
        saveIcon.style.color = "white";
        var unSave = {
            methods: 'GET',
        };
        fetch('/unSaveProject?id=' + projectId, unSave)
    }
}