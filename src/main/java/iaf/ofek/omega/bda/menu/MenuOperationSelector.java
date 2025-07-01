package iaf.ofek.omega.bda.menu;

import iaf.ofek.omega.bda.exceptions.InvalidMenuOperationException;
import iaf.ofek.omega.bda.logic.handlers.MenuOperationHandler;
import iaf.ofek.omega.bda.models.EncryptionKey;

import java.util.Map;
import java.util.Objects;

import static iaf.ofek.omega.bda.menu.MenuOperations.*;

public class MenuOperationSelector {

    private final Map<Integer, MenuOperation> menuOperations;

    public MenuOperationSelector(Map<Integer, MenuOperation> menuOperations) {
        this.menuOperations = menuOperations;
    }

    public <T extends EncryptionKey<?>> void registerOperations(MenuOperationHandler<T> menuOperationHandler) {
        menuOperations.put(Exit.getLabel(), menuOperationHandler::exitApplication);
        menuOperations.put(Encrypt.getLabel(), menuOperationHandler::encrypt);
        menuOperations.put(Decrypt.getLabel(), menuOperationHandler::decrypt);
    }

    public MenuOperation getMenuOperation(Integer key) {
        try {
            return Objects.requireNonNull(menuOperations.get(key));
        } catch (Exception e) {
            throw new InvalidMenuOperationException("Problem with receiving menu operation! Menu operation: " + key + " does not exists", e);
        }
    }

}
