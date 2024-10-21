package out;

import habitEntity.Habit;
import in.InputHandler;

import java.time.LocalDate;

public class HabitStatisticsHandler {

    private final InputHandler inputHandler = new InputHandler();
    private final OutputHandler outputHandler = new OutputHandler();

    public void habitsSectionMenu(Habit habit) {
        boolean isMenuClosed = false;

        while (!isMenuClosed) {
            outputHandler.displayHabitStatisticsSectionMenu();
            String input = inputHandler.getUserInput();

            switch (input) {
                case "1":
                    //Get the number of days the habit is completed
                    System.out.println("Date format should be yyyy-MM-dd");
                    String startDateStreak = inputHandler.getUserInputOrExit("start date streak",
                            "get the number of days the habit is completed");

                    if (startDateStreak == null) break;

                    boolean validStartDateStreak = inputHandler.isValidDate(startDateStreak);

                    if (!validStartDateStreak) {
                        System.out.println("Wrong date format");
                        System.out.println();
                        break;
                    }

                    System.out.println("Date format should be yyyy-MM-dd");
                    String endDateStreak = inputHandler.getUserInputOrExit("end date streak",
                            "get the number of days the habit is completed");

                    if (endDateStreak == null) break;

                    boolean validEndDateStreak = inputHandler.isValidDate(endDateStreak);

                    if (!validEndDateStreak) {
                        System.out.println("Wrong date format");
                        System.out.println();
                        break;
                    }

                    System.out.println("Your completion statistics between chosen dates:");
                    System.out.println(habit.getCompletionStats(LocalDate.parse(startDateStreak),
                            LocalDate.parse(endDateStreak)));
                    break;

                case "2":
                    //Get current streak
                    System.out.println("Your current streak:");
                    System.out.println(habit.getCurrentStreak());
                    break;

                case "3":
                    //Get success rate
                    System.out.println("Date format should be yyyy-MM-dd");
                    String startDateSuccessRate = inputHandler.getUserInputOrExit("start date success rate",
                            "get success rate");

                    if (startDateSuccessRate == null) break;

                    boolean validStartDateSuccessRate = inputHandler.isValidDate(startDateSuccessRate);

                    if (!validStartDateSuccessRate) {
                        System.out.println("Wrong date format");
                        System.out.println();
                        break;
                    }

                    System.out.println("Date format should be yyyy-MM-dd");
                    String endDateSuccessRate = inputHandler.getUserInputOrExit("end date success rate",
                            "get success rate");

                    if (endDateSuccessRate == null) break;

                    boolean validEndDateSuccessRate = inputHandler.isValidDate(endDateSuccessRate);

                    if (!validEndDateSuccessRate) {
                        System.out.println("Wrong date format");
                        System.out.println();
                        break;
                    }

                    System.out.println("Your success rate:");
                    System.out.println(habit.getSuccessRate(LocalDate.parse(startDateSuccessRate),
                            LocalDate.parse(endDateSuccessRate)));
                    break;

                case "4":
                    //Get whole progress report
                    System.out.println("Date format should be yyyy-MM-dd");
                    String startDateProgressReport = inputHandler.getUserInputOrExit("start date report progress",
                            "get whole progress report");

                    if (startDateProgressReport == null) break;

                    boolean validStartDateProgressReport = inputHandler.isValidDate(startDateProgressReport);

                    if (!validStartDateProgressReport) {
                        System.out.println("Wrong date format");
                        System.out.println();
                        break;
                    }

                    System.out.println("Date format should be yyyy-MM-dd");
                    String endDateProgressReport = inputHandler.getUserInputOrExit("end date report progress",
                            "get whole progress report");

                    if (endDateProgressReport == null) break;

                    boolean validEndDateProgressReport = inputHandler.isValidDate(endDateProgressReport);

                    if (!validEndDateProgressReport) {
                        System.out.println("Wrong date format");
                        System.out.println();
                        break;
                    }

                    System.out.println("Your progress report:");
                    System.out.println(habit.getHabitProgressReport(LocalDate.parse(startDateProgressReport),
                            LocalDate.parse(endDateProgressReport)));
                    break;

                case "5":
                    isMenuClosed = true;
                    break;

                default:
                    System.out.println("Invalid choice, please try again");
                    System.out.println();
            }
        }
    }
}
