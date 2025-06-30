package iaf.ofek.omega.bda.encryptions;

import iaf.ofek.omega.bda.models.EncryptionKey;


public interface EncryptionAlgorithm<K> {

    String encrypt(Long[] data, EncryptionKey<K> key);

    String decrypt(Long[] data, EncryptionKey<K> key);

    EncryptionKey<K> generateEncryptionKey();

    EncryptionKey<K> getEncryptionKey(String keyContent);

}
