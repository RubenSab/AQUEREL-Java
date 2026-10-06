package execution_data_structures.node_content_type;

public class NameContent implements NodeContent<String> {

    private final String value;

    public NameContent(String token) {
        value = token;
    }

    @Override
    public String getValue() {
        return value;
    }

    @Override
    public String toString() {
        return value;
    }
}
