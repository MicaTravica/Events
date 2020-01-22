package com.app.events.repository;

import java.util.Collection;
import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.events.model.Event;
import com.app.events.model.EventState;
import com.app.events.model.EventType;

public interface EventRepository extends JpaRepository<Event, Long> {
	@Query("SELECT CASE WHEN (COUNT(e) > 0) THEN true ELSE false END FROM Event e "
			+ "INNER JOIN e.halls h WHERE h.id = :id AND (e.fromDate BETWEEN :fromDate "
			+ "AND :toDate OR e.toDate BETWEEN :fromDate AND :toDate)")
	boolean hallHaveEvent(Long id, Date fromDate, Date toDate);

	@Query("SELECT CASE WHEN (count(e) > 0) THEN true ELSE false END FROM Event e "
			+ "INNER JOIN e.halls h WHERE e.id <> :id2 AND h.id = :id AND "
			+ "(e.fromDate BETWEEN :fromDate AND :toDate OR e.toDate between :fromDate and :toDate)")
	boolean hallHaveEventUpdate(Long id, Date fromDate, Date toDate, Long id2);

	@Query("SELECT e FROM Event e INNER JOIN e.halls h INNER JOIN h.place p WHERE e.name like concat('%',?1,'%')"
			+ " and (e.fromDate >= ?2 or ?2 = null) and (e.toDate <= ?3 or ?3 = null) and"
			+ " (e.eventState = ?4 or ?4 = null) and (e.eventType = ?5 or ?5 = null) and (p.id = ?6 or ?6 = null)")
	Page<Event> search(String name, Date fromDate, Date toDate, EventState eventState, EventType eventType,
			Long placeId, Pageable pageable);

	@Query("SELECT e FROM Event e WHERE e.eventState != 'FINISHED'")
	Collection<Event> findAllNotFinished();
	
}
