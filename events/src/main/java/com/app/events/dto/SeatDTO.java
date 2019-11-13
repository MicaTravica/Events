package com.app.events.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SeatDTO {

	private Long id;
	private int seatRow;
	private int seatColumn;
	private SectorDTO sector;
}
