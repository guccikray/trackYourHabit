package habitEntity;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import userEntity.User;
import userRepository.UserRepository;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class HabitTest {

    private UserRepository userRepository;
    private User newUser;
    private Habit newHabit;
    private HabitCompletion completion;

    @BeforeEach
    void initUserAndCompletionsList() {
        userRepository = Mockito.spy(UserRepository.class);
        newUser = new User("bob", "dod@gmail.com", "qwerty");
        userRepository.addUser(newUser);
        newHabit = new Habit("exercise", "do exercise every morning", Frequency.EVERYDAY);
        LocalDate today = LocalDate.now();
        userRepository.addHabitToUser(newUser.getUserId(), newHabit);
        newHabit.markCompletion(today.minusDays(9), false);
        newHabit.markCompletion(today.minusDays(8), true);
        newHabit.markCompletion(today.minusDays(7), true);
        newHabit.markCompletion(today.minusDays(6), false);
        newHabit.markCompletion(today.minusDays(5), true);
        newHabit.markCompletion(today.minusDays(4), true);
        newHabit.markCompletion(today.minusDays(3), true);
        newHabit.markCompletion(today.minusDays(2), false);
        newHabit.markCompletion(today.minusDays(1), true);
        newHabit.markCompletion(today, true);

    }


    @Test
    void testGetCompletionStats() {
        LocalDate startDate = LocalDate.now().minusDays(9);
        LocalDate endDate = LocalDate.now();

        assertEquals(7, newHabit.getCompletionStats(startDate, endDate));
    }

    @Test
    void testGetCurrentStreak() {
        assertEquals(2, newHabit.getCurrentStreak());
    }

    @Test
    void testGetSuccessRate() {
        LocalDate startDate = LocalDate.now().minusDays(9);
        LocalDate endDate = LocalDate.now();

        assertEquals(70, newHabit.getSuccessRate(startDate, endDate));
    }

    @Test
    void testGetHabitProgressionReport() {
        LocalDate startDate = LocalDate.now().minusDays(9);
        LocalDate endDate = LocalDate.now();

        String report = newHabit.getHabitProgressReport(startDate, endDate);

        String expectedReport = String.format("Habit: %s\n" +
                        "Period: %s to %s\n" +
                        "Total days: %d\n" +
                        "Completions: %d\n" +
                        "Current streak: %d days\n" +
                        "Success rate: %.2f%%\n",
                "exercise", startDate, endDate, 10, 7, 2, 70.0);

        assertEquals(expectedReport, report);
    }
}
