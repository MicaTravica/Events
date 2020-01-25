package com.app.events.repository;

import java.util.Collection;

import com.app.events.model.Sector;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SectorRepository extends JpaRepository<Sector, Long> {

    @Query("SELECT sector from Sector sector WHERE sector.hall.id = ?1")
	Collection<Sector> findAllByHallId(Long id);

}
