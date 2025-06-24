package iaf.ofek.omega.bda.encryptions.shift;

import iaf.ofek.omega.bda.utils.RandomUtil;

import java.math.BigInteger;

import static iaf.ofek.omega.bda.consts.EncryptionConstants.ENCRYPTED_CHARACTERS_SEPARATOR;

public class ShiftUpEncryption extends ShiftEncryption {

    public ShiftUpEncryption(RandomUtil randomUtil) {
        super(randomUtil);
    }

    @Override
    protected String processCharacterEncryption(BigInteger value, Integer key) {
        return value.add(BigInteger.valueOf(key)).toString() + ENCRYPTED_CHARACTERS_SEPARATOR;

    }

    @Override
    protected String processCharacterDecryption(BigInteger value, Integer key) {
        return value.subtract(BigInteger.valueOf(key)).toString() + ENCRYPTED_CHARACTERS_SEPARATOR;
    }

}
