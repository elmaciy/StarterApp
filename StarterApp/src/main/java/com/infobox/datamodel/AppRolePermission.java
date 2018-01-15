package com.infobox.datamodel;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class AppRolePermission {
	@Id
	@GeneratedValue
	Long id;
	Long roleId;
	Long permissionId;
	
	
	//--------------------------------------------
	Long createdBy;
	Date createdAt=Calendar.getInstance().getTime();
	Long updatedBy;
	Date updatedAt=Calendar.getInstance().getTime();
	

}
