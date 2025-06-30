package iaf.ofek.omega.bda.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

import static iaf.ofek.omega.bda.consts.EncryptionConstants.ENCRYPTED_CHARACTERS_SEPARATOR;
import static iaf.ofek.omega.bda.consts.EncryptionConstants.REGEX_PREFIX;

public class DataConvertionUtil {

    public Long[] convertStringToLongArray(String context) {
        StringBuilder numericContent = new StringBuilder();
        for (Character character : context.toCharArray()) {
            numericContent.append((int) character).append(ENCRYPTED_CHARACTERS_SEPARATOR);
        }
        return convertNumericStringToLongArray(numericContent.toString());
    }

    public Long[] convertNumericStringToLongArray(String data) {
        return Arrays.stream(data.split(REGEX_PREFIX + ENCRYPTED_CHARACTERS_SEPARATOR))
                .filter(s -> !s.isEmpty())
                .map(Long::parseLong)
                .toArray(Long[]::new);
    }

    public String convertNumericStringToText(String context) {
        return Arrays.stream(context.split(REGEX_PREFIX + ENCRYPTED_CHARACTERS_SEPARATOR))
                .filter(s -> !s.isEmpty())
                .map(s -> String.valueOf((char) Integer.parseInt(s)))
                .collect(Collectors.joining());
    }
}
