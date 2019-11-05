package com.app.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.events.model.Event;


public interface EventRepository extends JpaRepository<Event, Long> {

}
