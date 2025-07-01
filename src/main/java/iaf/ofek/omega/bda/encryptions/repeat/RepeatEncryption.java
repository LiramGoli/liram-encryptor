package iaf.ofek.omega.bda.encryptions.repeat;

import iaf.ofek.omega.bda.encryptions.EncryptionAlgorithm;
import iaf.ofek.omega.bda.models.CompositeEncryptionKey;
import iaf.ofek.omega.bda.models.EncryptionKey;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static iaf.ofek.omega.bda.consts.EncryptionConstants.KEYS_SEPARATOR;

public class RepeatEncryption implements EncryptionAlgorithm<CompositeEncryptionKey<Integer>> {

    private final EncryptionAlgorithm<EncryptionKey<Integer>> baseAlgorithm;
    private final Integer repeat;

    public RepeatEncryption(EncryptionAlgorithm<EncryptionKey<Integer>> baseAlgorithm, Integer repeat) {
        this.baseAlgorithm = baseAlgorithm;
        this.repeat = repeat;
    }

    @Override
    public List<Long> encrypt(List<Long> data, CompositeEncryptionKey<Integer> key) {
        return processEncryption(data, key.getValue(),
                (currentData, currentKey) -> baseAlgorithm.encrypt(currentData, baseAlgorithm.getEncryptionKey(currentKey)));
    }

    @Override
    public List<Long> decrypt(List<Long> data, CompositeEncryptionKey<Integer> key) {
        List<EncryptionKey<Integer>> encryptionKeys = key.getValue();
        Collections.reverse(encryptionKeys);
        return processEncryption(data, encryptionKeys,
                (currentData, currentKey) -> baseAlgorithm.decrypt(currentData, baseAlgorithm.getEncryptionKey(currentKey)));
    }

    @Override
    public CompositeEncryptionKey<Integer> generateEncryptionKey() {
        List<EncryptionKey<Integer>> keys = new ArrayList<>();
        for (int i = 0; i < repeat; i++) {
            keys.add(baseAlgorithm.generateEncryptionKey());
        }
        return new CompositeEncryptionKey<>(keys);
    }

    @Override
    public CompositeEncryptionKey<Integer> getEncryptionKey(String keyContent) {
        List<EncryptionKey<Integer>> keys = Arrays.stream(keyContent.split("\\" + KEYS_SEPARATOR))
                .map(baseAlgorithm::getEncryptionKey)
                .collect(Collectors.toList());
        return new CompositeEncryptionKey<>(keys);
    }

    private List<Long> processEncryption(List<Long> data, Iterable<EncryptionKey<Integer>> encryptionKeys, EncryptionProcess encryptionProcess) {
        for (EncryptionKey<Integer> encryptionKey : encryptionKeys) {
            data = encryptionProcess.process(data, encryptionKey.toString());
        }
        return data;
    }

}