package execution_data_structures.operations;

public enum PolymerOp implements Operation {
    APPEND("splice"),
    REPLACE("replace"),
    REMOVE("remove"),
    GET("get");

    private final String token;

    PolymerOp(String token) {
        this.token = token;
    }

    @Override
    public String getToken() {
        return token;
    }
}

