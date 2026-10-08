package execution_data_structures;

import execution_data_structures.node_content_type.BracketContent;
import execution_data_structures.node_content_type.NodeContent;

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

    public Node<?> clone() {
        return new Node<>(this.content);
    }

    public Polymer getCorrespondingPolymer() {
        if (content instanceof BracketContent) {
            if (content.equals(BracketContent.CLOSED)) {
                return new Polymer(((BracketContent) content).getCorresponding(), this);
            } else {
                return new Polymer(this, ((BracketContent) content).getCorresponding());
            }
        }
        return new Polymer(this);
    }

    @Override
    public String toString() {
        return content.toString();
    }
}
