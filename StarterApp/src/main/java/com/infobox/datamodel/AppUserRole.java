package com.infobox.datamodel;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.GeneratedValue;
import javax.persistence.Id;

public class AppUserRole {
	@Id
	@GeneratedValue
	Long id;
	Long userId;
	Long roleId;
	
	//--------------------------------------------
	Long createdBy;
	Date createdAt=Calendar.getInstance().getTime();
	Long updatedBy;
	Date updatedAt=Calendar.getInstance().getTime();
}
