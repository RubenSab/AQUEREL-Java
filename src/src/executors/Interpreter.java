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
            case "(" -> BracketContent.OPEN;
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
            mainPolymer.setEnzyme(executeNodeAndGetNext(mainPolymer.getEnzyme(), context));
        }
    }

    private Node<?> executeNodeAndGetNext(Node<?> current, Context context) {
        System.out.println(current);
        context.mainPolymer().extract_node(current); /*stud, check signature and extract args instead*/
        switch (current.getContent()) {
            case OperationContent operation:
                return current.getNext();
            case BracketContent bracket:
                return current.getNext();
            case NameContent name:
                return current.getNext();
            default:
                return current.getNext();
        }
        /*
        * 1. check number of arguments
        * 2. extract n. arguments
        * 3. call a function passing the arguments
        * */
        /* TODO implement here a switch based on NodeContent. if it's an operation, route the execution in an util static class OperationsExecution with a method for each op */
    }
}
