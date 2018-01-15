package com.infobox.datamodel;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Basic;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;

import lombok.Data;

@Entity
@Data
public class AppWordLanguage {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	Long wordId;
	Long languageId;
	String word;
	@Lob	@Basic
	String description;

	//----------------------------------------
	Long statusId;
	Long createdBy;
	Date createdAt=Calendar.getInstance().getTime();
	Long updatedBy;
	Date updatedAt=Calendar.getInstance().getTime();
}
