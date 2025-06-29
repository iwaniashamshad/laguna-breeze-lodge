package com.lagunabreezelodge.db.dao;

import com.lagunabreezelodge.model.Booking;
import java.util.List;

public interface BookingDAO {

    boolean addBooking(Booking booking);
    Booking getBookingById(int bookingId);
    List<Booking> getAllBookings();
    boolean updateBooking(Booking booking);
    boolean cancelBooking(int bookingId);

    //  METHOD to fetch bookings by user ID
    List<Booking> getBookingsByUserId(int userId);
}
