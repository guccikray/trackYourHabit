package userEntity;

import habitEntity.Habit;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class User {

    private final String userId;
    private String name;
    private String email;
    private String password;

    private List<Habit> habits;


    public User(String name, String email, String password) {
        userId = UUID.randomUUID().toString();
        this.name = name;
        this.email = email;
        this.password = password;
        habits = new ArrayList<>();
    }


    public String getUserId() {
        return userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Habit> getHabits() {
        return habits;
    }

    public void addHabit(Habit habit) {
        habits.add(habit);
    }

    public boolean hasHabit(String habitId) {
        return habits.stream().anyMatch(habit -> habit.getHabitId().equals(habitId));
    }

    public void removeHabit(String habitId) {
        habits.removeIf(habit -> habit.getHabitId().equals(habitId));
    }

    @Override
    public String toString() {
        return "\n User: {userId='" + userId + "',\n name='" + name + "',\n email='" + email  + "',\n habits=" + habits +
                "}";
    }
}
