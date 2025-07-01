package iaf.ofek.omega.bda.encryptions.repeat;

import iaf.ofek.omega.bda.encryptions.EncryptionAlgorithm;
import iaf.ofek.omega.bda.models.EncryptionKey;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static iaf.ofek.omega.bda.consts.EncryptionConstants.KEYS_SEPARATOR;

public class RepeatEncryption implements EncryptionAlgorithm<String> {

    private final EncryptionAlgorithm<Integer> innerAlgorithm;
    private final Integer repeat;

    public RepeatEncryption(EncryptionAlgorithm<Integer> innerAlgorithm, Integer repeat) {
        this.innerAlgorithm = innerAlgorithm;
        this.repeat = repeat;
    }

    @Override
    public List<Long> encrypt(List<Long> data, EncryptionKey<String> key) {
        List<String> encryptionKeys = Arrays.asList(key.getValue().split("\\" + KEYS_SEPARATOR));
        return processEncryption(data, encryptionKeys,
                (currentData, currentKey) -> innerAlgorithm.encrypt(currentData, innerAlgorithm.getEncryptionKey(currentKey)));
    }

    @Override
    public List<Long> decrypt(List<Long> data, EncryptionKey<String> key) {
        List<String> encryptionKeys = Arrays.asList(key.getValue().split("\\" + KEYS_SEPARATOR));
        Collections.reverse(encryptionKeys);
        return processEncryption(data, encryptionKeys,
                (currentData, currentKey) -> innerAlgorithm.decrypt(currentData, innerAlgorithm.getEncryptionKey(currentKey)));
    }

    @Override
    public EncryptionKey<String> generateEncryptionKey() {
        StringBuilder combinedKeys = new StringBuilder();
        for (int i = 0; i < repeat; i++) {
            combinedKeys.append(innerAlgorithm.generateEncryptionKey()).append(KEYS_SEPARATOR);
        }
        return new EncryptionKey<>(combinedKeys.toString());
    }

    @Override
    public EncryptionKey<String> getEncryptionKey(String keyContent) {
        return new EncryptionKey<>(keyContent);
    }

    private List<Long> processEncryption(List<Long> data, Iterable<String> encryptionKeys, EncryptionProcess encryptionProcess) {
        List<Long> numericContent = data;
        for (String encryptionKey : encryptionKeys) {
            numericContent = encryptionProcess.process(numericContent, encryptionKey);
        }
        return numericContent;
    }

}