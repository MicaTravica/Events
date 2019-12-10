package com.app.events.mapper;

import java.util.HashSet;

import com.app.events.dto.SectorDTO;
import com.app.events.model.Hall;
import com.app.events.model.Sector;
import com.app.events.model.SectorCapacity;
import com.app.events.model.Ticket;

public class SectorMapper {

    public static SectorDTO toDTO(Sector sector) {
        return new SectorDTO(sector.getId(), sector.getName(), 
                            sector.getSectorRows(), sector.getSectorRows(),
                            0, sector.getHall().getId()  
                        );
    }
    
    public static Sector toSector(SectorDTO sectorDTO){
        Sector sector = new Sector(
                            sectorDTO.getId(), 
                            sectorDTO.getName(),
                            sectorDTO.getSectorRows(), 
                            sectorDTO.getSectorColumns(),
                            new Hall(sectorDTO.getHallId()),
                            new HashSet<>(),
                            null,
                            new HashSet<>()
                        );
                        
        HashSet<SectorCapacity> sc = new HashSet<>();
        if(sectorDTO.getSectorCapacity() !=0)
        {
            sc.add(
                new SectorCapacity(null, new HashSet<Ticket>(), sector,
                    sectorDTO.getSectorCapacity(),sectorDTO.getSectorCapacity()
                ));
        }
        sector.setSectorCapacities(sc);
		return sector;
    }

}
