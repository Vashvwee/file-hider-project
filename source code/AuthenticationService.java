package com.filehider;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Random;

public class AuthenticationService {
    private static final String URL = "jdbc:mysql://localhost:3306/filehider";
    private static final String USER = "root";
    private static final String PASSWORD = "root";

    public static void register(String email, String password) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String hashedPassword = hashPassword(password);

            String sql = "INSERT INTO users (email, password) VALUES (?, ?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, hashedPassword);
            statement.executeUpdate();

            System.out.println("Registration successful!");

            OTPService.sendOTP(email);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static boolean login(String email, String password) {
        try (Connection connection = DriverManager.getConnection(URL, USER, PASSWORD)) {
            String hashedPassword = hashPassword(password);

            String sql = "SELECT * FROM users WHERE email = ? AND password = ?";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, email);
            statement.setString(2, hashedPassword);
            ResultSet resultSet = statement.executeQuery();

            return resultSet.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    private static String hashPassword(String password) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            byte[] hash = md.digest(password.getBytes());
            StringBuilder hexString = new StringBuilder();

            for (byte b : hash) {
                hexString.append(String.format("%02x", b));
            }

            return hexString.toString();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }
}
