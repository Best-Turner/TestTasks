package com.gridnine.testing.filtres;

import com.gridnine.testing.Flight;
import com.gridnine.testing.Segment;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

public class FilterTotalTimeDowntime implements Filter {
    @Override
    public boolean skip(Flight flight) {

        List<Segment> segments = flight.getSegments();
        boolean result = true;
        if (segments.size() > 1) {
            int countHours = 0;
            for (int i = 1; i < segments.size(); i++) {
                LocalDateTime departureDate = segments.get(i).getDepartureDate();
                LocalDateTime arriveDate = segments.get(i - 1).getArrivalDate();
                long between = ChronoUnit.HOURS.between(arriveDate, departureDate);
                countHours += between;
            }
            if (countHours > 2) {
                result = false;
            }
        }

        return result;
    }
}
