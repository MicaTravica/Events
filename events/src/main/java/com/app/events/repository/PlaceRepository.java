package com.app.events.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

import com.app.events.model.Place;

public interface PlaceRepository extends JpaRepository<Place, Long> {
    
    @Query("SELECT p from Place p WHERE p.name like concat('%',?1,'%') AND p.address like concat('%',?2,'%')")
    Page<Place> search(String name, String address, Pageable pageable);

    @Query("SELECT place from Place place WHERE place.address = ?1")
	Optional<Place> findByAddress(String address);

  

}
