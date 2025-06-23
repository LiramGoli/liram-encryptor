package iaf.ofek.omega.bda.utils;

import java.nio.file.Files;
import java.nio.file.Path;

public class PathValidationUtil {

    public boolean isValidFilePath(String path) {
        String pathRegex = "^.*[#%&{}<>*? \"$`!@|=].*|.*\\..*\\..*$";
        return !path.matches(pathRegex);
    }

    public boolean isFileExist(String path) {
        return Files.isRegularFile(Path.of(path).toAbsolutePath());
    }

}
