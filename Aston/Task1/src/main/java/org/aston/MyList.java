package org.aston;

/**
 * Интерфейс для пользовательской реализации списка, позволяющий хранить элементы типа E.
 */
public interface MyList<E> extends Iterable<E> {

    /**
     * Статический метод для выполнения пузырьковой сортировки объекта MyList.
     *
     * @param list, для которого необходимо выполнить пузырьковую сортировку.
     */
    static <E extends Comparable<? super E>> void bubbleSort(MyList<E> list) {
        if (!list.getFlag()) {
            for (int i = 0; i < list.size(); i++) {
                for (int j = 0; j < list.size() - i - 1; j++) {
                    E current = list.get(j);
                    E next = list.get(j + 1);

                    if (current.compareTo(next) > 0) {
                        list.set(j + 1, current);
                        list.set(j, next);
                    }
                }
                list.setFlag(true);
            }
        } else {
            System.out.print("Список уже отсортирован");
        }
    }

    /**
     * Добавляет элемент в список.
     *
     * @param e Элемент который нужно добавить в список.
     */
    void add(E e);

    /**
     * Получить элемент по указанному индексу.
     *
     * @param index Индекс элемента, который требуется получить.
     * @return Элемент по указанному индексу.
     */
    E get(int index);

    /**
     * Удалить элемент из списка.
     *
     * @param e Элемент, который нужно удалить.
     * @return true, если элемент был успешно удален, в противном случае — false.
     */
    boolean remove(E e);

    /**
     * Удалить элемент по указанному индексу из списка.
     *
     * @param index Индекс элемента, который нужно удалить.
     * @return true, если элемент был успешно удален, в противном случае — false.
     */
    boolean remove(int index);

    void addAll(MyList<? extends E> myList);

    boolean contains(E e);

    /**
     * Вернуть количество элементов в этом списке.
     *
     * @return количество элементов в этом списке.
     */
    int size();

    /**
     * Добавить элемент по указанному индексу в этот индекс.
     *
     * @param index Индекс, в котором элемент должен быть добавлен.
     * @param el    Добавляемый элемент.
     */
    void addToIndex(int index, E el);

    /**
     * Устанавливает элемент по указанному индексу в этом индексе в указанный элемент.
     *
     * @param index Индекс, в котором должен быть установлен элемент.
     * @param el    Элемент, который нужно установить.
     */
    void set(int index, E el);

    /**
     * Возвращает флаг, указывающий определенное условие.
     *
     * @return флаг, обозначающий определенное состояние.
     */
    boolean getFlag();

    /**
     * Устанавливает флаг, указывающий определенное состояние.
     *
     * @param flag флаг, который нужно установить *
     */
    void setFlag(boolean flag);
}

