package execution_data_structures.nodes.node_content_type;

public class NameContent implements NodeContentType<String> {

    private final String value;

    public NameContent(String token) {
        value = token;
    }

    @Override
    public String getValue() {
        return value;
    }
}
