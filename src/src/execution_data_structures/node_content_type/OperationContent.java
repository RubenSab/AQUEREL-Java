package execution_data_structures.node_content_type;


import execution_data_structures.Node;
import execution_data_structures.Polymer;
import execution_data_structures.context.Context;

import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.HashSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import static utils.Utils.*;

public enum OperationContent implements NodeContent<OperationContent> {
    /* binary num ops */
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
    /* unary num ops */
    FLOOR("floor", UNARY_NUM_SIGNATURE, OperationBehaviour::floor, false),
    CEIL("ceil", UNARY_NUM_SIGNATURE, OperationBehaviour::ceil, false),
    NOT("not", UNARY_NUM_SIGNATURE, OperationBehaviour::not, false),
    /* string ops */
    GETCHAR("getchar", new Class[]{NumberContent.class, StringContent.class}, OperationBehaviour::getchar, false),
    JOIN("join", new Class[]{StringContent.class, StringContent.class}, OperationBehaviour::join, false),
    /* polymer ops */
    APPEND("append", new Class[]{NodeContent.class, BracketContent.class}, OperationBehaviour::add, false),
    REPLACE("replace", new Class[]{NodeContent.class, NumberContent.class, BracketContent.class}, OperationBehaviour::add, false),
    SPLICE("splice", new Class[]{BracketContent.class, NumberContent.class, BracketContent.class}, OperationBehaviour::add, false),
    REMOVE("remove", new Class[]{NumberContent.class, BracketContent.class}, OperationBehaviour::add, false),
    GET("retrieve", new Class[]{NumberContent.class, BracketContent.class}, OperationBehaviour::add, false),
    RUN("run", new Class[]{BracketContent.class}, OperationBehaviour::add, false),
    /* context ops (stub) */
    SAVE("save", new Class[]{}, OperationBehaviour::add, true),
    LOAD("load", new Class[]{}, OperationBehaviour::add, true),
    /* explicit main manipulation ops */
    DUP("dup", UNARY_GENERIC_SIGNATURE, OperationBehaviour::add, false),
    LDROP("ldrop", ARG_LESS_SIGNATURE, OperationBehaviour::add, false),
    RDROP("rdrop", ARG_LESS_SIGNATURE, OperationBehaviour::add, false),
    PICK("pick", ARG_LESS_SIGNATURE, OperationBehaviour::add, false),
    THROW("throw", UNARY_NUM_SIGNATURE, OperationBehaviour::add, false),
    /* namespace ops */
    ASSIGN("=", new Class[]{NameContent.class, NodeContent.class}, OperationBehaviour::add, true),
    EXISTS("exists", UNARY_NAME_SIGNATURE, OperationBehaviour::add, true),
    DEL("del", UNARY_NAME_SIGNATURE, OperationBehaviour::add, true),
    RESOLVE("resolve", UNARY_NAME_SIGNATURE, OperationBehaviour::add, true),
    /* type and casting ops */
    TYPE("type", UNARY_GENERIC_SIGNATURE, OperationBehaviour::add, true),
    TOSTR("tostr", UNARY_GENERIC_SIGNATURE, OperationBehaviour::add, false),
    TONUM("tonum", UNARY_STR_SIGNATURE, OperationBehaviour::add, false),
    /* console ops */
    PRINT("print", UNARY_STR_SIGNATURE, OperationBehaviour::add, true),
    INPUT("input", ARG_LESS_SIGNATURE, OperationBehaviour::add, true),
    /* debugging ops */
    MAINSEQ("MAINSEQ", ARG_LESS_SIGNATURE, OperationBehaviour::add, true),
    NSPACE("NSPACE", ARG_LESS_SIGNATURE, OperationBehaviour::add, true),
    /* clock ops */
    NANOS("nanos", ARG_LESS_SIGNATURE, OperationBehaviour::add, false),
    EPOCHSEC("epochsec", ARG_LESS_SIGNATURE, OperationBehaviour::add, false),
    /* polymorphic ops */
    LEN("len", UNARY_GENERIC_SIGNATURE, OperationBehaviour::add, false);

    public static final Set<String> OP_NAMES = new HashSet<>(
            Arrays.stream(values())
                    .map(OperationContent::getToken)
                    .collect(Collectors.toList())
    );

