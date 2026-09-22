package execution_data_structures.operations;

public enum UnaryNumericOp implements Operation {
    FLOOR("floor"),
    CEIL("ceil"),
    NOT("not");

    private final String token;

    UnaryNumericOp(String token) {
        this.token = token;
    }

    @Override
    public String getToken() {
        return token;
    }
}
