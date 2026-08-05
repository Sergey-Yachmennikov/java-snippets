package remote_preparation;

public abstract class NodeUtil {

    private NodeUtil() {}

    public static <T> NodeV2<T> reverse(NodeV2<T> node) {
        NodeV2<T> prev = null;
        NodeV2<T> current = node;

        while (current != null) {
            NodeV2<T> next = current.next;   // сохраняем ссылку на следующий узел, пока не потеряли её
            current.next = prev;             // разворачиваем ссылку у текущего узла
            prev = current;                  // сдвигаем prev вперёд
            current = next;                  // сдвигаем current вперёд
            System.out.println(prev);
        }

        return prev; // prev теперь указывает на новую голову списка
    }
}