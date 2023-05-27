package com.gridnine.testing.filtres;

import com.gridnine.testing.Flight;
import com.gridnine.testing.Segment;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FilterDepartedFlightTest {
    private Filter filter;
    private Flight flight;
    private LocalDateTime dep;
    private LocalDateTime arr;
    private Segment segment;

    @Before
    public void setUp() throws Exception {
        filter = new FilterDepartedFlight();

    }

    @Test
    public void whenDepartedFlightOneHourAgoThenMustBeFalse() {
        dep = LocalDateTime.now().minusHours(1);
        arr = LocalDateTime.now().plusHours(2);
        segment = new Segment(dep, arr);
        flight = new Flight(Collections.singletonList(segment));
        assertFalse(filter.skip(flight));
    }

    @Test
    public void whenDepartedFlightOneDayAgoThenMustBeFalse() {
        dep = LocalDateTime.now().minusDays(1);
        arr = LocalDateTime.now().plusHours(2);
        segment = new Segment(dep, arr);
        flight = new Flight(Collections.singletonList(segment));
        assertFalse(filter.skip(flight));
    }

    @Test
    public void whenFlightHasNotYetDepartedMustBeTrue() {
        dep = LocalDateTime.now().plusHours(1);
        arr = LocalDateTime.now().plusHours(2);
        segment = new Segment(dep, arr);
        flight = new Flight(Collections.singletonList(segment));
        assertTrue(filter.skip(flight));
    }

}