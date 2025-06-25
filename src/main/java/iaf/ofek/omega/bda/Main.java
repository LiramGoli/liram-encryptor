package iaf.ofek.omega.bda;

import iaf.ofek.omega.bda.encryptions.shift.ShiftEncryption;
import iaf.ofek.omega.bda.encryptions.shift.ShiftMultiplyEncryption;
import iaf.ofek.omega.bda.initializers.ApplicationInitializer;
import iaf.ofek.omega.bda.logic.handlers.EncryptionHandler;
import iaf.ofek.omega.bda.logic.handlers.MenuOperationHandler;
import iaf.ofek.omega.bda.menu.MenuOperation;
import iaf.ofek.omega.bda.menu.MenuOperationSelector;
import iaf.ofek.omega.bda.utils.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        IOUtil ioUtil = new IOUtil(new Scanner(System.in));
        RandomUtil randomUtil = new RandomUtil(new Random());
        PathValidationUtil pathValidationUtil = new PathValidationUtil();
        EncryptionHandlerUtil encryptionHandlerUtil = new EncryptionHandlerUtil();
        FilesUtil filesUtil = new FilesUtil(pathValidationUtil, ioUtil);
        MenuIOUtil menuIOUtil = new MenuIOUtil(ioUtil);
        FileNameUtil fileNameUtil = new FileNameUtil(filesUtil);
        Map<Integer, MenuOperation> operationMap = new HashMap<>();
        ShiftEncryption shiftMultiplyEncryption = new ShiftMultiplyEncryption(randomUtil);
        MenuOperationSelector menuOperationSelector = new MenuOperationSelector(operationMap);
        EncryptionFilesUtil encryptionFilesUtil = new EncryptionFilesUtil(fileNameUtil, filesUtil);
        EncryptionHandler<Integer> encryptionHandler = new EncryptionHandler<>(shiftMultiplyEncryption, encryptionFilesUtil, encryptionHandlerUtil, filesUtil, 5, ioUtil);
        MenuOperationHandler<Integer> menuOperationHandler = new MenuOperationHandler<>(encryptionHandler, filesUtil, ioUtil);
        menuOperationSelector.registerOperations(menuOperationHandler);
        ApplicationInitializer applicationInitializer = new ApplicationInitializer(menuOperationSelector, menuIOUtil, ioUtil);
        applicationInitializer.initializeApp();
    }

}
