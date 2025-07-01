package iaf.ofek.omega.bda.encryptions;

import iaf.ofek.omega.bda.models.EncryptionKey;

import java.util.List;

public interface EncryptionAlgorithm<K extends EncryptionKey<?>> {

    List<Long> encrypt(List<Long> data, K key);

    List<Long> decrypt(List<Long> data, K key);

    K generateEncryptionKey();

    K getEncryptionKey(String keyContent);

}
