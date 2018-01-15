package com.infobox.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import com.infobox.datamodel.AppLanguage;
import com.infobox.datamodel.AppMenu;
import com.infobox.datamodel.AppUser;
import com.infobox.repository.AppLanguageRepo;
import com.infobox.repository.AppMenuRepo;
import com.infobox.repository.AppUserRepo;

@Service
public class AppService {
	
	private static final Logger logger = LoggerFactory.getLogger(AppService.class);


	@Autowired AppUserRepo appUserRepo;
	@Autowired AppMenuRepo appMenuRepo;
	@Autowired AppLanguageRepo languageRepo;
	
	
	
	//-----------------------------------------------------------------------------------
	public void mylog(String logstr) {
		logger.info(logstr);
	}
	
	
	//-----------------------------------------------------------------------------------
	@Cacheable("cacheForMenu")
	List<AppMenu> getActiveMenuList() {
		return appMenuRepo.findByIsActive(true);
	}
	//-----------------------------------------------------------------------------------
	public List<AppMenu> getMenByParentMenuId(Long parentMenuId) {
		List<AppMenu> allMenuList=getActiveMenuList();
		
		List<AppMenu>  ret1=new ArrayList<AppMenu>();
		for (int i=0;i<allMenuList.size();i++) {
			AppMenu menu=allMenuList.get(i);
			if (menu.getParentMenuId()!=parentMenuId) continue;
			
			ret1.add(menu);
			

		}
		
		//order the menu items
		for (int m=0;m<ret1.size();m++) {
			for (int n=m+1;n<ret1.size();n++) {
				if (ret1.get(m).getMenuOrder()<=ret1.get(n).getMenuOrder()) continue;
				
				AppMenu tmp=ret1.get(m);
				ret1.set(m, ret1.get(n));
				ret1.set(n, tmp);
				
			}
			
		}
		
		return ret1;
	}


	public AppMenu getAppMenuByMenuId(Long activeMainMenuId) {
		return appMenuRepo.findOne(activeMainMenuId);
	}


	@Cacheable("cacheForLanguages")
	public List<AppLanguage> getLanguageList() {
		return languageRepo.findAll();
	}
	
	public Long getDefaultLanguageId() {
		List<AppLanguage> langList=getLanguageList();
		for (int i=0;i<langList.size();i++) {
			AppLanguage lang=langList.get(i);
			if (lang.isDefault()) return lang.getId();
		}
		return 0L;
	}


	public void setUserLanguage(Long currentUserId, Long newLanguageId) {
		AppUser currentUser=appUserRepo.findOne(currentUserId);
		if (currentUser==null) {
			mylog("User not found with id : "+currentUserId);
			return;
		}
		
		currentUser.setActiveLanguageId(newLanguageId);
		
		appUserRepo.save(currentUser);
	}


	
	//-----------------------------------------------------------------------------------
}
