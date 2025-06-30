package iaf.ofek.omega.bda.logic.handlers;

import iaf.ofek.omega.bda.encryptions.EncryptionAlgorithm;
import iaf.ofek.omega.bda.models.EncryptionKey;
import iaf.ofek.omega.bda.utils.DataConvertionUtil;
import iaf.ofek.omega.bda.utils.EncryptionFilesUtil;
import iaf.ofek.omega.bda.utils.FilesUtil;
import iaf.ofek.omega.bda.utils.IOUtil;

import java.nio.file.Path;

import static iaf.ofek.omega.bda.consts.FileNameConstants.DECRYPTED_SUFFIX;
import static iaf.ofek.omega.bda.consts.FileNameConstants.ENCRYPTED_SUFFIX;

public class EncryptionHandler<K> {

    private final DataConvertionUtil dataConvertionUtil;
    private final EncryptionAlgorithm<K> encryptionAlgorithm;
    private final EncryptionFilesUtil encryptionFilesUtil;
    private final FilesUtil filesUtil;
    private final IOUtil ioUtil;

    public EncryptionHandler(DataConvertionUtil dataConvertionUtil, EncryptionAlgorithm<K> encryptionAlgorithm, EncryptionFilesUtil encryptionFilesUtil, FilesUtil filesUtil, IOUtil ioUtil) {
        this.dataConvertionUtil = dataConvertionUtil;
        this.encryptionAlgorithm = encryptionAlgorithm;
        this.encryptionFilesUtil = encryptionFilesUtil;
        this.filesUtil = filesUtil;
        this.ioUtil = ioUtil;
    }

    public void encrypt(Path path) {
        String content = filesUtil.readFile(path);
        Long[] numericContent = dataConvertionUtil.convertStringToLongArray(content);
        EncryptionKey<K> key = encryptionAlgorithm.generateEncryptionKey();
        content = encryptionAlgorithm.encrypt(numericContent, key);
        saveEncryptedOutput(path, content, key.toString());
    }

    public void decrypt(Path encryptedFile, Path keyPath) {
        String encryptedContent = filesUtil.readFile(encryptedFile);
        EncryptionKey<K> key = encryptionAlgorithm.getEncryptionKey(filesUtil.readFile(keyPath));
        Long[] numericContent = dataConvertionUtil.convertNumericStringToLongArray(encryptedContent);
        encryptedContent = encryptionAlgorithm.decrypt(numericContent, key);
        String decryptedContent = dataConvertionUtil.convertNumericStringToText(encryptedContent);
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
