package iaf.ofek.omega.bda.utils;

import java.math.BigInteger;
import java.util.Arrays;
import java.util.stream.Collectors;

import static iaf.ofek.omega.bda.consts.EncryptionConstants.ENCRYPTED_CHARACTERS_SEPARATOR;
import static iaf.ofek.omega.bda.consts.EncryptionConstants.REGEX_PREFIX;

public class EncryptionHandlerUtil {

    public BigInteger[] convertToNumericContent(String context) {
        StringBuilder numericContent = new StringBuilder();
        for (Character character : context.toCharArray()) {
            numericContent.append((int) character).append(ENCRYPTED_CHARACTERS_SEPARATOR);
        }
        return convertNumericStringToBigIntArray(numericContent.toString());
    }

    public BigInteger[] convertNumericStringToBigIntArray(String data) {
        return Arrays.stream(data.split(REGEX_PREFIX + ENCRYPTED_CHARACTERS_SEPARATOR))
                .filter(s -> !s.isEmpty())
                .map(BigInteger::new)
                .toArray(BigInteger[]::new);
    }

    public String convertNumericStringToText(String context) {
        return Arrays.stream(context.split(REGEX_PREFIX + ENCRYPTED_CHARACTERS_SEPARATOR))
                .filter(s -> !s.isEmpty())
                .map(s -> String.valueOf((char) new BigInteger(s).intValue()))
                .collect(Collectors.joining());
    }
}
