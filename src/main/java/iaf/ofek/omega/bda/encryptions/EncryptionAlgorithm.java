package iaf.ofek.omega.bda.encryptions;

import iaf.ofek.omega.bda.models.EncryptionKey;

public interface EncryptionAlgorithm<K> {

    String encrypt(String data, EncryptionKey<K> key);

    String decrypt(String data, EncryptionKey<K> key);

    EncryptionKey<K> generateEncryptionKey();

    EncryptionKey<K> getEncryptionKey(String keyContent);

}
