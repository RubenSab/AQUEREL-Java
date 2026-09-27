package executors;

import execution_data_structures.*;
import execution_data_structures.nodes.Node;
import execution_data_structures.nodes.node_content_type.*;
import execution_data_structures.nodes.Polymer;
import utils.Utils;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.Stack;

public class Interpreter {
    private MainPolymer mainPolymer;
    private NameSpace nameSpace;
    private Console console;
    private FileSystem fileSystem;
    private Clock clock;

    public Polymer parse(String filename) throws Exception {
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

    private NodeContent tokenToContent(String token) {
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

    public void interpret() {}
}
