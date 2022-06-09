package software.robsoncassiano.learn;

import com.zaxxer.hikari.HikariDataSource;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DataSourceExample {
    public static void main(String[] args) {
        DataSource dataSource = createDataSource();

        try (Connection connection = dataSource.getConnection()) {
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
            throw new RuntimeException(e);
        }

    }

    private static DataSource createDataSource() {
        HikariDataSource ds = new HikariDataSource();
        ds.setJdbcUrl("jdbc:h2:mem:;INIT=RUNSCRIPT FROM 'classpath:users.sql';");
        return ds;
    }

}
