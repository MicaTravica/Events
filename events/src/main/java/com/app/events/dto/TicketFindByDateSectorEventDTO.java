package com.app.events.dto;

import java.util.Date;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * TicketFindByDateSectorEventDTO
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class TicketFindByDateSectorEventDTO {
    private Date fromDate;
    private Date toDate;
    private Long sectorId;
    private Long eventId;
}