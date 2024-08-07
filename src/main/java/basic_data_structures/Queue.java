package basic_data_structures;

public interface Queue<T> {
    void add(T item);
    T remove();
    boolean isEmpty();
}
