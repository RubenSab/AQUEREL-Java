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

    public StringContent getchar(int index) {
        return new StringContent(String.valueOf(value.charAt(index)));
    }

    public StringContent join(StringContent other) {
        return new StringContent(value + other.getValue());
    }

    @Override
    public String toString() {
        return "'" + value + "'";
    }
}
