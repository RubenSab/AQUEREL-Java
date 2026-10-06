package execution_data_structures.node_content_type;

public class StringContent implements NodeContent<String> {

    private final String value;

    public StringContent(String name) {
        value = name;
    }

    @Override
    public String getValue() {
        return value;
    }

    public StringContent getchar(NumberContent index) {
        return new StringContent(String.valueOf(value.charAt(index.getValue().intValue())));
    }

    public StringContent join(StringContent other) {
        return new StringContent(String.join(value, other.getValue()));
    }

    @Override
    public String toString() {
        return "'" + value + "'";
    }
}
