package com.app.events.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.events.dto.PlaceDTO;
import com.app.events.model.Place;
import com.app.events.repository.PlaceRepository;
import com.app.events.service.PlaceService;

@Service
public class PlaceServiceImpl implements PlaceService{

	@Autowired
    private PlaceRepository placeRepository;

    @Override
    public PlaceDTO findOne(Long id) {
        Place place = this.placeRepository.findById(id).get();
        PlaceDTO placeDTO = new PlaceDTO(place);

        return placeDTO;
    }

    @Override
    public PlaceDTO create(Place place) {
        if(place.getId() != null){
            throw new RuntimeException("Place already exists and has ID.");
        }
        Place savedPlace = this.placeRepository.save(place);
        PlaceDTO placeSavedDTO = new PlaceDTO(savedPlace);
        
        return placeSavedDTO;
       
    }

    @Override
    public PlaceDTO update(Place place) {
        Place placeToUpdate = this.placeRepository.findById(place.getId()).get();
	    if (placeToUpdate == null) { 
	    	throw new RuntimeException("Not found place with this ID."); 
        }	    

	    placeToUpdate.setName(place.getName());
	    placeToUpdate.setAddress(place.getAddress());
	    placeToUpdate.setLatitude(place.getLatitude());
	    placeToUpdate.setLongitude(place.getLongitude());
	    
	   Place updatedPlace = this.placeRepository.save(placeToUpdate);
	   PlaceDTO updatedPlaceDTO = new PlaceDTO(updatedPlace);
	   
	   return updatedPlaceDTO;
	    
	    


    }

    @Override
    public void delete(Long id) {
        this.placeRepository.deleteById(id);
    }



}


