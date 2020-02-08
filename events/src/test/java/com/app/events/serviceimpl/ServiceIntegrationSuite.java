package com.app.events.serviceimpl;

import com.app.events.serviceimpl.event.EventServiceImplIntegrationTest;
import com.app.events.serviceimpl.hall.HallServiceImplIntegrationTest;
import com.app.events.serviceimpl.media.MediaServiceImplIntegrationTest;
import com.app.events.serviceimpl.place.PlaceServiceImplIntegrationTest;
import com.app.events.serviceimpl.priceList.PriceListImplIntegrationTest;
import com.app.events.serviceimpl.report.ReportservicaImplIntegrationTest;
import com.app.events.serviceimpl.seat.SeatServiceImplIntegrationTest;
import com.app.events.serviceimpl.sector.SectorServiceImplIntegrationTest;
import com.app.events.serviceimpl.sectorCapacity.SectorCapacityServiceImplIntegrationTest;
import com.app.events.serviceimpl.ticket.TicketServiceImplIntegrationTest;
import com.app.events.serviceimpl.user.UserServiceImplIntegrationTest;
import com.app.events.serviceimpl.userDetails.UserDetailServiceImplIntegrationTest;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        EventServiceImplIntegrationTest.class,
        HallServiceImplIntegrationTest.class,
        MediaServiceImplIntegrationTest.class,
        PlaceServiceImplIntegrationTest.class,
        PriceListImplIntegrationTest.class,
        ReportservicaImplIntegrationTest.class,
        SeatServiceImplIntegrationTest.class,
        SectorServiceImplIntegrationTest.class,
        SectorCapacityServiceImplIntegrationTest.class,
        TicketServiceImplIntegrationTest.class,
        UserServiceImplIntegrationTest.class,
        UserDetailServiceImplIntegrationTest.class
})
public class ServiceIntegrationSuite {
}
