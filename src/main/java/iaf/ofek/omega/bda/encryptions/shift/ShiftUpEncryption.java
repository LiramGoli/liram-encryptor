package iaf.ofek.omega.bda.encryptions.shift;

import iaf.ofek.omega.bda.utils.RandomUtil;

public class ShiftUpEncryption extends ShiftEncryption {

    public ShiftUpEncryption(RandomUtil randomUtil) {
        super(randomUtil);
    }

    @Override
    protected Long processCharacterEncryption(Long value, Integer key) {
        return value + key;

    }

    @Override
    protected Long processCharacterDecryption(Long value, Integer key) {
        return value - key;
    }

}
