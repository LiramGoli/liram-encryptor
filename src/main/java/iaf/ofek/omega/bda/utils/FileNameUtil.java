package iaf.ofek.omega.bda.utils;

import java.nio.file.Path;

import static iaf.ofek.omega.bda.consts.FilesConstants.FILE_EXTENSION_SEPARATOR;
import static iaf.ofek.omega.bda.consts.FilesConstants.FILE_NAME_WORD_SEPARATOR;

public class FileNameUtil {

    private final FilesUtil filesUtil;

    public FileNameUtil(FilesUtil filesUtil) {
        this.filesUtil = filesUtil;
    }

    public String createFileNameWithSuffix(Path path, String suffix) {
        String fileName = extractFileNameWithoutExtensionFromPath(path);
        String fileExtension = extractFileExtensionFromPath(path);
        return fileName + FILE_NAME_WORD_SEPARATOR + suffix + FILE_EXTENSION_SEPARATOR + fileExtension;
    }

    public String extractFileNameWithoutExtensionFromPath(Path path) {
        String fileName = String.valueOf(filesUtil.extractFileNameWithExtensionFromPath(path));
        return fileName.substring(0, fileName.lastIndexOf(FILE_EXTENSION_SEPARATOR));
    }

    public String extractFileExtensionFromPath(Path path) {
        String fileName = String.valueOf(filesUtil.extractFileNameWithExtensionFromPath(path));
        return fileName.substring(fileName.lastIndexOf(FILE_EXTENSION_SEPARATOR) + 1);
    }

}
