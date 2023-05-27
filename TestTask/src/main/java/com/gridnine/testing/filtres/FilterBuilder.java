package com.gridnine.testing.filtres;

import com.gridnine.testing.Flight;

import java.util.ArrayList;
import java.util.List;

public class FilterBuilder {

    public static List<Flight> getFilteredList(List<Flight> flight, FilterType... filterTypes) {
        Filter[] filters = getAllFilters(filterTypes);
        List<Flight> filteredList = new ArrayList<>();
        if (flight != null) {
            for (int i = 0; i < flight.size(); i++) {
                boolean flag = true;
                for (int j = 0; j < filters.length; j++) {
                    if (!filters[j].skip(flight.get(i))) {
                        flag = false;
                        break;
                    }
                }
                if (flag) {
                    filteredList.add(flight.get(i));
                }
            }
        }
        return filteredList;
    }

    private static Filter[] getAllFilters(FilterType... filterTypes) {
        if (filterTypes.length == 0) {
            throw new IllegalArgumentException("Вы не приминили ни одного фильтра");
        }
        int countFilters = filterTypes.length;
        Filter[] filters = new Filter[countFilters];

        for (int i = 0; i < countFilters; i++) {
            filters[i] = FilterFactory.createFilter(filterTypes[i]);
        }
        return filters;
    }

}
