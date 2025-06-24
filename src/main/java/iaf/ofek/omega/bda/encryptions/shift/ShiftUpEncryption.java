package iaf.ofek.omega.bda.encryptions.shift;

import iaf.ofek.omega.bda.utils.RandomUtil;

import static iaf.ofek.omega.bda.consts.EncryptionConstants.SEPARATOR;

public class ShiftUpEncryption extends ShiftEncryption {

    public ShiftUpEncryption(RandomUtil randomUtil) {
        super(randomUtil);
    }

    @Override
    protected String processCharacterEncryption(Long value, Integer key) {
        return String.valueOf(value + key) + SEPARATOR;
    }

    @Override
    protected String processCharacterDecryption(Long value, Integer key) {
        return String.valueOf(value - key)+ SEPARATOR;
    }

}
