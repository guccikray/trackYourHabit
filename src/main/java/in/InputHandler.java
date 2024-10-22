package in;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class InputHandler {

    private final Scanner scanner = new Scanner(System.in);

    public String getUserInput() {
        return scanner.nextLine();
    }

    public void close() {
        scanner.close();
    }

    //Method checks if user typed exit, if so we send user back to auth menu
    public String getUserInputOrExit(String dataType, String sectionName) {
        System.out.println("Type " + dataType + " (or type 'exit' to cancel " + sectionName +" section)");
        String input = getUserInput();
        if (input.equalsIgnoreCase("exit")) {
            System.out.println(sectionName + " canceled.");
            System.out.println();
            return null;
        }
        return input;
    }

    // Checks if data format is correct
    public boolean isValidDate(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        try {
            LocalDate.parse(date, formatter);
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}
