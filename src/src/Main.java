import execution_data_structures.Polymer;
import executors.Interpreter;

public class Main {
    public static void main(String[] args) throws Exception {
        Interpreter i = new Interpreter();
        Polymer parsed = Interpreter.parse("src/test/test");
        i.interpret(parsed, "src/test/");
    }
}
