package com.app.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.events.model.Place;

public interface PlaceRepository extends JpaRepository<Place, Long> {

}
