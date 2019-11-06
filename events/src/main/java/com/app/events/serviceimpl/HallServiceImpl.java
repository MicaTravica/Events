package com.app.events.serviceimpl;

import java.util.Collection;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.events.model.Hall;
import com.app.events.repository.HallRepository;
import com.app.events.service.HallService;

@Service
public class HallServiceImpl implements HallService {
	
	@Autowired
    private HallRepository hallRepository;

	
	@Override
	public Collection<Hall> findAll(){
		Collection<Hall> halls = this.hallRepository.findAll();
		return halls;
	}
	
	
    @Override
    public Hall findOne(Long id) {
        Hall hall = this.hallRepository.findById(id).get();

        return hall;
    }

    @Override
    public Hall create(Hall hall) {
        if(hall.getId() != null){
            throw new RuntimeException("Hall already exists and has ID.");
        }
        Hall savedHall = this.hallRepository.save(hall);
        
        return savedHall;
       
    }

    @Override
    public Hall update(Hall hall) {
        Hall hallToUpdate = this.hallRepository.findById(hall.getId()).get();
	    if (hallToUpdate == null) { 
	    	throw new RuntimeException("Not found hall with this ID."); 
        }	    

	    hallToUpdate.setName(hall.getName());
	    
	    Hall updatedHall = this.hallRepository.save(hallToUpdate);
	    
	    return updatedHall;
	    

    }

    @Override
    public void delete(Long id) {
        this.hallRepository.deleteById(id);
    }



}





