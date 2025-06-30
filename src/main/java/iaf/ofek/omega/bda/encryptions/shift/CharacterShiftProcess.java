package iaf.ofek.omega.bda.encryptions.shift;

@FunctionalInterface
public interface CharacterShiftProcess {

    Long process(Long value, Integer key);

}
