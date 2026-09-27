package executors;

public class ExceptionLogger {
    private static final String PARSER_EX = "Parser exception: ";
    private static final String INTERPRETER_EX = "executors.Interpreter exception: ";
    private static final String FILESYSTEM_EX = "Filesystem exception: ";

    public static void logUnmatchedBracket(int i) {
        System.err.println(PARSER_EX + "Unmatched ')' at line " + i+1);
    }

    public static void logInvalidFilename(String name) {
        System.err.println(PARSER_EX + "Invalid filename " + name + " (cannot contain '/' or '.')");
    }
}
