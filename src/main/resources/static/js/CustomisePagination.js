/**
 * function to customise pagination.
 */
function customisePagination(){
	var ul,li=[];
	var pagination = document.getElementById("customPagination");
	if(pagination){
		 ul = document.getElementById("ulPagination");
		if(ul){
			li = ul.getElementsByClassName("customLink");
		}
	}
	if(li.length>3){
		for(i=3;i<li.length;i++){
			li[i].style.display="none";
		}
		 var nextLi = document.createElement("li");
		 nextLi.id=("next");
		 var a = document.createElement("button");
	        a.classList.add("page-link");
	        a.id=("nextButton");
	        a.setAttribute("onclick", "displayRightPagination()");
	        var node = document.createTextNode(">>");
	        a.appendChild(node);
	        nextLi.appendChild(a);
	        ul.appendChild(nextLi);
	}
}

/**
 * function to display right pagination.
 */
function displayRightPagination(){
	var ul,li=[],pagesHidden,nextToDisplay=1;
	var pagination = document.getElementById("customPagination");
	if(pagination){
		 ul = document.getElementById("ulPagination");
		if(ul){
			li = ul.getElementsByClassName("customLink");
		}
	}
	for(i=0;i<li.length;i++){
		if(li[i].style.display==""){
			pagesHidden = i;
			li[i].style.display="none"
		}
	}
	if(pagesHidden>=0){
        document.getElementById("prev").style.display="";
	}
	for(i=pagesHidden;i<li.length;i++){
		if(nextToDisplay <= 3){
		if(li[i].style.display=="none"){
			nextToDisplay = nextToDisplay+1;
			li[i].style.display="";
		}
		}
	}
}

/**
 * function to display left pagination.
 */
function displayLeftPagination(){
	var ul,li=[],pagesHidden,nextToDisplay=1;
	var pagination = document.getElementById("customPagination");
	if(pagination){
		 ul = document.getElementById("ulPagination");
		if(ul){
			li = ul.getElementsByClassName("customLink");
		}
	}
	for(i=li.length-1;i>=0;i--){
		if(li[i].style.display==""){
			pagesHidden = i;
			li[i].style.display="none"
		}
	}
	
	for(i=pagesHidden;i>=0;i--){
		if(nextToDisplay <= 3){
		if(li[i].style.display=="none"){
			nextToDisplay = nextToDisplay+1;
			li[i].style.display="";
		}
		}
	}
}

/**
 * function to check active class for pagination.
 */
function checkActiveClass(){
	var ul,li=[],pagesHidden,nextToDisplay=1;
	var pagination = document.getElementById("customPagination");
	if(pagination){
		 ul = document.getElementById("ulPagination");
		if(ul){
			li = ul.getElementsByClassName("customLink");
		}
	}
	
	var isActive = false;
	for(i=0;i<li.length;i++){
		if(!(li[i].classList.contains("active"))){
			li[i].style.display="none"
				 document.getElementById("prev").style.display="";
		} else{
			if(li[i].style.display="none"){
				li[i].style.display="";
			}
			if(li[i-1] != undefined){
				li[i-1].style.display="";
			} else if(li[i+1] != undefined){
				li[i+1].style.display="";
			}
			
			if(li[i+1] != undefined){
				li[i+1].style.display="";
			} else if(li[i-1] != undefined){
				li[i-1].style.display="";
				li[i-2].style.display="";
			}
			isActive = true;
		}
	
		
		if(isActive == true){ break; }
}
	if(li[0].style.display==""){
		document.getElementById("prev").style.display="none";
	} 
	if(li[li.length-1].style.display==""){
		document.getElementById("next").style.display="none";
	}
}