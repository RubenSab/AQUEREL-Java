package execution_data_structures.nodes.strands;

import execution_data_structures.nodes.Node;

public class Polymer {
    private Node<?> start;
    private Node<?> end;

    public Polymer() {}

    public Polymer(Node<?> start, Node<?> end) {
        this.start = start;
        this.end = end;
    }

    @Override
    public String toString() {
        if (start == null) return "";
        StringBuilder sb = new StringBuilder();
        Node<?> node = start;
        while (node != null) {
            sb.append(node.getContent().toString());
            if (node.getNext() != null) sb.append(" -> ");
            node = node.getNext();
        }
        return sb.toString();
    }

    public Node<?> getEnd() {
        return end;
    }

    public Node<?> getStart() {
        return start;
    }

    public void insert_node_after(Node<?> target, Node<?> node) {
        if (target.getNext() != null) {
            target.getNext().setPrev(node);
            node.setNext(target.getNext());
        }
        target.setNext(node);
        node.setPrev(target);
        if (node.getNext() == null) {
            end = node;
        }
    }

    public void insert_strand_after(Node<?> target, Polymer polymer) {
        if (target.getNext() != null) {
            target.getNext().setPrev(polymer.getEnd());
            polymer.getEnd().setNext(target.getNext());
        }
        target.setNext(polymer.getStart());
        polymer.getStart().setPrev(target);
        if (polymer.getEnd().getNext() == null) {
            end = polymer.getEnd();
        }
    }

    public Node extract_node(Node<?> node) {
        Node<?> prev = node.getPrev();
        Node<?> next = node.getNext();
        if (prev != null) {
            prev.setNext(next);
        } else {
            start = node.getNext();
        }
        if (next != null) {
            next.setPrev(prev);
        } else {
            end = node.getPrev();
        }
        node.setPrev(null);
        node.setNext(null);
        return node;
    }

    public Polymer extract_strand(Node<?> start, Node<?> end) {
        Node<?> prev = start.getPrev();
        Node<?> next = end.getNext();
        if (prev != null) {
            prev.setNext(next);
        } else {
            this.start = end.getNext();
        }
        if (next != null) {
            next.setPrev(prev);
        } else {
            this.end = start.getPrev();
        }
        start.setPrev(null);
        end.setNext(null);
        return new Polymer(start, end);
    }

    public void replace_node_with_node(Node<?> target, Node<?> replacement) {
        if (target == start) {
            start = replacement;
        }
        if (target == end) {
            end = replacement;
        }
        if (target.getNext() != null) {
            target.getNext().setPrev(replacement);
        }
        if (target.getPrev() != null) {
            target.getPrev().setNext(replacement);
        }
        replacement.setNext(target.getNext());
        replacement.setPrev(target.getPrev());
        target.setNext(null);
        target.setPrev(null);
    }

    public void replace_node_with_strand(Node<?> target, Polymer replacement) {
        if (target == start) {
            start = replacement.getStart();
        }
        if (target == end) {
            end = replacement.getEnd();
        }
        if (target.getNext() != null) {
            target.getNext().setPrev(replacement.getEnd());
        }
        if (target.getPrev() != null) {
            target.getPrev().setNext(replacement.getStart());
        }
        replacement.getEnd().setNext(target.getNext());
        replacement.getStart().setPrev(target.getPrev());
        target.setNext(null);
        target.setPrev(null);
    }
}
