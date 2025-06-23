package iaf.ofek.omega.bda.encryptions;

@FunctionalInterface
public interface CharacterShiftProcess {

    Character process(Character character, Integer key);

}
