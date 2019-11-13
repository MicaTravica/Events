package com.app.events.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.events.model.Event;


public interface EventRepository extends JpaRepository<Event, Long> {

	@Query("SELECT case WHEN (count(e) > 0) then true else false end from Event e left join Hall h where h.id = :id and (e.fromDate between :fromDate and :toDate or e.toDate between :fromDate and :toDate)")
	boolean hallHaveEvent(Long id, Date fromDate, Date toDate);

	@Query("SELECT case WHEN (count(e) > 0) then true else false end from Event e left join Hall h where e.id <> :id2 and h.id = :id and (e.fromDate between :fromDate and :toDate or e.toDate between :fromDate and :toDate)")
	boolean hallHaveEventUpdate(Long id, Date fromDate, Date toDate, Long id2);

}
