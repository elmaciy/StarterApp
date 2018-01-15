package com.infobox.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infobox.datamodel.AppLanguage;

public interface AppLanguageRepo extends JpaRepository<AppLanguage, Long> {
	
}
