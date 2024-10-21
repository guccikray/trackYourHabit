package out;

import habitEntity.Frequency;
import habitEntity.Habit;
import in.InputHandler;
import userEntity.User;
import userRepository.UserRepository;

import java.time.LocalDate;
import java.util.List;

public class HabitHandler {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();
    private final UserRepository userRepository = new UserRepository();
    private final DeleteHabitHandler deleteHabitHandler = new DeleteHabitHandler();
    private final HabitStatisticsHandler habitStatisticsHandler = new HabitStatisticsHandler();

    public void habitsMenu(String userId) {
        boolean isMenuClosed = false;
        boolean userHasNoHabits = true;

        User user = userRepository.getUser(userId);
        List<Habit> habits = user.getHabits();

        while (!isMenuClosed) {
            if (habits.isEmpty()) {
                System.out.println("You have no habits");
            } else {
                userHasNoHabits = false;
                System.out.println("Your habits:");
                for (int i = 0; i < habits.size(); i++) {
                    System.out.println(i + 1 +". " + habits.get(i));
                }
            }
            outputHandler.displayHabitSectionMenu();
            String input = inputHandler.getUserInput();

            switch (input) {
                case "1":
                    // Add habit section
                    boolean correctNewHabitData = false;
                    while (!correctNewHabitData) {

                        String name = inputHandler.getUserInputOrExit("name", "add habit");
                        // If user typed exit, method getUserInputOrExit() returns null, so we can properly quit the
                        // loop
                        if (name == null) break;

                        String description = inputHandler.getUserInputOrExit("description", "add habit");
                        if (description == null) break;

                        Frequency frequency = chooseFrequency();

                        if (frequency == null) break;

                        if (name.isEmpty() || description.isEmpty()) {
                            System.out.println("Fields can't be empty, try again");
                            System.out.println();
                            break;
                        }

                        Habit newHabit = new Habit(name, description, frequency);
                        userRepository.addHabitToUser(userId, newHabit);
                        correctNewHabitData = true;
                    }
                    break;

                case "2":
                    //Edit habit section
                    if (userHasNoHabits){
                        System.out.println("You have no habits");
                    } else {
                        System.out.println("Type number of habit that you want to edit");
                        Habit habitToEdit = getHabitByNumber("edit", habits);

                        if (habitToEdit == null) break;

                        boolean correctEditHabitData = false;
                        while (!correctEditHabitData) {

                            String name = inputHandler.getUserInputOrExit("name", "edit habit");
                            // If user typed exit, method getUserInputOrExit() returns null, so we can properly quit the
                            // loop
                            if (name == null) break;

                            String description = inputHandler.getUserInputOrExit("description",
                                    "edit habit");
                            if (description == null) break;

                            Frequency frequency = chooseFrequency();

                            if (frequency == null) break;

                            if (name.isEmpty() || description.isEmpty()) {
                                System.out.println("Fields can't be empty, try again");
                                System.out.println();
                                break;
                            }

                            userRepository.editUserHabit(userId, habitToEdit.getHabitId(), name, description, frequency);
                            correctEditHabitData = true;
                        }
                    }
                    break;

                case "3":
                    //Remove habit section
                    if (userHasNoHabits) {
                        System.out.println("You have no habits");
                    } else {
                        Habit habitToRemove = getHabitByNumber("remove", habits);

                        if (habitToRemove == null) break;

                        deleteHabitHandler.deleteHabitMenu(userId, habitToRemove.getHabitId());
                    }
                    break;

                case "4":
                    // Mark habit as completed section
                    if (userHasNoHabits) {
                        System.out.println();
                        System.out.println("You have no habits");
                        System.out.println();
                    } else {
                        Habit habitToMarkCompletion = getHabitByNumber("mark completion", habits);

                        if (habitToMarkCompletion == null) break;

                        System.out.println("Date format should be yyyy-MM-dd");
                        String date = inputHandler.getUserInputOrExit("date of completion",
                                "mark completion");
                        if (date == null) break;

                        boolean validData = inputHandler.isValidDate(date);

                        if (!validData) {
                            System.out.println("Wrong date format");
                            System.out.println();
                            break;
                        }

                        String completion = inputHandler.getUserInputOrExit("'yes' or 'no'",
                                "mark completion");
                        if (completion == null) break;

                        boolean isCompleted;

                        if (completion.equals("yes")) isCompleted = true;

                        else if (completion.equals("no")) isCompleted = false;

                        else {
                            System.out.println("Wrong input");
                            System.out.println();
                            break;
                        }

                        habitToMarkCompletion.markCompletion(LocalDate.parse(date), isCompleted);
                    }

                    break;

                case "5":
                    // Get habits filtered by category section
                    if (userHasNoHabits) {
                        System.out.println("You have no habits");
                        System.out.println();
                    } else {
                        Frequency frequency = chooseFrequency();

                        try {
                            List<Habit> habitsFilteredByCategory = userRepository.getUserHabitsFilteredByCategory(userId,
                                    frequency);
                            habitsFilteredByCategory.forEach(System.out::println);
                        } catch (NullPointerException e) {
                            System.out.println(e.getMessage());
                        }
                    }

                     break;

                case "6":
                    // Get habits filtered by date of creation section
                    if (userHasNoHabits) {
                        System.out.println("You have no habits");
                        System.out.println();
                    } else {
                        System.out.println("Date format should be yyyy-MM-dd");
                        String dateOfCreation = inputHandler.getUserInputOrExit("date of creation",
                                "get habits filtered by category");

                        boolean validData = inputHandler.isValidDate(dateOfCreation);

                        if (!validData) {
                            System.out.println("Wrong date format");
                            System.out.println();
                            break;
                        }

                        try {
                            List<Habit> habitsFilteredByDateOfCreation =
                                    userRepository.getUserHabitsFilteredByDateOfCreation(userId,
                                            LocalDate.parse(dateOfCreation));
                            habitsFilteredByDateOfCreation.forEach(System.out::println);
                        } catch (NullPointerException e) {
                            System.out.println(e.getMessage());
                        }
                    }
                    break;

                case "7":
                    // Go to habits statistics section
                    if (userHasNoHabits) {
                        System.out.println("You have no habits");
                        System.out.println();
                    } else {
                        Habit habit = getHabitByNumber("statistics section", habits);
                        habitStatisticsHandler.habitsSectionMenu(habit);
                        isMenuClosed = true;
                    }
                    break;

                case "8":
                    isMenuClosed = true;
                    break;

                default:
                    System.out.println("Invalid choice, please try again");
                    System.out.println();
            }

        }
    }
    // Method that converts user input into frequency
    private Frequency chooseFrequency() {
        while (true) {
            System.out.println("Choose frequency:");
            System.out.println("1. Everyday");
            System.out.println("2. Every week");

            String frequency = inputHandler.getUserInputOrExit("number", "add habit");
            if (frequency == null) return null;

            switch (frequency) {
                case "1":
                    return Frequency.EVERYDAY;
                case "2":
                    return Frequency.EVERYWEEK;
                default:
                    System.out.println("Wrong frequency chosen, try again.");
            }
        }
    }
    // Method that takes habit from list based number that user chose
    private Habit getHabitByNumber(String section, List<Habit> habits) throws NumberFormatException {
        if (habits.isEmpty()) {
            System.out.println("No habits to " + section);
            return null;
        }

        int habitToEditIndex = 0;

        try {
            habitToEditIndex = Integer.parseInt(inputHandler.getUserInputOrExit("number of habit",
                    section + " habit"));
        } catch (NumberFormatException e) {
            System.out.println("Wrong input, try again");
            System.out.println();
            return null;
        }

        if (habitToEditIndex - 1 < 0 || habitToEditIndex - 1 >= habits.size()) {
            System.out.println("Invalid habit number");
            System.out.println();
            return null;
        }
        return habits.get(habitToEditIndex - 1);
    }
}
