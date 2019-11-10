package com.app.events.mapper;

import com.app.events.dto.SectorDTO;
import com.app.events.model.Hall;
import com.app.events.model.Sector;
import org.springframework.stereotype.Service;

@Service
public class SectorMapper {

    public SectorDTO toDTO(Sector sector) {
        return new SectorDTO(sector);
    }
    
    public Sector toSector(SectorDTO sectorDTO){
		Sector sector = new Sector( sectorDTO.getId(), 
                        sectorDTO.getName(),
                        sectorDTO.getSectorRows(), 
                        sectorDTO.getSectorColumns()
                    );
        sector.setHall(new Hall(sectorDTO.getHall().getId()));
        return sector;
    }

}
