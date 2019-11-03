package com.app.events.serviceimpl;

import com.app.events.dto.SectorDTO;
import com.app.events.model.Sector;
import com.app.events.repository.PriceListRepository;
import com.app.events.repository.SectorCapacityRepository;
import com.app.events.repository.SectorRepository;
import com.app.events.service.SectorService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SectorServiceImpl implements SectorService {

    @Autowired
    private SectorRepository sectorRepository;

    @Autowired
    private PriceListRepository priceListRepository;

    @Autowired
    private SectorCapacityRepository sectorCapacityRepository;

    @Override
    public SectorDTO findOne(Long id) {
        Sector sector = this.sectorRepository.findById(id).get();
        sector.setPriceLists(priceListRepository.findBySectorId(id));
        sector.setSectorCapacities(sectorCapacityRepository.findBySectorId(id));
        SectorDTO sectorDTO = new SectorDTO(sector);

        return sectorDTO;
    }

    @Override
    public SectorDTO create(Sector sector) {
        if(sector.getId() != null){
            throw new RuntimeException("Sector already exists and has ID."); // custom exception here!
        }
        sector.getPriceLists().forEach(list->list.setSector(sector));
        sector.getSectorCapacities()
            .forEach(list-> {
                list.setSector(sector);
                list.setFree(list.getCapacity());
                }
            );
        Sector savedSector = this.sectorRepository.save(sector);
        SectorDTO sectorDTO = new SectorDTO(savedSector);
        
        return sectorDTO;
    }

    @Override
    public SectorDTO update(Sector sector) {
        Sector sectorToUpdate = this.sectorRepository.findById(sector.getId()).get();
	    if (sectorToUpdate == null) { 
	    	throw new RuntimeException("Not found."); // custom exception here!
	    }	    
	    sectorToUpdate.setHall(sector.getHall());
	    sectorToUpdate.setName(sector.getName());
        sectorToUpdate.setPriceLists(sector.getPriceLists());
        sectorToUpdate.setSeats(sector.getSeats());
        sectorToUpdate.setSectorCapacities(sector.getSectorCapacities());
        sectorToUpdate.setSectorColumns(sector.getSectorColumns());
        sectorToUpdate.setSectorRows(sector.getSectorRows());

	    Sector updatedSeat = this.sectorRepository.save(sectorToUpdate);
	    SectorDTO updatedSeatDTO = new SectorDTO(updatedSeat);
	        
	    return updatedSeatDTO;
    }

    @Override
    public void delete(Long id) {
        this.sectorRepository.deleteById(id);
    }

}
