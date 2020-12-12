package com.example.demo.database.repository;

import javax.annotation.PostConstruct;

import com.example.demo.database.entity.User;
import org.omg.PortableInterceptor.INACTIVE;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

@Repository
public class UserRepository {

    //database URL
    static final String URL = "jdbc:postgresql://localhost:5432/lesson3_db";

    //DATABASE credentials
    static final String USER = "postgres";
    static final String PASSWORD = "postgres";

//    @PostConstruct
//    public void init() {
//        User user = new User();
//        user.setId(9);
//        user.setAge(22);
//        user.setFirstName("Sofi");
//        user.setLastName("Olenyn");
//
//        save(user);
//    }

    public void save(User user) {

        String INSERT_QUERY = "INSERT INTO my_user (id, first_name, last_name, email, password)" +
                "VALUES (?,?,?,?,?);";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(INSERT_QUERY)) {
            preparedStatement.setLong(1, user.getId());
            preparedStatement.setString(2, user.getFirstName());
            preparedStatement.setString(3, user.getLastName());
            preparedStatement.setString(4, user.getEmail());
            preparedStatement.setString(5, user.getPassword());
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void update(User user) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            String UPDATE_QUERY = "UPDATE my_user " +
                    " SET first_name=?, last_name=?, email=?, password=?" +
                    " WHERE id=?;";

            try (PreparedStatement preparedStatement = connection.prepareStatement(UPDATE_QUERY)) {
                connection.setAutoCommit(false);

                preparedStatement.setString(1, user.getFirstName());
                preparedStatement.setString(2, user.getLastName());
                preparedStatement.setString(3, user.getEmail());
                preparedStatement.setString(4, user.getPassword());

                preparedStatement.setLong(5, user.getId());
                preparedStatement.executeQuery();
                connection.commit();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
//                returns Exception with message "No results were returned by the query" even if was updated successfully
//                connection.rollback();
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void delete(User user) {

        String DELETE_QUERY = "DELETE FROM my_user WHERE id=?;";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {

            preparedStatement.setLong(1, user.getId());
            preparedStatement.executeQuery();
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteById(Long id) {

        String DELETE_QUERY = "DELETE FROM my_user WHERE id=?;";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {

            preparedStatement.setLong(1, id);
            preparedStatement.executeQuery();

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public void deleteAll() {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {

            String DELETE_QUERY = "DELETE FROM my_user;";

            try (PreparedStatement preparedStatement = connection.prepareStatement(DELETE_QUERY)) {
                connection.setAutoCommit(false);
                preparedStatement.executeQuery();
                connection.commit();
            } catch (SQLException e) {
                // delete all or rollback
                connection.rollback();
                System.out.println(e.getMessage());
            } finally {
                connection.setAutoCommit(true);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
    }

    public Optional<User> findById(Long id) {

        String SELECT_QUERY = "SELECT * FROM my_user WHERE id=?;";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY)) {

            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
//                System.out.println(user);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    public Optional<User> findByEmail(String email) {

        String SELECT_QUERY = "SELECT * FROM my_user WHERE email=?;";

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY)) {

            preparedStatement.setString(1, email);
            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
//                System.out.println(user);
                return Optional.of(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return Optional.empty();
    }

    public HashMap<String, Integer> groupingByName() {

        String SELECT_QUERY = "SELECT first_name, COUNT(id) AS amount" +
                " FROM my_user" +
                " GROUP BY first_name;";

        HashMap<String, Integer> res = new HashMap<>();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                res.put(resultSet.getString("first_name"), resultSet.getInt("amount"));
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }

    public List<User> findAll() {
        String SELECT_QUERY = "SELECT * FROM my_user;";
        LinkedList<User> res = new LinkedList<>();

        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD);
             PreparedStatement preparedStatement = connection.prepareStatement(SELECT_QUERY)) {

            ResultSet resultSet = preparedStatement.executeQuery();

            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("id"));
                user.setFirstName(resultSet.getString("first_name"));
                user.setLastName(resultSet.getString("last_name"));
                user.setEmail(resultSet.getString("email"));
                user.setPassword(resultSet.getString("password"));
//                System.out.println(user);
                res.add(user);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        return res;
    }
}
