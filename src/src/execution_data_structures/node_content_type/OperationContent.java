package execution_data_structures.node_content_type;


import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;

import static utils.Utils.*;

public enum OperationContent implements NodeContent<OperationContent> {
    /* binary num op */
    ADD("+", BINARY_NUM_SIGNATURE),
    SUB("-", BINARY_NUM_SIGNATURE),
    MUL("*", BINARY_NUM_SIGNATURE),
    DIV("/", BINARY_NUM_SIGNATURE),
    POW("^", BINARY_NUM_SIGNATURE),
    ROUND("round", BINARY_NUM_SIGNATURE),
    EQ("==", BINARY_NUM_SIGNATURE),
    NEQ("!=", BINARY_NUM_SIGNATURE),
    GT(">", BINARY_NUM_SIGNATURE),
    LT("<", BINARY_NUM_SIGNATURE),
    GEQ(">=", BINARY_NUM_SIGNATURE),
    LEQ("<=", BINARY_NUM_SIGNATURE),
    AND("and", BINARY_NUM_SIGNATURE),
    OR("or", BINARY_NUM_SIGNATURE),
    XOR("xor", BINARY_NUM_SIGNATURE),
    /* unary num op */
    FLOOR("floor", UNARY_NUM_SIGNATURE),
    CEIL("ceil", UNARY_NUM_SIGNATURE),
    NOT("not", UNARY_NUM_SIGNATURE),
    /* string op */
    GETCHAR("getchar", new Class[]{NumberContent.class, StringContent.class}),
    JOIN("join", new Class[]{StringContent.class, StringContent.class}),
    /* polymer op */
    APPEND("append", new Class[]{NodeContent.class, BracketContent.class}),
    REPLACE("replace", new Class[]{NodeContent.class, NumberContent.class, BracketContent.class}),
    SPLICE("splice", new Class[]{BracketContent.class, NumberContent.class, BracketContent.class}),
    REMOVE("remove", new Class[]{NumberContent.class, BracketContent.class}),
    GET("retrieve", new Class[]{NumberContent.class, BracketContent.class}),
    RUN("run", new Class[]{BracketContent.class}),
    /* context op (stub) */
    SAVE("save", new Class[]{}),
    LOAD("load", new Class[]{}),
    /* others */
    DUP("dup", UNARY_GENERIC_SIGNATURE),
    LDROP("ldrop", ARG_LESS_SIGNATURE),
    RDROP("rdrop", ARG_LESS_SIGNATURE),
    PICK("pick", ARG_LESS_SIGNATURE),
    THROW("throw", UNARY_NUM_SIGNATURE),
    MAINLEN("mainlen", UNARY_NUM_SIGNATURE),
    ASSIGN("=", new Class[]{NameContent.class, NodeContent.class}),
    EXISTS("exists", UNARY_NAME_SIGNATURE),
    DEL("del", UNARY_NAME_SIGNATURE),
    RESOLVE("resolve", UNARY_NAME_SIGNATURE),
    TYPE("type", UNARY_GENERIC_SIGNATURE),
    TOSTR("tostr", UNARY_GENERIC_SIGNATURE),
    TONUM("tonum", UNARY_STR_SIGNATURE),
    PRINT("print", UNARY_STR_SIGNATURE),
    INPUT("input", ARG_LESS_SIGNATURE),
    TIME("time", ARG_LESS_SIGNATURE),
    RAND("rand", ARG_LESS_SIGNATURE),
    SEED("seed", UNARY_NUM_SIGNATURE),
    MAINSEQ("MAINSEQ", ARG_LESS_SIGNATURE),
    NSPACE("NSPACE", ARG_LESS_SIGNATURE),
    LEN("len", UNARY_GENERIC_SIGNATURE);

    public static final Set<String> OP_NAMES = new HashSet<>(Arrays.asList(
            // binary num op
            "+", "-", "*", "/", "^", "round",
            "==", "!=", ">", "<", ">=", "<=",
            "and", "or", "xor",
            // unary num op
            "floor", "ceil", "not",
            // string op
            "getchar", "join",
            // polymer op
            "append", "replace", "splice", "remove", "retrieve", "run",
            // context op (stub)
            "save", "load",
            // others
            "dup", "ldrop", "rdrop", "pick", "throw", "mainlen",
            "=", "exists", "del", "resolve", "type", "tostr", "tonum",
            "print", "input", "time", "rand", "seed",
            "MAINSEQ", "NSPACE", "len"
    ));

    private final String token;
    private final Class<?>[] signature;

    OperationContent(String token, Class<?>[] signature) {
        this.token = token;
        this.signature = signature;
    }

    @Override
    public OperationContent getValue() {
        return this;
    }

    public String getToken() {
        return token;
    }

    public Class<?>[] getSignature() {
        return signature;
    }

    public static OperationContent fromToken(String symbol) {
        for (OperationContent op : values()) {
            if (op.getToken().equals(symbol)) {
                return op;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return token;
    }
}
