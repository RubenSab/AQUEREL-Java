package execution_data_structures;

import execution_data_structures.nodes.Node;
import execution_data_structures.nodes.strands.Polymer;

public class MainSequence extends Polymer {
    private Node<?> enzyme;

    public Node<?> getCurrent() {
        return enzyme;
    }

    public void moveRight() {
        enzyme = enzyme.getNext();
    }

    public void moveLeft() {
        enzyme = enzyme.getPrev();
    }

    public void jumpTo(Node<?> target) {
        enzyme = target;
    }

    public Node getRight() {
        return enzyme.getNext();
    }

    public Node getLeft() {
        return enzyme.getPrev();
    }

    public void serialize() {}

    public void deserialize() {}
}