    private final String token;
    private final Class<?>[] signature;
    private final Function<List<Node<?>>, Polymer> opMethod;
    private final boolean contextful;

    OperationContent(String token, Class<?>[] signature, Function<List<Node<?>>, Polymer> op_method, boolean contextful) {
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

    public Polymer computeResult(List<Node<?>> args, Context context) {
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

        /* binary num op */

        public static Polymer add(List<Node<?>> args) {
            return new Polymer(new Node<>(
                    ((NumberContent) args.get(0).getContent()).add((NumberContent) args.get(1).getContent())));
        }

        public static Polymer sub(List<Node<?>> args) {
            return new Polymer(new Node<>(
                    ((NumberContent) args.get(0).getContent()).sub((NumberContent) args.get(1).getContent())));
        }

        public static Polymer mul(List<Node<?>> args) {
            return new Polymer(new Node<>(
                    ((NumberContent) args.get(0).getContent()).mul((NumberContent) args.get(1).getContent())));
        }

        public static Polymer div(List<Node<?>> args) {
            return new Polymer(new Node<>(
                    ((NumberContent) args.get(0).getContent()).div((NumberContent) args.get(1).getContent())));
        }

        public static Polymer pow(List<Node<?>> args) {
            return new Polymer(new Node<>(
                    ((NumberContent) args.get(0).getContent()).pow((NumberContent) args.get(1).getContent())));
        }

        public static Polymer round(List<Node<?>> args) {
            return new Polymer(new Node<>(
                    ((NumberContent) args.get(0).getContent()).round((NumberContent) args.get(1).getContent())));
        }

        public static Polymer eq(List<Node<?>> args) {
            return new Polymer(new Node<>(
                    ((NumberContent) args.get(0).getContent()).eq((NumberContent) args.get(1).getContent())));
        }

        public static Polymer neq(List<Node<?>> args) {
            return new Polymer(new Node<>(
                    ((NumberContent) args.get(0).getContent()).neq((NumberContent) args.get(1).getContent())));
        }

        public static Polymer gt(List<Node<?>> args) {
            return new Polymer(new Node<>(
                    ((NumberContent) args.get(0).getContent()).gt((NumberContent) args.get(1).getContent())));
        }

        public static Polymer lt(List<Node<?>> args) {
            return new Polymer(new Node<>(
                    ((NumberContent) args.get(0).getContent()).lt((NumberContent) args.get(1).getContent())));
        }

        public static Polymer geq(List<Node<?>> args) {
            return new Polymer(new Node<>(
                    ((NumberContent) args.get(0).getContent()).geq((NumberContent) args.get(1).getContent())));
        }

        public static Polymer leq(List<Node<?>> args) {
            return new Polymer(new Node<>(
                    ((NumberContent) args.get(0).getContent()).leq((NumberContent) args.get(1).getContent())));
        }

        public static Polymer and(List<Node<?>> args) {
            return new Polymer(new Node<>(
                    ((NumberContent) args.get(0).getContent()).and((NumberContent) args.get(1).getContent())));
        }

        public static Polymer or(List<Node<?>> args) {
            return new Polymer(new Node<>(
                    ((NumberContent) args.get(0).getContent()).or((NumberContent) args.get(1).getContent())));
        }

        public static Polymer xor(List<Node<?>> args) {
            return new Polymer(new Node<>(
                    ((NumberContent) args.get(0).getContent()).xor((NumberContent) args.get(1).getContent())));
        }

        /* unary num op */

        public static Polymer floor(List<Node<?>> args) {
            return new Polymer(new Node<>(((NumberContent) args.getFirst().getContent()).floor()));
        }

        public static Polymer ceil(List<Node<?>> args) {
            return new Polymer(new Node<>(((NumberContent) args.getFirst().getContent()).ceil()));
        }

        public static Polymer not(List<Node<?>> args) {
            return new Polymer(new Node<>(((NumberContent) args.getFirst().getContent()).not()));
        }

        /* string op */

        public static Polymer getchar(List<Node<?>> args) {
            return new Polymer(new Node<>(((StringContent) args.get(1).getContent()).getchar(((Double) args.get(0).getContent().getValue()).intValue())));
        }

        public static Polymer join(List<Node<?>> args) {
            return new Polymer(new Node<>(((StringContent) args.get(0).getContent()).join((StringContent) args.get(1).getContent())));
        }
    }
}
