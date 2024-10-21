package out;

import in.InputHandler;
import userRepository.UserRepository;

public class DeleteUserHandler {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();
    private final UserRepository userRepository = new UserRepository();

    public void deleteUserMenu(String userId) {
        boolean isUserDeletedOrMenuClosed = false;

        outputHandler.displayDeleteAccountMenu();

        while (!isUserDeletedOrMenuClosed) {
            String input = inputHandler.getUserInput();

            switch (input) {
                case "1":
                    userRepository.deleteUser(userId);
                    System.out.println();
                    isUserDeletedOrMenuClosed = true;
                    break;

                case "2":
                    isUserDeletedOrMenuClosed = true;
                    break;

                default:
                    System.out.println("Invalid choice, please try again");
                    System.out.println();
            }
        }
    }
}
