package iaf.ofek.omega.bda.encryptions.shift;

import iaf.ofek.omega.bda.utils.RandomUtil;

public class ShiftMultiplyEncryption extends ShiftEncryption {

    public ShiftMultiplyEncryption(RandomUtil randomUtil) {
        super(randomUtil);
    }

    @Override
    protected Long processCharacterEncryption(Long value, Integer key) {
        return value * key;
    }

    @Override
    protected Long processCharacterDecryption(Long value, Integer key) {
        return value / key;
    }

}
