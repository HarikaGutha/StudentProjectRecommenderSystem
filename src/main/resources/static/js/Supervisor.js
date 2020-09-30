$('.savedProjectColSize').matchHeight();
window.onload = function(event) {
    /**
     * Adding dynamic colors for the Research area div headers.
     */
    var researchGroup = document.getElementsByClassName("researchGroup");
    if (researchGroup) {
        for (i = 0; i < researchGroup.length; i++) {
            if ((i + 1) % 5 == 0) {
                researchGroup[i].innerText = researchGroup[i].innerText.toUpperCase();
                researchGroup[i].style.background = "#F5B7B1";
            } else if ((i + 1) % 4 == 0) {
                researchGroup[i].innerText = researchGroup[i].innerText.toUpperCase();
                researchGroup[i].style.background = "#82E0AA";
            } else if ((i + 1) % 3 == 0) {
                researchGroup[i].innerText = researchGroup[i].innerText.toUpperCase();
                researchGroup[i].style.background = "#2471A3";
            } else if ((i + 1) % 2 == 0) {
                researchGroup[i].innerText = researchGroup[i].innerText.toUpperCase();
                researchGroup[i].style.background = "#AED6F1";
            } else if ((i + 1) % 1 == 0) {
                researchGroup[i].innerText = researchGroup[i].innerText.toUpperCase();
                researchGroup[i].style.background = "orange";
            } else {
                researchGroup[i].innerText = researchGroup[i].innerText.toUpperCase();
                researchGroup[i].style.background = "#82E0AA";
            }

        }
    }
    /**
     * Adding active class for pagination.
     */
    var anchor = document.getElementsByTagName("li")
    if (anchor) {
        for (i = 0; i < anchor.length; i++) {
        	if(i!=0){
        		anchor[i].classList.add("page-item","customLink");
        	}
            
        }
    }
    customisePagination();
};
/**
 * sending pagination request to controller.
 * @param {} pageNumber 
 * @param {*} groupId 
 */
function DoPost(pageNumber, groupId) {
    document.getElementById('selectedPage').value = pageNumber;
    document.getElementById('selectedGroupId').value = groupId;
    document.getElementById('paginationForm').submit();
}