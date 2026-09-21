package execution_data_structures.nodes.node_content_type;

public enum OperationContent implements NodeContentType<OperationContent> {
    ADD("+"),
    SUB("-"),
    MUL("*"),
    DIV("/"),
    POW("^"),
    ROUND("round"),
    FLOOR("floor"),
    CEIL("ceil"),
    EQ("=="),
    NEQ("!="),
    GT(">"),
    LT("<"),
    GEQ(">="),
    LEQ("<="),
    AND("and"),
    OR("or"),
    XOR("xor"),
    NOT("not"),
    RUN("run"),
    DUP("dup"),
    LDROP("ldrop"),
    RDROP("rdrop"),
    PICK("pick"),
    THROW("throw"),
    MAINLEN("mainlen"), /* to be erased */
    ASSIGN("="),
    EXISTS("exists"),
    DEL("del"),
    RESOLVE("resolve"),
    SPLICE("splice"),
    REPLACE("replace"),
    REMOVE("remove"),
    GET("get"),
    GETCHAR("getchar"),
    JOIN("join"),
    TYPE("type"),
    TOSTR("tostr"),
    TONUM("tonum"),
    PRINT("print"),
    INPUT("input"),
    TIME("time"),
    SAVE("save"),
    LOAD("load"),
    CHAIN("chain"),
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
}