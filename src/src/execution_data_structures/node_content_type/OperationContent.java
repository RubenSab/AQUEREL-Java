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
    /* explicit main manipulation ops */
    DUP("dup", UNARY_GENERIC_SIGNATURE, OperationBehaviour::dup, true),
    LDROP("ldrop", UNARY_GENERIC_SIGNATURE, OperationBehaviour::stub, true),
    RDROP("rdrop", ARG_LESS_SIGNATURE, OperationBehaviour::stub, true),
    PICK("pick", UNARY_NUM_SIGNATURE, OperationBehaviour::stub, true),
    THROW("throw", UNARY_NUM_SIGNATURE, OperationBehaviour::stub, true),
    /* polymer ops */
    APPEND("append", new Class[]{NodeContent.class, BracketContent.class}, OperationBehaviour::stub, false),
    REPLACE("replace", new Class[]{NodeContent.class, NumberContent.class, BracketContent.class}, OperationBehaviour::stub, false),
    SPLICE("splice", new Class[]{BracketContent.class, NumberContent.class, BracketContent.class}, OperationBehaviour::stub, false),
    REMOVE("remove", new Class[]{NumberContent.class, BracketContent.class}, OperationBehaviour::stub, false),
    GET("retrieve", new Class[]{NumberContent.class, BracketContent.class}, OperationBehaviour::stub, false),
    RUN("run", new Class[]{BracketContent.class}, OperationBehaviour::stub, false),
    /* namespace ops */
    ASSIGN("=", new Class[]{NameContent.class, NodeContent.class}, OperationBehaviour::stub, true),
    EXISTS("exists", UNARY_NAME_SIGNATURE, OperationBehaviour::stub, true),
    DEL("del", UNARY_NAME_SIGNATURE, OperationBehaviour::stub, true),
    RESOLVE("resolve", UNARY_NAME_SIGNATURE, OperationBehaviour::stub, true),
    /* type and casting ops */
    TYPE("type", UNARY_GENERIC_SIGNATURE, OperationBehaviour::stub, true),
    TOSTR("tostr", UNARY_GENERIC_SIGNATURE, OperationBehaviour::stub, false),
    TONUM("tonum", UNARY_STR_SIGNATURE, OperationBehaviour::stub, false),
    /* console ops */
    PRINT("print", UNARY_STR_SIGNATURE, OperationBehaviour::stub, true),
    INPUT("input", ARG_LESS_SIGNATURE, OperationBehaviour::stub, true),
    /* debugging ops */
    MAINSEQ("MAINSEQ", ARG_LESS_SIGNATURE, OperationBehaviour::stub, true),
    NSPACE("NSPACE", ARG_LESS_SIGNATURE, OperationBehaviour::stub, true),
    /* clock ops */
    NANOS("nanos", ARG_LESS_SIGNATURE, OperationBehaviour::stub, false),
    EPOCHSEC("epochsec", ARG_LESS_SIGNATURE, OperationBehaviour::stub, false),
    /* polymorphic ops */
    LEN("len", UNARY_GENERIC_SIGNATURE, OperationBehaviour::stub, false),
    /* context ops (stub) */
    SAVE("save", new Class[]{}, OperationBehaviour::stub, true),
    LOAD("load", new Class[]{}, OperationBehaviour::stub, true);

    public static final Set<String> OP_NAMES = new HashSet<>(
            Arrays.stream(values())
                    .map(OperationContent::getToken)
                    .collect(Collectors.toList())
    );

    private final String token;
    private final Class<?>[] signature;
    private final Function<Context, Polymer> opMethod;
    private final boolean contextful;
    private Context context;

    OperationContent(String token, Class<?>[] signature, Function<Context, Polymer> op_method, boolean contextful) {
        this.token = token;
        this.signature = signature;
        this.opMethod = op_method;
        this.contextful = contextful;
        this.context = null;
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

    public Polymer computeResult(Context context) {
        if (contextful) {
            this.context = context;
        }
        return this.opMethod.apply(context);
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

        public static Polymer add(Context context) {
            Node<?> b = context.mainPolymer().getEnzyme().getPrev();
            Node<?> a = b.getPrev();
            context.mainPolymer().extract_polymer(a, b);
            return new Polymer(new Node<>(
                    ((NumberContent) a.getContent()).add((NumberContent) b.getContent())));
        }

        public static Polymer sub(Context context) {
            Node<?> b = context.mainPolymer().getEnzyme().getPrev();
            Node<?> a = b.getPrev();
            context.mainPolymer().extract_polymer(a, b);
            return new Polymer(new Node<>(
                    ((NumberContent) a.getContent()).sub((NumberContent) b.getContent())));
        }

        public static Polymer mul(Context context) {
            Node<?> b = context.mainPolymer().getEnzyme().getPrev();
            Node<?> a = b.getPrev();
            context.mainPolymer().extract_polymer(a, b);
            return new Polymer(new Node<>(
                    ((NumberContent) a.getContent()).mul((NumberContent) b.getContent())));
        }

        public static Polymer div(Context context) {
            Node<?> b = context.mainPolymer().getEnzyme().getPrev();
            Node<?> a = b.getPrev();
            context.mainPolymer().extract_polymer(a, b);
            return new Polymer(new Node<>(
                    ((NumberContent) a.getContent()).div((NumberContent) b.getContent())));
        }

        public static Polymer pow(Context context) {
            Node<?> b = context.mainPolymer().getEnzyme().getPrev();
            Node<?> a = b.getPrev();
            context.mainPolymer().extract_polymer(a, b);
            return new Polymer(new Node<>(
                    ((NumberContent) a.getContent()).pow((NumberContent) b.getContent())));
        }

        public static Polymer round(Context context) {
            Node<?> b = context.mainPolymer().getEnzyme().getPrev();
            Node<?> a = b.getPrev();
            context.mainPolymer().extract_polymer(a, b);
            return new Polymer(new Node<>(
                    ((NumberContent) a.getContent()).round((NumberContent) b.getContent())));
        }

        public static Polymer eq(Context context) {
            Node<?> b = context.mainPolymer().getEnzyme().getPrev();
            Node<?> a = b.getPrev();
            context.mainPolymer().extract_polymer(a, b);
            return new Polymer(new Node<>(
                    ((NumberContent) a.getContent()).eq((NumberContent) b.getContent())));
        }

        public static Polymer neq(Context context) {
            Node<?> b = context.mainPolymer().getEnzyme().getPrev();
            Node<?> a = b.getPrev();
            context.mainPolymer().extract_polymer(a, b);
            return new Polymer(new Node<>(
                    ((NumberContent) a.getContent()).neq((NumberContent) b.getContent())));
        }

        public static Polymer gt(Context context) {
            Node<?> b = context.mainPolymer().getEnzyme().getPrev();
            Node<?> a = b.getPrev();
            context.mainPolymer().extract_polymer(a, b);
            return new Polymer(new Node<>(
                    ((NumberContent) a.getContent()).gt((NumberContent) b.getContent())));
        }

        public static Polymer lt(Context context) {
            Node<?> b = context.mainPolymer().getEnzyme().getPrev();
            Node<?> a = b.getPrev();
            context.mainPolymer().extract_polymer(a, b);
            return new Polymer(new Node<>(
                    ((NumberContent) a.getContent()).lt((NumberContent) b.getContent())));
        }

        public static Polymer geq(Context context) {
            Node<?> b = context.mainPolymer().getEnzyme().getPrev();
            Node<?> a = b.getPrev();
            context.mainPolymer().extract_polymer(a, b);
            return new Polymer(new Node<>(
                    ((NumberContent) a.getContent()).geq((NumberContent) b.getContent())));
        }

        public static Polymer leq(Context context) {
            Node<?> b = context.mainPolymer().getEnzyme().getPrev();
            Node<?> a = b.getPrev();
            context.mainPolymer().extract_polymer(a, b);
            return new Polymer(new Node<>(
                    ((NumberContent) a.getContent()).leq((NumberContent) b.getContent())));
        }

        public static Polymer and(Context context) {
            Node<?> b = context.mainPolymer().getEnzyme().getPrev();
            Node<?> a = b.getPrev();
            context.mainPolymer().extract_polymer(a, b);
            return new Polymer(new Node<>(
                    ((NumberContent) a.getContent()).and((NumberContent) b.getContent())));
        }

        public static Polymer or(Context context) {
            Node<?> b = context.mainPolymer().getEnzyme().getPrev();
            Node<?> a = b.getPrev();
            context.mainPolymer().extract_polymer(a, b);
            return new Polymer(new Node<>(
                    ((NumberContent) a.getContent()).or((NumberContent) b.getContent())));
        }

        public static Polymer xor(Context context) {
            Node<?> b = context.mainPolymer().getEnzyme().getPrev();
            Node<?> a = b.getPrev();
            context.mainPolymer().extract_polymer(a, b);
            return new Polymer(new Node<>(
                    ((NumberContent) a.getContent()).xor((NumberContent) b.getContent())));
        }

        /* unary num op */

        public static Polymer floor(Context context) {
            Node<?> a = context.mainPolymer().getEnzyme().getPrev();
            return new Polymer(new Node<>(((NumberContent) a.getContent()).floor()));
        }

        public static Polymer ceil(Context context) {
            Node<?> a = context.mainPolymer().getEnzyme().getPrev();
            return new Polymer(new Node<>(((NumberContent) a.getContent()).ceil()));
        }

        public static Polymer not(Context context) {
            Node<?> a = context.mainPolymer().getEnzyme().getPrev();
            return new Polymer(new Node<>(((NumberContent) a.getContent()).not()));
        }

        /* string op */

        public static Polymer getchar(Context context) {
            Node<?> b = context.mainPolymer().getEnzyme().getPrev();
            Node<?> a = b.getPrev();
            context.mainPolymer().extract_polymer(a, b);
            return new Polymer(new Node<>(
                    ((StringContent) b.getContent()).getchar(((Double) a.getContent().getValue()).intValue())));
        }

        public static Polymer join(Context context) {
            Node<?> b = context.mainPolymer().getEnzyme().getPrev();
            Node<?> a = b.getPrev();
            context.mainPolymer().extract_polymer(a, b);
            return new Polymer(new Node<>(
                    ((StringContent) a.getContent()).join((StringContent) b.getContent())));
        }

        /* explicit main manipulation ops */

        public static Polymer dup(Context context) {
            return null;
        }

        public static Polymer stub(Context context) {
            System.out.println("not yet implemented");
            return null;
        }
    }
}
