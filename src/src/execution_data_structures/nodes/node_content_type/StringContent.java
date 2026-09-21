package execution_data_structures.nodes.node_content_type;

public class StringContent implements NodeContentType<String> {

    private final String value;

    public StringContent(String name) {
        value = name;
    }

    @Override
    public String getValue() {
        return value;
    }
}
