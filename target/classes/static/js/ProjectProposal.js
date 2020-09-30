$(document).ready(function() {
    /**
     * function foe validations of project proposal page.
     */
    $("#uploadButton").click(function() {
        var title = document.getElementById("title").value;
        var description = document.getElementById("description").value;
        var projectType = $("input[name='projectType']").is(':checked');
        var topics = $('#topics');
        var information =  document.getElementById("information").value;
        if (topics.val().length == 0 || projectType == false || title == "" ||
             title.trim().length > 255|| description == "" || description.trim().length > 5000
            || information.trim().length > 3000)
             
             {
            alert("Field can not be empty and do not enter more than acceptable values.");
            if (title == "" || title == undefined || title.trim().length > 255) {
            	validateTitle(description);
            }  if (description == "" || description == undefined || description.trim().length > 5000) {
            	validateDescription(title);
            } if (information.trim().length > 3000){
            	document.getElementById("informationError").style.display = "";
            } else {
            	if(document.getElementById("informationError").style.display == ""){
            		document.getElementById("informationError").style.display = "none";
            	}
            }if (projectType == false) {
            	document.getElementById("typeError").style.display = "";
            } else {
            	if(document.getElementById("typeError").style.display == ""){
            		document.getElementById("typeError").style.display = "none";
            	}
            } 
            if (topics.val().length == 0) {
                document.getElementById("topicsError").style.display = "";
            } else {
            	if(document.getElementById("topicsError").style.display == ""){
            		document.getElementById("topicsError").style.display = "none";
            	}
            }
            return false;
        } else {
        	clearErrorMessages();
        	
        }

    });
});

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
    if (title == "" || title == undefined || title.trim().length > 255) {
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
	if(document.getElementById("topicsError").style.display == ""){
		document.getElementById("topicsError").style.display = "none";
	}
	if(document.getElementById("typeError").style.display == ""){
		document.getElementById("typeError").style.display = "none";
	}
}