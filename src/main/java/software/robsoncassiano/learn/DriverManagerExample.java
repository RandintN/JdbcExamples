package software.robsoncassiano.learn;

import java.sql.*;

public class Main {
    public static void main(String[] args) {
        try (Connection connection = DriverManager.getConnection("jdbc:h2:mem:;INIT=RUNSCRIPT FROM 'classpath:users.sql';")) {
            System.out.println("JDBC Connection is valid? " + connection.isValid(0));

            // Select
            final PreparedStatement ps = connection.prepareStatement("select * from USERS where name = ?");
            ps.setString(1, "Marco");
            final ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                System.out.println(resultSet.getInt("id") + " - " + resultSet.getString("name"));
            }

            // Insert
            final PreparedStatement insertPs = connection.prepareStatement("insert into USERS (name) values (?)");
            insertPs.setString(1, "John");
            System.out.println("insertCount = " + insertPs.executeUpdate());

            // Updates
            final PreparedStatement updatePs = connection.prepareStatement("update USERS set name = ? where name = ?");
            updatePs.setString(1, "Johnny");
            updatePs.setString(2, "John");

            System.out.println("Update count = " + updatePs.executeUpdate());

            // Deletes
            final PreparedStatement deletePs = connection.prepareStatement("delete from Users where name = ?");
            deletePs.setString(1, "Johnny");
            System.out.println("Delete count = " + deletePs.executeUpdate());

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}