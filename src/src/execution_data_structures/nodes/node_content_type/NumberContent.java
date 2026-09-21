package execution_data_structures.nodes.node_content_type;

public class NumberContent implements NodeContentType<Double> {

    private final Double value;

    public NumberContent(String token) {
        value = Double.parseDouble(token);
    }

    @Override
    public Double getValue() {
        return value;
    }
}