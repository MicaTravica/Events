package com.app.events.repository;

import java.util.Date;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.events.model.Event;

public interface EventRepository extends JpaRepository<Event, Long> {
	@Query("SELECT CASE WHEN (COUNT(e) > 0) THEN true ELSE false END FROM Event e inner join e.halls h where h.id = :id and (e.fromDate between :fromDate and :toDate or e.toDate between :fromDate and :toDate)")
	boolean hallHaveEvent(Long id, Date fromDate, Date toDate);

	@Query("SELECT case WHEN (count(e) > 0) then true else false end from Event e inner join e.halls h where e.id <> :id2 and h.id = :id and (e.fromDate between :fromDate and :toDate or e.toDate between :fromDate and :toDate)")
	boolean hallHaveEventUpdate(Long id, Date fromDate, Date toDate, Long id2);

}
