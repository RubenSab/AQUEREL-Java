package execution_data_structures.node_content_type;

public interface NodeContent<T> {
    T getValue();
    String getTypeString();
    String toPrintableString();
    default int length() {
        return 1;
    }
}
