package com.app.events.repository;

import java.util.Collection;

import com.app.events.model.Hall;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface HallRepository extends JpaRepository<Hall, Long> {

	Collection<Hall> findAllByEventsId(Long id);

	@Query("SELECT hall from Hall hall WHERE hall.place.id = ?1")
	Collection<Hall> findAllByPlaceId(Long id);

}
