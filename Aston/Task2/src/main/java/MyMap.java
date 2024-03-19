public interface MyMap<K, V> {

    void put(K key, V value);

    V get(K key);

    boolean remove(K key);

    boolean containsKey(Object key);

    int size();
}
