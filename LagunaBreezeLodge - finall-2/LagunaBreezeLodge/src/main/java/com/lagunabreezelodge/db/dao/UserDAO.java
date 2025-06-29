package com.lagunabreezelodge.db.dao;

import com.lagunabreezelodge.model.User;

public interface UserDAO {

    // Find user by email (for login & registration check)
    User findByEmail(String email);

    // Insert new user into DB (registration)
    boolean insert(User user);

    // Get user by ID (for booking history or profile display)
    User getUserById(int id);
}
