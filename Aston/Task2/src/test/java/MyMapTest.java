import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyMapTest {

    private static final String KEY = "key";
    private static final String VALUE = "value";
    private static final int COUNT_OBJECTS = 10000;

    private MyMap<String, String> map;

    @Before
    public void setUp() throws Exception {
        map = new MyHashMap<>();
    }

    @Test
    public void whenAddOneObjectThenSizeShouldBeIncrease() {
        assertEquals(0, map.size());
        map.put(KEY, VALUE);
        assertEquals(1, map.size());
    }

    @Test
    public void whenAddManyObjectThenSizeShouldBeIncrease() {
        long before = System.currentTimeMillis();
        assertEquals(0, map.size());
        addManyObjects();
        assertEquals(COUNT_OBJECTS, map.size());
        long after = System.currentTimeMillis();
        System.out.println("Время выполнения  = " + (after - before)); // ~ 6 - 7 секунд
    }


    @Test
    public void whenGetExistsValueByKeyThenReturnThisValue() {
        map.put(KEY, VALUE);
        assertEquals(VALUE, map.get(KEY));
    }

    @Test
    public void whenKeyNotExistsThenReturnNull() {
        map.put(KEY, VALUE);
        assertNull(map.get(KEY + 1));
    }


    @Test
    public void whenKeyExistsThenReturnTrue() {
        map.put(KEY, VALUE);
        assertTrue(map.containsKey(KEY));
    }

    @Test
    public void whenKeyNotExistsThenReturnFalse() {
        assertFalse(map.containsKey(KEY));
    }

    @Test
    public void whenRemoveExistsElementThenReturnTrue() {
        map.put(KEY, VALUE);
        assertTrue(map.remove(KEY));
    }

    @Test
    public void whenClearMapThanSizeMustBe0() {
        addManyObjects();
        assertEquals(COUNT_OBJECTS, map.size());
        map.clear();
        assertEquals(0, map.size());
    }

    @Test
    public void whenRemoveNotExistsElementThenReturnFalse() {
        assertFalse(map.containsKey(KEY));
    }



    private void addManyObjects() {
        for (int i = 0; i < COUNT_OBJECTS; i++) {
            map.put(KEY.concat(String.valueOf(i)), VALUE.concat(String.valueOf(i)));
        }
    }
}