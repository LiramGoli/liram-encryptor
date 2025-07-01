package iaf.ofek.omega.bda;

import iaf.ofek.omega.bda.encryptions.EncryptionAlgorithm;
import iaf.ofek.omega.bda.encryptions.repeat.RepeatEncryption;
import iaf.ofek.omega.bda.encryptions.shift.ShiftMultiplyEncryption;
import iaf.ofek.omega.bda.initializers.ApplicationInitializer;
import iaf.ofek.omega.bda.logic.handlers.EncryptionHandler;
import iaf.ofek.omega.bda.logic.handlers.MenuOperationHandler;
import iaf.ofek.omega.bda.menu.MenuOperation;
import iaf.ofek.omega.bda.menu.MenuOperationSelector;
import iaf.ofek.omega.bda.models.CompositeEncryptionKey;
import iaf.ofek.omega.bda.models.EncryptionKey;
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
        DataConvertionUtil dataConvertionUtil = new DataConvertionUtil();
        FilesUtil filesUtil = new FilesUtil(pathValidationUtil, ioUtil);
        MenuIOUtil menuIOUtil = new MenuIOUtil(ioUtil);
        FileNameUtil fileNameUtil = new FileNameUtil(filesUtil);
        Map<Integer, MenuOperation> operationMap = new HashMap<>();
        EncryptionAlgorithm<EncryptionKey<Integer>> shiftMultiplyEncryption = new ShiftMultiplyEncryption(randomUtil);
        EncryptionAlgorithm<CompositeEncryptionKey<Integer>> repeatEncryption = new RepeatEncryption(shiftMultiplyEncryption, 2);
        MenuOperationSelector menuOperationSelector = new MenuOperationSelector(operationMap);
        EncryptionFilesUtil encryptionFilesUtil = new EncryptionFilesUtil(fileNameUtil, filesUtil);
        EncryptionHandler<CompositeEncryptionKey<Integer>> encryptionHandler = new EncryptionHandler<>(dataConvertionUtil, repeatEncryption, encryptionFilesUtil, filesUtil, ioUtil);
        MenuOperationHandler<CompositeEncryptionKey<Integer>> menuOperationHandler = new MenuOperationHandler<>(encryptionHandler, filesUtil, ioUtil);
        menuOperationSelector.registerOperations(menuOperationHandler);
        ApplicationInitializer applicationInitializer = new ApplicationInitializer(menuOperationSelector, menuIOUtil, ioUtil);
        applicationInitializer.initializeApp();
    }

}
