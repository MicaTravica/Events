package com.app.events.serviceimpl;

import com.app.events.dto.SectorCapacityDTO;
import com.app.events.model.SectorCapacity;
import com.app.events.repository.SectorCapacityRepository;
import com.app.events.service.SectorCapacityService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SectorCapacityServiceImpl implements SectorCapacityService {

    @Autowired
    private SectorCapacityRepository sectorCapacityRepository;

    @Override
    public SectorCapacityDTO findOne(Long id) {
        SectorCapacity sectorCapacity = this.sectorCapacityRepository.findById(id).get();
        SectorCapacityDTO sectorDTO = new SectorCapacityDTO(sectorCapacity);
        return sectorDTO;
    }

    @Override
    public SectorCapacityDTO create(SectorCapacity sector) {
        if(sector.getId() != null){
            throw new RuntimeException("SectorCapacity already exists and has ID."); // custom exception here!
        }
        sector.setFree(sector.getCapacity());
        SectorCapacity savedSector = this.sectorCapacityRepository.save(sector);
        SectorCapacityDTO sectorCapacityDTO = new SectorCapacityDTO(savedSector);
        
        return sectorCapacityDTO;
    }

    @Override
    public SectorCapacityDTO update(SectorCapacity sectorCapacity) {
        SectorCapacity sectorCapacityToUpdate = this.sectorCapacityRepository.findById(sectorCapacity.getId()).get();
	    if (sectorCapacityToUpdate == null) { 
	    	throw new RuntimeException("Not found."); // custom exception here!
        }	    
        
        sectorCapacityToUpdate.setSector(sectorCapacity.getSector());
        sectorCapacityToUpdate.setFree(sectorCapacity.getFree());
        sectorCapacityToUpdate.setCapacity(sectorCapacity.getCapacity());
        sectorCapacityToUpdate.setTickets(sectorCapacity.getTickets());

	    SectorCapacity updatedSeat = this.sectorCapacityRepository.save(sectorCapacityToUpdate);
	    SectorCapacityDTO updatedSeatDTO = new SectorCapacityDTO(updatedSeat);
	        
	    return updatedSeatDTO;
    }

    @Override
    public void delete(Long id) {
        this.sectorCapacityRepository.deleteById(id);
    }

}
