package iaf.ofek.omega.bda.models;

import java.util.List;
import java.util.stream.Collectors;

import static iaf.ofek.omega.bda.consts.EncryptionConstants.KEYS_SEPARATOR;

public class CompositeEncryptionKey<T> extends EncryptionKey<List<EncryptionKey<T>>> {

    public CompositeEncryptionKey(List<EncryptionKey<T>> value) {
        super(value);
    }

    public List<EncryptionKey<T>> getValue() {
        return value;
    }

    public String toString() {
        return value.stream()
                .map(EncryptionKey::toString)
                .collect(Collectors.joining(KEYS_SEPARATOR.toString()));
    }

}
