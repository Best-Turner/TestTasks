import java.util.List;

public interface MyList<E> {
    void add(E e);

    E get(int index);

    boolean remove(E e);

    void addAll(MyList<? extends E> myList);

    int size();
}
