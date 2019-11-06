package com.app.events.dto;

import java.util.HashSet;
import java.util.Set;
import com.app.events.model.Sector;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SectorDTO {

	private Long id;
	private String name;

	private int sectorRows;
	private int sectorColumns;

	private HallDTO hall;
	private Set<PriceListDTO> priceLists = new HashSet<PriceListDTO>();
	private Set<SectorCapacityDTO> sectorCapacities = new HashSet<SectorCapacityDTO>();
	private Set<SeatDTO> seats = new HashSet<SeatDTO>();;

	public SectorDTO(Sector sector) {
		this.id = sector.getId();
		this.name = sector.getName();
		this.sectorRows = sector.getSectorRows();
		this.sectorColumns = sector.getSectorColumns(); 
		this.hall = new HallDTO(sector.getHall());
		sector.getPriceLists().stream()
			.forEach(y-> this.priceLists.add(new PriceListDTO(y)));
		sector.getSectorCapacities()
			.forEach(x->this.sectorCapacities.add(new SectorCapacityDTO(x)));
		sector.getSeats().forEach(x->this.seats.add(new SeatDTO(x)));
	}

	public Sector toSector()
	{
		Sector sector = new Sector( this.getId(), 
									this.getName(),
									this.getSectorRows(), 
									this.getSectorColumns()
								);
		sector.setHall(this.getHall().toSimpleHall());
		this.getPriceLists()
			.forEach(priceList->{
				sector.getPriceLists().add(priceList.toPriceList());
			});
		this.getSectorCapacities()
			.forEach(sectorCapacity->{
				sector.getSectorCapacities().add(sectorCapacity.toSectorCapacity());
			});
		this.getSeats()
			.forEach(seat->{
				sector.getSeats().add(seat.toSeat());
			});

		return sector;
	}

	public Sector toSimpleSector() {
		return this != null ?
			new Sector( 
				this.getId(),
				this.getName(),
				this.getSectorRows(),
				this.getSectorColumns()
			)
		: null;
	}

	public static SectorDTO makeSimpleSectorDTO(Sector sector){
        return sector != null ? 
            new SectorDTO(
                new Sector(
                    sector.getId(),
                    sector.getName(),
                    sector.getSectorColumns(),
                    sector.getSectorRows()
                )
            )
            : null;
	}
}
