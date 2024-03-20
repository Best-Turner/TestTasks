import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MyMapTest {

    private static final String KEY = "key";
    private static final String VALUE = "value";

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
    public void whenAdd100ObjectThenSizeShouldBe100() {
        assertEquals(0, map.size());
        for (int i = 0; i < 100; i++) {
            map.put(KEY + i, VALUE + i);
        }
        assertEquals(100, map.size());
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
    public void whenRemoveNotExistsElementThenReturnFalse() {
        assertFalse(map.containsKey(KEY));
    }
}