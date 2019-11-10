package com.app.events.dto;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

import com.app.events.mapper.SectorMapper;
import com.app.events.model.SectorCapacity;

import org.springframework.beans.factory.annotation.Autowired;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SectorCapacityDTO {

	@Autowired
	private SectorMapper sectorMapper;

    private Long id;
    private Set<TicketDTO> tickets = new HashSet<>();
    private SectorDTO sector;
    private int capacity;
	private int free;

	public SectorCapacityDTO(SectorCapacity sectorCapacity) {
        this.id = sectorCapacity.getId();
        this.capacity = sectorCapacity.getCapacity();
        this.free = sectorCapacity.getFree();
        this.sector = sectorMapper.toDTO(sectorCapacity.getSector());
        sectorCapacity.getTickets()
                    .forEach(ticket->
                        this.tickets.add(new TicketDTO(ticket))
                    );
	}

	public SectorCapacity toSectorCapacity() {
        return new SectorCapacity( this.getId(),
                                this.getTickets()
                                    .stream()
                                    .map(ticketDTO-> ticketDTO.toSimpleTicket())
                                    .collect(Collectors.toSet()),
                                sectorMapper.toSector(this.getSector()),
                                this.getCapacity(),
                                this.getFree());
    }
}
