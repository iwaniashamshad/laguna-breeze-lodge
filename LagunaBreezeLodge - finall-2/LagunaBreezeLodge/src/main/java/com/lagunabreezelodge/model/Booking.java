package com.lagunabreezelodge.model;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

public class Booking {

    private int bookingId;
    private int roomId;
    private int userId;
    private LocalDate checkInDate;
    private LocalDate checkOutDate;
    private int numberOfGuests;
    private double totalPrice;
    private boolean isCancelled;

    // Constructors
    public Booking(int bookingId, int userId, int roomId, LocalDate checkInDate,
                   LocalDate checkOutDate, int numberOfGuests, double totalPrice) {
        this.bookingId = bookingId;
        this.userId = userId;
        this.roomId = roomId;
        this.checkInDate = checkInDate;
        this.checkOutDate = checkOutDate;
        this.numberOfGuests = numberOfGuests;
        this.totalPrice = totalPrice;
        this.isCancelled = false;
    }


    public Booking(int userId, int roomId, LocalDate checkInDate,
                   LocalDate checkOutDate, int numberOfGuests, double totalPrice) {
        this(-1, userId, roomId, checkInDate, checkOutDate, numberOfGuests, totalPrice);
    }


    public Booking() {
        // Empty constructor
    }

    // Getters and setters
    public int getBookingId() {
        return bookingId;
    }

    public void setBookingId(int bookingId) {
        this.bookingId = bookingId;
    }

    public int getRoomId() {
        return roomId;
    }

    public void setRoomId(int roomId) {
        this.roomId = roomId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public LocalDate getCheckInDate() {
        return checkInDate;
    }

    public void setCheckInDate(LocalDate checkInDate) {
        this.checkInDate = checkInDate;
    }

    public LocalDate getCheckOutDate() {
        return checkOutDate;
    }

    public void setCheckOutDate(LocalDate checkOutDate) {
        this.checkOutDate = checkOutDate;
    }

    public int getNumberOfGuests() {
        return numberOfGuests;
    }

    public void setNumberOfGuests(int numberOfGuests) {
        this.numberOfGuests = numberOfGuests;
    }

    public double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public boolean isCancelled() {
        return isCancelled;
    }

    public void setCancelled(boolean cancelled) {
        isCancelled = cancelled;
    }

    public long getNumberOfNights() {
        if (checkInDate != null && checkOutDate != null) {
            return ChronoUnit.DAYS.between(checkInDate, checkOutDate);
        }
        return 0;
    }

    public void calculateTotalPrice(double pricePerNight) {
        long nights = getNumberOfNights();
        this.totalPrice = pricePerNight * nights * numberOfGuests;
    }

    @Override
    public String toString() {
        return "Booking{" +
                "bookingId=" + bookingId +
                ", userId=" + userId +
                ", roomId=" + roomId +
                ", checkInDate=" + checkInDate +
                ", checkOutDate=" + checkOutDate +
                ", numberOfGuests=" + numberOfGuests +
                ", totalPrice=" + totalPrice +
                ", isCancelled=" + isCancelled +
                '}';
    }
}
