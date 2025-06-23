package iaf.ofek.omega.bda.utils;

import iaf.ofek.omega.bda.exceptions.FileReadingException;
import iaf.ofek.omega.bda.exceptions.FileWritingException;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FilesUtil {

    private final PathValidationUtil pathValidationUtil;
    private final IOUtil ioUtil;

    public FilesUtil(PathValidationUtil pathValidationUtil, IOUtil ioUtil) {
        this.pathValidationUtil = pathValidationUtil;
        this.ioUtil = ioUtil;
    }

    public String readFile(Path filePath) {
        try {
            return Files.readString(filePath);
        } catch (IOException e) {
            throw new FileReadingException("Error Occurred while trying to read file to path: " + filePath + ". The Error: " + e.getMessage(), e);
        }
    }

    public void writeFile(Path filePath, String content) {
        try {
            Files.writeString(filePath, content);
        } catch (IOException e) {
            throw new FileWritingException("Error Occurred while trying to write file to path: " + filePath + ". The Error: " + e.getMessage(), e);
        }
    }

    public Path getValidFilePath(String query) {
        String path = ioUtil.readInput(query);
        while (!pathValidationUtil.isValidFilePath(path) || !pathValidationUtil.isFileExist(path)) {
            ioUtil.printErrorMessage("the given path is invalid! given path: " + path);
            path = ioUtil.readInput(query);
        }
        return Path.of(path);
    }

    public Path extractParentPath(Path path) {
        return path.getParent();
    }

    public Path extractFileNameWithExtensionFromPath(Path path) {
        return path.getFileName();
    }

}
