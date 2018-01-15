package com.infobox.datamodel;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

import lombok.Data;

@Entity
@Data
public class AppWord {
	@Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
	String wordCode;
	String description;

	//----------------------------------------
	Long statusId;
	Long createdBy;
	Date createdAt=Calendar.getInstance().getTime();
	Long updatedBy;
	Date updatedAt=Calendar.getInstance().getTime();
}
