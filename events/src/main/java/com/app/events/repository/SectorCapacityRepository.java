package com.app.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Set;

import com.app.events.model.SectorCapacity;

public interface SectorCapacityRepository extends JpaRepository<SectorCapacity, Long> {
    
    @Query("SELECT sectorCapacity from SectorCapacity sectorCapacity WHERE sectorCapacity.sector.id = ?1")
	Set<SectorCapacity> findBySectorId(Long id);

}
