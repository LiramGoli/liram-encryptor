package iaf.ofek.omega.bda.encryptions;

@FunctionalInterface
public interface CharacterShiftProcess {

    String process(Long value, Integer key);

}
