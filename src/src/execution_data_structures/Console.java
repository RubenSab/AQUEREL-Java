package execution_data_structures;

import java.util.Scanner;

public class Console {
    private Scanner inputScanner;

    public Console() {
        inputScanner = new Scanner(System.in);
    }

    public String readInput() {
        return inputScanner.nextLine();
    }

    public void printOutput(String output) {
        System.out.println(output);
    }
}