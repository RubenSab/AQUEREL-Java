package execution_data_structures.node_content_type;

import execution_data_structures.Node;

public enum BracketContent implements NodeContent<BracketContent> {
    OPEN("("),
    CLOSED(")");

    private final String token;
    private Node<?> corresponding;

    BracketContent(String token) {
        this.token = token;
    }

    public void setCorresponding(Node<?> corresponding) {
        this.corresponding = corresponding;
    }

    public Node<?> getCorresponding() {
        return corresponding;
    }

    @Override
    public BracketContent getValue() {
        return this;
    }

    @Override
    public String toString() {
        return token;
    }
}