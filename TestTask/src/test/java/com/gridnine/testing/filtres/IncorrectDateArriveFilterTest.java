package com.gridnine.testing.filtres;

import com.gridnine.testing.Flight;
import com.gridnine.testing.Segment;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.DisplayName;

import java.time.LocalDateTime;
import java.util.Collections;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class IncorrectDateArriveFilterTest {
    private Filter filter;

    private Flight flightCorrect;
    private Flight flightIncorrect;

    @Before
    public void setUp() {
        filter = new FilterIncorrectDateArrive();

        flightCorrect =
                new Flight(Collections.singletonList(
                        new Segment(LocalDateTime.now().minusHours(1),
                                LocalDateTime.now().plusHours(2))));

        flightIncorrect = new Flight(Collections.singletonList(
                new Segment(LocalDateTime.now(),
                        LocalDateTime.now().minusHours(2))));
    }

    @Test
    @DisplayName("Correct flight should pass")
    public void whenCorrectDateThenMustBeTrue() {
        assertTrue(filter.skip(flightCorrect));
    }

    @Test
    @DisplayName("Incorrect flight should fail")
    public void whenIncorrectDateThenMustBeFalse() {
        assertFalse(filter.skip(flightIncorrect));
    }
}