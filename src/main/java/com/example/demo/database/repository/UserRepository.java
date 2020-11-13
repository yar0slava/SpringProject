package com.example.demo.database.repository;

import javax.annotation.PostConstruct;

import com.example.demo.database.entity.User;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.Optional;

@Repository
public class UserRepository {

    //database URL
    static final String URL = "jdbc:postgresql://localhost:5432/postgres";

    //DATABASE credentionls
    static final String USER = "postgres";
    static final String PASSWORD = "postgres";

//    @PostConstruct
    public void init() {
        User user = new User();
        user.setId(9);
        user.setAge(22);
        user.setFirstName("Sofi");
        user.setLastName("Olenyn");

        save(user);
    }

    public void save(User user) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            String INSERT_QUERY = "INSERT INTO demo_user (id, last_name, first_name, age)" +
                    "VALUES (?,?,?,?);";

            try (PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
                connection.setAutoCommit(false);
                preparedStatement.setLong(1, user.getId());
                preparedStatement.setString(2, user.getFirstName());
                preparedStatement.setString(3, user.getLastName());
                preparedStatement.setInt(4, user.getAge());
                preparedStatement.executeQuery();
                connection.commit();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            } finally {
                connection.setAutoCommit(true);
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

    }

    public Optional<User> findById(Long id) {

        String SELECT_QUERY = "SELECT * FROM demo_user WHERE id=?;";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY)) {
            connection.setAutoCommit(false);
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setLastName(resultSet.getString("last_name"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setAge(resultSet.getInt("age"));
                System.out.println(user);
                return Optional.of(user);
            }
            connection.commit();
            connection.setAutoCommit(true);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }
}
