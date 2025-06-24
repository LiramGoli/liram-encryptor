package iaf.ofek.omega.bda.encryptions;

import java.math.BigInteger;

@FunctionalInterface
public interface CharacterShiftProcess {

    String process(BigInteger value, Integer key);

}
