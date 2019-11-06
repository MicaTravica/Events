package com.app.events.dto;

import com.app.events.model.Hall;
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

	public SectorDTO(Sector sector) {
		this.id = sector.getId();
		this.name = sector.getName();
		this.sectorRows = sector.getSectorRows();
		this.sectorColumns = sector.getSectorColumns(); 
		this.hall = this.getHallInfo(sector.getHall());
	}

	public Sector toSimpleSector() {
		return id != 0 ?
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

	public Sector toSector(){
		Sector sector = new Sector( this.getId(), 
									this.getName(),
									this.getSectorRows(), 
									this.getSectorColumns()
								);
		sector.setHall(new Hall(this.hall.getId()));
		return sector;
	}

	public HallDTO getHallInfo(Hall hall)
	{
		return hall != null ?
			new HallDTO(hall.getId(), 
						hall.getName()
					)
			: null;
	}

}
