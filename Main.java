public class Main {
    public static void main(String[] args) {

        User user = FileManager.loadUser();

        new GymUI(user);
    }
}