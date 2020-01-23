package com.app.events.repository;

import java.util.Collection;

import java.util.Date;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.events.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

	@Query("SELECT CASE WHEN (COUNT(e) > 0) THEN true ELSE false END FROM Ticket t inner join t.event e where e.id = ?1 and t.ticketState = 'BOUGHT' ")
	boolean ticketForEventIsSale(Long id);

	Collection<Ticket> findAllByEventId(Long id);

	@Query("SELECT t FROM Ticket t inner join t.user u where u.username = ?1 and t.ticketState = 'RESERVED'")
	Collection<Ticket> findAllReservationsByUserId(String username);

	@Query("SELECT t FROM Ticket t inner join t.user u where u.username = ?1 and t.ticketState = 'BOUGHT'")
	Page<Ticket> findAllTicketsByUserId(String username, Pageable pageable);

	@Query("SELECT t FROM Ticket t WHERE t.fromDate >= ?2 AND t.toDate <= ?3 AND t.event.id = ?1")
	Collection<Ticket> findTicketsByDatesAndEventId(Long eventId, Date fromDate, Date toDate);

	@Query("SELECT t FROM Ticket t WHERE t.toDate <= ?1 AND t.toDate >= ?2 AND t.ticketState = 'RESERVED'")
	Collection<Ticket> findAllForNotification(Date date, Date currentDate);

	@Query("SELECT SUM(t.price) FROM Ticket t where t.event.id = ?1 AND t.ticketState = 'BOUGHT'")
	Double findProfitByEventId(Long eventId);

	@Query("SELECT SUM(t.price) FROM Ticket t INNER JOIN t.event e INNER JOIN e.halls h INNER JOIN h.place p WHERE p.id = ?1 "
			+ "AND e.eventState = 'FINISHED' AND t.fromDate >= ?2 AND t.fromDate < ?3 AND t.ticketState = 'BOUGHT'")
	Double findProfitByTime(Long placeId, Date fromDate, Date toDate);
}
