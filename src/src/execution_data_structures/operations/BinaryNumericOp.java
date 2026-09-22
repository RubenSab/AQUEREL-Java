package execution_data_structures.operations;

public enum BinaryNumericOp implements Operation {
    ADD("+"),
    SUB("-"),
    MUL("*"),
    DIV("/"),
    POW("^"),
    ROUND("round"),
    EQ("=="),
    NEQ("!="),
    GT(">"),
    LT("<"),
    GEQ(">="),
    LEQ("<="),
    AND("and"),
    OR("or"),
    XOR("xor");

    private final String token;

    BinaryNumericOp(String token) {
        this.token = token;
    }

    @Override
    public String getToken() {
        return token;
    }
}
