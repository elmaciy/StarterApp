package com.infobox.datamodel;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class AppRole {
	@Id
	@GeneratedValue
	Long id;
	@Column(length=100, nullable=false)
	String roleName;
	
	//--------------------------------------------
	Long createdBy;
	Date createdAt=Calendar.getInstance().getTime();
	Long updatedBy;
	Date updatedAt=Calendar.getInstance().getTime();
}
