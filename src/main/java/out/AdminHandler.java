package out;

import in.InputHandler;
import userEntity.User;
import userRepository.UserRepository;

import java.util.Map;

public class AdminHandler {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();
    private final UserRepository userRepository = new UserRepository();

    public void adminMenu() {
        System.out.println();
        System.out.println("You signed in as admin successfully!");
        System.out.println();
        boolean endSession = false;
        boolean noUsers = true;

        Map<String, User> allUsers =  userRepository.getAllUsersAndHabits();

        while (!endSession) {

            if (allUsers.isEmpty()) {
                System.out.println("No users have registered yet");
            } else {
                noUsers = false;
                System.out.println("Users");
                int index = 1;
                for (Map.Entry<String, User> entry : allUsers.entrySet()) {
                    String key = entry.getKey();
                    User value = entry.getValue();
                    System.out.println(index + ". Key: " + key + ", " + value);
                    index++;
                }
            }

            outputHandler.displayAdminMainMenu();
            String input = inputHandler.getUserInput();

            switch (input) {
                case "1":
                    if (noUsers) {
                        System.out.println("No users have registered yet");
                        System.out.println();
                        break;
                    } else {
                        System.out.println("Choose user to delete");
                        String userId = getUserByNumber("remove", allUsers);

                        if (userId == null) break;

                        userRepository.deleteUser(userId);
                    }
                    break;

                case "2":
                    endSession = true;
                    break;

                default:
                    System.out.println("Invalid choice, please try again");
                    System.out.println();
            }

        }
    }

    private String getUserByNumber(String section, Map<String, User> allUsers) throws NumberFormatException{
        if (allUsers.isEmpty()) {
            System.out.println("No users to " + section);
            return null;
        }

        int userToRemoveIndex = 0;

        try {
            userToRemoveIndex = Integer.parseInt(inputHandler.getUserInputOrExit("user number",
                    section + " user"));
        } catch (NumberFormatException e) {
            System.out.println("Wrong input, try again");
            System.out.println();
            return null;
        }

        if (userToRemoveIndex - 1 < 0 || userToRemoveIndex - 1 >= allUsers.size()) {
            System.out.println("Invalid habit number");
            System.out.println();
            return null;
        }

        int currentIndex = 0;
        for (String key : allUsers.keySet()) {
            if (currentIndex == userToRemoveIndex - 1) {
                return key;
            }
            currentIndex++;
        }
        return null;
    }
}
