package out;

import in.InputHandler;
import userRepository.UserRepository;

public class DeleteHabitHandler {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();
    private final UserRepository userRepository = new UserRepository();

    public void deleteHabitMenu(String userId, String habitId) {
        boolean isHabitDeletedOrMenuClosed = false;

        outputHandler.displayDeleteHabitMenu();

        while (!isHabitDeletedOrMenuClosed) {
            String input = inputHandler.getUserInput();

            switch (input) {
                case "1":
                    userRepository.removeHabitFromUser(userId, habitId);
                    System.out.println();
                    isHabitDeletedOrMenuClosed = true;
                    break;

                case "2":
                    isHabitDeletedOrMenuClosed = true;
                    break;

                default:
                    System.out.println("Invalid choice, please try again");
                    System.out.println();
            }
        }
    }
}
