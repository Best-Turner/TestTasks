package org.aston;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;

/**
 * Реализация интерфейса MyList с использованием массива.
 * <p>
 * Позволяет добавлять, удалять и получать элементы из списка.
 * <p>
 * Поддерживает динамическое увеличение размера массива при необходимости.
 *
 * @param <E> тип элементов списка
 */

public class MyArrayList<E> implements MyList<E> {

    private static final int DEFAULT_CAPACITY = 10;
    private static final float LOAD_FACTOR = 1.5f;
    private int size = 0;
    private boolean isSorted = false;
    private Object[] array;

    /**
     * Конструктор по умолчанию.
     * Создает массив размером по умолчанию равным 10.
     */
    public MyArrayList() {
        array = new Object[DEFAULT_CAPACITY];
    }

    /**
     * Конструктор с указанием начальной емкости списка.
     *
     * @param capacity начальная емкость списка
     * @throws IllegalArgumentException если указанная емкость <= 0 или >= Integer.MAX_VALUE
     */

    public MyArrayList(int capacity) throws IllegalArgumentException {
        if (capacity <= 0 || capacity >= Integer.MAX_VALUE) {
            throw new IllegalArgumentException("Capacity cannot be less than zero and cannot be too large =(");
        }
        size = 0;
        array = new Object[capacity];
    }

    /**
     * Конструктор, который принимает объект списка и инициализирует новый MyArrayList.
     *
     * @param list список, который будет использоваться для инициализации MyArrayList
     * @throws IllegalArgumentException если переданный список равен null
     */
    public MyArrayList(MyList<? extends E> list) throws IllegalArgumentException {
        int size;
        if (list != null) {
            size = list.size();
        } else {
            throw new IllegalArgumentException("List cannot be null");
        }
        array = new Object[size];
        list.forEach(this::add);
    }

    /**
     * Добавляет элемент в MyArrayList.
     *
     * @param e элемент, который будет добавлен в список
     */
    @Override
    public void add(E e) {
        increaseArray();
        array[size++] = e;
        setFlag(false);
    }

    /**
     * Возвращает элемент по указанному индексу.
     *
     * @param index индекс элемента
     * @return элемент списка по указанному индексу
     */
    @Override
    public E get(int index) {
        checkIndex(index);
        return (E) array[index];
    }

    /**
     * Удаляет первое вхождение элемента из MyArrayList, если он присутствует.
     *
     * @param e элемент, который будет удален из списка
     * @return true, если элемент был удален; в противном случае - false
     */
    @Override
    public boolean remove(E e) {
        int temp;
        for (int i = 0; i < size; i++) {
            if (array[i].equals(e)) {
                temp = i;
                for (int j = temp; j < size - 1; j++) {
                    array[j] = array[j + 1];
                }
                size--;
                return true;
            }
        }
        return false;
    }


    /**
     * Удаляет элемент с данным индексом из MyArrayList, если он присутствует.
     *
     * @param index элемент, который будет удален из списка
     * @return true, если элемент был удален; в противном случае - false
     */
    @Override
    public boolean remove(int index) {
        checkIndex(index);
        for (int i = index; i < size - 1; i++) {
            array[i] = array[i + 1];
        }
        size--;
        return true;
    }


    /**
     * Добавляет все элементы из другого списка в MyArrayList.
     *
     * @param myList список, содержащий элементы, которые будут добавлены
     */
    @Override
    public void addAll(MyList<? extends E> myList) {
        myList.forEach(this::add);
        setFlag(false);
    }

    /**
     * Проверяет, содержит ли MyArrayList указанный элемент.
     *
     * @param e элемент, который будет проверен на наличие в списке
     * @return true, если элемент содержится в списке; в противном случае - false
     */
    @Override
    public boolean contains(E e) {
        for (int i = 0; i < size; i++) {
            if (array[i].equals(e)) {
                return true;
            }
        }
        return false;
    }

    /**
     * Возвращает количество элементов в MyArrayList.
     *
     * @return количество элементов в списке
     */
    @Override
    public int size() {
        return size;
    }

    /**
     * Добавляет указанный элемент в MyArrayList по указанному индексу.
     *
     * @param index индекс, куда будет добавлен элемент.
     * @param el    элемент, который будет добавлен.
     */
    @Override
    public void addToIndex(int index, E el) {
        checkIndex(index);
        increaseArray();
        System.arraycopy(array, index, array, index + 1, size - index);
        array[index] = el;
        size++;
        setFlag(false);
    }

    /**
     * Заменяет элемент в MyArrayList с указанным индексом на переданный элемент.
     *
     * @param index Индекс, в котором должен быть установлен элемент.
     * @param el    Элемент, который нужно установить.
     */

    @Override
    public void set(int index, E el) {
        checkIndex(index);
        array[index] = el;
        setFlag(false);
    }

    /**
     * Возвращает флаг, указывающий на отсортированность MyArrayList.
     *
     * @return true, если список отсортирован; в противном случае - false
     */
    @Override
    public boolean getFlag() {
        return isSorted;
    }

    /**
     * Устанавливает флаг, указывающий на отсортированность MyArrayList.
     *
     * @param flag значение флага: true, если список отсортирован; в противном случае - false
     */
    @Override
    public void setFlag(boolean flag) {
        isSorted = flag;
    }

    /**
     * Сортирует MyArrayList с использованием алгоритма быстрой сортировки.
     *
     * @param comparator компаратор для сравнения элементов
     */
    public void quicksort(Comparator<? super E> comparator) {
        quicksort(0, size() - 1, comparator);
    }

    private void quicksort(int low, int high, Comparator<? super E> comparator) {
        if (low < high) {
            int pivotIndex = partition(low, high, comparator);
            quicksort(low, pivotIndex - 1, comparator);
            quicksort(pivotIndex + 1, high, comparator);
        }
    }

    private int partition(int low, int high, Comparator<? super E> comparator) {
        E pivotValue = get(high);
        int i = low - 1;
        for (int j = low; j < high; j++) {
            if (comparator.compare(get(j), pivotValue) < 0) {
                i++;
                swap(i, j);
            }
        }
        swap(i + 1, high);
        return i + 1;
    }

    private void swap(int i, int j) {
        E temp = get(i);
        set(i, get(j));
        set(j, temp);
    }

    private void increaseArray() {
        if (size == array.length - 1) {
            array = Arrays.copyOf(array, (int) (array.length * LOAD_FACTOR));
        }
    }


    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {
            private int index = 0;

            @Override
            public boolean hasNext() {
                return index < size;
            }

            @Override
            public E next() {
                return (E) array[index++];
            }
        };
    }

    private void checkIndex(int index) {
        if (index < 0 || index > size - 1) {
            throw new IndexOutOfBoundsException("Не верный индекс");
        }
    }
}
