package com.infobox.datamodel;

import java.util.Calendar;

import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;
@Entity
@Data
public class AppLanguage {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	@Column(nullable=false, length=50)
	String languageName;
	@Column(nullable=false, length=10)
	String shortCode;
	boolean isDefault=false;

	
	//--------------------------------------------------
	Long  statusId;
	Long createdBy;
	Date createdAt=Calendar.getInstance().getTime();
	Long updatedBy;
	Date updatedAt=Calendar.getInstance().getTime();


	
}
