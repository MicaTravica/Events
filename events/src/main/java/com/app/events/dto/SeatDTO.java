package com.app.events.dto;

import com.app.events.mapper.SectorMapper;
import com.app.events.model.Seat;

public class SeatDTO {

	private Long id;
	private int seatRow;
	private int seatColumn;
	private SectorDTO sector;

	public SeatDTO(Seat seat) {
		this.id = seat.getId();
		this.seatRow = seat.getSeatRow();
		this.seatColumn = seat.getSeatColumn();
		this.sector = SectorMapper.toDTO(seat.getSector());
	}
	
	public Seat toSeat() {
		Seat seat = new Seat();
		
		seat.setId(this.getId());
		seat.setSeatRow(this.getSeatRow());
		seat.setSeatColumn(this.getSeatColumn());
		seat.setSector(SectorMapper.toSector(this.getSector()));

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

	public SectorDTO getSector() {
		return sector;
	}
//
//	public void setSector(int sector) {
//		this.sector = sector;
//	}
	
	

}
