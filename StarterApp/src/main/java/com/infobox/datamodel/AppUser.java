package com.infobox.datamodel;

import java.util.Calendar;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

import org.hibernate.annotations.Index;

import lombok.Data;

@SuppressWarnings("deprecation")
@Entity
@Data
public class AppUser {
	@Id
	@GeneratedValue
	Long id;
	@Column(length=100, nullable=false)
	String firstName;
	@Column(length=100, nullable=false)
	String lastName;
	@Column(length=100, nullable=false)
	@Index(name="AppUserNdxUsername")
	String username;
	@Column(length=100, nullable=false)
	String password;
	boolean isAdmin=false;
	boolean isValid=true;
	boolean isExpired=false;
	Date passwordExpirationDate;
	@Column(length=100)
	String password_old1;
	@Column(length=100)
	String password_old2;
	@Column(length=100)
	String password_old3;
	Long activeLanguageId;
	boolean isLocked=false;
	int invalidAttemptCount=0;
	String userAttribute1;
	String userAttribute2;
	String userAttribute3;
	String userAttribute4;
	String userAttribute5;
	String userAttribute6;
	String userAttribute7;
	String userAttribute8;
	
	
	//--------------------------------------------
	Long createdBy;
	Date createdAt=Calendar.getInstance().getTime();
	Long updatedBy;
	Date updatedAt=Calendar.getInstance().getTime();
	

	
}
