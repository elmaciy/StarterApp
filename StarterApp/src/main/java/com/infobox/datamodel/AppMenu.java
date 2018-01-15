package com.infobox.datamodel;


import lombok.Data;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;


@Entity
@Data
public class AppMenu {
	
	private AppMenu() {}
	
	@Id
	@GeneratedValue
	Long id;
	String menuIcon;
	String menuLabel;
	Long menuLabelWordId=0L;
	String menuAction;
	String actionDetails;
	String target;
	boolean isActive;
	Long parentMenuId=0L;
	int menuOrder=100;
	Long requredPermissionId;
	//--------------------------------------------
	Long createdBy;
	Date createdAt=Calendar.getInstance().getTime();
	Long updatedBy;
	Date updatedAt=Calendar.getInstance().getTime();
}
