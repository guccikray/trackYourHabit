package userRepository;

import exception.DataAlreadyExistsException;
import habitEntity.Frequency;
import habitEntity.Habit;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import userEntity.User;

import java.time.LocalDate;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;

class UserRepositoryTest {

    private UserRepository userRepository;

    private User newUser;
    private Habit newHabit;

    @BeforeEach
    void initUserRepositoryAndUser() {
        userRepository = Mockito.spy(UserRepository.class);
        newUser = new User("bob", "dod@gmail.com", "qwerty");
        userRepository.addUser(newUser);
        newHabit = new Habit("exercise", "do exercise every morning", Frequency.EVERYDAY);
        userRepository.addHabitToUser(newUser.getUserId(), newHabit);
    }

    @Test
    void testAddUser() {
        verify(userRepository).addUser(newUser);
    }

    @Test
    void testChangeUserName() throws DataAlreadyExistsException {
        String newName = "john";
        userRepository.changeUserName(newUser.getUserId(), newName);

        assertThat(newUser.getName()).isEqualTo(newName);
    }

    @Test
    void testChangeUserName_ThisUserNameAlreadyInUse() throws DataAlreadyExistsException {
        String newName = "bob";

        assertThrows(DataAlreadyExistsException.class, () -> {
                userRepository.changeUserName(newUser.getUserId(), newName);
        });
    }

    @Test
    void testChangeUserEmail() throws DataAlreadyExistsException{
        String newEmail = "kray@gmail.com";
        userRepository.changeUserEmail(newUser.getUserId(), newEmail);

        assertThat(newUser.getEmail()).isEqualTo(newEmail);
    }

    @Test
    void testChangeUserEmail_ThisEmailAlreadyInUse() throws DataAlreadyExistsException {
        String newEmail = "dod@gmail.com";

        assertThrows(DataAlreadyExistsException.class, () -> {
            userRepository.changeUserEmail(newUser.getUserId(), newEmail);
        });
    }

    @Test
    void testIsEmailExists_ReturnsTrue() {
        String email = "dod@gmail.com";

        assertTrue(userRepository.isEmailExists(email));
    }

    @Test
    void testIsEmailExists_ReturnsFalse() {
        String email = "git@gmail.com";

        assertFalse(userRepository.isEmailExists(email));
    }

    @Test
    void testChangeUserPassword() throws DataAlreadyExistsException{
        String newPassword = "zxcvb";
        userRepository.changeUserPassword(newUser.getUserId(), newPassword);

        assertThat(newUser.getPassword()).isEqualTo(newPassword);
    }

    @Test
    void testChangeUserPassword_ThisUserPasswordAlreadyInUse() throws DataAlreadyExistsException{
        String newPassword = "qwerty";

        assertThrows(DataAlreadyExistsException.class, () -> {
            userRepository.changeUserPassword(newUser.getUserId(), newPassword);
        });
    }

    @Test
    void testGetAllUserAndHabits() {
        assertThat(userRepository.getAllUsersAndHabits()).isNotNull();
    }

    @Test
    void testDeleteUser() {
        userRepository.deleteUser(newUser.getUserId());

        assertFalse(userRepository.getAllUsersAndHabits().containsKey(newUser.getUserId()));
    }


    @Test
    void testAddHabitToUser() {
        verify(userRepository).addHabitToUser(newUser.getUserId(), newHabit);
    }


    @Test
    void testRemoveHabitFromUser() {
        userRepository.removeHabitFromUser(newUser.getUserId(), newHabit.getHabitId());

        assertFalse(newUser.hasHabit(newHabit.getHabitId()));
    }


    @Test
    void testEditUserHabit() {
        String newHabitName = "jogging";
        String newHabitDescription = "jogging every morning";
        userRepository.editUserHabit(newUser.getUserId(), newHabit.getHabitId(), newHabitName, newHabitDescription,
                newHabit.getFrequency());

        assertEquals(newHabitName, newHabit.getName());
        assertEquals(newHabitDescription, newHabit.getDescription());
    }


    @Test
    void testGetUserHabitsFilteredByCategory() {
        assertThat(userRepository.getUserHabitsFilteredByCategory(newUser.getUserId(), Frequency.EVERYDAY)).isNotNull();
    }


    @Test
    void testGetUserHabitsFilteredByCategory_UserHasNoHabits() throws NullPointerException {
        userRepository.removeHabitFromUser(newUser.getUserId(), newHabit.getHabitId());

        assertThrows(NullPointerException.class, () -> {
            userRepository.getUserHabitsFilteredByCategory(newUser.getUserId(), Frequency.EVERYDAY);
        });
    }

    @Test
    void testGetUserHabitsFilteredByCategory_HabitsWithThisCategoryCantBeFound() throws NullPointerException {
        assertThrows(NullPointerException.class, () -> {
            userRepository.getUserHabitsFilteredByCategory(newUser.getUserId(), Frequency.EVERYWEEK);
        });
    }

    @Test
    void testGetUserHabitsFilteredByDateOfCreation() {
        assertThat(userRepository.getUserHabitsFilteredByDateOfCreation(newUser.getUserId(),
                LocalDate.now())).isNotNull();
    }


    @Test
    void testGetUserHabitsFilteredByDateOfCreation_UserHasNoHabits() throws NullPointerException {
        userRepository.removeHabitFromUser(newUser.getUserId(), newHabit.getHabitId());
        assertThrows(NullPointerException.class, () -> {
            userRepository.getUserHabitsFilteredByDateOfCreation(newUser.getUserId(), LocalDate.now());
        });
    }

    @Test
    void testGetUserHabitsFilteredByDateOfCreation_HabitsWithThisDateOfCreationCantBeFound() throws NullPointerException {
        assertThrows(NullPointerException.class, () -> {
            userRepository.getUserHabitsFilteredByDateOfCreation(newUser.getUserId(),
                    LocalDate.now().minusDays(100));
        });
    }
}
