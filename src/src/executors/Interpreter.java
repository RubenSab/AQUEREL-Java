package executors;

import execution_data_structures.Node;
import execution_data_structures.context.*;
import execution_data_structures.node_content_type.*;
import execution_data_structures.Polymer;
import utils.Utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

import static execution_data_structures.node_content_type.BracketContent.OPEN;

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

    private static NodeContent tokenToContent(String token) {
        return switch (token) {
            case "(" -> OPEN;
            case ")" -> BracketContent.CLOSED;
            case String t when Utils.isNumeric(t) -> new NumberContent(token);
            case String t when t.startsWith("'") && t.endsWith("'") ->
                    new StringContent(t.substring(1, t.length()-1));
            case String t when Arrays.asList(OperationContent.values()).contains(t) ->
                    OperationContent.fromToken(t);
            default -> new NameContent(token);
        };
    }

    public void interpret(Polymer polymer, String sandboxRoot) {
        MainPolymer mainPolymer = new MainPolymer(polymer);
        NameSpace nameSpace = new NameSpace();
        Console console = new Console();
        FileSystem fileSystem = new FileSystem(sandboxRoot);
        Context context = new Context(mainPolymer, nameSpace, console, fileSystem);
        while (mainPolymer.hasEnzyme()) {
            Node<?> next = executeNodeAndGetNext(mainPolymer.getEnzyme(), context);
            mainPolymer.setEnzyme(next);
        }
    }

    private Node<?> executeNodeAndGetNext(Node<?> current, Context context) {
        System.out.println(current);
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
                return old_next; /* stub */

            default:
                return old_next;
        }
        /*
        * 1. check number of arguments
        * 2. extract n. arguments
        * 3. call a function passing the arguments
        * */
        /* TODO implement here a switch based on NodeContent. if it's an operation, route the execution in an util static class OperationsExecution with a method for each op */
    }
}
