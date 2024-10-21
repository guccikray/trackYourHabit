package out;

import exception.DataAlreadyExistsException;
import in.InputHandler;
import userRepository.UserRepository;

import java.io.IOException;

public class UserHandler {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();
    private final UserRepository userRepository = new UserRepository();
    private final DeleteUserHandler deleteUserHandler = new DeleteUserHandler();
    private final HabitHandler habitHandler = new HabitHandler();

    public UserHandler() throws IOException {
    }

    public void userSession(String userId) throws DataAlreadyExistsException {
        System.out.println();
        System.out.println("You signed in successfully!");
        System.out.println();
        boolean endSession = false;
        while (!endSession) {
            System.out.println(userRepository.getUser(userId).toString());
            outputHandler.displayMainUserMenu();
            String input = inputHandler.getUserInput();

            switch (input) {
                case "1":
                    System.out.println("Type new user name");
                    String newName = inputHandler.getUserInputOrExit("name", "change name");

                    if (newName == null) break;

                    try {
                        userRepository.changeUserName(userId, newName);
                    } catch (DataAlreadyExistsException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "2":
                    System.out.println("Type new email");
                    String newEmail = inputHandler.getUserInputOrExit("email", "change email");

                    if (newEmail == null) break;

                    try {
                        userRepository.changeUserEmail(userId, newEmail);
                    } catch (DataAlreadyExistsException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "3":
                    System.out.println("Type new password");
                    String newPassword = inputHandler.getUserInputOrExit("password",
                            "change password");

                    if (newPassword == null) break;

                    try {
                        userRepository.changeUserPassword(userId, newPassword);
                    } catch (DataAlreadyExistsException e) {
                        System.out.println(e.getMessage());
                    }
                    break;

                case "4":
                    deleteUserHandler.deleteUserMenu(userId);

                    if (userRepository.getUser(userId) == null) {
                        endSession = true;
                    }
                    break;

                case "5":
                    habitHandler.habitsMenu(userId);
                    break;

                case "6":
                    endSession = true;
                    break;

                default:
                    System.out.println("Invalid choice, please try again");
                    System.out.println();
            }
        }
    }
}
