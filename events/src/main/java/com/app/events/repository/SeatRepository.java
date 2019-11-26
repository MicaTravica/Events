package com.app.events.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.Optional;

import com.app.events.model.Seat;

@Repository
public interface SeatRepository extends JpaRepository<Seat, Long> {

    @Query("SELECT seat FROM Seat seat WHERE seat.sector.id = ?3 AND seat.seatColumn = ?1 AND seat.seatRow = ?2")
	Optional<Seat> findSeatByAllParams(int seatColumn, int seatRow, Long sectorId);


	Collection<Seat> findSeatBySectorId(Long id);

}
