package backend;

public class XPSystem {

    public static int calculateXP(Workout w) {
        return (w.reps * w.sets) / 2;
    }
}