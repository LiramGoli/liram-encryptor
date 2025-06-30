package iaf.ofek.omega.bda.encryptions.repeat;

import iaf.ofek.omega.bda.encryptions.EncryptionAlgorithm;
import iaf.ofek.omega.bda.models.EncryptionKey;
import iaf.ofek.omega.bda.utils.DataConvertionUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import static iaf.ofek.omega.bda.consts.EncryptionConstants.KEYS_SEPARATOR;
import static iaf.ofek.omega.bda.consts.EncryptionConstants.REGEX_PREFIX;

public class RepeatEncryption implements EncryptionAlgorithm<String> {

    private final EncryptionAlgorithm<Integer> innerAlgorithm;
    private final DataConvertionUtil dataConvertionUtil;
    private final int repeat;

    public RepeatEncryption(EncryptionAlgorithm<Integer> innerAlgorithm, DataConvertionUtil dataConvertionUtil, int repeat) {
        this.innerAlgorithm = innerAlgorithm;
        this.dataConvertionUtil = dataConvertionUtil;
        this.repeat = repeat;
    }

    @Override
    public String encrypt(Long[] data, EncryptionKey<String> key) {
        String[] encryptionKeys = key.getValue().split(REGEX_PREFIX + KEYS_SEPARATOR);
        return processEncryption(data, Arrays.asList(encryptionKeys),
                (currentData, currentKey) -> innerAlgorithm.encrypt(currentData, innerAlgorithm.getEncryptionKey(currentKey)));
    }

    @Override
    public String decrypt(Long[] data, EncryptionKey<String> key) {
        List<String> encryptionKeys = Arrays.asList(key.getValue().split(REGEX_PREFIX + KEYS_SEPARATOR));
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

    private String processEncryption(Long[] data, Iterable<String> encryptionKeys, EncryptionProcess encryptionProcess) {
        String content = "";
        Long[] numericContent = data;
        for (String encryptionKey : encryptionKeys) {
            content = encryptionProcess.process(numericContent, encryptionKey);
            numericContent = dataConvertionUtil.convertNumericStringToLongArray(content);
        }
        return content;
    }

}