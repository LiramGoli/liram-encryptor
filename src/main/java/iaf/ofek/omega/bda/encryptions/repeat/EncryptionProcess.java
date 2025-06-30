package iaf.ofek.omega.bda.encryptions.repeat;

@FunctionalInterface
public interface EncryptionProcess {

    String process(Long[] data, String key);

}
