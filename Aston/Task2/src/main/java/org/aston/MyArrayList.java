import java.util.Arrays;

public class MyArrayList<E> implements MyList<E> {

    private static final int DEFAULT_CAPACITY = 10;
    private int size;
    private Object[] array;

    public MyArrayList() {
        size = 0;
        array = new Object[DEFAULT_CAPACITY];
    }

    public MyArrayList(int capacity) {
        if (capacity <= 0) {
            throw new IllegalArgumentException("Capacity cannot be less than zero =(");
        }
        size = 0;
        array = new Object[capacity];
    }


    @Override
    public void add(E e) {
        if (size == array.length - 1) {
            increaseArray();
        }
        array[size++] = e;
    }

    private void increaseArray() {
        array = Arrays.copyOf(array, (int) (array.length * 1.5f));
    }

    @Override
    public E get(int index) {
        if (index >= size || index < 0) {
            throw new IndexOutOfBoundsException("Incorrect input index");
        }
        return (E) array[index];
    }

    @Override
    public boolean remove(E e) {
        int temp;
        for (int i = 0; i < size; i++) {
            if (array[i].equals(e)) {
                temp = i;
                for (int j = temp; j < size; j++) {
                    array[j] = array[j + 1];
                }
                size--;
                return true;
            }
        }
        return false;
    }

    @Override
    public void addAll(MyList<? extends E> myList) {
        for (int i = 0; i < myList.size(); i++) {
            this.add(myList.get(i));
        }
    }

    @Override
    public int size() {
        return size;
    }


    private long getIndex(E e) {
        return Arrays.stream(array).filter(el -> el.equals(e)).findFirst().stream().count();
    }
}
