package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    private final Connection conn;

    public UserDaoJDBCImpl() {
        Util util = new Util();
        conn = util.createConnection();
    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS Person"
                + "(id INTEGER not NULL AUTO_INCREMENT, "
                + " firstName VARCHAR(50), "
                + " lastName VARCHAR(50), "
                + " age INTEGER, "
                + " PRIMARY KEY ( id ))";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS Person";
        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public void saveUser(String firstName, String lastName, byte age) {
        try (PreparedStatement pstm = conn.prepareStatement("INSERT INTO Person (firstName, lastName, age) VALUES (?, ?, ?)")) {
            pstm.setString(1, firstName);
            pstm.setString(2, lastName);
            pstm.setByte(3, age);
            pstm.executeUpdate();
            System.out.println("User с именем – " + firstName + " добавлен в базу данных");
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        try (PreparedStatement pstm = conn.prepareStatement("DELETE FROM Person WHERE id = ?")) {
            pstm.setInt(1, (int) id);
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();
        try (Statement stmt = conn.createStatement()) {
            ResultSet res = stmt.executeQuery("SELECT * FROM  Person");
            while (res.next()) {
                User user = new User(res.getString(2), res.getString(3), res.getByte(4));
                user.setId(res.getLong(1));
                userList.add(user);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        try (PreparedStatement pstm = conn.prepareStatement("DELETE FROM Person")) {
            pstm.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
