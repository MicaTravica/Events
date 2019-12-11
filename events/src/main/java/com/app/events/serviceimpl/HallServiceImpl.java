package com.app.events.serviceimpl;


import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.events.exception.ResourceExistsException;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Hall;
import com.app.events.model.Place;
import com.app.events.repository.HallRepository;
import com.app.events.service.HallService;
import com.app.events.service.PlaceService;

@Service
public class HallServiceImpl implements HallService {
	
	@Autowired
    private HallRepository hallRepository;

    @Autowired
    private PlaceService placeService;

	
    @Override
    public Hall findOne(Long id) throws ResourceNotFoundException {
        return this.hallRepository.findById(id)
                    .orElseThrow(
                        ()-> new ResourceNotFoundException("Hall")
                    ); 
    }

    @Override
    public Hall create(Hall hall) throws Exception{
        if(hall.getId() != null){
            throw new ResourceExistsException("Hall");
        }
        Place place = placeService.findOne(hall.getPlace().getId());
        hall.setPlace(place);
        return this.hallRepository.save(hall);       
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
		return hallRepository.findAllByEvents(id);
	}

}





