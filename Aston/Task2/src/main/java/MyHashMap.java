import java.util.*;

public class MyHashMap<K, V> implements MyMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private Entry[] array;
    private int size;

    public MyHashMap() {
        array = new Entry[DEFAULT_CAPACITY];
    }

    @Override
    public void put(K key, V value) {
        Entry<K, V> entryToAdd = new Entry<>(key, value, null);
        int index = getIndex(key);

        Entry<K, V> current = array[index];
        if (current == null) {
            array[index] = entryToAdd;
            size++;
        } else {
            while (current.next != null) {
                if (equalsKey(key, current.getKey())) {
                    current.value = value;
                    return;
                }
                current = current.next;
            }
            current.next = entryToAdd;
            size++;
        }
    }

    private boolean equalsKey(K key, K key1) {
        return equalsHashCode(key, key1) && key.equals(key1);
    }

    private boolean equalsHashCode(K key, K key1) {
        return key.hashCode() == key1.hashCode();
    }


    @Override
    public V get(K key) {
        int index = getIndex(key);
        Entry<K, V> currentEntry = array[index];
        V value = null;
        while (currentEntry != null) {
            if (currentEntry.key.equals(key)) {
                value = currentEntry.value;
                break;
            } else {
                currentEntry = currentEntry.next;
            }
        }
        return value;
    }

    @Override
    public boolean remove(K key) {
        int index = getIndex(key);
        Entry<K, V> current = array[index];
        Entry<K, V> prev = null;

        while (current != null) {

            if (current.key.equals(key)) {
                if (prev == null) {
                    array[index] = current.next;
                } else {
                    prev.next = current.next;
                }
                size--;
                return true;
            }
            prev = current;
            current = current.next;
        }
        return false;
    }

    @Override
    public boolean containsKey(Object key) {
        int index = getIndex(key);
        Entry<K, V> currentEntry = array[index];

        while (currentEntry != null) {
            if (currentEntry.key.equals(key)) {
                return true;
            }
            currentEntry = (Entry<K, V>) currentEntry.next();
        }
        return false;
    }

    @Override
    public boolean containsValue(Object value) {
        return values().stream().anyMatch(el -> el.equals(value));
    }

    @Override
    public boolean isEmpty() {
        return !(size > 0);
    }

    @Override
    public Set<K> keySet() {
        Set<K> keys = new HashSet<>();
        getEntrySet().forEach(el -> keys.add(el.getKey()));
        //return (Set<K>) getEntrySet().stream().map(MyMap.Entry::getValue).collect(Collectors.toSet());
        return keys;
    }

    @Override
    public Collection<V> values() {
        List<V> values = new ArrayList<>();
        Set<MyMap.Entry<K, V>> entrys = getEntrySet();
        entrys.forEach(el -> values.add(el.getValue()));
        return values;
    }

    @Override
    public Set<MyMap.Entry<K, V>> entrySet() {
        return getEntrySet();
    }

    private Set<MyMap.Entry<K, V>> getEntrySet() {
        Set<MyMap.Entry<K, V>> entries = new HashSet<>();
        Entry<K, V> current;
        for (Entry entry : array) {
            if (entry == null) {
                continue;
            }
            current = entry;
            entries.add(current);
            while (current.next() != null) {
                current = current.next;
                entries.add(current);
            }
        }
        return entries;
    }


    @Override
    public void clear() {
        array = new Entry[DEFAULT_CAPACITY];
        size = 0;
    }

    @Override
    public int size() {
        return this.size;
    }


    private int getIndex(Object key) {
        return Math.abs(key.hashCode() % array.length);
    }

    private static class Entry<K, V> implements MyMap.Entry<K, V> {
        private final K key;
        private V value;
        private Entry<K, V> next;

        public Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }

        @Override
        public K getKey() {
            return key;
        }

        @Override
        public V getValue() {
            return value;
        }

        @Override
        public MyMap.Entry<K, V> next() {
            return next;
        }
    }
}
