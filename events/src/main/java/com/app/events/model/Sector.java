package com.app.events.model;

import java.util.HashSet;
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
import javax.validation.constraints.NotBlank;
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
public class Sector {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	@NotBlank(message = "name can not be empty string")
	private String name;
	
	@PositiveOrZero(message ="number of rows must be positive number or zero")
	private int sectorRows;
	
	@PositiveOrZero(message ="number of columns must be positive number or zero")
	private int sectorColumns;
	
	@NotNull(message = "sector must be asociated with hall")
	@ManyToOne
	@JoinColumn(name="hall_id", referencedColumnName="id")
	private Hall hall;
	
	@OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<PriceList> priceLists;
	
	@OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<SectorCapacity> sectorCapacities;
	
	@OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Seat> seats;

	public Sector(String name, int sectorRows, int sectorColumns){
		this.name = name;
		this.sectorRows = sectorRows;
		this.sectorColumns = sectorColumns;
		this.priceLists = new HashSet<PriceList>();
	}

	public Sector(Long id, String name, int sectorRows, int sectorColumns){
		this.id = id;
		this.name = name;
		this.sectorRows = sectorRows;
		this.sectorColumns = sectorColumns;
		this.priceLists = new HashSet<PriceList>();
	}

	public Sector(Long sectorId) {
		this.id = sectorId;
	}

	public Sector(Long id, String name, int sectorRows, int sectorColumns, Hall hall, int priceList) {
		this.id = id;
		this.name = name;
		this.sectorRows = sectorRows;
		this.sectorColumns = sectorColumns;
		this.hall = hall;
		this.priceLists = new HashSet<PriceList>();
	}
}
