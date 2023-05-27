package com.gridnine.testing;

import com.gridnine.testing.filtres.FilterBuilder;
import com.gridnine.testing.filtres.FilterType;

import java.util.List;

public class Main {
    public static void main(String[] args) {

        List<Flight> flights = FlightBuilder.createFlights();

        System.out.println("\tВсе сегменты:\n");
        flights.forEach(System.out::println);

        System.out.println("\n\tСписок сегментов, кроме вылетевших:\n");
        List<Flight> sortedList = FilterBuilder.getFilteredList(flights, FilterType.DEPARTED);
        sortedList.forEach(System.out::println);

        System.out.println("\n\tСписок сегментов с корректной датой прилета:\n");
        sortedList = FilterBuilder.getFilteredList(flights, FilterType.INCORRECT_DATE_ARRIVE);
        sortedList.forEach(System.out::println);

        System.out.println("\n\tСписок сегментов, кроме тех, у которых время простоя более 2 часов:\n");
        sortedList = FilterBuilder.getFilteredList(flights, FilterType.TOTAL_DOWNTIME_TWO_HOURS);
        sortedList.forEach(System.out::println);

        System.out.println("\n\tСписок сегментов с применением всех фильтров:\n");
        sortedList = FilterBuilder.getFilteredList(flights,
                FilterType.TOTAL_DOWNTIME_TWO_HOURS,
                FilterType.DEPARTED,
                FilterType.INCORRECT_DATE_ARRIVE);
        sortedList.forEach(System.out::println);

    }

}

