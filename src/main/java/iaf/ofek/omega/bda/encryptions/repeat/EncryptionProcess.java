package iaf.ofek.omega.bda.encryptions.repeat;

import java.util.List;

@FunctionalInterface
public interface EncryptionProcess {

    List<Long> process(List<Long> data, String key);

}
