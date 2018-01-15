package com.infobox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infobox.datamodel.AppUser;

public interface AppUserRepo extends JpaRepository<AppUser, Long> {

}
