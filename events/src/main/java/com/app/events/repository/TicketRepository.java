package com.app.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.app.events.model.Ticket;

public interface TicketRepository extends JpaRepository<Ticket, Long> {

}
