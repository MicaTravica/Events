package com.app.events.serviceimpl;

import java.util.Collection;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.events.exception.ResourceExistsException;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Place;
import com.app.events.repository.PlaceRepository;
import com.app.events.service.PlaceService;

@Service
public class PlaceServiceImpl implements PlaceService{

	@Autowired
    private PlaceRepository placeRepository;

	@Override
	public Collection<Place> findAll(){
		return this.placeRepository.findAll();
	}
	
    @Override
    public Place findOne(Long id) throws ResourceNotFoundException {
        return this.placeRepository.findById(id)
                    .orElseThrow(
                        ()-> new ResourceNotFoundException("Place")
                    ); 
    }

    @Override
    public Place create(Place place) throws Exception{
        if(place.getId() != null){
            throw new ResourceExistsException("Place");
        }
        this.coordinatesCheckReserved(place);
        return this.placeRepository.save(place);
    }

    @Override
    public Place update(Place place) throws Exception{
        Place placeToUpdate = this.findOne(place.getId());
        this.setPlaceFields(placeToUpdate, place);
        this.coordinatesCheckReserved(placeToUpdate);	    
	    return this.placeRepository.save(placeToUpdate);
    }

    @Override
    public void delete(Long id) {
        this.placeRepository.deleteById(id);
    }

    public void coordinatesCheckReserved(Place place) throws ResourceExistsException
    {
        Optional<Place> optPlace = placeRepository.findByCoordinates(place.getLongitude(), place.getLatitude());
        if(optPlace.isPresent() && optPlace.get().getId() != place.getId()){
            throw new ResourceExistsException("Place");
        }
    }

    public Place setPlaceFields(Place placeToUpdate, Place place)
    {
	    placeToUpdate.setName(place.getName());
	    placeToUpdate.setAddress(place.getAddress());
	    placeToUpdate.setLatitude(place.getLatitude());
        placeToUpdate.setLongitude(place.getLongitude());
        return placeToUpdate;
    }


}


