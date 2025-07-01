package iaf.ofek.omega.bda.encryptions.shift;

import iaf.ofek.omega.bda.encryptions.EncryptionAlgorithm;
import iaf.ofek.omega.bda.models.EncryptionKey;
import iaf.ofek.omega.bda.utils.RandomUtil;

import java.util.ArrayList;
import java.util.List;

import static iaf.ofek.omega.bda.consts.EncryptionConstants.CHAR_MAX_VALUE;

public abstract class ShiftEncryption implements EncryptionAlgorithm<EncryptionKey<Integer>> {

    private final RandomUtil randomUtil;

    public ShiftEncryption(RandomUtil randomUtil) {
        this.randomUtil = randomUtil;
    }

    @Override
    public List<Long> encrypt(List<Long> data, EncryptionKey<Integer> key) {
        return processEncryption(data, key.getValue(), this::processCharacterEncryption);
    }

    @Override
    public List<Long> decrypt(List<Long> data, EncryptionKey<Integer> key) {
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

    protected List<Long> processEncryption(List<Long> data, Integer key, CharacterShiftProcess characterShiftProcess) {
        List<Long> processedData = new ArrayList<>();
        for (Long value : data) {
            processedData.add(characterShiftProcess.process(value, key));
        }
        return processedData;
    }

    protected abstract Long processCharacterEncryption(Long value, Integer key);

    protected abstract Long processCharacterDecryption(Long value, Integer key);

}
