package iaf.ofek.omega.bda.logic.handlers;

import iaf.ofek.omega.bda.encryptions.EncryptionAlgorithm;
import iaf.ofek.omega.bda.models.EncryptionKey;
import iaf.ofek.omega.bda.utils.DataConvertionUtil;
import iaf.ofek.omega.bda.utils.EncryptionFilesUtil;
import iaf.ofek.omega.bda.utils.FilesUtil;
import iaf.ofek.omega.bda.utils.IOUtil;

import java.nio.file.Path;
import java.util.List;

import static iaf.ofek.omega.bda.consts.FileNameConstants.DECRYPTED_SUFFIX;
import static iaf.ofek.omega.bda.consts.FileNameConstants.ENCRYPTED_SUFFIX;

public class EncryptionHandler<T extends EncryptionKey<?>> {

    private final DataConvertionUtil dataConvertionUtil;
    private final EncryptionAlgorithm<T> encryptionAlgorithm;
    private final EncryptionFilesUtil encryptionFilesUtil;
    private final FilesUtil filesUtil;
    private final IOUtil ioUtil;

    public EncryptionHandler(DataConvertionUtil dataConvertionUtil, EncryptionAlgorithm<T> encryptionAlgorithm, EncryptionFilesUtil encryptionFilesUtil, FilesUtil filesUtil, IOUtil ioUtil) {
        this.dataConvertionUtil = dataConvertionUtil;
        this.encryptionAlgorithm = encryptionAlgorithm;
        this.encryptionFilesUtil = encryptionFilesUtil;
        this.filesUtil = filesUtil;
        this.ioUtil = ioUtil;
    }

    public void encrypt(Path path) {
        String fileContent = filesUtil.readFile(path);
        List<Long> asciiList = dataConvertionUtil.convertDataToListOfAscii(fileContent);
        T key = encryptionAlgorithm.generateEncryptionKey();
        List<Long> encryptedContent = encryptionAlgorithm.encrypt(asciiList, key);
        String parsedContent = dataConvertionUtil.convertListToEncryptedString(encryptedContent);
        saveEncryptedFile(path, key, parsedContent);
    }

    public void decrypt(Path encryptedFile, Path keyPath) {
        String encryptedContent = filesUtil.readFile(encryptedFile);
        T key = encryptionAlgorithm.getEncryptionKey(filesUtil.readFile(keyPath));
        List<Long> encryptedContentList = dataConvertionUtil.convertProcessedDataToLongArray(encryptedContent);
        List<Long> decryptedContent = encryptionAlgorithm.decrypt(encryptedContentList, key);
        String parsedContent = dataConvertionUtil.convertListToDecryptedString(decryptedContent);
        saveDecryptedFile(encryptedFile, parsedContent);
    }

    private void saveEncryptedFile(Path filePath, T key, String encryptedContent) {
        Path encryptedFile = encryptionFilesUtil.createPathWithSuffix(filePath, ENCRYPTED_SUFFIX);
        Path keyFile = encryptionFilesUtil.createKeyFilePath(encryptedFile);
        filesUtil.writeFile(encryptedFile, encryptedContent);
        filesUtil.writeFile(keyFile, key.toString());
        ioUtil.printMessage("File encrypted successfully: " + encryptedFile);
        ioUtil.printMessage("Keys saved at: " + keyFile);
    }

    private void saveDecryptedFile(Path filePath, String decryptedContent) {
        Path decryptedFile = encryptionFilesUtil.createPathWithSuffix(filePath, DECRYPTED_SUFFIX);
        filesUtil.writeFile(decryptedFile, decryptedContent);
        ioUtil.printMessage("File decrypted successfully: " + decryptedFile);
    }

}
