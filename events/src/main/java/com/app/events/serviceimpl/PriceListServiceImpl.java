package com.app.events.serviceimpl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.app.events.exception.ResourceExistsException;
import com.app.events.exception.ResourceNotFoundException;
import com.app.events.model.Event;
import com.app.events.model.PriceList;
import com.app.events.model.Sector;
import com.app.events.repository.PriceListRepository;
import com.app.events.service.EventService;
import com.app.events.service.PriceListService;
import com.app.events.service.SectorService;


@Service
public class PriceListServiceImpl implements PriceListService {

    @Autowired
    private PriceListRepository priceListRepository;

    @Autowired
    private SectorService sectorService;

    @Autowired
    private EventService eventService;

    @Override
    public PriceList findOne(Long id) throws ResourceNotFoundException{
        return this.priceListRepository.findById(id)
                    .orElseThrow(()-> new ResourceNotFoundException("PriceList"));
    }

    @Override
    public PriceList create(PriceList priceList) throws Exception {
        if(priceList.getId() != null){
            throw new ResourceExistsException("Price list");
        }
        Sector sector = sectorService.findOne(priceList.getSector().getId());
        priceList.setSector(sector);
        Event event = eventService.findOne(priceList.getEvent().getId());
        priceList.setEvent(event);
        return this.priceListRepository.save(priceList);
    }

    @Override
    public PriceList update(PriceList priceList) throws Exception {
        PriceList priceListToUpdate = this.findOne(priceList.getId());
        priceListToUpdate.setPrice(priceList.getPrice());
        return this.priceListRepository.save(priceListToUpdate);
    }

    @Override
    public void delete(Long id) {
        this.priceListRepository.deleteById(id);
    }

}
