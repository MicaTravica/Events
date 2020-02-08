package com.app.events.serviceimpl;

import com.app.events.serviceimpl.event.EventServiceImplIntegrationTest;
import com.app.events.serviceimpl.hall.HallServiceImplIntegrationTest;
import com.app.events.serviceimpl.media.MediaServiceImplIntegrationTest;
import com.app.events.serviceimpl.place.PlaceServiceImplIntegrationTest;
import com.app.events.serviceimpl.seat.SeatServiceImplIntegrationTest;
import com.app.events.serviceimpl.sector.SectorServiceImplIntegrationTest;
import com.app.events.serviceimpl.sectorCapacity.SectorCapacityServiceImplIntegrationTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        EventServiceImplIntegrationTest.class,
        HallServiceImplIntegrationTest.class,
        MediaServiceImplIntegrationTest.class,
        PlaceServiceImplIntegrationTest.class,
        SeatServiceImplIntegrationTest.class,
        SectorServiceImplIntegrationTest.class,
        SectorCapacityServiceImplIntegrationTest.class
})
public class ServiceIntegrationSuite {
}
