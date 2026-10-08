package execution_data_structures.node_content_type;


import execution_data_structures.Node;
import execution_data_structures.Polymer;
import execution_data_structures.context.Context;

import java.util.Arrays;
import java.util.Set;
import java.util.HashSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import static utils.Utils.*;

public enum OperationContent implements NodeContent<OperationContent> {
    /* binary num ops */
    ADD("+", BINARY_NUM_SIGNATURE, OperationBehaviour::add),
    SUB("-", BINARY_NUM_SIGNATURE, OperationBehaviour::sub),
    MUL("*", BINARY_NUM_SIGNATURE, OperationBehaviour::mul),
    DIV("/", BINARY_NUM_SIGNATURE, OperationBehaviour::div),
    POW("^", BINARY_NUM_SIGNATURE, OperationBehaviour::pow),
    ROUND("round", BINARY_NUM_SIGNATURE, OperationBehaviour::round),
    EQ("==", BINARY_NUM_SIGNATURE, OperationBehaviour::eq),
    NEQ("!=", BINARY_NUM_SIGNATURE, OperationBehaviour::neq),
    GT(">", BINARY_NUM_SIGNATURE, OperationBehaviour::gt),
    LT("<", BINARY_NUM_SIGNATURE, OperationBehaviour::lt),
    GEQ(">=", BINARY_NUM_SIGNATURE, OperationBehaviour::geq),
    LEQ("<=", BINARY_NUM_SIGNATURE, OperationBehaviour::leq),
    AND("and", BINARY_NUM_SIGNATURE, OperationBehaviour::and),
    OR("or", BINARY_NUM_SIGNATURE, OperationBehaviour::or),
    XOR("xor", BINARY_NUM_SIGNATURE, OperationBehaviour::xor),
    /* unary num ops */
    FLOOR("floor", UNARY_NUM_SIGNATURE, OperationBehaviour::floor),
    CEIL("ceil", UNARY_NUM_SIGNATURE, OperationBehaviour::ceil),
    NOT("not", UNARY_NUM_SIGNATURE, OperationBehaviour::not),
    /* string ops */
    GETCHAR("getchar", new Class[]{NumberContent.class, StringContent.class}, OperationBehaviour::getchar),
    JOIN("join", new Class[]{StringContent.class, StringContent.class}, OperationBehaviour::join),
    /* explicit main manipulation ops */
    DUP("dup", UNARY_GENERIC_SIGNATURE, OperationBehaviour::dup),
    LDROP("ldrop", UNARY_GENERIC_SIGNATURE, OperationBehaviour::stub),
    RDROP("rdrop", ARG_LESS_SIGNATURE, OperationBehaviour::stub),
    PICK("pick", UNARY_NUM_SIGNATURE, OperationBehaviour::stub),
    THROW("throw", UNARY_NUM_SIGNATURE, OperationBehaviour::stub),
    /* polymer ops */
    APPEND("append", new Class[]{NodeContent.class, BracketContent.class}, OperationBehaviour::stub),
    REPLACE("replace", new Class[]{NodeContent.class, NumberContent.class, BracketContent.class}, OperationBehaviour::stub),
    SPLICE("splice", new Class[]{BracketContent.class, NumberContent.class, BracketContent.class}, OperationBehaviour::stub),
    REMOVE("remove", new Class[]{NumberContent.class, BracketContent.class}, OperationBehaviour::stub),
    GET("retrieve", new Class[]{NumberContent.class, BracketContent.class}, OperationBehaviour::stub),
    RUN("run", new Class[]{BracketContent.class}, OperationBehaviour::stub),
    /* namespace ops */
    ASSIGN("=", new Class[]{NameContent.class, NodeContent.class}, OperationBehaviour::stub),
    EXISTS("exists", UNARY_NAME_SIGNATURE, OperationBehaviour::stub),
    DEL("del", UNARY_NAME_SIGNATURE, OperationBehaviour::stub),
    RESOLVE("resolve", UNARY_NAME_SIGNATURE, OperationBehaviour::stub),
    /* type and casting ops */
    TYPE("type", UNARY_GENERIC_SIGNATURE, OperationBehaviour::stub),
    TOSTR("tostr", UNARY_GENERIC_SIGNATURE, OperationBehaviour::stub),
    TONUM("tonum", UNARY_STR_SIGNATURE, OperationBehaviour::stub),
    /* console ops */
    PRINT("print", UNARY_STR_SIGNATURE, OperationBehaviour::stub),
    INPUT("input", ARG_LESS_SIGNATURE, OperationBehaviour::stub),
    /* debugging ops */
    MAINSEQ("MAINSEQ", ARG_LESS_SIGNATURE, OperationBehaviour::stub),
    NSPACE("NSPACE", ARG_LESS_SIGNATURE, OperationBehaviour::stub),
    /* clock ops */
    NANOS("nanos", ARG_LESS_SIGNATURE, OperationBehaviour::stub),
    EPOCHSEC("epochsec", ARG_LESS_SIGNATURE, OperationBehaviour::stub),
    /* polymorphic ops */
    LEN("len", UNARY_GENERIC_SIGNATURE, OperationBehaviour::stub),
    /* context ops (stub) */
    SAVE("save", new Class[]{}, OperationBehaviour::stub),
    LOAD("load", new Class[]{}, OperationBehaviour::stub);

