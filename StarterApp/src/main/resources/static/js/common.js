var AJAX = createXMLHttpRequest();


var z_index_counter=9999;

var queueForAction=[];
var queueForDiv=[];
var quueueForPar1=[];
var quueueForPar2=[];
var quueueForPar3=[];
var quueueForPar4=[];
var quueueForPar5=[];
var quueueForPar6=[];


//*************************************************************************
function createXMLHttpRequest(){
//*************************************************************************
  // See http://en.wikipedia.org/wiki/XMLHttpRequest
  // Provide the XMLHttpRequest class for IE 5.x-6.x:
  if( typeof XMLHttpRequest == "undefined" ) XMLHttpRequest = function() {
    try { return new ActiveXObject("Msxml2.XMLHTTP.6.0"); } catch(e) {  console.log(e); }
    try { return new ActiveXObject("Msxml2.XMLHTTP.3.0"); } catch(e) { console.log(e); }
    try { return new ActiveXObject("Msxml2.XMLHTTP"); } catch(e) { console.log(e); }
    try { return new ActiveXObject("Microsoft.XMLHTTP"); } catch(e) { console.log(e); }
    throw new Error( "This browser does not support XMLHttpRequest." );
  };
  return new XMLHttpRequest();
}




//*************************************************************************
function ajaxDynamicComponentCaller(action,div_id,par1){
//*************************************************************************	
	ajaxDynamicComponentCaller(action,div_id,par1,"","","","","");
}

//*************************************************************************
function ajaxDynamicComponentCaller(action,div_id,par1,par2){
//*************************************************************************	
	ajaxDynamicComponentCaller(action,div_id,par1,par2,"","","","");
}

//*************************************************************************
function ajaxDynamicComponentCaller(action,div_id,par1,par2,par3){
//*************************************************************************	
	ajaxDynamicComponentCaller(action,div_id,par1,par2,par3,"","","");
}

//*************************************************************************
function ajaxDynamicComponentCaller(action,div_id,par1,par2,par3,par4){
//*************************************************************************	
	ajaxDynamicComponentCaller(action,div_id,par1,par2,par3,par4,"","");
}

//*************************************************************************
function ajaxDynamicComponentCaller(action,div_id,par1,par2,par3,par4,par5){
//*************************************************************************	
	ajaxDynamicComponentCaller(action,div_id,par1,par2,par3,par4,par5,"");
}



