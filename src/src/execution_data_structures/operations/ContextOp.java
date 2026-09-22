package execution_data_structures.operations;

public enum ContextOp implements Operation {
    SAVE("save"),
    LOAD("load");

    private final String token;

    ContextOp(String token) {
        this.token = token;
    }

    @Override
    public String getToken() {
        return token;
    }
}
