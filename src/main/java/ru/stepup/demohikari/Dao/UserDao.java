package ru.stepup.demohikari.Dao;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.stepup.demohikari.Entity.User;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@Component
public class UserDao {
    private Connection connection;
    @Autowired
    public UserDao(Connection jdbcConnection) {
        this.connection = jdbcConnection;
    }

    public long save (User user) {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("INSERT INTO users(username) VALUES(?)", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, user.getUsername());
            int row = preparedStatement.executeUpdate();
            if (row > 0) {
                var rs = preparedStatement.getGeneratedKeys();
                if (rs.next()) {
                    user.setId(rs.getLong(1));
                    return rs.getLong(1);
                }
            }
        }
        catch (SQLException e) {
            System.err.format("SQL State: %s\n%s", e.getSQLState(), e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return -1;
    }

    // выборка из таблицы Users в список
    public List<User> getUsers() {

        List<User> listUser = new ArrayList<>();
        try {
            Statement statement = connection.createStatement();
            String SQL = "SELECT * FROM users";
            ResultSet resultSet = statement.executeQuery(SQL);

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setUsername(resultSet.getString("username"));
                listUser.add(user);
            }
        }
        catch (SQLException e) {
            e.printStackTrace();
        }

        return listUser;
    }

    public User getUser(long id) {
        User user = null;

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("SELECT * FROM users WHERE id = ?");
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            user = new User();
            user.setId(resultSet.getLong("id"));
            user.setUsername(resultSet.getString("username"));
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        return user;
    }

    public void updateUser(long id, String username) {

        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("UPDATE users SET username = ? WHERE id = ?");
            preparedStatement.setString(1, username);
            preparedStatement.setLong(2, id);
            int row = preparedStatement.executeUpdate();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void deleteAll() {
        try {
            PreparedStatement preparedStatement =
                    connection.prepareStatement("DELETE FROM users");
            preparedStatement.executeQuery();
        }
        catch (SQLException e) {
        }
    }
}
