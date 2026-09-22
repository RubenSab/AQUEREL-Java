package execution_data_structures;

import execution_data_structures.nodes.strands.Polymer;

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
        space.remove(name);
    }

    public boolean check(String name) {
        return space.containsKey(name);
    }

    public Polymer get(String name) {
        return space.get(name);
    }
}
