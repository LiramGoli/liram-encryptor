package iaf.ofek.omega.bda.logic.handlers;

import iaf.ofek.omega.bda.encryptions.EncryptionAlgorithm;
import iaf.ofek.omega.bda.models.EncryptionKey;
import iaf.ofek.omega.bda.utils.EncryptionFilesUtil;
import iaf.ofek.omega.bda.utils.FilesUtil;
import iaf.ofek.omega.bda.utils.IOUtil;

import java.nio.file.Path;

import static iaf.ofek.omega.bda.consts.EncryptionConstants.SEPARATOR;
import static iaf.ofek.omega.bda.consts.FileNameConstants.DECRYPTED_SUFFIX;
import static iaf.ofek.omega.bda.consts.FileNameConstants.ENCRYPTED_SUFFIX;

public class EncryptionHandler<K> {

    private final EncryptionAlgorithm<K> encryptionAlgorithm;
    private final EncryptionFilesUtil encryptionFilesUtil;
    private final FilesUtil filesUtil;
    private final IOUtil ioUtil;

    public EncryptionHandler(EncryptionAlgorithm<K> encryptionAlgorithm, EncryptionFilesUtil encryptionFilesUtil, FilesUtil filesUtil, IOUtil ioUtil) {
        this.encryptionAlgorithm = encryptionAlgorithm;
        this.encryptionFilesUtil = encryptionFilesUtil;
        this.filesUtil = filesUtil;
        this.ioUtil = ioUtil;
    }

    public void encrypt(Path path) {
        String fileContent = filesUtil.readFile(path);
        String ascii = convertToAsciiString(fileContent);
        EncryptionKey<K> key = encryptionAlgorithm.generateEncryptionKey();
        String encryptedText = encryptionAlgorithm.encrypt(ascii, key);
        Path encryptedFilePath = encryptionFilesUtil.createPathWithSuffix(path, ENCRYPTED_SUFFIX);
        Path keyPath = encryptionFilesUtil.createKeyFilePath(encryptedFilePath);
        filesUtil.writeFile(keyPath, key.toString());
        filesUtil.writeFile(encryptedFilePath, encryptedText);
        ioUtil.printMessage("File encrypted successfully and stored in: " + encryptedFilePath);
        ioUtil.printMessage("Key successfully stored in: " + keyPath);
    }

    public void decrypt(Path filePath, Path keyPath) {
        String encryptedText = filesUtil.readFile(filePath);
        EncryptionKey<K> key = encryptionAlgorithm.getEncryptionKey(filesUtil.readFile(keyPath));
        String decryptedText = encryptionAlgorithm.decrypt(encryptedText, key);
        Path decryptedFilePath = encryptionFilesUtil.createPathWithSuffix(filePath, DECRYPTED_SUFFIX);
        filesUtil.writeFile(decryptedFilePath, decryptedText);
        ioUtil.printMessage("File decrypted successfully and stored in: " + decryptedFilePath);
    }

    private String convertToAsciiString(String context) {
        StringBuilder asciiString = new StringBuilder();
        for (Character character : context.toCharArray()) {
            asciiString.append((int) character).append(SEPARATOR);
        }
        return asciiString.toString();
    }

}
