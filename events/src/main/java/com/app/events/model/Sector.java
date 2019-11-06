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
	private String name;
	
	private int sectorRows;
	private int sectorColumns;
	
	@ManyToOne
	@JoinColumn(name="hall_id", referencedColumnName="id")
	private Hall hall = new Hall();
	
	@OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<PriceList> priceLists = new HashSet<PriceList>();
	
	@OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<SectorCapacity> sectorCapacities = new HashSet<SectorCapacity>();
	
	@OneToMany(mappedBy = "id", fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	private Set<Seat> seats = new HashSet<Seat>();

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
}
