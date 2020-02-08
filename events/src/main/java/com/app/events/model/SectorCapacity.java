package com.app.events.model;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
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
public class SectorCapacity {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Ticket> tickets;
	
	@ManyToOne
	@JoinColumn(name="sector_id", referencedColumnName="id")
	@NotNull(message = "SectorCapacity must be asociated with Sector")
	private Sector sector;
	
	@PositiveOrZero(message ="capacity must be positive number or zero")
	private int capacity;

	@PositiveOrZero(message ="free must be positive number or zero")
	private int free;
	
	public SectorCapacity(Long id) {
		super();
		this.id = id;
	}

	public SectorCapacity(Long id, @NotNull(message = "SectorCapacity must be asociated with Sector") Sector sector,
			@PositiveOrZero(message = "capacity must be positive number or zero") int capacity,
			@PositiveOrZero(message = "free must be positive number or zero") int free) {
		super();
		this.id = id;
		this.sector = sector;
		this.capacity = capacity;
		this.free = free;
	}
	
}
