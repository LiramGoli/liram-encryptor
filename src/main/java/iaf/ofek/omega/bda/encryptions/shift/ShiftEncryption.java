package iaf.ofek.omega.bda.encryptions.shift;

import iaf.ofek.omega.bda.encryptions.CharacterShiftProcess;
import iaf.ofek.omega.bda.encryptions.EncryptionAlgorithm;
import iaf.ofek.omega.bda.models.EncryptionKey;
import iaf.ofek.omega.bda.utils.RandomUtil;

import static iaf.ofek.omega.bda.consts.EncryptionConstants.CHAR_MAX_VALUE;

public abstract class ShiftEncryption implements EncryptionAlgorithm<Integer> {

    private final RandomUtil randomUtil;

    public ShiftEncryption(RandomUtil randomUtil) {
        this.randomUtil = randomUtil;
    }

    @Override
    public String encrypt(String data, EncryptionKey<Integer> key) {
        return processEncryption(data, key.getValue(), this::processCharacterEncryption);
    }

    @Override
    public String decrypt(String data, EncryptionKey<Integer> key) {
        return processEncryption(data, key.getValue(), this::processCharacterDecryption);
    }

    @Override
    public EncryptionKey<Integer> generateEncryptionKey() {
        return new EncryptionKey<>(randomUtil.generateRandomInteger(CHAR_MAX_VALUE));
    }

    @Override
    public EncryptionKey<Integer> getEncryptionKey(String keyContent) {
        return new EncryptionKey<>(Integer.parseInt(keyContent));
    }

    protected String processEncryption(String data, Integer key, CharacterShiftProcess characterShiftProcess) {
        StringBuilder encryptedText = new StringBuilder();
        for (Character character : data.toCharArray()) {
            encryptedText.append(characterShiftProcess.process(character, key));
        }
        return String.valueOf(encryptedText);
    }

    protected abstract Character processCharacterEncryption(Character character, Integer key);

    protected abstract Character processCharacterDecryption(Character character, Integer key);

}
