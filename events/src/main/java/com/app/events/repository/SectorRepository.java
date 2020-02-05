package com.app.events.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.events.model.Sector;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SectorRepository extends JpaRepository<Sector, Long> {

    @Query("SELECT sector from Sector sector WHERE sector.hall.id = ?1")
	Collection<Sector> findAllByHallId(Long id);

	@Query("SELECT s FROM Sector s LEFT JOIN PriceList pl ON pl.sector.id = s.id WHERE s.hall.id = ?1 AND pl.event.id = ?2")
	public Collection<Sector> findAllByHallIdAndEventId(Long hallId, Long eventId);

	@Query("SELECT sector from Sector sector WHERE sector.hall.id = ?1")
	public Collection<Sector> findAllByHallId(Long id);
	
}
