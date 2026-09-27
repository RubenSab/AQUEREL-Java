package execution_data_structures;

import execution_data_structures.nodes.Node;
import execution_data_structures.nodes.Polymer;

public class MainPolymer extends Polymer {
    private Node<?> enzyme;
    private Polymer polymer;
    
    public MainPolymer(Polymer polymer) {
        this.polymer = polymer;
        this.enzyme = polymer.getStart();
    }

    public boolean hasEnzyme() {
        return enzyme != null;
    }

    public Node<?> getEnzyme() {
        return enzyme;
    }
    
    public void setEnzyme(Node<?> target) {
        this.enzyme = target;
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
