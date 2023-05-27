package com.gridnine.testing.filtres;

public final class FilterFactory {
    public static Filter createFilter(FilterType type) {
        Filter filter = null;
        switch (type) {
            case INCORRECT_DATE_ARRIVE -> filter = new FilterIncorrectDateArrive();
            case DEPARTED -> filter = new FilterDepartedFlight();
            case TOTAL_DOWNTIME_TWO_HOURS -> filter = new FilterTotalTimeDowntime();
        }
        return filter;
    }
}
