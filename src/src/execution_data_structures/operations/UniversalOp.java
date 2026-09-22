package execution_data_structures.operations;

public enum UniversalOp implements Operation {
    DUP("dup"),
    PRINT("print"),
    LDROP("ldrop"),
    RDROP("rdrop"),
    PICK("pick"),
    THROW("throw"),
    TOSTR("tostr"),
    TONUM("tonum"),

    RUN("run"),
    MAINLEN("mainlen"), /* to be erased */
    ASSIGN("="),
    EXISTS("exists"),
    DEL("del"),
    RESOLVE("resolve"),
    TYPE("type"),
    INPUT("input"),
    TIME("time"),
    CHAIN("chain"),
    MAINSEQ("MAINSEQ"),
    NSPACE("NSPACE"),
    LEN("len");

    private final String token;

    UniversalOp(String token) {
        this.token = token;
    }

    @Override
    public String getToken() {
        return token;
    }
}
