package execution_data_structures.context;

import execution_data_structures.Node;
import execution_data_structures.Polymer;

public class MainPolymer extends Polymer {
    private Node<?> enzyme;
    
    public MainPolymer(Polymer polymer) {
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

    public void serialize() {}

    public void deserialize() {}
}
