import execution_data_structures.nodes.Polymer;
import executors.Interpreter;

public class Main {
    public static void main(String[] args) throws Exception {
        Interpreter i = new Interpreter();
        Polymer parsed = i.parse("src/test/test");
        System.out.println(parsed);
        i.interpret(parsed, "src/test/");
    }
}
