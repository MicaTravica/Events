package com.app.events.repository;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        EventRepositoryTest.class,
        HallRepositoryTest.class,
        MediaRepositoryTest.class,
        PlaceRepositoryTest.class,
        PriceListRepositoryTest.class,
        SeatRepositoryTest.class,
        SectorRepositoryTest.class,
        SectorCapacityRepositoryTest.class,
        TicketRepositoryTest.class,
        UserRepositoryTest.class,
        VerificationTokenRepositoryTest.class
})
public class RepositorySuite {
}
