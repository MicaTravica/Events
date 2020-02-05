package com.app.events.serviceimpl;


import java.util.Collection;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.events.exception.ResourceExistsException;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Hall;
import com.app.events.model.Place;
import com.app.events.model.Sector;
import com.app.events.repository.HallRepository;
import com.app.events.service.HallService;
import com.app.events.service.PlaceService;
import com.app.events.service.SectorService;

@Service
public class HallServiceImpl implements HallService {
	
	@Autowired
    private HallRepository hallRepository;

    @Autowired
    private PlaceService placeService;
    
    @Autowired
    private SectorService sectorService;

    @Override
    public Hall findOne(Long id) throws ResourceNotFoundException {
    	return this.hallRepository.findById(id).orElseThrow(
    			()-> new ResourceNotFoundException("Hall")
        ); 
    }
    
    @Override
  	public Hall findOneAndLoadSectors(Long id) throws ResourceNotFoundException {
  		Hall hall = this.hallRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Hall"));
  		Set<Sector> sectors = this.sectorService.getSectorsByHallId(id).stream().collect(Collectors.toSet());
  		hall.setSectors(sectors);
  		return hall;
  	}
      
    @Override
    public Hall create(Hall hall) throws Exception{
        if(hall.getId() != null){ 
            throw new ResourceExistsException("Hall");
        }
        Place place = placeService.findOne(hall.getPlace().getId());
        hall.setPlace(place);
        Hall newHall = new Hall();
        newHall.setName(hall.getName());
        newHall.setPlace(place);
        Hall sh = this.hallRepository.save(newHall);
        for(Sector s: hall.getSectors()) {
        	s.setHall(sh);
        	sectorService.create(s);
        }
        return sh;
    }

    @Override
    public Hall update(Hall hall) throws Exception{
        Hall hallToUpdate = this.findOne(hall.getId());
        Place newPlace = placeService.findOne(hall.getPlace().getId());
        hallToUpdate.setPlace(newPlace);
	    hallToUpdate.setName(hall.getName());
	    return this.hallRepository.save(hallToUpdate);
    }


    @Override
    public void delete(Long id) {
        this.hallRepository.deleteById(id);
    }

	@Override
	public Collection<Hall> findHallByEventId(Long id) {
		return hallRepository.findAllByEventsId(id);
	}

    @Override
	public Collection<Hall> getHallsByPlaceId(Long id) {
		return hallRepository.findAllByPlaceId(id);
	}
}





