package com.gridnine.testing.filtres;

import com.gridnine.testing.Flight;
import com.gridnine.testing.Segment;

import java.util.List;

public class FilterIncorrectDateArrive implements Filter {

    @Override
    public boolean skip(Flight flight) {
        boolean result = true;
        List<Segment> segments = flight.getSegments();
        for (Segment seg : segments) {
            if (seg.getArrivalDate().isBefore(seg.getDepartureDate())) {
                result = false;
            }
        }
        return result;
    }
}
