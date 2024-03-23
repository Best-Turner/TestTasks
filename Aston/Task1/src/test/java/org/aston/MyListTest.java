package org.aston;


import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Comparator;

import static org.junit.Assert.*;

public class MyListTest {

    private MyList<String> list;

    @Before
    public void setUp() throws Exception {
        list = new MyArrayList(); // реализация ArrayList
        addTenElement();
    }


    @Test
    public void whenAdd100ElementsThenReturnSize110() {
        assertEquals(10, list.size());
        for (int i = 0; i < 100; i++) {
            list.add("Test " + i);
        }
        assertEquals(110, list.size());
    }


    @Test
    public void whenAddElementWithIndexThenSizeShouldBeIncrease() {
        final String element = "Some element";
        assertEquals(10, list.size());
        list.addToIndex(1, element);
        assertEquals(11, list.size());
    }

    @Test
    public void whenAddElementToIndexThenThisMustBeUnderThatIndex() {
        final String element = "Some element";
        assertEquals(10, list.size());
        list.addToIndex(1, element);
        String actual = list.get(1);
        assertEquals(element, actual);
    }

    @Test
    public void whenElementExistThenReturnTrue() {
        assertTrue(list.contains("Test0"));
        assertTrue(list.contains("Test9"));
    }


    @Test
    public void whenElementNonExistThenReturnFalse() {
        assertFalse(list.contains("Test10"));
    }

    @Test
    public void whenGetLastElementThenReturnLastElement() {
        String actual = list.get(9);
        assertEquals("Test9", actual);
    }


    @Test
    public void whenRemoveElementThenSizeMustBeDecreaseAndReturnTrue() {
        assertEquals(10, list.size());
        boolean actual = list.remove("Test2");
        assertEquals(9, list.size());
        assertTrue(actual);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenRemoveElementByIndexThanNonExist() {
        list.remove(10);
    }

    @Test
    public void whenRemoveElementByIndexThenSizeMustBeDecrease() {
        assertEquals(10, list.size());
        list.remove(2);
        assertEquals(9, list.size());
    }

    @Test
    public void whenRemoveFirstElementThenSecondBecomesFirst() {
        assertEquals(10, list.size());
        list.remove(0);
        String actual = list.get(0);
        assertEquals("Test1", actual);
        assertEquals(9, list.size());
    }


    @Test
    public void whenRemoveElementThatDontExistThenReturnFalse() {
        assertEquals(10, list.size());
        boolean actual = list.remove("Test10");
        assertEquals(10, list.size());
        assertFalse(actual);
    }

    @Test
    public void whenGetByIndexThenReturnThisElement() {
        String actual = list.get(0);
        assertEquals("Test0", actual);
    }

    @Test(expected = IndexOutOfBoundsException.class)
    public void whenGetElementByIndexThatOutsideSize() {
        list.get(100);
    }

    @Test
    public void testQuicksort() {
        MyArrayList<Integer> list = new MyArrayList<>();
        list.add(3);
        list.add(1);
        list.add(5);
        list.add(2);
        list.add(4);

        Comparator<Integer> comparator = Comparator.naturalOrder();
        list.quicksort(comparator);

        Integer[] expected = {1, 2, 3, 4, 5};
        for (int i = 0; i < list.size(); i++) {
            assertEquals(expected[i], list.get(i));
        }
    }

    @Test
    public void whenAddListToAnotherListThenElementsMustBeEquals() {
        MyList<String> myList = new MyLinkedList<>();
        myList.addAll(list);
        assertEquals(10, myList.size());
        assertEquals(list.get(0), myList.get(0));
        assertEquals(list.get(1), myList.get(1));
        assertEquals(list.get(2), myList.get(2));
        assertEquals(list.get(9), myList.get(9));
    }

    @Test
    public void whenCreateListBasedOnAnotherList() {
        MyList<String> myList = new MyArrayList<>(list);
        assertEquals(myList.size(), list.size());
        for (int i = 0; i < myList.size(); i++) {
            assertEquals(myList.get(i), list.get(i));
        }
    }

    @Test(expected = IllegalArgumentException.class)
    public void whenCreateListBasedOnAnotherListWhichEqualsNull() {
        list = new MyArrayList<>(null);
    }

    @Test
    public void whenUseSortThenListMustBeSorted() {
        MyList<Integer> myList = new MyArrayList<>();
        myList.add(2);
        myList.add(1);
        myList.add(4);
        myList.add(3);
        MyList.bubbleSort(myList);
        for (int i = 0; i < myList.size(); i++) {
            assertEquals(i + 1, (int) myList.get(i));
        }
    }


    @Test
    public void whenListIsAlreadySortedThenSortingDontShouldBeRun() {
        MyList<Integer> myList = new MyArrayList<>();
        myList.add(2);
        myList.add(1);
        myList.add(4);
        myList.add(3);
        MyList.bubbleSort(myList);
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));
        MyList.bubbleSort(myList);
        assertEquals("Список уже отсортирован", outContent.toString());
        System.setOut(System.out);
    }

    private void addTenElement() {
        for (int i = 0; i < 10; i++) {
            list.add("Test" + i);
        }
    }
}