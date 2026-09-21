package execution_data_structures.nodes.node_content_type;

public enum BracketContent implements NodeContentType<BracketContent> {
    OPEN("("),
    CLOSED(")");

    private final String token;

    BracketContent(String token) {
        this.token = token;
    }

    @Override
    public BracketContent getValue() {
        return this;
    }

    public String getToken() {
        return token;
    }
}