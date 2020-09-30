/**
 * function to display and hide external text box.
 */
function displayExternalInputbox() {
    var checkbox = document.getElementById("externalCheckbox");
    var textbox = document.getElementById("externalTextbox");
    if (checkbox.checked == true) {
        textbox.style.display = "block";
    } else {
        textbox.style.display = "none";
    }
}

/**
 * function to display and hide internal text box.
 */
function displayInternalDropdownbox() {
    var checkbox = document.getElementById("internalCheckbox");
    var dropdownbox = document.getElementById("internalDropdownbox");
    if (checkbox.checked == true) {
        dropdownbox.style.display = "block";
    } else {
        dropdownbox.style.display = "none";
    }
}
$(document).ready(function() {
    /**
     * function to validate upload project form.
     */
    $("#uploadButton").click(function() {
        var title = document.getElementById("title").value;
        var description = document.getElementById("description").value;
        var topics = $('#topics');
        var dissertationModules = $('#dissertationModules');
        var information = document.getElementById("information").value;
        var background = document.getElementById("background").value;
        var artefact = document.getElementById("artefact").value;
        if (topics.val().length == 0 || dissertationModules.val().length == 0 || title == "" ||
            title.trim().length > 255 || description == "" || 
           	description.trim().length > 5000 || information.trim().length > 3000||
           	background.trim().length > 3000||artefact.trim().length > 3000) {
            alert("Field can not be empty and do not enter more than acceptable values.");
            if (title == "" || title == undefined || title.trim().length > 255) {
            	validateTitle(description);
            } if (description == "" || description == undefined || description.trim().length > 5000) {
            	validateDescription(title);
            } if (information.trim().length > 3000){
            	document.getElementById("informationError").style.display = "";
            } else {
            	if(document.getElementById("informationError").style.display == ""){
            		document.getElementById("informationError").style.display = "none";
            	}
            }           	
            	if (background.trim().length > 3000){
            	document.getElementById("backgroundError").style.display = "";
            } else{
            	if(document.getElementById("backgroundError").style.display == ""){
            		document.getElementById("backgroundError").style.display = "none";
            	}
            }
            	if (artefact.trim().length > 3000){
            	document.getElementById("artefactError").style.display = "";
            } else{
            	if(document.getElementById("artefactError").style.display == ""){
            		document.getElementById("artefactError").style.display = "none";
            	}
            }
            	if (topics.val().length == 0) {
            	document.getElementById("topicsError").style.display="";
            } else{
            	if(document.getElementById("topicsError").style.display==""){
            		document.getElementById("topicsError").style.display="none";
            	}
            }
            	if (dissertationModules.val().length == 0) {
            	document.getElementById("dissertationError").style.display="";
            } else {
            	if(document.getElementById("dissertationError").style.display==""){
            		document.getElementById("dissertationError").style.display="none";
            	}
            }
            
            return false;
        } else {
        	clearErrorMessages();
        }

    });
})

/**
 * function to validate title.
 * @param description the description field
 */
function validateTitle(description){
	 if (description == "" || description == undefined || description.trim().length > 5000) {
         document.getElementById("descriptionError").style.display = "";
     } else {
         document.getElementById("descriptionError").style.display = "none";
     }
     document.getElementById("titleError").style.display = "";
}

/**
 * function to validate description.
 * @param title the title field
 */
function validateDescription(title){
    if (title == ""|| title == undefined || title.trim().length > 255) {
        document.getElementById("titleError").style.display = "";
    } else {
        document.getElementById("titleError").style.display = "none";
    }
    document.getElementById("descriptionError").style.display = "";
}

/**
 * function to clear error messages.
 */
function clearErrorMessages(){
	if(document.getElementById("titleError").style.display == ""){
		document.getElementById("titleError").style.display = "none";
	}
	if(document.getElementById("descriptionError").style.display == ""){
		document.getElementById("descriptionError").style.display = "none";
	}
	if(document.getElementById("informationError").style.display == ""){
		document.getElementById("informationError").style.display = "none";
	}
	if(document.getElementById("artefactError").style.display == ""){
		document.getElementById("artefactError").style.display = "none";
	}
	if(document.getElementById("backgroundError").style.display == ""){
		document.getElementById("backgroundError").style.display = "none";
	}
	if(document.getElementById("topicsError").style.display == ""){
		document.getElementById("topicsError").style.display = "none";
	}
	if(document.getElementById("dissertationError").style.display == ""){
		document.getElementById("dissertationError").style.display = "none";
	}
}