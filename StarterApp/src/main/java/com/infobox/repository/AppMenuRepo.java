package com.infobox.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.infobox.datamodel.AppMenu;

public interface AppMenuRepo extends JpaRepository<AppMenu, Long> {
	public List<AppMenu> findByIsActive(boolean isActive);
}
