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

public class LoginController {

    public static void show() {
        Stage stage = new Stage();
        stage.getIcons().add(new Image(LoginController.class.getResourceAsStream("/images/logo.jpg")));
        stage.setTitle(" Login - Laguna Breeze Lodge");

         Text LoginTitle = new Text("✧˖°Login✧˖°");
        LoginTitle.setFont(Font.font("PlayFair Display", FontWeight.BOLD, 35));
        LoginTitle.setFill(Color.web("#26524b"));
        HBox titleBox = new HBox(LoginTitle);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(10, 0, 10, 0));
        // Email and Password Fields
        Label emailLabel = new Label("Email:");
        emailLabel.setFont(Font.font("PlayFair Display", FontWeight.SEMI_BOLD, 12));
        emailLabel.setTextFill(Color.web("#5C4033"));
        TextField emailField = new TextField();
        emailField.setPromptText("Enter your email");
        emailField.setMaxWidth(200);

        Label passwordLabel = new Label("Password:");
        passwordLabel.setFont(Font.font("PlayFair Display", FontWeight.SEMI_BOLD, 12));
        passwordLabel.setTextFill(Color.web("#5C4033"));
        PasswordField passwordField = new PasswordField();
        passwordField.setPromptText("Enter your password");
        passwordField.setMaxWidth(200);

        // Form Grid
        GridPane formGrid = new GridPane();
        formGrid.setAlignment(Pos.CENTER);
        formGrid.setHgap(10);
        formGrid.setVgap(10);
        formGrid.setPadding(new Insets(10));
        formGrid.add(emailLabel, 0, 0);
        formGrid.add(emailField, 1, 0);
        formGrid.add(passwordLabel, 0, 1);
        formGrid.add(passwordField, 1, 1);

        // Tighten up grid column behavior
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setMinWidth(Region.USE_PREF_SIZE);
        col1.setHgrow(Priority.NEVER);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHgrow(Priority.NEVER);

        formGrid.getColumnConstraints().addAll(col1, col2);

        // White box for form only
        VBox whiteBox = new VBox(15, formGrid);
        whiteBox.setPadding(new Insets(20));
        whiteBox.setAlignment(Pos.CENTER);
        whiteBox.setStyle("-fx-background-color: rgba(255,255,255,0.3); -fx-background-radius: 10;");
        whiteBox.setPrefWidth(260);

        // Login Button placed outside
        Button loginButton = new Button("Login");
        loginButton.setMaxWidth(260);
        loginButton.setStyle(
                "-fx-background-color: #4d663b; " +  // Sage green (matches header)
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 18px; " +
                        "-fx-font-family: 'Playfair Display'; " +
                        "-fx-padding: 10 20 10 20; " +
                        "-fx-background-radius: 5; " +
                        "-fx-cursor: hand; " +
                        "-fx-border-color: transparent; " +  // No border by default
                        "-fx-border-width: 1px; " +
                        "-fx-border-radius: 5;"
        );
        // Hover: Darker sage background + border
        loginButton.setOnMouseEntered(e -> {
            loginButton.setStyle(
                    "-fx-background-color: #7a8c6d; " +  // Darker sage
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 18px; " +
                            "-fx-font-family: 'Playfair Display'; " +
                            "-fx-padding: 10 20 10 20; " +
                            "-fx-background-radius: 5; " +
                            "-fx-cursor: hand; " +
                            "-fx-border-color: #5a6a4f; " +  // Darkest sage border
                            "-fx-border-width: 1px; " +
                            "-fx-border-radius: 5;"
            );
        });

// Revert to normal state
        loginButton.setOnMouseExited(e -> {
            loginButton.setStyle(
                    "-fx-background-color: #4d663b; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 18px; " +
                            "-fx-font-family: 'Playfair Display'; " +
                            "-fx-padding: 10 20 10 20; " +
                            "-fx-background-radius: 5; " +
                            "-fx-cursor: hand; " +
                            "-fx-border-color: transparent; " +
                            "-fx-border-width: 1px; " +
                            "-fx-border-radius: 5;"
            );
        });

// Optional: Pressed state (even darker)
        loginButton.setOnMousePressed(e -> {
            loginButton.setStyle(
                    "-fx-background-color: #6a7b5f; " +  // Darkest sage
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


        // Register link
        Hyperlink registerLink = new Hyperlink("Don't have an account? Register");
        registerLink.setFont(Font.font("Playfair Display", 13));
        registerLink.setStyle("-fx-text-fill: #5C4033;");
        registerLink.setBorder(Border.EMPTY);

        // Message Label
        Label messageLabel = new Label();
        messageLabel.setFont(Font.font("Playfair Display", 12));

        // Outer layout
        VBox outer = new VBox(15, LoginTitle, whiteBox, loginButton, registerLink, messageLabel);
        outer.setAlignment(Pos.CENTER);
        outer.setPadding(new Insets(30, 26, 30, 26));
        outer.setStyle("-fx-background-color: #e6c8a0;");

        // Login action
        loginButton.setOnAction(e -> {
            String email = emailField.getText().trim();
            String password = passwordField.getText().trim();

            if (email.isEmpty() || password.isEmpty()) {
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("Please enter email and password.");
                return;
            }

            AuthService authService = new AuthService();
            User user = authService.login(email, password);

            if (user != null) {
                messageLabel.setTextFill(Color.GREEN);
                messageLabel.setText("Login successful! Welcome " + user.getName());
                stage.close();
                HomeController.show(new Stage());
            } else {
                messageLabel.setTextFill(Color.RED);
                messageLabel.setText("Invalid email or password.");
            }
        });

        // Register action
        registerLink.setOnAction(e -> {
            stage.close();
            RegisterController.show();
        });

        // Scene
        Scene scene = new Scene(outer, 320, 450);
        stage.setScene(scene);
        stage.setResizable(false);
        stage.show();
    }

}