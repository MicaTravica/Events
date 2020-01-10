package com.app.events.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Seat {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@PositiveOrZero(message ="Number of row must be positive number or zero")
	private int seatRow;
	
	@PositiveOrZero(message ="Number of row must be positive number or zero")
	private int seatColumn;
	
	@NotNull(message = "Seat must be asociated with sector")
	@ManyToOne
	@JoinColumn(name="sector_id", referencedColumnName="id")
	private Sector sector;
	
	public Seat(Long id, @PositiveOrZero(message = "Number of row must be positive number or zero") int seatRow,
			@PositiveOrZero(message = "Number of row must be positive number or zero") int seatColumn) {
		super();
		this.id = id;
		this.seatRow = seatRow;
		this.seatColumn = seatColumn;
	}

	public Seat(Long id) {
		super();
		this.id = id;
	}
}