//*************************************************************************
function ajaxDynamicComponentCaller(action,div_id,par1,par2,par3,par4,par5,par6){
//*************************************************************************	
	

	if (div_id!="NONE" && div_id.indexOf("NOFADE_")==-1) showBlocker();
	
	 
	
	if (par1) {
		par1 = par1.replace(/(?:\r\n|\r|\n)/g, '**NEWLINE**');
	}
	if (par2) {
		par2 = par2.replace(/(?:\r\n|\r|\n)/g, '**NEWLINE**');
	}
	if (par3) {
		par3 = par3.replace(/(?:\r\n|\r|\n)/g, '\**NEWLINE**');
	}
	if (par4) {
		par4 = par4.replace(/(?:\r\n|\r|\n)/g, '**NEWLINE**');
	}
	if (par5) {
		par5 = par5.replace(/(?:\r\n|\r|\n)/g, '**NEWLINE**');
	}
	if (par6) {
		par6 = par6.replace(/(?:\r\n|\r|\n)/g, '**NEWLINE**');
	}
	
	
	
	
  div_id.split(':::::').forEach(function(curr_div_id){
	  if (curr_div_id!="NONE" && curr_div_id.indexOf("NOFADE_")==-1)
		  {
		  var el=document.getElementById(curr_div_id);
		  if (!el) 
			  console.log("No such div found id : " + curr_div_id + " @ajaxDynamicComponentCaller");
		  else 
			  {
			  orig_div_contents[curr_div_id]=el.innerHTML;
			  }
 		  }
  });
  
  showHourGlass();
  
  
  
  
  if(AJAX.readyState == 1 || AJAX.readyState == 2 || AJAX.readyState == 3 ) {
	  
	  console.log("AJAX.readyState " + AJAX.readyState);
	  console.log("AJAX.status " + AJAX.status);
	  
	  console.log("queued action : "  + action);
	  console.log("div_id : " + div_id);
	  console.log("par1 : " + par1);
	  console.log("par2 : " + par2);
	  console.log("par3 : " + par3);
	  console.log("par4 : " + par4);
	  console.log("par5 : " + par5);
	  console.log("par6 : " + par6);
	  
	  
	  
	  queueForAction[queueForAction.length]=action;
	  queueForDiv[queueForDiv.length]=div_id;
	  quueueForPar1[quueueForPar1.length]=par1;
	  quueueForPar2[quueueForPar2.length]=par2;
	  quueueForPar3[quueueForPar3.length]=par3;
	  quueueForPar4[quueueForPar4.length]=par4;
	  quueueForPar5[quueueForPar5.length]=par5;
	  quueueForPar6[quueueForPar6.length]=par6;
	  
	  
	  return;
  }
  
  AJAX.onreadystatechange = handler_handler;
  
  par1=encodeURIComponent(par1);
  par2=encodeURIComponent(par2);
  par3=encodeURIComponent(par3);
  par4=encodeURIComponent(par4);
  par5=encodeURIComponent(par5);
  par6=encodeURIComponent(par6);
  
  
  
  
  var AJAX_URL="ajaxDynamicComponent?action=" + action + "&div=" + div_id + "&par1=" + par1 + "&par2=" + par2 + "&par3="+par3+ "&par4="+par4+ "&par5="+par5+ "&par6="+par6;
  var async=true;
  AJAX.open("POST", AJAX_URL , async);
  AJAX.setRequestHeader( 'content-type', 'text/html;charset=UTF-8');
  AJAX.timeout = 1200000;
  AJAX.ontimeout = function () { myalert("Ajax Timed out!!!");   restoreDivOriginalContents(); };
  AJAX.send("");
  
}




//*************************************************************************
function handler_handler() {
//*************************************************************************
  if(AJAX.readyState == 4 && AJAX.status == 200) {
	  
	 
	  hideBlocker();
	  clearHourGlass();
	  
	  var json =null;
	  
	  try {json=JSON.parse( AJAX.responseText ); } catch(err) {
		  json =null;
		  myalert("Json Parse Error : " +err+" (See browser's console for AJAX response.)");
		  console.log("AJAX.responseText : " +AJAX.responseText);
		  location.href="default2.jsp";
		  return;
		  }
      var msg=json.msg;

      for (var i=1;i<1000;i++) {
    	  var div=json["div"+i];
    	  if (!div) break;
    	  
    	  var html=json["html"+i];
    	  
    	  if(html) {
              var targetdiv=document.getElementById(div);
              if (targetdiv) targetdiv.innerHTML=html;
              if (html.indexOf("javascript:")==0) runjs_code(html.substring(11));
          } else {
        	  if (div!="NONE")  document.getElementById(div).innerHTML=orig_div_contents[div];
          }
      } //for (var i=1;i<1000;i++)
      
      
      if (msg && msg.indexOf("nok:")==0) {
    	  
    	  
    	  restoreDivOriginalContents();
    	  myalert(msg.substring(4));
    	 
      }
      else if (msg && msg.indexOf("ok:javascript:")==0) {
    	  
    	  var js_code_to_execute=msg.substring(14);
    	  runjs_code(js_code_to_execute);
    	  
      }
      else if (!msg) {
    	  restoreDivOriginalContents();
    	  myalert("Message is not transmitted to the client.");
    	  console.log("Message is not transmitted to the client.");
    	  
    	  
      } else if (msg!="ok") {
    	  console.log("exceptional msg : " + msg);
      }
      
      
      orig_div_contents=[];
      
      setActiveTableRow();
      setActiveList();
      showTableValidationResults();
      setToOriginalScrollTops();
      
      performActionQueue();
      
      
      
  } else if (AJAX.readyState == 4 && AJAX.status != 200) {
	  
	  //clear qyeye on error
	  queueForAction=[];
	  queueForDiv=[];
	  quueueForPar1=[];
	  quueueForPar2=[];
	  quueueForPar3=[];
	  quueueForPar4=[];
	  quueueForPar5=[];
	  quueueForPar6=[];
	  
	  hideBlocker();
	 clearHourGlass();
	 //restoreDivOriginalContents();
	 var alertcontent='Something went wrong on ajax call...(AJAX.readyState='+AJAX.readyState+', AJAX.status='+AJAX.status+')' ;
	 alertcontent=alertcontent  + 
	 	"<hr>"+
	 	"<span class=\"label label-danger\">Details of Error</span>"+
	 	"<hr>"+
	 	"<textarea style=\"width:100%; font-family: monospace; background-color:red; color:white;\" rows=10>"+AJAX.responseText+"</textarea>";
	 
	 myalert(alertcontent);
	 
	 
  }  
  
}





