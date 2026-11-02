package execution_data_structures.node_content_type;


import execution_data_structures.Node;
import execution_data_structures.Polymer;
import execution_data_structures.context.Context;
import execution_data_structures.context.MainPolymer;

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
    LDROP("ldrop", UNARY_GENERIC_SIGNATURE, OperationBehaviour::ldrop),
    RDROP("rdrop", ARG_LESS_SIGNATURE, OperationBehaviour::rdrop),
    PICK("pick", UNARY_NUM_SIGNATURE, OperationBehaviour::pick),
    THROW_LEFT("throw", new Class[]{NodeContent.class, NumberContent.class}, OperationBehaviour::throwLeft),
    /* polymer ops */
    APPEND("append", new Class[]{NodeContent.class, BracketContent.class}, OperationBehaviour::append),
    REPLACE("replace", new Class[]{NodeContent.class, NumberContent.class, BracketContent.class}, OperationBehaviour::replace),
    SPLICE("splice", new Class[]{BracketContent.class, NumberContent.class, BracketContent.class}, OperationBehaviour::splice),
    REMOVE("remove", new Class[]{NumberContent.class, BracketContent.class}, OperationBehaviour::remove),
    GET("retrieve", new Class[]{NumberContent.class, BracketContent.class}, OperationBehaviour::retrieve),
    RUN("run", new Class[]{BracketContent.class}, OperationBehaviour::run),
    /* namespace ops */
    ASSIGN("=", new Class[]{NameContent.class, NodeContent.class}, OperationBehaviour::assign),
    EXISTS("exists", UNARY_NAME_SIGNATURE, OperationBehaviour::exists),
    DEL("del", UNARY_NAME_SIGNATURE, OperationBehaviour::del),
    RESOLVE("resolve", UNARY_NAME_SIGNATURE, OperationBehaviour::resolve),
    /* type and casting ops */
    TYPE("type", UNARY_GENERIC_SIGNATURE, OperationBehaviour::type),
    TOSTR("tostr", UNARY_GENERIC_SIGNATURE, OperationBehaviour::toStr),
    TONUM("tonum", UNARY_STR_SIGNATURE, OperationBehaviour::toNum),
    /* console ops */
    PRINT("print", UNARY_STR_SIGNATURE, OperationBehaviour::print),
    INPUT("input", ARG_LESS_SIGNATURE, OperationBehaviour::input),
    /* debugging ops */
    MAINSEQ("MAINSEQ", ARG_LESS_SIGNATURE, OperationBehaviour::mainSeq),
    NSPACE("NSPACE", ARG_LESS_SIGNATURE, OperationBehaviour::nSpace),
    /* clock ops */
    NANOS("nanos", ARG_LESS_SIGNATURE, OperationBehaviour::nanos),
    EPOCHSEC("epochsec", ARG_LESS_SIGNATURE, OperationBehaviour::epochSec),
    /* polymorphic ops */
    LEN("len", UNARY_GENERIC_SIGNATURE, OperationBehaviour::len),
    /* context ops (stub) */
    SAVE("save", new Class[]{}, OperationBehaviour::save),
    LOAD("load", new Class[]{}, OperationBehaviour::load);

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
            context.mainPolymer().extractPolymer(a, b);
            context.mainPolymer().replaceNodeWithNode(op, result);
            return result.getNext();
        }

        public static Node<?> sub(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> b = op.getPrev();
            Node<?> a = b.getPrev();
            Node<?> result = new Node<>(((NumberContent) a.getContent()).sub((NumberContent) b.getContent()));
            context.mainPolymer().extractPolymer(a, b);
            context.mainPolymer().replaceNodeWithNode(op, result);
            return result.getNext();
        }

        public static Node<?> mul(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> b = op.getPrev();
            Node<?> a = b.getPrev();
            Node<?> result = new Node<>(((NumberContent) a.getContent()).mul((NumberContent) b.getContent()));
            context.mainPolymer().extractPolymer(a, b);
            context.mainPolymer().replaceNodeWithNode(op, result);
            return result.getNext();
        }

        public static Node<?> div(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> b = op.getPrev();
            Node<?> a = b.getPrev();
            Node<?> result = new Node<>(((NumberContent) a.getContent()).div((NumberContent) b.getContent()));
            context.mainPolymer().extractPolymer(a, b);
            context.mainPolymer().replaceNodeWithNode(op, result);
            return result.getNext();
        }

        public static Node<?> pow(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> b = op.getPrev();
            Node<?> a = b.getPrev();
            Node<?> result = new Node<>(((NumberContent) a.getContent()).pow((NumberContent) b.getContent()));
            context.mainPolymer().extractPolymer(a, b);
            context.mainPolymer().replaceNodeWithNode(op, result);
            return result.getNext();
        }

        public static Node<?> round(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> b = op.getPrev();
            Node<?> a = b.getPrev();
            Node<?> result = new Node<>(((NumberContent) a.getContent()).round((NumberContent) b.getContent()));
            context.mainPolymer().extractPolymer(a, b);
            context.mainPolymer().replaceNodeWithNode(op, result);
            return result.getNext();
        }

        public static Node<?> eq(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> b = op.getPrev();
            Node<?> a = b.getPrev();
            Node<?> result = new Node<>(((NumberContent) a.getContent()).eq((NumberContent) b.getContent()));
            context.mainPolymer().extractPolymer(a, b);
            context.mainPolymer().replaceNodeWithNode(op, result);
            return result.getNext();
        }

        public static Node<?> neq(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> b = op.getPrev();
            Node<?> a = b.getPrev();
            Node<?> result = new Node<>(((NumberContent) a.getContent()).neq((NumberContent) b.getContent()));
            context.mainPolymer().extractPolymer(a, b);
            context.mainPolymer().replaceNodeWithNode(op, result);
            return result.getNext();
        }

        public static Node<?> gt(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> b = op.getPrev();
            Node<?> a = b.getPrev();
            Node<?> result = new Node<>(((NumberContent) a.getContent()).gt((NumberContent) b.getContent()));
            context.mainPolymer().extractPolymer(a, b);
            context.mainPolymer().replaceNodeWithNode(op, result);
            return result.getNext();
        }

        public static Node<?> lt(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> b = op.getPrev();
            Node<?> a = b.getPrev();
            Node<?> result = new Node<>(((NumberContent) a.getContent()).lt((NumberContent) b.getContent()));
            context.mainPolymer().extractPolymer(a, b);
            context.mainPolymer().replaceNodeWithNode(op, result);
            return result.getNext();
        }

        public static Node<?> geq(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> b = op.getPrev();
            Node<?> a = b.getPrev();
            Node<?> result = new Node<>(((NumberContent) a.getContent()).geq((NumberContent) b.getContent()));
            context.mainPolymer().extractPolymer(a, b);
            context.mainPolymer().replaceNodeWithNode(op, result);
            return result.getNext();
        }

        public static Node<?> leq(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> b = op.getPrev();
            Node<?> a = b.getPrev();
            Node<?> result = new Node<>(((NumberContent) a.getContent()).leq((NumberContent) b.getContent()));
            context.mainPolymer().extractPolymer(a, b);
            context.mainPolymer().replaceNodeWithNode(op, result);
            return result.getNext();
        }

        public static Node<?> and(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> b = op.getPrev();
            Node<?> a = b.getPrev();
            Node<?> result = new Node<>(((NumberContent) a.getContent()).and((NumberContent) b.getContent()));
            context.mainPolymer().extractPolymer(a, b);
            context.mainPolymer().replaceNodeWithNode(op, result);
            return result.getNext();
        }

        public static Node<?> or(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> b = op.getPrev();
            Node<?> a = b.getPrev();
            Node<?> result = new Node<>(((NumberContent) a.getContent()).or((NumberContent) b.getContent()));
            context.mainPolymer().extractPolymer(a, b);
            context.mainPolymer().replaceNodeWithNode(op, result);
            return result.getNext();
        }

        public static Node<?> xor(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> b = op.getPrev();
            Node<?> a = b.getPrev();
            Node<?> result = new Node<>(((NumberContent) a.getContent()).xor((NumberContent) b.getContent()));
            context.mainPolymer().extractPolymer(a, b);
            context.mainPolymer().replaceNodeWithNode(op, result);
            return result.getNext();
        }

        /* unary num op */

        public static Node<?> floor(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> arg = op.getPrev();
            Node<?> result = new Node<>(((NumberContent) arg.getContent()).floor());
            context.mainPolymer().extractNode(arg);
            context.mainPolymer().replaceNodeWithNode(op, result);
            return result.getNext();
        }

        public static Node<?> ceil(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> arg = op.getPrev();
            Node<?> result = new Node<>(((NumberContent) arg.getContent()).ceil());
            context.mainPolymer().extractNode(arg);
            context.mainPolymer().replaceNodeWithNode(op, result);
            return result.getNext();
        }

        public static Node<?> not(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> arg = op.getPrev();
            Node<?> result = new Node<>(((NumberContent) arg.getContent()).not());
            context.mainPolymer().extractNode(arg);
            context.mainPolymer().replaceNodeWithNode(op, result);
            return result.getNext();
        }

        /* string op */

        public static Node<?> getchar(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> b = op.getPrev();
            Node<?> a = b.getPrev();
            Node<?> result = new Node<>(
                    ((StringContent) b.getContent()).getchar(((Double) a.getContent().getValue()).intValue()));
            context.mainPolymer().extractPolymer(a, b);
            context.mainPolymer().replaceNodeWithNode(op, result);
            return result.getNext();
        }

        public static Node<?> join(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Node<?> b = op.getPrev();
            Node<?> a = b.getPrev();
            Node<?> result = new Node<>(((StringContent) a.getContent()).join((StringContent) b.getContent()));
            context.mainPolymer().extractPolymer(a, b);
            context.mainPolymer().replaceNodeWithNode(op, result);
            return result.getNext();
        }

        /* explicit main manipulation ops */

        public static Node<?> dup(Context context) {
            Node<?> op = context.mainPolymer().getEnzyme();
            Polymer arg = op.getPrev().getCorrespondingPolymer().clone();
            context.mainPolymer().replaceNodeWithPolymer(op, arg);
            return arg.getNext();
        }

        public static Node<?> ldrop(Context context) {
            MainPolymer main = context.mainPolymer();
            Node<?> op = main.getEnzyme();
            Node<?> next = op.getNext();
            Polymer arg = op.getPrev().getCorrespondingPolymer();
            main.extractNode(op);
            main.extractPolymer(arg);
            return next;
        }

        public static Node<?> rdrop(Context context) {
            MainPolymer main = context.mainPolymer();
            Node<?> op = main.getEnzyme();
            Node<?> next = op.getNext();
            Polymer arg = op.getNext().getCorrespondingPolymer();
            main.extractNode(op);
            main.extractPolymer(arg);
            return next;
        }

        public static Node<?> pick(Context context) {
            MainPolymer main = context.mainPolymer();
            Node<?> op = main.getEnzyme();
            Node<?> next = op.getNext();
            Node<?> arg = op.getPrev();
            Polymer to_pick = arg.getPrev().getCorrespondingPolymer();
            for (int i=0; (i<((NumberContent) arg.getContent()).getValue() && to_pick.getPrev() != null); i++) {
                to_pick = to_pick.getPrev().getCorrespondingPolymer();
            }
            main.extractPolymer(to_pick);
            main.insertPolymerAfter(op, to_pick);
            main.extractNode(op);
            main.extractNode(arg);
            return next;
        }

        public static Node<?> throwLeft(Context context) {
            MainPolymer main = context.mainPolymer();
            Node<?> op = main.getEnzyme();
            Node<?> next = op.getNext();
            Node<?> index = op.getPrev();
            if (((NumberContent) index.getContent()).getValue() != 0) {
                Polymer to_throw = index.getPrev().getCorrespondingPolymer();
                Polymer anchor = to_throw;
                for (int i = 0; (i < ((NumberContent) index.getContent()).getValue() && anchor.getPrev()!=null); i++) {
                    anchor = anchor.getPrev().getCorrespondingPolymer();
                }
                main.extractPolymer(to_throw);
                main.insertPolymerBefore(anchor.getStart(), to_throw);
            }
            main.extractNode(op);
            main.extractNode(index);
            return next;
        }

        /* polymer ops */

        public static Node<?> append(Context context) {
            return null;
        }

        public static Node<?> replace(Context context) {
            return null;
        }

        public static Node<?> splice(Context context) {
            return null;
        }

        public static Node<?> remove(Context context) {
            return null;
        }

        public static Node<?> retrieve(Context context) {
            return null;
        }

        public static Node<?> get(Context context) {
            return null;
        }

        public static Node<?> run(Context context) {
            return null;
        }

        /* namespace ops */

        public static Node<?> assign(Context context) {
            return null;
        }

        public static Node<?> exists(Context context) {
            return null;
        }

        public static Node<?> del(Context context) {
            return null;
        }

        public static Node<?> resolve(Context context) {
            return null;
        }

        /* type and casting ops */

        public static Node<?> type(Context context) {
            return null;
        }

        public static Node<?> toStr(Context context) {
            return null;
        }

        public static Node<?> toNum(Context context) {
            return null;
        }

        /* console ops */

        public static Node<?> print(Context context) {
            return null;
        }

        public static Node<?> input(Context context) {
            return null;
        }

        /* debugging ops */

        public static Node<?> mainSeq(Context context) {
            return null;
        }

        public static Node<?> nSpace(Context context) {
            return null;
        }

        /* clock ops */

        public static Node<?> nanos(Context context) {
            return null;
        }

        public static Node<?> epochSec(Context context) {
            return null;
        }

        /* polymorphic ops */

        public static Node<?> len(Context context) {
            return null;
        }

        /* context ops (stub) */

        public static Node<?> save(Context context) {
            return null;
        }

        public static Node<?> load(Context context) {
            return null;
        }
    }
}
