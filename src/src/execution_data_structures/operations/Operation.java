package execution_data_structures.operations;

public interface Operation {
    default Operation getValue() {
        return this;
    };
    String getToken();
}
