package execution_data_structures.node_content_type;

import execution_data_structures.Node;

public class BracketContent implements NodeContent<BracketContent> {
    private final String token;
    private Node<?> corresponding;

    public BracketContent(String token) {
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

    public boolean isOpen() {
        return token.equals("(");
    }

    public boolean isClosed() {
        return token.equals(")");
    }

    @Override
    public String getTypeString() {
        return "bracket";
    }

    @Override
    public String toString() {
        return token;
    }

}