package userRepository;

import exception.DataAlreadyExistsException;
import habitEntity.Frequency;
import habitEntity.Habit;
import userEntity.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class UserRepository {

    private static Map<String, User> usersMap = new HashMap<>();

    public UserRepository () {

    }

    public void addUser(User user) {
        usersMap.put(user.getUserId(), user);
    }

    public String isUserExists(String name, String password) {
        Map<String, User> allUsers = getAllUsersAndHabits();

        for (User user : allUsers.values()) {
            if (user.getName().equals(name) && user.getPassword().equals(password)) {
                return user.getUserId();
            }
        }
        return null;
    }

    public User getUser(String userId) {
        return usersMap.get(userId);
    }

    public void changeUserName(String userId, String newName) throws DataAlreadyExistsException {
        User user = getUser(userId);
        if (user.getName().equals(newName)) {
            throw new DataAlreadyExistsException("This name is already in use, please type new one");
        } else {
            user.setName((newName));
            System.out.println("Username changed successfully");
            System.out.println();
        }
    }

    public void changeUserEmail(String userId, String newEmail) throws DataAlreadyExistsException{
        User user = getUser(userId);
        if (user.getEmail().equals(newEmail)) {
            throw new DataAlreadyExistsException("This email is already in use, please type new one");
        } else {
            user.setEmail((newEmail));
            System.out.println("Email changed successfully");
            System.out.println();
        }
    }

    public boolean isEmailExists(String email) {
        Map<String, User> allUsers = getAllUsersAndHabits();

        for (User user : allUsers.values()) {
            if (user.getEmail().equals(email)) {
                return true;
            }
        }
        return false;
    }

    public void changeUserPassword(String userId, String newPassword) throws DataAlreadyExistsException{
        User user = getUser(userId);
        if (user.getPassword().equals(newPassword)) {
            throw new DataAlreadyExistsException("This password is already in use, please type new one");
        } else {
            user.setPassword((newPassword));
            System.out.println("Password changed successfully");
            System.out.println();
        }
    }

    public void deleteUser(String userId) {
        if (usersMap.containsKey(userId)) {
            usersMap.remove(userId);
            System.out.println("User deleted successfully");
            System.out.println();
        }
    }

    public Map<String, User> getAllUsersAndHabits() {
        return usersMap;
    }

    public void addHabitToUser(String userId, Habit habit) {
       User user = getUser(userId);
       user.addHabit(habit);
       System.out.println("Habit added successfully");
       System.out.println();
    }

    public void removeHabitFromUser(String userId, String habitId) {
        User user = getUser(userId);
        user.removeHabit(habitId);
        System.out.println("Habit removed successfully");
        System.out.println();
    }

    public void editUserHabit(String userId, String habitId, String newName, String newDescription,
                              Frequency newFrequency) {
        User user = getUser(userId);
        user.getHabits().stream()
                .filter(habit -> habit.getHabitId().equals(habitId))
                .forEach(habit -> {
                    habit.setName(newName);
                    habit.setDescription(newDescription);
                    habit.setFrequency(newFrequency);
                });
    }


    public List<Habit> getUserHabitsFilteredByCategory(String userId, Frequency frequency) throws NullPointerException {
        List<Habit> filteredHabits;
        User user = getUser(userId);

        if (user.getHabits() == null || user.getHabits().isEmpty()) {
            throw new NullPointerException("User has no habits");
        } else {
            filteredHabits = user.getHabits().stream()
                    .filter(habit -> habit.getFrequency().equals(frequency))
                    .collect(Collectors.toList());
        }

        if (filteredHabits.isEmpty()) {
            throw new NullPointerException("Habits with this category can't be found");
        }
        return filteredHabits;
    }

    public List<Habit> getUserHabitsFilteredByDateOfCreation(String userId, LocalDate date) throws NullPointerException {
        List<Habit> filteredHabits;

        User user = getUser(userId);

        if (user.getHabits() == null || user.getHabits().isEmpty()) {
            throw new NullPointerException("User has no habits");
        } else {
            filteredHabits = user.getHabits().stream()
                    .filter(habit -> habit.getDateOfCreatingHabit().equals(date))
                    .collect(Collectors.toList());
        }

        if (filteredHabits.isEmpty()) {
            throw new NullPointerException("Habits with this date of creation can't be found");
        }
        return filteredHabits;
    }
}