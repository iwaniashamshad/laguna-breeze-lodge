package com.lagunabreezelodge.model;

import java.math.BigDecimal;

public class Room {
    private final int id;
    private final String name;
    private final String category;
    private final BigDecimal pricePerNight;
    private final String description;
    private final String imagePath;
    private final int maxPeople;

    // Constructor
    public Room(int id, String name, String category, BigDecimal pricePerNight, String description, String imagePath, int maxPeople) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.pricePerNight = pricePerNight;
        this.description = description;
        this.imagePath = "/images/" + imagePath; // Ensure correct path format
        this.maxPeople = maxPeople;
    }

    // Getters (Encapsulation)
    public String getName() { return name; }
    public BigDecimal getPricePerNight() { return pricePerNight; }
    public String getDescription() { return description; }
    public String getImagePath() { return imagePath; }
    public int getMaxPeople() { return maxPeople; }

    // Polymorphic method for room details
    public String getRoomDetails() {
        return String.format("%s (%s) - Max %d people - $%.2f per night", name, category, maxPeople, pricePerNight);
    }

    @Override
    public String toString() {
        return getRoomDetails();
    }

    public int getId() {
        return id;
    }
}