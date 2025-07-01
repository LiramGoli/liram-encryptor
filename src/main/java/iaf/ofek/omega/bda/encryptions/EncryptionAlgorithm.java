package iaf.ofek.omega.bda.encryptions;

import iaf.ofek.omega.bda.models.EncryptionKey;

import java.util.List;


public interface EncryptionAlgorithm<K> {

    List<Long> encrypt(List<Long> data, EncryptionKey<K> key);

    List<Long> decrypt(List<Long> data, EncryptionKey<K> key);

    EncryptionKey<K> generateEncryptionKey();

    EncryptionKey<K> getEncryptionKey(String keyContent);

}
