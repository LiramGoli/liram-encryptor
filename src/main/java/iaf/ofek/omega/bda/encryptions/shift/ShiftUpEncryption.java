package iaf.ofek.omega.bda.encryptions.shift;

import iaf.ofek.omega.bda.utils.RandomUtil;

public class ShiftUpEncryption extends ShiftEncryption {

    public ShiftUpEncryption(RandomUtil randomUtil) {
        super(randomUtil);
    }

    @Override
    protected Character processCharacterEncryption(Character character, Integer key) {
        return (char) (character + key);
    }

    @Override
    protected Character processCharacterDecryption(Character character, Integer key) {
        return (char) (character - key);
    }

}
