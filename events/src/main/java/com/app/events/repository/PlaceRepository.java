package com.app.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

import com.app.events.model.Place;

public interface PlaceRepository extends JpaRepository<Place, Long> {

    @Query("SELECT place from Place place WHERE place.longitude = ?1 AND place.latitude = ?2")
	Optional<Place> findByCoordinates(double longitude, double latitude);

}
