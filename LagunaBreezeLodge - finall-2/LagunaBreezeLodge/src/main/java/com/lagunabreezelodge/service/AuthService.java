package com.lagunabreezelodge.service;

import com.lagunabreezelodge.db.dao.UserDAO;
import com.lagunabreezelodge.db.dao.impl.UserDAOImpl;
import com.lagunabreezelodge.model.User;

public class AuthService {

    private final UserDAO userDAO;

    //  Static user to track current login session
    private static User loggedInUser = null;

    public AuthService() {
        userDAO = new UserDAOImpl();
    }

    //  Login method (returns User and sets session)
    public User login(String email, String password) {
        System.out.println("Trying login for email: " + email);
        User user = userDAO.findByEmail(email);
        if (user != null) {
            System.out.println("User found with email: " + user.getEmail());
            System.out.println("Comparing passwords: " + user.getPassword() + " vs " + password);
            if (user.getPassword().equals(password)) {
                System.out.println("Passwords match. Login success.");
                loggedInUser = user; //  session set here
                return user;
            }
        }
        System.out.println("Login failed.");
        return null;
    }

    //  Register user
    public boolean register(User user) {
        if (userDAO.findByEmail(user.getEmail()) != null) {
            return false;
        }
        return userDAO.insert(user);
    }

    //  Get current logged-in user
    public static User getLoggedInUser() {
        return loggedInUser;
    }

    //  Check if user is logged in
    public static boolean isLoggedIn() {
        return loggedInUser != null;
    }

    //  Logout method
    public static void logout() {
        loggedInUser = null;
    }

    //  Set logged-in user manually (used after login)
    public static void setLoggedInUser(User user) {
        loggedInUser = user;
    }

    // Get logged-in user's ID directly
    public static int getLoggedInUserId() {
        return loggedInUser != null ? loggedInUser.getId() : -1;
    }

}
