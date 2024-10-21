package out;

import adminEntity.Admin;
import exception.DataAlreadyExistsException;
import in.InputHandler;
import userEntity.User;
import userRepository.UserRepository;

import java.io.IOException;

public class AuthHandler {

    private final InputHandler inputHandler =  new InputHandler();
    private final OutputHandler outputHandler =  new OutputHandler();
    private final Admin admin = Admin.getInstance();
    private final UserRepository userRepository = new UserRepository();
    private final UserHandler userHandler = new UserHandler();
    private final AdminHandler adminHandler = new AdminHandler();

    public AuthHandler() throws IOException {
    }

    public void authMenu() throws DataAlreadyExistsException {
        boolean endSession = false;
        //Saving userId to put it into UserSession, so UserSession will now with which user it should work
        String userId = null;

        while (!endSession) {
            outputHandler.displayAuthMenu();
            String input = inputHandler.getUserInput();

            switch (input) {
                //Registration module
                case "1":
                    boolean correctRegistrationUserData = false;
                    while (!correctRegistrationUserData) {

                        String name = inputHandler.getUserInputOrExit("name", "registration");
                        // If user typed exit, method getUserInputOrExit() returns null, so we can properly quit the
                        // loop
                        if (name == null) break;

                        String email = inputHandler.getUserInputOrExit("email", "registration");
                        if (email == null) break;

                        String password = inputHandler.getUserInputOrExit("password", "registration");
                        if (password == null) break;

                        if (name.isEmpty() || email.isEmpty() || password.isEmpty()) {
                            System.out.println("Fields can't be empty, try again");
                            System.out.println();
                            break;
                        }

                        if (userRepository.isEmailExists(email)) {
                            System.out.println("User with this email already exists, choose another email or sign in");

                        } else {
                            User newUser = new User(name, email, password);
                            userRepository.addUser(newUser);
                            System.out.println("Now you can sign in!");
                            System.out.println();
                            correctRegistrationUserData = true;
                        }
                    }
                    break;
                //Sign in module
                case "2":
                    //Check if there are no registered users, so we forbid user to sign in
                    if (!userRepository.getAllUsersAndHabits().isEmpty()) {
                        boolean correctSignInUserData = false;
                        while (!correctSignInUserData) {

                            String name = inputHandler.getUserInputOrExit("name", "sign in");
                            if (name == null) break;

                            String password = inputHandler.getUserInputOrExit("password", "sign in");
                            if (password == null) break;

                            userId = userRepository.isUserExists(name, password);
                            if (userId == null) {
                                System.out.println("Wrong user name or password, try again");
                            } else {
                                correctSignInUserData = true;
                                userHandler.userSession(userId);
                            }
                        }
                    } else {
                        System.out.println("There is no users, be first!");
                        System.out.println();
                    }
                    break;
                //Sign in as admin module
                case "3":
                    boolean correctSignInAdminData = false;
                    while (!correctSignInAdminData) {
                        String name = inputHandler.getUserInputOrExit("name", "sign in as admin");
                        if (name == null) break;

                        String email = inputHandler.getUserInputOrExit("email", "sign in as admin");
                        if (email == null) break;

                        String password = inputHandler.getUserInputOrExit("password", "sign in sa admin");
                        if (password == null) break;

                        try {
                            admin.checkIfAdmin(name, email, password);
                            correctSignInAdminData = true;
                            adminHandler.adminMenu();
                        } catch (IllegalArgumentException e) {
                            System.out.println(e.getMessage());
                            System.out.println("Try again");
                            System.out.println();
                        }
                    }
                    break;

                case "4":
                    endSession = true;
                    break;

                default:
                    System.out.println("Invalid choice, please try again");
                    System.out.println();
            }
        }
    }

}
