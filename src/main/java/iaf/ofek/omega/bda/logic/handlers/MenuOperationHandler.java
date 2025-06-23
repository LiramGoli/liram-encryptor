package iaf.ofek.omega.bda.logic.handlers;

import iaf.ofek.omega.bda.utils.FilesUtil;
import iaf.ofek.omega.bda.utils.IOUtil;

import java.nio.file.Path;

public class MenuOperationHandler<T> {

    private final EncryptionHandler<T> encryptionHandler;
    private final FilesUtil filesUtil;
    private final IOUtil ioUtil;

    public MenuOperationHandler(EncryptionHandler<T> encryptionHandler, FilesUtil filesUtil, IOUtil ioUtil) {
        this.encryptionHandler = encryptionHandler;
        this.filesUtil = filesUtil;
        this.ioUtil = ioUtil;
    }

    public void encrypt() {
        Path filePath = filesUtil.getValidFilePath("Please insert path of file: ");
        encryptionHandler.encrypt(filePath);
    }

    public void decrypt() {
        Path filePath = filesUtil.getValidFilePath("Please insert path of file: ");
        Path keyPath = filesUtil.getValidFilePath("Please insert path of key file: ");
        encryptionHandler.decrypt(filePath, keyPath);
    }

    public void exitApplication() {
        ioUtil.printMessage("Exiting...");
    }

}
