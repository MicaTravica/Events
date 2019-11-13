package com.app.events.mapper;

import com.app.events.dto.SeatDTO;
import com.app.events.model.Seat;
import com.app.events.model.Sector;

public class SeatMapper {

    public static SeatDTO toDTO(Seat seat) {
        return new SeatDTO(
            seat.getId(), 
            seat.getSeatRow(),
            seat.getSeatColumn(), 
            SectorMapper.toDTO(seat.getSector())
        );
    }
    
    public static Seat toPriceList(SeatDTO seatDTO) {
        return new Seat(seatDTO.getId(), seatDTO.getSeatRow(),
                        seatDTO.getSeatColumn(), new Sector(seatDTO.getSector().getId())
                    );
    }

}
