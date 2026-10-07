package execution_data_structures.node_content_type;


import execution_data_structures.Node;
import execution_data_structures.Polymer;
import execution_data_structures.context.Context;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.function.Function;

import static utils.Utils.*;

public enum OperationContent implements NodeContent<OperationContent> {
    /* binary num op */
    ADD("+", BINARY_NUM_SIGNATURE, OperationBehaviour::add, false),
    SUB("-", BINARY_NUM_SIGNATURE, OperationBehaviour::sub, false),
    MUL("*", BINARY_NUM_SIGNATURE, OperationBehaviour::mul, false),
    DIV("/", BINARY_NUM_SIGNATURE, OperationBehaviour::div, false),
    POW("^", BINARY_NUM_SIGNATURE, OperationBehaviour::pow, false),
    ROUND("round", BINARY_NUM_SIGNATURE, OperationBehaviour::round, false),
    EQ("==", BINARY_NUM_SIGNATURE, OperationBehaviour::eq, false),
    NEQ("!=", BINARY_NUM_SIGNATURE, OperationBehaviour::neq, false),
    GT(">", BINARY_NUM_SIGNATURE, OperationBehaviour::gt, false),
    LT("<", BINARY_NUM_SIGNATURE, OperationBehaviour::lt, false),
    GEQ(">=", BINARY_NUM_SIGNATURE, OperationBehaviour::geq, false),
    LEQ("<=", BINARY_NUM_SIGNATURE, OperationBehaviour::leq, false),
    AND("and", BINARY_NUM_SIGNATURE, OperationBehaviour::and, false),
    OR("or", BINARY_NUM_SIGNATURE, OperationBehaviour::or, false),
    XOR("xor", BINARY_NUM_SIGNATURE, OperationBehaviour::xor, false),
    /* unary num op */
    FLOOR("floor", UNARY_NUM_SIGNATURE, OperationBehaviour::floor, false),
    CEIL("ceil", UNARY_NUM_SIGNATURE, OperationBehaviour::ceil, false),
    NOT("not", UNARY_NUM_SIGNATURE, OperationBehaviour::not, false),
    /* string op */
    GETCHAR("getchar", new Class[]{NumberContent.class, StringContent.class}, OperationBehaviour::getchar, false),
    JOIN("join", new Class[]{StringContent.class, StringContent.class}, OperationBehaviour::join, false),
    /* polymer op */
    APPEND("append", new Class[]{NodeContent.class, BracketContent.class}, OperationBehaviour::add, false),
    REPLACE("replace", new Class[]{NodeContent.class, NumberContent.class, BracketContent.class}, OperationBehaviour::add, false),
    SPLICE("splice", new Class[]{BracketContent.class, NumberContent.class, BracketContent.class}, OperationBehaviour::add, false),
    REMOVE("remove", new Class[]{NumberContent.class, BracketContent.class}, OperationBehaviour::add, false),
    GET("retrieve", new Class[]{NumberContent.class, BracketContent.class}, OperationBehaviour::add, false),
    RUN("run", new Class[]{BracketContent.class}, OperationBehaviour::add, false),
    /* context op (stub) */
    SAVE("save", new Class[]{}, OperationBehaviour::add, true),
    LOAD("load", new Class[]{}, OperationBehaviour::add, true),
    /* others */
    DUP("dup", UNARY_GENERIC_SIGNATURE, OperationBehaviour::add, true),
    LDROP("ldrop", ARG_LESS_SIGNATURE, OperationBehaviour::add, true),
    RDROP("rdrop", ARG_LESS_SIGNATURE, OperationBehaviour::add, true),
    PICK("pick", ARG_LESS_SIGNATURE, OperationBehaviour::add, true),
    THROW("throw", UNARY_NUM_SIGNATURE, OperationBehaviour::add, true),
    MAINLEN("mainlen", UNARY_NUM_SIGNATURE, OperationBehaviour::add, true),
    ASSIGN("=", new Class[]{NameContent.class, NodeContent.class}, OperationBehaviour::add, true),
    EXISTS("exists", UNARY_NAME_SIGNATURE, OperationBehaviour::add, true),
    DEL("del", UNARY_NAME_SIGNATURE, OperationBehaviour::add, true),
    RESOLVE("resolve", UNARY_NAME_SIGNATURE, OperationBehaviour::add, true),
    TYPE("type", UNARY_GENERIC_SIGNATURE, OperationBehaviour::add, true),
    TOSTR("tostr", UNARY_GENERIC_SIGNATURE, OperationBehaviour::add, false),
    TONUM("tonum", UNARY_STR_SIGNATURE, OperationBehaviour::add, false),
    PRINT("print", UNARY_STR_SIGNATURE, OperationBehaviour::add, true),
    INPUT("input", ARG_LESS_SIGNATURE, OperationBehaviour::add, true),
    TIME("time", ARG_LESS_SIGNATURE, OperationBehaviour::add, false),
    MAINSEQ("MAINSEQ", ARG_LESS_SIGNATURE, OperationBehaviour::add, true),
    NSPACE("NSPACE", ARG_LESS_SIGNATURE, OperationBehaviour::add, true),
    LEN("len", UNARY_GENERIC_SIGNATURE, OperationBehaviour::add, false);

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
            "print", "input", "time",
            "MAINSEQ", "NSPACE", "len"
    ));

    private final String token;
    private final Class<?>[] signature;
    private final Function<List<NodeContent<?>>, Polymer> opMethod;
    private final boolean contextful;

    OperationContent(String token, Class<?>[] signature, Function<List<NodeContent<?>>, Polymer> op_method, boolean contextful) {
        this.token = token;
        this.signature = signature;
        this.opMethod = op_method;
        this.contextful = contextful;
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

    public Polymer computeResult(List<NodeContent<?>> args, Context context) {
        return this.opMethod.apply(args);
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

    private static class OperationBehaviour {
        public static Polymer add(List<NodeContent<?>> args) {
            return new Polymer(new Node(
                    ((NumberContent) args.get(0)).add((NumberContent) args.get(1))));
        }

        public static Polymer sub(List<NodeContent<?>> args) {
            return new Polymer(new Node(
                    ((NumberContent) args.get(0)).sub((NumberContent) args.get(1))));
        }

        public static Polymer mul(List<NodeContent<?>> args) {
            return new Polymer(new Node(
                    ((NumberContent) args.get(0)).mul((NumberContent) args.get(1))));
        }

        public static Polymer div(List<NodeContent<?>> args) {
            return new Polymer(new Node(
                    ((NumberContent) args.get(0)).div((NumberContent) args.get(1))));
        }

        public static Polymer pow(List<NodeContent<?>> args) {
            return new Polymer(new Node(
                    ((NumberContent) args.get(0)).pow((NumberContent) args.get(1))));
        }

        public static Polymer round(List<NodeContent<?>> args) {
            return new Polymer(new Node(
                    ((NumberContent) args.get(0)).round((NumberContent) args.get(1))));
        }

        public static Polymer eq(List<NodeContent<?>> args) {
            return new Polymer(new Node(
                    ((NumberContent) args.get(0)).eq((NumberContent) args.get(1))));
        }

        public static Polymer neq(List<NodeContent<?>> args) {
            return new Polymer(new Node(
                    ((NumberContent) args.get(0)).neq((NumberContent) args.get(1))));
        }

        public static Polymer gt(List<NodeContent<?>> args) {
            return new Polymer(new Node(
                    ((NumberContent) args.get(0)).gt((NumberContent) args.get(1))));
        }

        public static Polymer lt(List<NodeContent<?>> args) {
            return new Polymer(new Node(
                    ((NumberContent) args.get(0)).lt((NumberContent) args.get(1))));
        }

        public static Polymer geq(List<NodeContent<?>> args) {
            return new Polymer(new Node(
                    ((NumberContent) args.get(0)).geq((NumberContent) args.get(1))));
        }

        public static Polymer leq(List<NodeContent<?>> args) {
            return new Polymer(new Node(
                    ((NumberContent) args.get(0)).leq((NumberContent) args.get(1))));
        }

        public static Polymer and(List<NodeContent<?>> args) {
            return new Polymer(new Node(
                    ((NumberContent) args.get(0)).and((NumberContent) args.get(1))));
        }

        public static Polymer or(List<NodeContent<?>> args) {
            return new Polymer(new Node(
                    ((NumberContent) args.get(0)).or((NumberContent) args.get(1))));
        }

        public static Polymer xor(List<NodeContent<?>> args) {
            return new Polymer(new Node(
                    ((NumberContent) args.get(0)).xor((NumberContent) args.get(1))));
        }

        public static Polymer floor(List<NodeContent<?>> args) {
            return new Polymer(new Node(((NumberContent) args.get(0)).floor()));
        }

        public static Polymer ceil(List<NodeContent<?>> args) {
            return new Polymer(new Node(((NumberContent) args.get(0)).ceil()));
        }

        public static Polymer not(List<NodeContent<?>> args) {
            return new Polymer(new Node(((NumberContent) args.get(0)).not()));
        }

        public static Polymer getchar(List<NodeContent<?>> args) {
            return new Polymer(new Node(((StringContent) args.get(1)).getchar(((Double) args.get(0).getValue()).intValue())));
        }

        public static Polymer join(List<NodeContent<?>> args) {
            return new Polymer(new Node(((StringContent) args.get(0)).join((StringContent) args.get(1))));
        }
    }
}
