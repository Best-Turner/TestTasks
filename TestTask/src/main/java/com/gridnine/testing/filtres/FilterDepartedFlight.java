package com.gridnine.testing.filtres;

import com.gridnine.testing.Flight;
import com.gridnine.testing.Segment;

import java.time.LocalDateTime;
import java.util.List;

public class FilterDepartedFlight implements Filter {


    @Override
    public boolean skip(Flight flight) {
        boolean result = true;
        List<Segment> segments = flight.getSegments();
        for (Segment seg : segments) {
            if (seg.getDepartureDate().isBefore(LocalDateTime.now())) {
                result = false;
            }
        }
        return result;
    }
}
