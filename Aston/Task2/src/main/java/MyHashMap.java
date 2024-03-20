public class MyHashMap<K, V> implements MyMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private Entry<K, V>[] array;
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
                if (current.key.equals(key)) {
                    current.value = value;
                    return;
                }
                current = current.next;
            }
            current.next = entryToAdd;
            size++;
        }
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
            currentEntry = currentEntry.next;
        }
        return false;
    }

    @Override
    public int size() {
        return this.size;
    }


    private int getIndex(Object key) {
        return key.hashCode() % array.length;
    }

    private class Entry<K, V> {
        private K key;
        private V value;
        private Entry<K, V> next;

        public Entry(K key, V value, Entry<K, V> next) {
            this.key = key;
            this.value = value;
            this.next = next;
        }
    }
}
