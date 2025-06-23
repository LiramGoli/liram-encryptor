package iaf.ofek.omega.bda.utils;

import java.nio.file.Path;

import static iaf.ofek.omega.bda.consts.FileNameConstants.KEY_FILE_NAME;
import static iaf.ofek.omega.bda.consts.FilesConstants.FILE_EXTENSION_SEPARATOR;
import static iaf.ofek.omega.bda.consts.FilesConstants.TEXT_FILE_EXTENSION;

public class EncryptionFilesUtil {

    private final FileNameUtil fileNameUtil;
    private final FilesUtil filesUtil;

    public EncryptionFilesUtil(FileNameUtil fileNameUtil, FilesUtil filesUtil) {
        this.fileNameUtil = fileNameUtil;
        this.filesUtil = filesUtil;
    }

    public Path createPathWithSuffix(Path path, String suffix) {
        Path parentPath = filesUtil.extractParentPath(path);
        String fileNameWithSuffix = fileNameUtil.createFileNameWithSuffix(path, suffix);
        return parentPath.resolve(fileNameWithSuffix);
    }

    public Path createKeyFilePath(Path path) {
        Path parentPath = filesUtil.extractParentPath(path);
        return parentPath.resolve(KEY_FILE_NAME + FILE_EXTENSION_SEPARATOR + TEXT_FILE_EXTENSION);
    }

}
