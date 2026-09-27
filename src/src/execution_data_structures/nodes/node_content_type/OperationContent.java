package execution_data_structures.nodes.node_content_type;

import execution_data_structures.nodes.Node;


public enum OperationContent implements NodeContent<OperationContent> {
    /* binary num op */
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
    XOR("xor"),
    /* unary num op */
    FLOOR("floor"),
    CEIL("ceil"),
    NOT("not"),
    /* string op */
    GETCHAR("getchar"),
    JOIN("join"),
    /* polymer op */
    APPEND("append"),
    REPLACE("replace"),
    REMOVE("remove"),
    GET("get"),
    RUN("run"),
    /* context op */
    SAVE("save"),
    LOAD("load"),
    /* others */
    DUP("dup"),
    LDROP("ldrop"),
    RDROP("rdrop"),
    PICK("pick"),
    THROW("throw"),
    MAINLEN("mainlen"),
    ASSIGN("="),
    EXISTS("exists"),
    DEL("del"),
    RESOLVE("resolve"),
    SPLICE("splice"),
    TYPE("type"),
    TOSTR("tostr"),
    TONUM("tonum"),
    PRINT("print"),
    INPUT("input"),
    TIME("time"),
    IN("in"),
    RAND("rand"),
    SEED("seed"),
    MAINSEQ("MAINSEQ"),
    NSPACE("NSPACE"),
    LEN("len");


    private final String token;

    OperationContent(String token) {
        this.token = token;
    }

    @Override
    public OperationContent getValue() {
        return this;
    }

    public String getToken() {
        return token;
    }

    public static OperationContent fromToken(String symbol) {
        for (OperationContent op : values()) {
            if (op.getToken().equals(symbol)) {
                return op;
            }
        }
        return null;
    }
}
