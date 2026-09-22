package execution_data_structures.nodes.node_content_type;

import execution_data_structures.nodes.Node;

public enum BracketContent implements NodeContentType<BracketContent> {
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

    public String getToken() {
        return token;
    }
}