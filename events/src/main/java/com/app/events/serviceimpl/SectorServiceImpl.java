package com.app.events.serviceimpl;

import java.util.Optional;

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
    public Sector findOne(Long id) {
        Optional<Sector> sectorOpt = this.sectorRepository.findById(id);
        return sectorOpt.isPresent() == true ? sectorOpt.get() : new Sector();
    }

    @Override
    public Sector create(SectorDTO sectorDTO) {
        if(sectorDTO.getId() != null){
            throw new RuntimeException("Sector already exists and has ID."); // custom exception here!
        }
        return null;
        // prebaci dto na obican....

        // sectorDTO.getPriceLists().forEach(list->list.setSector(null));
        // sectorDTO.getSectorCapacities()
        //     .forEach(list-> {
        //         list.setSector(sectorDTO);
        //         list.setFree(list.getCapacity());
        //         }
        //     );
        // return this.sectorRepository.save(sector);  
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
