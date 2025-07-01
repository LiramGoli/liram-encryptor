package iaf.ofek.omega.bda.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static iaf.ofek.omega.bda.consts.EncryptionConstants.ENCRYPTED_CHARACTERS_SEPARATOR;

public class DataConvertionUtil {

    public List<Long> convertDataToAscii(String data) {
        return data.chars()
                .mapToLong(c -> (long) c)
                .boxed()
                .collect(Collectors.toList());
    }

    public List<Long> convertProcessedDataToLongArray(String data) {
        return Arrays.stream(data.split("\\" + ENCRYPTED_CHARACTERS_SEPARATOR))
                .filter(s -> !s.isEmpty())
                .map(Long::parseLong)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    public String convertListToEncryptedString(List<Long> content) {
        return content.stream()
                .map(String::valueOf)
                .collect(Collectors.joining(String.valueOf(ENCRYPTED_CHARACTERS_SEPARATOR)));
    }

    public String convertListToDecryptedString(List<Long> content) {
        return content.stream()
                .map(value -> String.valueOf((char) value.intValue()))
                .collect(Collectors.joining());
    }

}
