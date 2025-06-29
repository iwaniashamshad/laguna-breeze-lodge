package com.lagunabreezelodge.controller;

import com.lagunabreezelodge.model.User;
import com.lagunabreezelodge.service.AuthService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class RegisterController {

    public static void show() {
        Stage stage = new Stage();
        stage.getIcons().add(new Image(RegisterController.class.getResourceAsStream("/images/logo.jpg")));
        stage.setTitle(" Registration - Laguna Breeze Lodge");

        // Heading
        Text registerTitle = new Text("✧˖°Registration✧˖°");
        registerTitle.setFont(Font.font("Playfair Display", FontWeight.BOLD, 35));
        registerTitle.setFill(Color.web("#26524b"));
        HBox titleBox = new HBox(registerTitle);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(10, 0, 10, 0));

        // Name, Email & Password Fields
        Label nameLabel = new Label("Full Name:");
        nameLabel.setFont(Font.font("PlayFair Display", FontWeight.SEMI_BOLD, 12));
        nameLabel.setTextFill(Color.web("#5C4033"));
        TextField nameField = new TextField();
        nameField.setPromptText("Enter your name");
        nameField.setPrefWidth(200);

        Label emailLabel = new Label("Email:");
        emailLabel.setFont(Font.font("PlayFair Display", FontWeight.SEMI_BOLD, 12));
        emailLabel.setTextFill(Color.web("#5C4033"));
        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");
        emailField.setPrefWidth(200);

        Label passwordLabel = new Label("Password:");
        passwordLabel.setFont(Font.font("PlayFair Display", FontWeight.SEMI_BOLD, 12));
        passwordLabel.setTextFill(Color.web("#5C4033"));
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setPrefWidth(200);

        Label confirmPasswordLabel = new Label("Confirm Password:");
        confirmPasswordLabel.setFont(Font.font("PlayFair Display", FontWeight.SEMI_BOLD, 12));
        confirmPasswordLabel.setTextFill(Color.web("#5C4033"));
        PasswordField confirmPasswordField = new PasswordField();
        confirmPasswordField.setPromptText("Confirm your password");
        confirmPasswordField.setPrefWidth(200);

        // Form Grid
        GridPane formGrid = new GridPane();
        formGrid.setAlignment(Pos.CENTER);
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(15, 15, 15, 15));
        formGrid.add(nameLabel, 0, 0);
        formGrid.add(nameField, 1, 0);
        formGrid.add(emailLabel, 0, 1);
        formGrid.add(emailField, 1, 1);
        formGrid.add(passwordLabel, 0, 2);
        formGrid.add(passwordField, 1, 2);
        formGrid.add(confirmPasswordLabel, 0, 3);
        formGrid.add(confirmPasswordField, 1, 3);

        // White box for form
        VBox whiteBox = new VBox(15, formGrid);
        whiteBox.setPadding(new Insets(10));
        whiteBox.setAlignment(Pos.CENTER);
        whiteBox.setStyle("-fx-background-color: rgba(255,255,255,0.3); -fx-background-radius: 10;");
        whiteBox.setPrefWidth(320); // Adjusted width

        // Register Button with hover effects
        Button registerButton = new Button("Register");
        registerButton.setMaxWidth(320);
        registerButton.setStyle(
                "-fx-background-color: #4d663b; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 18px; " +
                        "-fx-font-family: 'Playfair Display'; " +
                        "-fx-padding: 10 20 10 20; " +
                        "-fx-background-radius: 5; " +
                        "-fx-cursor: hand;"
        );

        registerButton.setOnMouseEntered(e -> {
            registerButton.setStyle(
                    "-fx-background-color: #7a8c6d; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 18px; " +
                            "-fx-font-family: 'Playfair Display'; " +
                            "-fx-padding: 10 20 10 20; " +
                            "-fx-background-radius: 5; " +
                            "-fx-cursor: hand; " +
                            "-fx-border-color: #5a6a4f; " +
                            "-fx-border-width: 1px; " +
                            "-fx-border-radius: 5;"
            );
        });

        registerButton.setOnMouseExited(e -> {
            registerButton.setStyle(
                    "-fx-background-color: #4d663b; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 18px; " +
                            "-fx-font-family: 'Playfair Display'; " +
                            "-fx-padding: 10 20 10 20; " +
                            "-fx-background-radius: 5; " +
                            "-fx-cursor: hand;"
            );
        });

        // Back to Login Link
        Hyperlink loginLink = new Hyperlink("Already have an account? Login");
        loginLink.setFont(Font.font("Playfair Display", 13));
        loginLink.setStyle("-fx-text-fill: #5C4033;");
        loginLink.setBorder(Border.EMPTY);

        // Message Label
        Label messageLabel = new Label();
        messageLabel.setFont(Font.font("Playfair Display", 12));

        // Outer layout
        VBox outer = new VBox(15, registerTitle, whiteBox, registerButton, loginLink, messageLabel);
        outer.setAlignment(Pos.CENTER);
        outer.setPadding(new Insets(30, 26, 30, 26));
        outer.setStyle("-fx-background-color: #e6c8a0;");

        // Register action
        registerButton.setOnAction(e -> {
            String name = nameField.getText().trim();
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();
            String confirmPassword = confirmPasswordField.getText().trim();

            if (name.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("Please fill all fields.");
                return;
            }
            if (!isValidEmail(email)) {
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("Invalid email format. Use something like: name@example.com");
                return;
            }


            if (!password.equals(confirmPassword)) {
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("Passwords do not match.");
                return;
            }

            AuthService authService = new AuthService();
            User user = new User();
            user.setName(name);
            user.setEmail(email);
            user.setPassword(password);

            boolean success = authService.register(user);

            if (success) {
                messageLabel.setTextFill(Color.GREEN);
                messageLabel.setText("Registration successful! Please login.");
                // Optional: Clear fields or redirect to login page here
                nameField.clear();
                emailField.clear();
                passwordField.clear();
                confirmPasswordField.clear();
            } else {
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("Registration failed. Email may already exist.");
            }
        });



        loginLink.setOnAction(e -> {
            stage.close();
            LoginController.show();
        });

        Scene scene = new Scene(outer, 420, 500); // Increased window size
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

    private static boolean isValidEmail(String email) {

            String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}$";
            return email.matches(emailRegex);


    }
}