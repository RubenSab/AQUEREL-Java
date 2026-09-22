package execution_data_structures.operations;

public enum StringOp implements Operation {
    GETCHAR("getchar"),
    JOIN("join");

    private final String token;

    StringOp(String token) {
        this.token = token;
    }

    @Override
    public String getToken() {
        return token;
    }
}
