package iaf.ofek.omega.bda.encryptions;

import iaf.ofek.omega.bda.models.EncryptionKey;
import iaf.ofek.omega.bda.utils.DataConvertionUtil;

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
        String content = "";
        Long[] numericContent = data;
        String[] encryptionKeys = key.getValue().split(REGEX_PREFIX + KEYS_SEPARATOR);
        for (String encryptionKey : encryptionKeys) {
            content = innerAlgorithm.encrypt(numericContent, innerAlgorithm.getEncryptionKey(encryptionKey));
            numericContent = dataConvertionUtil.convertNumericStringToLongArray(content);
        }
        return content;
    }

    @Override
    public String decrypt(Long[] data, EncryptionKey<String> key) {
        String content = "";
        Long[] numericContent = data;
        String[] encryptionKeys = key.getValue().split(REGEX_PREFIX + KEYS_SEPARATOR);
        for (int i = encryptionKeys.length - 1; i >= 0; i--) {
            content = innerAlgorithm.decrypt(numericContent, innerAlgorithm.getEncryptionKey(encryptionKeys[i]));
            numericContent = dataConvertionUtil.convertNumericStringToLongArray(content);
        }
        return content;
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

}