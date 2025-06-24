package iaf.ofek.omega.bda.logic.handlers;

import iaf.ofek.omega.bda.encryptions.EncryptionAlgorithm;
import iaf.ofek.omega.bda.models.EncryptionKey;
import iaf.ofek.omega.bda.utils.EncryptionFilesUtil;
import iaf.ofek.omega.bda.utils.EncryptionHandlerUtil;
import iaf.ofek.omega.bda.utils.FilesUtil;
import iaf.ofek.omega.bda.utils.IOUtil;

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
        Long[] numericContent = encryptionHandlerUtil.convertToNumericContent(content);
        StringBuilder allKeys = new StringBuilder();
        for (int i = 0; i < repeat; i++) {
            EncryptionKey<K> key = encryptionAlgorithm.generateEncryptionKey();
            content = encryptionAlgorithm.encrypt(numericContent, key);
            numericContent = encryptionHandlerUtil.convertNumericStringToLongArray(content);
            allKeys.append(key.getValue()).append(KEYS_SEPARATOR);
        }
        Path encryptedFile = encryptionFilesUtil.createPathWithSuffix(path, ENCRYPTED_SUFFIX);
        Path keyFile = encryptionFilesUtil.createKeyFilePath(encryptedFile);
        filesUtil.writeFile(encryptedFile, content);
        filesUtil.writeFile(keyFile, allKeys.toString());
        ioUtil.printMessage("File encrypted successfully: " + encryptedFile);
        ioUtil.printMessage("Keys saved at: " + keyFile);
    }

    public void decrypt(Path encryptedFile, Path keyFile) {
        String encryptedContent = filesUtil.readFile(encryptedFile);
        String[] keysArray = filesUtil.readFile(keyFile).split(REGEX_PREFIX+KEYS_SEPARATOR);
        Long[] numericContent = encryptionHandlerUtil.convertNumericStringToLongArray(encryptedContent);
        for (int i = repeat - 1; i >= 0; i--) {
            EncryptionKey<K> key = encryptionAlgorithm.getEncryptionKey(keysArray[i]);
            encryptedContent = encryptionAlgorithm.decrypt(numericContent, key);
            numericContent = encryptionHandlerUtil.convertNumericStringToLongArray(encryptedContent);
        }
        String decryptedContent = encryptionHandlerUtil.convertNumericStringToText(encryptedContent);
        Path decryptedFile = encryptionFilesUtil.createPathWithSuffix(encryptedFile, DECRYPTED_SUFFIX);
        filesUtil.writeFile(decryptedFile, decryptedContent);
        ioUtil.printMessage("File decrypted successfully: " + decryptedFile);
    }
}