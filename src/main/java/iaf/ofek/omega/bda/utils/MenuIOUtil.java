package iaf.ofek.omega.bda.utils;

import iaf.ofek.omega.bda.menu.MenuOperations;

public class MenuIOUtil {

    private final IOUtil ioUtil;

    public MenuIOUtil(IOUtil ioUtil) {
        this.ioUtil = ioUtil;
    }

    public void printTitle() {
        final String menuTitle = "\n\t\t\\M/ ENCRYPTOR \\M/\n";
        ioUtil.printMessage(menuTitle);
    }

    public String getUserInput() {
        final String menu = "Hello user! Please select an option:\n"
                + "-----------------------------------\n"
                + MenuOperations.Encrypt.getLabel() + ". Encrypt\n"
                + MenuOperations.Decrypt.getLabel() + ". Decrypt\n"
                + MenuOperations.Exit.getLabel() + ". Exit\n"
                + "-----------------------------------";
        return ioUtil.readInput(menu);
    }

}
