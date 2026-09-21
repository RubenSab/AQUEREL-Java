package execution_data_structures.nodes;

import execution_data_structures.nodes.node_content_type.NodeContentType;

public abstract class Node<T extends NodeContentType<?>> {
    private Node<?> next;
    private Node<?> prev;
    private final T content;

    public Node(T content) {
        this.content = content;
    }

    public Node<?> getNext() {
        return next;
    }

    public Node<?> getPrev() {
        return prev;
    }

    public T getContent() {
        return content;
    }

    public void setNext(Node<?> next) {
        this.next = next;
    }

    public void setPrev(Node<?> prev) {
        this.prev = prev;
    }
}
