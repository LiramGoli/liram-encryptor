package iaf.ofek.omega.bda.initializers;

import iaf.ofek.omega.bda.menu.MenuOperationSelector;
import iaf.ofek.omega.bda.menu.MenuOperations;
import iaf.ofek.omega.bda.utils.IOUtil;
import iaf.ofek.omega.bda.utils.MenuIOUtil;

public class ApplicationInitializer {

    private final MenuOperationSelector menuOperationSelector;
    private final MenuIOUtil menuIOUtil;
    private final IOUtil ioUtil;

    public ApplicationInitializer(MenuOperationSelector menuOperationSelector, MenuIOUtil menuIOUtil, IOUtil ioUtil) {
        this.menuOperationSelector = menuOperationSelector;
        this.menuIOUtil = menuIOUtil;
        this.ioUtil = ioUtil;
    }

    public void initializeApp() {
        boolean isAppClosed = false;
        menuIOUtil.printTitle();
        while (!isAppClosed) {
            try {
                Integer userInput = Integer.parseInt(menuIOUtil.getUserInput());
                menuOperationSelector.getMenuOperation(userInput).execute();
                isAppClosed = userInput.equals(MenuOperations.Exit.getLabel());
            } catch (Exception e) {
                ioUtil.printErrorMessage("An unexpected error occurred while running the program. The error: " + e.getMessage());
            }
        }
    }

}
