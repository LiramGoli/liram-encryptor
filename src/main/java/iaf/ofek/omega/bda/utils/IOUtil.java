package iaf.ofek.omega.bda.utils;

import java.util.Scanner;

public class IOUtil {

    private final Scanner scanner;

    public IOUtil(Scanner scanner) {
        this.scanner = scanner;
    }

    public String readInput(String query) {
        printMessage(query);
        return scanner.nextLine();
    }

    public void printMessage(String message) {
        System.out.println(message);
    }

    public void printErrorMessage(String message) {
        System.err.println(message);
    }

}
