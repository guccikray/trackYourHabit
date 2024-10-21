package out;

public class OutputHandler {

    public void displayMessage(String message) {
        System.out.println(message);
    }

    public void displayAuthMenu() {
        System.out.println("Welcome to TrackYourHabit!");
        System.out.println("Register or sign in if you already have an existing account");
        System.out.println("Type a number to choose an action:");
        System.out.println("1. Register");
        System.out.println("2. Sign in as user");
        System.out.println("3. Sign in as admin");
        System.out.println("4. Exit");
    }

    public void displayMainUserMenu() {
        System.out.println("Type a number to choose an action:");
        System.out.println("1. Change name");
        System.out.println("2. Change email");
        System.out.println("3. Change password");
        System.out.println("4. Delete account");
        System.out.println("5. Open habits section");
        System.out.println("6. Log out");
    }

    public void displayDeleteAccountMenu() {
        System.out.println("Do you sure that you want to delete account?");
        System.out.println("1. Yes");
        System.out.println("2. No");
    }

    public void displayHabitSectionMenu() {
        System.out.println("1. Add new habit");
        System.out.println("2. Edit habit");
        System.out.println("3. Remove habit");
        System.out.println("4. Mark the completion of a habit");
        System.out.println("5. Get habits filtered by category");
        System.out.println("6. Get habits filtered by date of creation");
        System.out.println("7. Open statistics section");
        System.out.println("8. Go back");
    }

    public void displayDeleteHabitMenu() {
        System.out.println("Do you sure that you want to delete habit?");
        System.out.println("1. Yes");
        System.out.println("2. No");
    }

    public void displayHabitStatisticsSectionMenu() {
        System.out.println("1. Get the number of days the habit is completed");
        System.out.println("2. Get current streak");
        System.out.println("3. Get success rate");
        System.out.println("4. Get whole progress report");
        System.out.println("5. Go back");
    }

    public void displayAdminMainMenu() {
        System.out.println("1. Delete user");
        System.out.println("2. Log out");
    }
}
