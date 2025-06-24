package iaf.ofek.omega.bda.utils;

import java.util.Arrays;
import java.util.stream.Collectors;

import static iaf.ofek.omega.bda.consts.EncryptionConstants.SEPARATOR;

public class EncryptionHandlerUtil {

    public Long[] convertToNumericContent(String context) {
        StringBuilder numericContent = new StringBuilder();
        for (Character character : context.toCharArray()) {
            numericContent.append((int) character).append(SEPARATOR);
        }
        return convertNumericStringToLongArray(numericContent.toString());
    }

    public Long[] convertNumericStringToLongArray(String data) {
        return Arrays.stream(data.split("\\" + SEPARATOR))
                .filter(s -> !s.isEmpty())
                .map(Long::parseLong)
                .toArray(Long[]::new);
    }

    public String convertNumericStringToText(String context) {
        return Arrays.stream(context.split("\\" + SEPARATOR))
                .filter(s -> !s.isEmpty())
                .map(s -> String.valueOf((char) Integer.parseInt(s)))
                .collect(Collectors.joining());
    }

}
