package iaf.ofek.omega.bda.encryptions.shift;

@FunctionalInterface
public interface CharacterShiftProcess {

    String process(Long value, Integer key);

}