    public static final Set<String> OP_NAMES = new HashSet<>(
            Arrays.stream(values())
                    .map(OperationContent::getToken)
                    .collect(Collectors.toList())
    );

    private final String token;
    private final Class<?>[] signature;
    private final Function<Context, Node<?>> opMethod;

    OperationContent(String token, Class<?>[] signature, Function<Context, Node<?>> opMethod) {
        this.token = token;
        this.signature = signature;
        this.opMethod = opMethod;
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

    public Function<Context, Node<?>> getOpMethod() {
        return opMethod;
    }

    public Node<?> computeResult(Context context) {
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

        public static Node<?> add(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> b = op.getPrev();
            Node<?> a = b.getPrev();
            Node<?> result = new Node<>(((NumberContent) a.getContent()).add((NumberContent) b.getContent()));
            context.mainPolymer().extract_polymer(a, b);
            context.mainPolymer().replace_node_with_node(op, result);
            return result.getNext();
        }

        public static Node<?> sub(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> b = op.getPrev();
            Node<?> a = b.getPrev();
            Node<?> result = new Node<>(((NumberContent) a.getContent()).sub((NumberContent) b.getContent()));
            context.mainPolymer().extract_polymer(a, b);
            context.mainPolymer().replace_node_with_node(op, result);
            return result.getNext();
        }

        public static Node<?> mul(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> b = op.getPrev();
            Node<?> a = b.getPrev();
            Node<?> result = new Node<>(((NumberContent) a.getContent()).mul((NumberContent) b.getContent()));
            context.mainPolymer().extract_polymer(a, b);
            context.mainPolymer().replace_node_with_node(op, result);
            return result.getNext();
        }

        public static Node<?> div(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> b = op.getPrev();
            Node<?> a = b.getPrev();
            Node<?> result = new Node<>(((NumberContent) a.getContent()).div((NumberContent) b.getContent()));
            context.mainPolymer().extract_polymer(a, b);
            context.mainPolymer().replace_node_with_node(op, result);
            return result.getNext();
        }

        public static Node<?> pow(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> b = op.getPrev();
            Node<?> a = b.getPrev();
            Node<?> result = new Node<>(((NumberContent) a.getContent()).pow((NumberContent) b.getContent()));
            context.mainPolymer().extract_polymer(a, b);
            context.mainPolymer().replace_node_with_node(op, result);
            return result.getNext();
        }

        public static Node<?> round(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> b = op.getPrev();
            Node<?> a = b.getPrev();
            Node<?> result = new Node<>(((NumberContent) a.getContent()).round((NumberContent) b.getContent()));
            context.mainPolymer().extract_polymer(a, b);
            context.mainPolymer().replace_node_with_node(op, result);
            return result.getNext();
        }

        public static Node<?> eq(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> b = op.getPrev();
            Node<?> a = b.getPrev();
            Node<?> result = new Node<>(((NumberContent) a.getContent()).eq((NumberContent) b.getContent()));
            context.mainPolymer().extract_polymer(a, b);
            context.mainPolymer().replace_node_with_node(op, result);
            return result.getNext();
        }

        public static Node<?> neq(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> b = op.getPrev();
            Node<?> a = b.getPrev();
            Node<?> result = new Node<>(((NumberContent) a.getContent()).neq((NumberContent) b.getContent()));
            context.mainPolymer().extract_polymer(a, b);
            context.mainPolymer().replace_node_with_node(op, result);
            return result.getNext();
        }

        public static Node<?> gt(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> b = op.getPrev();
            Node<?> a = b.getPrev();
            Node<?> result = new Node<>(((NumberContent) a.getContent()).gt((NumberContent) b.getContent()));
            context.mainPolymer().extract_polymer(a, b);
            context.mainPolymer().replace_node_with_node(op, result);
            return result.getNext();
        }

        public static Node<?> lt(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> b = op.getPrev();
            Node<?> a = b.getPrev();
            Node<?> result = new Node<>(((NumberContent) a.getContent()).lt((NumberContent) b.getContent()));
            context.mainPolymer().extract_polymer(a, b);
            context.mainPolymer().replace_node_with_node(op, result);
            return result.getNext();
        }

        public static Node<?> geq(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> b = op.getPrev();
            Node<?> a = b.getPrev();
            Node<?> result = new Node<>(((NumberContent) a.getContent()).geq((NumberContent) b.getContent()));
            context.mainPolymer().extract_polymer(a, b);
            context.mainPolymer().replace_node_with_node(op, result);
            return result.getNext();
        }

        public static Node<?> leq(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> b = op.getPrev();
            Node<?> a = b.getPrev();
            Node<?> result = new Node<>(((NumberContent) a.getContent()).leq((NumberContent) b.getContent()));
            context.mainPolymer().extract_polymer(a, b);
            context.mainPolymer().replace_node_with_node(op, result);
            return result.getNext();
        }

        public static Node<?> and(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> b = op.getPrev();
            Node<?> a = b.getPrev();
            Node<?> result = new Node<>(((NumberContent) a.getContent()).and((NumberContent) b.getContent()));
            context.mainPolymer().extract_polymer(a, b);
            context.mainPolymer().replace_node_with_node(op, result);
            return result.getNext();
        }

        public static Node<?> or(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> b = op.getPrev();
            Node<?> a = b.getPrev();
            Node<?> result = new Node<>(((NumberContent) a.getContent()).or((NumberContent) b.getContent()));
            context.mainPolymer().extract_polymer(a, b);
            context.mainPolymer().replace_node_with_node(op, result);
            return result.getNext();
        }

        public static Node<?> xor(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> b = op.getPrev();
            Node<?> a = b.getPrev();
            Node<?> result = new Node<>(((NumberContent) a.getContent()).xor((NumberContent) b.getContent()));
            context.mainPolymer().extract_polymer(a, b);
            context.mainPolymer().replace_node_with_node(op, result);
            return result.getNext();
        }

        /* unary num op */

        public static Node<?> floor(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> arg = op.getPrev();
            Node<?> result = new Node<>(((NumberContent) arg.getContent()).floor());
            context.mainPolymer().extract_node(arg);
            context.mainPolymer().replace_node_with_node(op, result);
            return result.getNext();
        }

        public static Node<?> ceil(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> arg = op.getPrev();
            Node<?> result = new Node<>(((NumberContent) arg.getContent()).ceil());
            context.mainPolymer().extract_node(arg);
            context.mainPolymer().replace_node_with_node(op, result);
            return result.getNext();
        }

        public static Node<?> not(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> arg = op.getPrev();
            Node<?> result = new Node<>(((NumberContent) arg.getContent()).not());
            context.mainPolymer().extract_node(arg);
            context.mainPolymer().replace_node_with_node(op, result);
            return result.getNext();
        }

        /* string op */

        public static Node<?> getchar(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> b = op.getPrev();
            Node<?> a = b.getPrev();
            Node<?> result = new Node<>(
                    ((StringContent) b.getContent()).getchar(((Double) a.getContent().getValue()).intValue()));
            context.mainPolymer().extract_polymer(a, b);
            context.mainPolymer().replace_node_with_node(op, result);
            return result.getNext();
        }

        public static Node<?> join(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> b = op.getPrev();
            Node<?> a = b.getPrev();
            Node<?> result = new Node<>(((StringContent) a.getContent()).join((StringContent) b.getContent()));
            context.mainPolymer().extract_polymer(a, b);
            context.mainPolymer().replace_node_with_node(op, result);
            return result.getNext();
        }

        /* explicit main manipulation ops */

        public static Node<?> dup(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Polymer arg = op.getPrev().getCorrespondingPolymer().clone();
            context.mainPolymer().replace_node_with_polymer(op, arg);
            return arg.getNext();
        }

        public static Node<?> stub(Context context) {
            System.out.println("not yet implemented");
            return null;
        }
    }
}
