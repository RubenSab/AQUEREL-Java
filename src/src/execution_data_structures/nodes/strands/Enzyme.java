package execution_data_structures.nodes.strands;

import execution_data_structures.nodes.Node;

public class Enzyme {
    private Node<?> position;

    public Node<?> getPosition() {
        return position;
    }

    public void move_right() {
        position = position.getNext();
    }

    public void move_left() {
        position = position.getPrev();
    }

    public void jump_to(Node<?> target) {
        position = target;
    }
}
