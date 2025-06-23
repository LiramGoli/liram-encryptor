package iaf.ofek.omega.bda.models;

public class EncryptionKey<T> {

    private final T value;

    public EncryptionKey(T value) {
        this.value = value;
    }

    public T getValue() {
        return value;
    }

    @Override
    public String toString() {
        return this.value.toString();
    }

}
