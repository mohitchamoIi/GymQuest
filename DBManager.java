import java.sql.*;

public class DBManager {

    static final String URL = "jdbc:mysql://localhost:3306/gymquest";
    static final String USER = "root";
    static final String PASSWORD = "Gym1234"; // confidential

    public static Connection getConnection() throws Exception {
        return DriverManager.getConnection(URL, USER, PASSWORD);
    }

    public static User loadUser(String name) {
        try {
            Connection con = getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "SELECT * FROM users WHERE name=?"
            );
            ps.setString(1, name);

            ResultSet rs = ps.executeQuery();

            if (rs.next()) {
                User user = new User(rs.getString("name"));
                user.xp = rs.getInt("xp");
                user.level = rs.getInt("level");
                return user;
            }

            // New user
            User user = new User(name);
            saveUser(user);
            return user;

        } catch (Exception e) {
            e.printStackTrace();
        }

        return new User(name);
    }

    public static void saveUser(User user) {
        try {
            Connection con = getConnection();

            PreparedStatement ps = con.prepareStatement(
                    "REPLACE INTO users (name, xp, level) VALUES (?, ?, ?)"
            );

            ps.setString(1, user.name);
            ps.setInt(2, user.xp);
            ps.setInt(3, user.level);

            ps.executeUpdate();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}