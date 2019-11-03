package com.app.events.dto;

import com.app.events.model.Seat;
import com.app.events.model.Sector;

public class SeatDTO {

	private Long id;
	private int seatRow;
	private int seatColumn;

	// private SectorDTO sector;

	public SeatDTO(Seat seat) {
		this.id = seat.getId();
		this.seatRow = seat.getSeatRow();
		this.seatColumn = seat.getSeatColumn();
		// this.sector = new SectorDTO(seat.getSector());
	}
	
	public Seat toSeat() {
		Seat seat = new Seat();
		
		seat.setId(this.getId());
		seat.setSeatRow(this.getSeatRow());
		seat.setSeatColumn(this.getSeatColumn());
		//seat.setSector(this.getSector().toSector());

		return seat;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public int getSeatRow() {
		return seatRow;
	}

	public void setSeatRow(int seatRow) {
		this.seatRow = seatRow;
	}

	public int getSeatColumn() {
		return seatColumn;
	}

	public void setSeatColumn(int seatColumn) {
		this.seatColumn = seatColumn;
	}

//	public int getSector() {
//		return sector;
//	}
//
//	public void setSector(int sector) {
//		this.sector = sector;
//	}
	
	

}
