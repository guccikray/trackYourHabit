package habitEntity;

import java.time.LocalDate;

public class HabitCompletion {

    private LocalDate date;
    private boolean isCompleted;
    private Habit habit;

    public HabitCompletion(LocalDate date, boolean isCompleted) {
        this.date = date;
        this.isCompleted = isCompleted;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public boolean isCompleted() {
        return isCompleted;
    }

    public void setCompleted(boolean completed) {
        isCompleted = completed;
    }

}
