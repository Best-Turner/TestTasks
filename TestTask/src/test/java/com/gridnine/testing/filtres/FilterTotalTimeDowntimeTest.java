package com.gridnine.testing.filtres;

import com.gridnine.testing.Flight;
import com.gridnine.testing.Segment;
import org.junit.Before;
import org.junit.Test;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class FilterTotalTimeDowntimeTest {

    private Filter filter;
    private LocalDateTime dep;
    private LocalDateTime arr;
    private List<Segment> segments;
    private Flight flight;

    @Before
    public void setUp() throws Exception {
        filter = new FilterTotalTimeDowntime();
        segments = new ArrayList<>();
        dep = LocalDateTime.now();
        arr = LocalDateTime.now().plusHours(2);
        segments.add(new Segment(dep, arr));

    }

    @Test
    public void whenTotalTimeIsMore2HoursThenMustBeFalse() {
        segments.add(new Segment(dep.plusHours(5), arr.plusHours(6)));
        flight = new Flight(segments);
        assertFalse(filter.skip(flight));
    }

    @Test
    public void whenTotalTimeIsLess2HoursThenMustBeTrue() {
        segments.add(new Segment(dep.plusHours(3), arr.plusHours(4)));
        flight = new Flight(segments);
        assertTrue(filter.skip(flight));
    }
}