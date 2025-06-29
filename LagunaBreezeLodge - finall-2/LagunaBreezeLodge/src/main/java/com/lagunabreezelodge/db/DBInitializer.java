package com.lagunabreezelodge.db;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

public class DBInitializer {

    public static void initializeDatabase() {
        try (Connection connection = DBConnection.getConnection();
             Statement stmt = connection.createStatement()) {

            // Users table
            String usersTable = "CREATE TABLE IF NOT EXISTS users (" +
                    "id INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "name TEXT NOT NULL," +
                    "email TEXT UNIQUE NOT NULL," +
                    "password TEXT NOT NULL" +
                    ");";

            // Bookings table
            String bookingTable = "CREATE TABLE IF NOT EXISTS bookings (" +
                    "bookingId INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "userId INTEGER NOT NULL," +
                    "roomId INTEGER NOT NULL," +
                    "checkInDate TEXT NOT NULL," +
                    "checkOutDate TEXT NOT NULL," +
                    "numberOfGuests INTEGER NOT NULL," +
                    "totalPrice REAL NOT NULL," +
                    "isCancelled INTEGER DEFAULT 0," +
                    "FOREIGN KEY(userId) REFERENCES users(id)" +
                    ");";

            stmt.executeUpdate(usersTable);
            stmt.executeUpdate(bookingTable);

            System.out.println("Database tables created or verified successfully.");

        } catch (SQLException e) {
            System.err.println("Error initializing database: " + e.getMessage());
            e.printStackTrace();
        } catch (Exception e) {
            throw new RuntimeException("Unexpected error initializing database", e);
        }
    }

    public static Connection getConnection() throws Exception {
        return DBConnection.getConnection();
    }
}