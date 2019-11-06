package com.app.events.dto;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import com.app.events.model.SectorCapacity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SectorCapacityDTO {

    private Long id;
    private Set<TicketDTO> tickets = new HashSet<>();
    private SectorDTO sector;
    private int capacity;
	private int free;

	public SectorCapacityDTO(SectorCapacity sectorCapacity) {
        this.id = sectorCapacity.getId();
        this.capacity = sectorCapacity.getCapacity();
        this.free = sectorCapacity.getFree();
        this.sector = SectorDTO.makeSimpleSectorDTO(sectorCapacity.getSector());
        sectorCapacity.getTickets()
                    .forEach(ticket->{
                        this.tickets.add(new TicketDTO(ticket));
                    });
	}

	public SectorCapacity toSectorCapacity() {
        return new SectorCapacity( this.getId(),
                                this.getTickets()
                                    .stream()
                                    .map(ticketDTO->{
                                        return ticketDTO.toSimpleTicket();
                                    })
                                    .collect(Collectors.toSet()),
                                this.getSector().toSimpleSector(),
                                this.getCapacity(),
                                this.getFree());
    }
}
