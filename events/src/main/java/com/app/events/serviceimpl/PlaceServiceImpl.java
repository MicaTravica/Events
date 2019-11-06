package com.app.events.serviceimpl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.events.model.Place;
import com.app.events.repository.PlaceRepository;
import com.app.events.service.PlaceService;

@Service
public class PlaceServiceImpl implements PlaceService{

	@Autowired
    private PlaceRepository placeRepository;

	@Override
	public Collection<Place> findAll(){
		Collection<Place> places = this.placeRepository.findAll();
		return places;
	}
	
	
    @Override
    public Place findOne(Long id) {
        Place place = this.placeRepository.findById(id).get();

        return place;
    }

    @Override
    public Place create(Place place) {
        if(place.getId() != null){
            throw new RuntimeException("Place already exists and has ID.");
        }
        Place savedPlace = this.placeRepository.save(place);
        
        return savedPlace;
       
    }

    @Override
    public Place update(Place place) {
        Place placeToUpdate = this.placeRepository.findById(place.getId()).get();
	    if (placeToUpdate == null) { 
	    	throw new RuntimeException("Not found place with this ID."); 
        }	    

	    placeToUpdate.setName(place.getName());
	    placeToUpdate.setAddress(place.getAddress());
	    placeToUpdate.setLatitude(place.getLatitude());
	    placeToUpdate.setLongitude(place.getLongitude());
	    
	   Place updatedPlace = this.placeRepository.save(placeToUpdate);
	   
	   return updatedPlace;

    }

    @Override
    public void delete(Long id) {
        this.placeRepository.deleteById(id);
    }



}


