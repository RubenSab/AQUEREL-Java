package execution_data_structures.context;

import execution_data_structures.Polymer;
import executors.ExceptionLogger;

import java.util.HashMap;
import java.util.Map;

public class NameSpace {
    private final Map<String, Polymer> space;

    public NameSpace() {
        space = new HashMap<>();
    }

    public void map(String name, Polymer polymer) {
        space.put(name, polymer);
    }

    public void unmap(String name) {
        if (!space.containsKey(name)) {
            ExceptionLogger.logUndefinedPolymer(name);
        }
        space.remove(name);
    }

    public boolean check(String name) {
        return space.containsKey(name);
    }

    public Polymer retrieve(String name) {
        if (!space.containsKey(name)) {
            ExceptionLogger.logUndefinedPolymer(name);
        }
        return space.get(name);
    }
}
