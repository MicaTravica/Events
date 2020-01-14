package com.app.events.repository;

import java.util.Collection;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.app.events.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

	@Query("SELECT CASE WHEN (COUNT(e) > 0) THEN true ELSE false END FROM Ticket t inner join t.event e where e.id = ?1 and t.ticketState = 'BOUGHT' ")
	boolean ticketForEventIsSale(Long id);

	Collection<Ticket> findAllByEventId(Long id);

	@Query("SELECT t FROM Ticket t inner join t.user u where u.id = ?1 and t.ticketState = 'RESERVED'")
	Collection<Ticket> findAllReservationsByUserId(Long userId);

}
