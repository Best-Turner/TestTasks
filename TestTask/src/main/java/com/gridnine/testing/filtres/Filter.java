package com.gridnine.testing.filtres;

import com.gridnine.testing.Flight;

public interface Filter {
    boolean skip(Flight flight);

}