//***********************************************
function performActionQueue() {
//***********************************************
	
	if (queueForAction.length==0) {
		//console.log("no action waiting in queue");
		return;
	}
	
	console.log("action queue size : " + queueForAction.length);
	
	var queue_action=queueForAction[0];
	var queue_div=queueForDiv[0];
	var queue_par1=quueueForPar1[0];
	var queue_par2=quueueForPar2[0];
	var queue_par3=quueueForPar3[0];
	var queue_par4=quueueForPar4[0];
	var queue_par5=quueueForPar5[0];
	var queue_par6=quueueForPar6[0];
	
	console.log("executing action ["+queue_action+"] from queue");
	
	ajaxDynamicComponentCaller(queue_action, queue_div, queue_par1, queue_par2, queue_par3, queue_par4, queue_par5, queue_par6);
	
	//delete first item
	tmpForAction=queueForAction;
	tmpForDiv=queueForDiv;
	tmpForPar1=quueueForPar1;
	tmpForPar2=quueueForPar2;
	tmpForPar3=quueueForPar3;
	tmpForPar4=quueueForPar4;
	tmpForPar5=quueueForPar5;
	tmpForPar6=quueueForPar6;
	
	queueForAction=[];
	queueForDiv=[];
	quueueForPar1=[];
	quueueForPar2=[];
	quueueForPar3=[];
	quueueForPar4=[];
	quueueForPar5=[];
	quueueForPar6=[];
	
	for (var i=1;i<tmpForAction.length;i++) {
		queueForAction[queueForAction.length]=tmpForAction[i];
		queueForDiv[queueForDiv.length]=tmpForDiv[i];
		quueueForPar1[quueueForPar1.length]=tmpForPar1[i];
		quueueForPar2[quueueForPar2.length]=tmpForPar2[i];
		quueueForPar3[quueueForPar3.length]=tmpForPar3[i];
		quueueForPar4[quueueForPar4.length]=tmpForPar4[i];
		quueueForPar5[quueueForPar5.length]=tmpForPar5[i];
		quueueForPar6[quueueForPar6.length]=tmpForPar6[i];
	}
	
	


}








//***********************************************
function restoreDivOriginalContents() {
//***********************************************
	hideBlocker();
	clearHourGlass();
	
	var divArr=Object.keys(orig_div_contents);

	for (var d=0;d<divArr.length;d++) {
		  var div_id=divArr[d];
		  var div_content=orig_div_contents[d];
		  if (!div_content) continue;
		  if (div_id=="NONE" || div_id.indexOf("NOFADE_")==0) continue;
		  if (div_content.length==0) continue;
		  
		  var el=document.getElementById(div_id);
		  if (!el) {
			  console.log("div ["+div_id+"] not found@handler_handler");
			  continue;
		  }
		  el.innerHTML=div_content;
		  
	}

}






//******************************************************************************************************

function showModal(modaldivid) {
	var el=document.getElementById(modaldivid);
	if (!el) myalert("Modal not found : " + modaldivid);
	z_index_counter++;
	el.style.zIndex=""+z_index_counter;
	
	
	$('#'+modaldivid).modal('show');
}



//*************************************************************************
function runjs_code(jsscript){
//*************************************************************************
var msg1="";
 
 try {
	 ret1=eval(jsscript);
 } catch (e) {
	        msg1='<br>Error : <b><font color=red>'+e.message+'</font></b>';
	        console.log(msg1);
	        console.log("while executing javascript : ");
	        console.log(jsscript);
	}
  
};