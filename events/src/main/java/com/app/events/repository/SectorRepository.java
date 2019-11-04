package com.app.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.events.model.Sector;

public interface SectorRepository extends JpaRepository<Sector, Long> {

}
