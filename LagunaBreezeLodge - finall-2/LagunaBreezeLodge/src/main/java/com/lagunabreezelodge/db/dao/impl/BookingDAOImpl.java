package com.lagunabreezelodge.db.dao.impl;

import com.lagunabreezelodge.db.DBConnection;
import com.lagunabreezelodge.db.dao.BookingDAO;
import com.lagunabreezelodge.model.Booking;
import java.time.Instant;
import java.time.ZoneId;

import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class BookingDAOImpl implements BookingDAO {

    private final Connection conn;

    public BookingDAOImpl() throws Exception {
        this.conn = DBConnection.getConnection();
    }

    @Override
    public boolean addBooking(Booking booking) {
        String sql = "INSERT INTO bookings(userId, roomId, checkInDate, checkOutDate, numberOfGuests, totalPrice) VALUES (?, ?, ?, ?, ?, ?)";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, booking.getUserId());
            pstmt.setInt(2, booking.getRoomId());
            pstmt.setDate(3, Date.valueOf(booking.getCheckInDate()));
            pstmt.setDate(4, Date.valueOf(booking.getCheckOutDate()));
            pstmt.setInt(5, booking.getNumberOfGuests());
            pstmt.setDouble(6, booking.getTotalPrice());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error inserting booking: " + e.getMessage());
        }
        return false;
    }

    @Override
    public Booking getBookingById(int bookingId) {
        String sql = "SELECT * FROM bookings WHERE bookingId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookingId);
            ResultSet rs = pstmt.executeQuery();
            if (rs.next()) {
                return extractBookingFromResultSet(rs);
            }
        } catch (SQLException e) {
            System.err.println("Error fetching booking by ID: " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Booking> getAllBookings() {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bookings.add(extractBookingFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching all bookings: " + e.getMessage());
        }
        return bookings;
    }

    @Override
    public boolean updateBooking(Booking booking) {
        String sql = "UPDATE bookings SET roomId=?, checkInDate=?, checkOutDate=?, numberOfGuests=?, totalPrice=? WHERE id=?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, booking.getRoomId());
            pstmt.setDate(2, Date.valueOf(booking.getCheckInDate()));
            pstmt.setDate(3, Date.valueOf(booking.getCheckOutDate()));
            pstmt.setInt(4, booking.getNumberOfGuests());
            pstmt.setDouble(5, booking.getTotalPrice());
            pstmt.setInt(6, booking.getBookingId());

            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error updating booking: " + e.getMessage());
        }
        return false;
    }

    @Override
    public boolean cancelBooking(int bookingId) {
        String sql = "DELETE FROM bookings WHERE bookingId = ?";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, bookingId);
            return pstmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.err.println("Error canceling booking: " + e.getMessage());
        }
        return false;
    }

    @Override
    public List<Booking> getBookingsByUserId(int userId) {
        List<Booking> bookings = new ArrayList<>();
        String sql = "SELECT * FROM bookings WHERE userId = ? ORDER BY checkInDate DESC";
        try (PreparedStatement pstmt = conn.prepareStatement(sql)) {
            pstmt.setInt(1, userId);
            ResultSet rs = pstmt.executeQuery();
            while (rs.next()) {
                bookings.add(extractBookingFromResultSet(rs));
            }
        } catch (SQLException e) {
            System.err.println("Error fetching bookings by user ID: " + e.getMessage());
        }
        return bookings;
    }



    private Booking extractBookingFromResultSet(ResultSet rs) throws SQLException {
        long checkInMillis = rs.getLong("checkInDate");
        long checkOutMillis = rs.getLong("checkOutDate");

        LocalDate checkIn = Instant.ofEpochMilli(checkInMillis).atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate checkOut = Instant.ofEpochMilli(checkOutMillis).atZone(ZoneId.systemDefault()).toLocalDate();

        return new Booking(
                rs.getInt("bookingId"),
                rs.getInt("userId"),
                rs.getInt("roomId"),
                checkIn,
                checkOut,
                rs.getInt("numberOfGuests"),
                rs.getDouble("totalPrice")
        );
    }
}