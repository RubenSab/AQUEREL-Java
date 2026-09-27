package execution_data_structures.nodes;

import execution_data_structures.nodes.node_content_type.NodeContent;

public class Node<T extends NodeContent<?>> {
    private Node<?> next;
    private Node<?> prev;
    private final T content;

    public Node(T content) {
        this.content = content;
    }

    public Node<?> getNext() {
        return next;
    }

    public boolean hasNext() {
        return getNext() != null;
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

    public Node<?> execute() {
        return null;
    }

    @Override
    public String toString() {
        return content.toString();
    }
}
