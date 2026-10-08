package execution_data_structures;

import execution_data_structures.node_content_type.BracketContent;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Polymer {
    protected Node<?> start;
    protected Node<?> end;

    public Polymer() {}

    public Polymer(Node<?> start, Node<?> end) {
        this.start = start;
        this.end = end;
        start.setPrev(null);
        end.setNext(null);
    }

    public Polymer(Node<?> node) {
        this(node, node);
    }

    public Polymer(List<Node<?>> nodes) {
        this(nodes.getFirst(), nodes.getLast());
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

    public Node<?> getNext() {
        return end.getNext();
    }

    public Node<?> getPrev() {
        return start.getPrev();
    }

    public Polymer clone() { // AI
        if (this.start == null) {
            return new Polymer();
        }
        Polymer clonedPolymer = new Polymer();
        // Map to keep track of: Original Node -> Cloned Node
        // Stored on the Heap, not the call stack.
        Map<Node<?>, Node<?>> nodeMap = new HashMap<>();
        // PASS 1: Iterative linear pass to clone nodes & recreate next/prev chain
        Node<?> current = this.start;
        while (current != null) {
            // Create isolated copy of node content
            Node<?> clonedNode = new Node<>(current.getContent());
            // Append to new polymer (wires up prev/next pointers iteratively)
            clonedPolymer.append(clonedNode);
            // Register in map
            nodeMap.put(current, clonedNode);
            // Advance linearly along original list
            current = current.getNext();
        }
        // PASS 2: Iterative linear pass to wire up cross-references
        current = this.start;
        while (current != null) {
            if (current.getContent() instanceof BracketContent bracket) {
                Node<?> originalCorresponding = bracket.getCorresponding();

                if (originalCorresponding != null) {
                    // Fetch the cloned counterparts directly from the Map (O(1) heap lookups)
                    Node<?> clonedBracketNode = nodeMap.get(current);
                    Node<?> clonedCorrespondingNode = nodeMap.get(originalCorresponding);

                    // Update the cloned bracket's reference
                    BracketContent clonedContent = (BracketContent) clonedBracketNode.getContent();
                    clonedContent.setCorresponding(clonedCorrespondingNode);
                }
            }
            // Advance linearly
            current = current.getNext();
        }
        return clonedPolymer;
    }

    public void append(Node<?> node) {
        if (start == null || end == null) {
            node.setNext(null);
            node.setPrev(null);
            start = node;
            end = node;
        } else {
            end.setNext(node);
            node.setPrev(end);
            end = node;
        }
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

    public void insert_polymer_after(Node<?> target, Polymer polymer) {
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

    public Node<?> extract_node(Node<?> node) {
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

    public Polymer extract_polymer(Node<?> start, Node<?> end) {
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

    public void replace_node_with_polymer(Node<?> target, Polymer replacement) {
        if (target==start) {
            start = replacement.getStart();
        }
        if (target==end) {
            end = replacement.getEnd();
        }
        if (target.getNext()!=null) {
            target.getNext().setPrev(replacement.getEnd());
        }
        if (target.getPrev()!=null) {
            target.getPrev().setNext(replacement.getStart());
        }
        replacement.getEnd().setNext(target.getNext());
        replacement.getStart().setPrev(target.getPrev());
        target.setNext(null);
        target.setPrev(null);
    }
}
