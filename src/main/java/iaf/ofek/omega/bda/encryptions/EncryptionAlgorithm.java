package iaf.ofek.omega.bda.encryptions;

import iaf.ofek.omega.bda.models.EncryptionKey;

import java.math.BigInteger;

public interface EncryptionAlgorithm<K> {

    String encrypt(BigInteger[] data, EncryptionKey<K> key);

    String decrypt(BigInteger[] data, EncryptionKey<K> key);

    EncryptionKey<K> generateEncryptionKey();

    EncryptionKey<K> getEncryptionKey(String keyContent);

}
