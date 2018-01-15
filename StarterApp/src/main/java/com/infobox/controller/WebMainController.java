package com.infobox.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.infobox.commonLib;
import com.infobox.datamodel.AppMenu;
import com.infobox.service.AppService;

@Controller
@RequestMapping("/")
public class WebMainController {

	@Autowired
	AppService appService;
	
	private void mylog(String logstr) {
		appService.mylog(logstr);
	}

	@RequestMapping("/home")
	public String indexController(
			Model model, 
			HttpServletRequest request,
			HttpSession session
			) {
		
		

		Long currentUserId=1L;
		try{currentUserId=Long.parseLong(commonLib.nvl((String) session.getAttribute("currentUserId"),"x")); } catch(Exception e) {}
		
		boolean isLogedIn=false;
		try{isLogedIn=Boolean.parseBoolean(commonLib.nvl((String) session.getAttribute("isLoggedIn"),"false")); } catch(Exception e) {}
		
		if (!isLogedIn) {
			
			return "login";
		}

		Long currentLanguageId=appService.getDefaultLanguageId();
		try{currentLanguageId=Long.parseLong(commonLib.nvl((String) session.getAttribute("currentLanguageId"),"x")); } catch(Exception e) {}
		

		Long activeMainMenuId=0L;
		try{activeMainMenuId=Long.parseLong(commonLib.nvl((String) session.getAttribute("activeMainMenuId"), "0")); } catch(Exception e) {e.printStackTrace();}
		
		List<AppMenu> mainMenuList=appService.getMenByParentMenuId(0L);
		if (activeMainMenuId==0L && mainMenuList.size()>0) activeMainMenuId=mainMenuList.get(0).getId();

		
		
		String action=commonLib.nvl((String) request.getParameter("action"),"NoAction");
		
		
		switch(action) {
		case "ClickMainMenu" : {
			Long clickedMainMenuId=0L;
			try{clickedMainMenuId=Long.parseLong(commonLib.nvl((String) request.getParameter("mainMenuId"), "0")); } catch(Exception e) {}
			session.setAttribute("activeMainMenuId", ""+clickedMainMenuId);
			activeMainMenuId=clickedMainMenuId;
			break;
		}
		case "subMenuClicked" : {
			String clickedSubMenuId=commonLib.nvl((String) request.getParameter("subMenuId"),"0");			
			if (!clickedSubMenuId.equals("0")) 
				session.setAttribute("activeSubMenuId_for_"+activeMainMenuId, clickedSubMenuId);
			
			break;
		}
		case "changeLanguage": {
			Long newLanguageId=0L;
			try{newLanguageId=Long.parseLong(commonLib.nvl((String) request.getParameter("newLanguageId"), "0")); } catch(Exception e) {e.printStackTrace();}
			if (newLanguageId>0 && newLanguageId!=currentLanguageId)  {
				appService.setUserLanguage(currentUserId, newLanguageId);
				currentLanguageId=newLanguageId;
				session.setAttribute("currentLanguageId", ""+newLanguageId);
			}
			break;
		}
		default : ;
		}
	
		

		
		
		List<AppMenu> subMenuList=appService.getMenByParentMenuId(activeMainMenuId);
		
		Long activeSubMenuId=0L;
		
		try{activeSubMenuId=Long.parseLong(commonLib.nvl((String) session.getAttribute("activeSubMenuId_for_"+activeMainMenuId),"0"));} catch(Exception e) {}
		
		String fragmentFile="noTargetFound";
		String fragment="main";
		
		mylog("activeSubMenuId : "+activeSubMenuId);
		
		if (activeSubMenuId>0) {
			AppMenu activeSubMenu=appService.getAppMenuByMenuId(activeSubMenuId);

			if (activeSubMenu!=null) {
				String fragmentPath=activeSubMenu.getActionDetails();
				try {
					String[] arr=fragmentPath.split("::");
					fragmentFile=arr[0].trim();
					fragment=arr[1].trim();
					
					mylog("Opening fragment : "+fragmentFile+"::"+fragment);
					
				} catch(Exception e) {
					e.printStackTrace();
					
					model.addAttribute("fragmentPath", fragmentPath);
					fragmentFile="noTargetFound";
					fragment="main";
					
				}
				
			}
			
	
		}
		


		model.addAttribute("activeMainMenuId",activeMainMenuId);
		model.addAttribute("mainMenuList", mainMenuList);

		model.addAttribute("activeSubMenuId",activeSubMenuId);
		model.addAttribute("subMenuList", subMenuList);
		
		model.addAttribute("fragmentFile", fragmentFile);
		model.addAttribute("fragment", fragment);

		System.out.println("Current Language id : "+currentLanguageId);
		
		model.addAttribute("currentLanguageId", currentLanguageId);
		model.addAttribute("languageList",appService.getLanguageList());
		
			
		return "index";
	}
	
	


}
