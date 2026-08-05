package remote_preparation;

public class NodeV2<T> {
    public T value;
    public NodeV2<T> next;

    NodeV2(T value, NodeV2<T> next) {
        this.value = value;
        this.next = next;
    }

    @Override
    public String toString() {
        NodeV2<T> current = this;
        StringBuilder sb = new StringBuilder();
        sb.append("[");
        while (current != null) {
            sb.append(current.value);
            if (current.next != null) {
                sb.append(" -> ");
            }
            current = current.next;
        }
        sb.append("]");

        return sb.toString();
    }
}
