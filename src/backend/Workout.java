package backend;

public class Workout {
    public String exercise;
    public int reps;
    public int sets;

    public Workout(String exercise, int reps, int sets) {
        this.exercise = exercise;
        this.reps = reps;
        this.sets = sets;
    }
}