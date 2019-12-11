package com.app.events.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.events.model.Hall;

public interface HallRepository extends JpaRepository<Hall, Long> {

	Collection<Hall> findAllByEvents(Long id);

}
