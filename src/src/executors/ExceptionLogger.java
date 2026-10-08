package executors;

import execution_data_structures.node_content_type.NodeContent;
import execution_data_structures.node_content_type.OperationContent;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class ExceptionLogger {
    private static final String PARSER_EX = "Parser exception: ";
    private static final String INTERPRETER_EX = "Interpreter exception: ";
    private static final String FILESYSTEM_EX = "Filesystem exception: ";

    public static void logUnmatchedBracket(int i) {
        System.err.println(PARSER_EX + "Unmatched ')' at line " + i+1);
        System.exit(0);
    }

    public static void logInvalidFilename(String name) {
        System.err.println(FILESYSTEM_EX + "Invalid filename " + name + " (cannot contain '/' or '.')");
        System.exit(0);
    }

    public static void logUndefinedPolymer(String name) {
        System.err.println(INTERPRETER_EX + "Undefined polymer \"" + name + "\"");
        System.exit(0);
    }

    public static void logInvalidArgs(OperationContent op, List<NodeContent<?>> args) {
        System.err.println(INTERPRETER_EX + "Invalid args " + args.stream().map(NodeContent::toString).collect(Collectors.joining(", ")) + " for operation " + op);
        System.exit(0);
    }
}
