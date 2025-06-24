package iaf.ofek.omega.bda.encryptions.shift;

import iaf.ofek.omega.bda.encryptions.CharacterShiftProcess;
import iaf.ofek.omega.bda.encryptions.EncryptionAlgorithm;
import iaf.ofek.omega.bda.models.EncryptionKey;
import iaf.ofek.omega.bda.utils.RandomUtil;

import java.math.BigInteger;

import static iaf.ofek.omega.bda.consts.EncryptionConstants.CHAR_MAX_VALUE;

public abstract class ShiftEncryption implements EncryptionAlgorithm<Integer> {

    private final RandomUtil randomUtil;

    public ShiftEncryption(RandomUtil randomUtil) {
        this.randomUtil = randomUtil;
    }

    @Override
    public String encrypt(BigInteger[] data, EncryptionKey<Integer> key) {
        return processEncryption(data, key.getValue(), this::processCharacterEncryption);
    }

    @Override
    public String decrypt(BigInteger[] data, EncryptionKey<Integer> key) {
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

    protected String processEncryption(BigInteger[] data, Integer key, CharacterShiftProcess characterShiftProcess) {
        StringBuilder processedData = new StringBuilder();
        for (BigInteger value : data) {
            processedData.append(characterShiftProcess.process(value, key));
        }
        return processedData.toString();
    }

    protected abstract String processCharacterEncryption(BigInteger value, Integer key);

    protected abstract String processCharacterDecryption(BigInteger value, Integer key);

}
