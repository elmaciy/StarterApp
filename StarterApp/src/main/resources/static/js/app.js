

function  mainMenuClick(mainMenuId) {
	var url="?action=ClickMainMenu&mainMenuId="+mainMenuId;
	window.open(url,"_self");
}


function subMenuAction(subMenuId, menuTarget) {
	
	var menuAction="subMenuClicked";
	
	var url="?action="+menuAction+"&subMenuId="+subMenuId;
	window.open(url,menuTarget);	
}

function changeLanguage() {
	var  newLanguageId=document.getElementById("listOfLanguage").value;
	
	var url="?action=changeLanguage&newLanguageId="+newLanguageId;
	window.open(url,"_self");
}