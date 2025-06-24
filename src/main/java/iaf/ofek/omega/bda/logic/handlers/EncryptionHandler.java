package iaf.ofek.omega.bda.logic.handlers;

import iaf.ofek.omega.bda.encryptions.EncryptionAlgorithm;
import iaf.ofek.omega.bda.models.EncryptionKey;
import iaf.ofek.omega.bda.utils.EncryptionFilesUtil;
import iaf.ofek.omega.bda.utils.EncryptionHandlerUtil;
import iaf.ofek.omega.bda.utils.FilesUtil;
import iaf.ofek.omega.bda.utils.IOUtil;

import java.math.BigInteger;
import java.nio.file.Path;

import static iaf.ofek.omega.bda.consts.EncryptionConstants.KEYS_SEPARATOR;
import static iaf.ofek.omega.bda.consts.EncryptionConstants.REGEX_PREFIX;
import static iaf.ofek.omega.bda.consts.FileNameConstants.DECRYPTED_SUFFIX;
import static iaf.ofek.omega.bda.consts.FileNameConstants.ENCRYPTED_SUFFIX;

public class EncryptionHandler<K> {

    private final EncryptionHandlerUtil encryptionHandlerUtil;
    private final EncryptionAlgorithm<K> encryptionAlgorithm;
    private final EncryptionFilesUtil encryptionFilesUtil;
    private final FilesUtil filesUtil;
    private final Integer repeat;
    private final IOUtil ioUtil;

    public EncryptionHandler(EncryptionAlgorithm<K> encryptionAlgorithm, EncryptionFilesUtil encryptionFilesUtil, EncryptionHandlerUtil encryptionHandlerUtil,
                             FilesUtil filesUtil, Integer repeat, IOUtil ioUtil) {
        this.encryptionHandlerUtil = encryptionHandlerUtil;
        this.encryptionAlgorithm = encryptionAlgorithm;
        this.encryptionFilesUtil = encryptionFilesUtil;
        this.filesUtil = filesUtil;
        this.repeat = repeat;
        this.ioUtil = ioUtil;
    }

    public void encrypt(Path path) {
        String content = filesUtil.readFile(path);
        BigInteger[] numericContent = encryptionHandlerUtil.convertToNumericContent(content);
        StringBuilder keysBuilder = new StringBuilder();
        for (int i = 0; i < repeat; i++) {
            EncryptionKey<K> key = encryptionAlgorithm.generateEncryptionKey();
            content = encryptionAlgorithm.encrypt(numericContent, key);
            numericContent = encryptionHandlerUtil.convertNumericStringToBigIntArray(content);
            keysBuilder.append(key.getValue()).append(KEYS_SEPARATOR);
        }
        saveEncryptedOutput(path, content, keysBuilder.toString());
    }

    public void decrypt(Path encryptedFile, Path keyFile) {
        String encryptedContent = filesUtil.readFile(encryptedFile);
        String[] keysArray = filesUtil.readFile(keyFile).split(REGEX_PREFIX + KEYS_SEPARATOR);
        BigInteger[] numericContent = encryptionHandlerUtil.convertNumericStringToBigIntArray(encryptedContent);
        for (int i = repeat - 1; i >= 0; i--) {
            EncryptionKey<K> key = encryptionAlgorithm.getEncryptionKey(keysArray[i]);
            encryptedContent = encryptionAlgorithm.decrypt(numericContent, key);
            numericContent = encryptionHandlerUtil.convertNumericStringToBigIntArray(encryptedContent);
        }
        String decryptedContent = encryptionHandlerUtil.convertNumericStringToText(encryptedContent);
        saveDecryptedOutput(encryptedFile, decryptedContent);
    }

    private void saveEncryptedOutput(Path originalFile, String encryptedContent, String key) {
        Path encryptedFile = encryptionFilesUtil.createPathWithSuffix(originalFile, ENCRYPTED_SUFFIX);
        Path keyFile = encryptionFilesUtil.createKeyFilePath(encryptedFile);
        filesUtil.writeFile(encryptedFile, encryptedContent);
        filesUtil.writeFile(keyFile, key);
        ioUtil.printMessage("File encrypted successfully: " + encryptedFile);
        ioUtil.printMessage("Keys saved at: " + keyFile);
    }

    private void saveDecryptedOutput(Path encryptedFile, String decryptedContent) {
        Path decryptedFile = encryptionFilesUtil.createPathWithSuffix(encryptedFile, DECRYPTED_SUFFIX);
        filesUtil.writeFile(decryptedFile, decryptedContent);
        ioUtil.printMessage("File decrypted successfully: " + decryptedFile);
    }

}
