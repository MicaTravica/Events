package com.app.events.serviceimpl;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.app.events.exception.ResourceExistsException;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Hall;
import com.app.events.model.Place;
import com.app.events.model.SearchParamsPlace;
import com.app.events.repository.PlaceRepository;
import com.app.events.service.HallService;
import com.app.events.service.PlaceService;

@Service
public class PlaceServiceImpl implements PlaceService{

	@Autowired
    private PlaceRepository placeRepository;
	
	@Autowired
	private HallService hallService;
	
    @Override
    public List<Place> findAll() {
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
	public Place findOneAndLoadHalls(Long id) throws ResourceNotFoundException {
		Place place = this.placeRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Place"));
		Set<Hall> halls = this.hallService.getHallsByPlaceId(id).stream().collect(Collectors.toSet());
		place.setHalls(halls);
		return place;
	}
   
    @Override
    public Place create(Place place) throws Exception{
        this.checkAddress(place);
        return this.placeRepository.save(place);
    }

  
    private void checkAddress(Place place) throws ResourceExistsException {
    	Optional<Place> optPlace = placeRepository.findByAddress(place.getAddress());
        if(optPlace.isPresent()){
            throw new ResourceExistsException("Place");
        }		 
	}
    
    private void checkAddressupdate(Place place) throws ResourceExistsException {
    	Optional<Place> optPlace = placeRepository.findByAddress(place.getAddress());
        if(optPlace.isPresent() && optPlace.get().getId() != place.getId()){
            throw new ResourceExistsException("Place");
        }		
	}

	@Override
    public Place update(Place place) throws Exception{
        Place placeToUpdate = this.findOne(place.getId());
        this.setPlaceFields(placeToUpdate, place);
        this.checkAddressupdate(placeToUpdate);	    
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

	
	
	@Override
	public Page<Place> search(SearchParamsPlace params) {
		if (params.getSortBy().equals("")) {
			params.setSortBy("name");
		}
		Pageable pageable;
		if(params.isAscending()) {
			pageable = PageRequest.of(params.getNumOfPage(), params.getSizeOfPage(),
					Sort.by(params.getSortBy()).ascending());
		} else {
			pageable = PageRequest.of(params.getNumOfPage(), params.getSizeOfPage(),
					Sort.by(params.getSortBy()).descending());
		}
		Page<Place> found = placeRepository.search(params.getName(),params.getAddress(), pageable);
		return found;
	}
	
}


