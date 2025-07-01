package iaf.ofek.omega.bda.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static iaf.ofek.omega.bda.consts.EncryptionConstants.ENCRYPTED_CHARACTERS_SEPARATOR;
import static iaf.ofek.omega.bda.consts.EncryptionConstants.REGEX_PREFIX;

public class DataConvertionUtil {

    public List<Long> convertStringToLongArray(String context) {
        StringBuilder numericContent = new StringBuilder();
        for (Character character : context.toCharArray()) {
            numericContent.append((long) character).append(ENCRYPTED_CHARACTERS_SEPARATOR);
        }
        return convertNumericStringToLongArray(numericContent.toString());
    }

    public List<Long> convertNumericStringToLongArray(String data) {
        return Arrays.stream(data.split(REGEX_PREFIX + ENCRYPTED_CHARACTERS_SEPARATOR))
                .filter(s -> !s.isEmpty())
                .map(Long::parseLong)
                .collect(Collectors.toCollection(ArrayList::new));
    }

    //bad names wip
    public String convertListToEncryptedString(List<Long> content) {
        StringBuilder result = new StringBuilder();
        for (Long value : content) {
            result.append(value).append(ENCRYPTED_CHARACTERS_SEPARATOR);
        }
        return result.toString();
    }

    public String convertListToDecryptedString(List<Long> content) {
        return content.stream()
                .map(value -> String.valueOf((char) value.intValue()))
                .collect(Collectors.joining());
    }

}
