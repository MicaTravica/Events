package com.app.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Collection;

import com.app.events.model.Sector;

public interface SectorRepository extends JpaRepository<Sector, Long> {

    public Collection<Sector> findAllByHallId(Long id);
}
