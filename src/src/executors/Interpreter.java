package executors;

import execution_data_structures.Node;
import execution_data_structures.context.*;
import execution_data_structures.node_content_type.*;
import execution_data_structures.Polymer;
import utils.Utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

import static execution_data_structures.node_content_type.BracketContent.*;

public class Interpreter {
    private Context context;

    public static Polymer parse(String filename) throws Exception {
        List<String> lines = Files.readAllLines(Paths.get(filename));
        String.join("\n", lines);
        if (lines.isEmpty()) {
            return null;
        }
        Polymer parsed = new Polymer();
        Stack<Node<BracketContent>> bracketStack = new Stack<>();
        Node prevNode = null;
        for (int i = 0; i < lines.size(); i++) {
            List<String> tokens = Utils.findallMatches("'(?:\\\\.|[^'\\\\])*'|[^\\s()]+|[()]", lines.get(i));
            for (String t : tokens) {
                NodeContent content = tokenToContent(t);
                Node<?> current = new Node<>(content);
                parsed.append(current);
                if (content instanceof BracketContent) {
                    if (content.getValue().equals(BracketContent.CLOSED)) {
                        if (bracketStack.isEmpty()) {
                            ExceptionLogger.logUnmatchedBracket(i);
                        }
                        Node<BracketContent> corresponding = bracketStack.pop();
                        ((BracketContent) current.getContent()).setCorresponding(corresponding);
                        corresponding.getContent().setCorresponding(current);
                    } else {
                        bracketStack.push((Node<BracketContent>) current);
                    }
                }
            }

        }
        return parsed;
    }

    private static NodeContent<?> tokenToContent(String token) {
        if (token.equals("(")) {
            return OPEN;
        } else if (token.equals(")")) {
            return CLOSED;
        } else if (Utils.isNumeric(token)) {
            return new NumberContent(token);
        } else if (token.startsWith("'") && token.endsWith("'")) {
            return new StringContent(token.substring(1, token.length()-1));
        } else if (OperationContent.OP_NAMES.contains(token)) {
            return OperationContent.fromToken(token);
        } else {
            return new NameContent(token);
        }
    }

    public void interpret(Polymer polymer, String sandboxRoot) {
        MainPolymer mainPolymer = new MainPolymer(polymer);
        NameSpace nameSpace = new NameSpace();
        Console console = new Console();
        FileSystem fileSystem = new FileSystem(sandboxRoot);
        this.context = new Context(mainPolymer, nameSpace, console, fileSystem);
        while (mainPolymer.hasEnzyme()) {
            Node<?> next = executeNodeAndGetNext(mainPolymer.getEnzyme());
            mainPolymer.setEnzyme(next);
            System.out.println(mainPolymer);
        }
    }

    private Node<?> executeNodeAndGetNext(Node<?> current) {
        Node<?> old_next = current.getNext();
        Node<?> old_prev = current.getPrev();
        switch (current.getContent()) {
            case BracketContent bracket:
                if (bracket.getValue() == OPEN) {
                    return bracket.getCorresponding().getNext();
                } else {
                    return old_next;
                }

            case NameContent name:
                Polymer polymer = context.nameSpace().retrieve(name.getValue());
                context.mainPolymer().replace_node_with_polymer(current, polymer);
                return polymer.getStart();

            case OperationContent operation:
                MainPolymer mainPolymer = context.mainPolymer();
                Class<?>[] signature = operation.getSignature();
                List<NodeContent<?>> args = new ArrayList<>();
                Node<?> signatureScanner = current.getPrev();
                for (int i=signature.length-1; i>0; i--) {
                    args.addFirst(signatureScanner.getContent());
                    if (!signatureScanner.getContent().getClass().equals(signature[i])) {
                        ExceptionLogger.logInvalidArgs(operation, args);
                    }
                    signatureScanner = signatureScanner.getPrev();
                }
                /* replace current node with operation's result */
                mainPolymer.replace_node_with_polymer(current, operation.computeResult(context));
            default:
                return old_next;
        }
    }
}
