package habitEntity;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.UUID;

public class Habit {

    private final String habitId;
    private String name;
    private String description;
    private Frequency frequency;
    private final LocalDate dateOfCreatingHabit;
    private List<HabitCompletion> completions = new ArrayList<>();

    public Habit(String name, String description, Frequency frequency) {
        habitId = UUID.randomUUID().toString();
        this.name = name;
        this.description = description;
        this.frequency = frequency;
        dateOfCreatingHabit = LocalDate.now();
    }

    public String getHabitId() {
        return habitId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Frequency getFrequency() {
        return frequency;
    }

    public void setFrequency(Frequency frequency) {
            this.frequency = frequency;
    }

    public LocalDate getDateOfCreatingHabit() {
        return dateOfCreatingHabit;
    }

    public void markCompletion(LocalDate date, boolean isCompleted) {
        completions.add(new HabitCompletion(date, isCompleted));
    }

    public List<HabitCompletion> getCompletions() {
        return completions;
    }

    public long getCompletionStats(LocalDate startDate, LocalDate endDate) {
        return completions.stream()
                .filter(day -> !day.getDate().isBefore(startDate) && !day.getDate().isAfter(endDate))
                .filter(HabitCompletion::isCompleted)
                .count();
    }

    public int getCurrentStreak() {
        int streak = 0;
        LocalDate today = LocalDate.now();

        completions.sort(Comparator.comparing(HabitCompletion::getDate));

        for (int i = completions.size() - 1; i >= 0; i--) {
            HabitCompletion completion = completions.get(i);

            if (completion.isCompleted() && completion.getDate().equals(today.minusDays(streak))) {
                streak++;
            } else {
                break;
            }
        }
        return streak;
    }

    public double getSuccessRate(LocalDate startDate, LocalDate endDate) {
        long totalDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        if (totalDays <= 0) {
            return 0.0;
        }

        long completedDays = getCompletionStats(startDate, endDate);

        return (double) completedDays / totalDays * 100;
    }

    public String getHabitProgressReport(LocalDate startDate, LocalDate endDate) {
        int streak = getCurrentStreak();
        double successRate = getSuccessRate(startDate, endDate);
        long completionsCount = getCompletionStats(startDate, endDate);
        long totalDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;

        return String.format("Habit: %s\n" +
                        "Period: %s to %s\n" +
                        "Total days: %d\n" +
                        "Completions: %d\n" +
                        "Current streak: %d days\n" +
                        "Success rate: %.2f%%\n",
                name, startDate, endDate, totalDays, completionsCount, streak, successRate);
    }

    @Override
    public String toString() {
        return "\n  Habit: \n   name='" + name + "',\n   description='" + description +
                "',\n   frequency='" + frequency + "',\n   date of creation='" + dateOfCreatingHabit + "'}";
    }
}
