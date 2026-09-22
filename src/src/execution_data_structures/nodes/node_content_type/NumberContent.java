package execution_data_structures.nodes.node_content_type;

public class NumberContent implements NodeContentType<Double> {

    private final Double value;

    public NumberContent(String token) {
        value = Double.parseDouble(token);
    }

    public NumberContent(double value) {
        this.value = value;
    }

    public NumberContent(int value) {
        this.value = (double) value;
    }

    @Override
    public Double getValue() {
        return value;
    }


    public NumberContent add(NumberContent other) {
        return new NumberContent(this.value + other.getValue());
    }

    public NumberContent sub(NumberContent other) {
        return new NumberContent(this.value - other.getValue());
    }

    public NumberContent mul(NumberContent other) {
        return new NumberContent(this.value * other.getValue());
    }

    public NumberContent div(NumberContent other) {
        return new NumberContent(this.value / other.getValue());
    }

    public NumberContent pow(NumberContent other) {
        return new NumberContent(Math.pow(this.value, other.getValue()));
    }

    public NumberContent round(NumberContent other) {
        double factor = Math.pow(10, other.getValue());
        return new NumberContent(Math.round(this.value * factor) / factor);
    }

    public NumberContent eq(NumberContent other) {
        return new NumberContent(this.value == other.getValue() ? 1 : 0);
    }

    public NumberContent neq(NumberContent other) {
        return new NumberContent(this.value != other.getValue() ? 1 : 0);
    }

    public NumberContent gt(NumberContent other) {
        return new NumberContent(this.value > other.getValue() ? 1 : 0);
    }

    public NumberContent lt(NumberContent other) {
        return new NumberContent(this.value < other.getValue() ? 1 : 0);
    }

    public NumberContent geq(NumberContent other) {
        return new NumberContent(this.value >= other.getValue() ? 1 : 0);
    }

    public NumberContent leq(NumberContent other) {
        return new NumberContent(this.value <= other.getValue() ? 1 : 0);
    }

    public NumberContent and(NumberContent other) {
        return new NumberContent((this.value != 0 && other.getValue() != 0) ? 1 : 0);
    }

    public NumberContent or(NumberContent other) {
        return new NumberContent((this.value != 0 || other.getValue() != 0) ? 1 : 0);
    }

    public NumberContent xor(NumberContent other) {
        boolean a = this.value != 0;
        boolean b = other.getValue() != 0;
        return new NumberContent(a ^ b ? 1 : 0);
    }

    public NumberContent floor() {
        return new NumberContent(Math.floor(this.value));
    }

    public NumberContent ceil() {
        return new NumberContent(Math.ceil(this.value));
    }

    public NumberContent not() {
        return new NumberContent(this.value == 0 ? 1 : 0);
    }

}