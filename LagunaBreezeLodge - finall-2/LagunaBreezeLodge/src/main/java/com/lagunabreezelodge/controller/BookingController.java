package com.lagunabreezelodge.controller;

import com.lagunabreezelodge.db.dao.BookingDAO;
import com.lagunabreezelodge.db.dao.impl.BookingDAOImpl;
import com.lagunabreezelodge.model.Booking;
import com.lagunabreezelodge.model.Room;
import com.lagunabreezelodge.service.AuthService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Optional;

public class BookingController {
    private int roomId;
    private Room room;
    private final BookingDAO bookingDAO;

    public BookingController(Room room) {
        this.room = room;
        try {
            this.bookingDAO = new BookingDAOImpl();
        } catch (Exception e) {
            throw new RuntimeException("Failed to initialize BookingDAO", e);
        }
    }

    public Scene getBookingScene() {
        VBox rootLayout = new VBox(40);
        rootLayout.setPadding(new Insets(50));
        rootLayout.setAlignment(Pos.CENTER);
        rootLayout.setStyle("-fx-background-color: #e6c8a0;");

        Text titleLabel = new Text("✧˖°BOOKING FORM✧˖°");
        titleLabel.setFont(Font.font("Playfair Display", FontWeight.BOLD, 35));
        titleLabel.setFill(Color.web("#26524b"));

        GridPane grid = createGridPane();
        TextField nameField = createStyledTextField();
        TextField roomField = createStyledTextField();
        roomField.setText(String.valueOf(room.getId()));
        roomField.setEditable(false);

        Spinner<Integer> guestsSpinner = new Spinner<>(1, 10, 1);
        DatePicker checkInDatePicker = new DatePicker(LocalDate.now());
        DatePicker checkOutDatePicker = new DatePicker(LocalDate.now().plusDays(1));

        addToGrid(grid, "Name:", nameField, 1);
        addToGrid(grid, "Room ID:", roomField, 2);
        addToGrid(grid, "Guests:", guestsSpinner, 3);
        addToGrid(grid, "Check-in:", checkInDatePicker, 4);
        addToGrid(grid, "Check-out:", checkOutDatePicker, 5);

        VBox whiteBox = new VBox(15, grid);
        whiteBox.setPadding(new Insets(20));
        whiteBox.setAlignment(Pos.CENTER);
        whiteBox.setStyle("-fx-background-color: rgba(255,255,255,0.3); -fx-background-radius: 10;");
        whiteBox.setPrefWidth(260);

        Button confirmBookingButton = new Button("Book Now");
        confirmBookingButton.setMaxWidth(260);
        confirmBookingButton.setStyle(
                "-fx-background-color: #4d663b; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 18px; " +
                        "-fx-font-family: 'Playfair Display'; " +
                        "-fx-padding: 10 20 10 20; " +
                        "-fx-background-radius: 5; " +
                        "-fx-cursor: hand;"
        );
        confirmBookingButton.setOnMouseEntered(e -> {
            confirmBookingButton.setStyle(
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

        confirmBookingButton.setOnMouseExited(e -> {
            confirmBookingButton.setStyle(
                    "-fx-background-color: #4d663b; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 18px; " +
                            "-fx-font-family: 'Playfair Display'; " +
                            "-fx-padding: 10 20 10 20; " +
                            "-fx-background-radius: 5; " +
                            "-fx-cursor: hand;"
            );
        });

        confirmBookingButton.setOnAction(e -> handleBooking(nameField, roomField, guestsSpinner, checkInDatePicker, checkOutDatePicker));

        VBox outer = new VBox(15, titleLabel, whiteBox, confirmBookingButton);
        outer.setAlignment(Pos.CENTER);
        outer.setPadding(new Insets(30, 26, 30, 26));
        outer.setStyle("-fx-background-color: #e6c8a0;");

        return new Scene(outer, 600, 600);
    }




    private void handleBooking(TextField nameField, TextField roomField, Spinner<Integer> guestsSpinner, DatePicker checkInPicker, DatePicker checkOutPicker) {
        if (!validateFields(nameField, roomField)) {
            showPopup("Booking Error", "Please fill in all required fields.");
            return;
        }

        try {
            roomId = Integer.parseInt(roomField.getText());
            int guests = guestsSpinner.getValue();
            LocalDate checkIn = checkInPicker.getValue();
            LocalDate checkOut = checkOutPicker.getValue();

            if (checkIn.isAfter(checkOut)) {
                showPopup("Date Error", "Check-out date must be after check-in.");
                return;
            }

            double totalPrice = calculateTotalPrice(guests, checkIn, checkOut);
            int userId = AuthService.getLoggedInUser().getId();
            Booking booking = new Booking(userId, roomId, checkIn, checkOut, guests, totalPrice);

            openCancelBookingWindow(booking);



        } catch (NumberFormatException e) {
            showPopup("Input Error", "Room ID must be a number.");
        }
    }
    private void openCancelBookingWindow(Booking booking) {
        Stage cancelStage = new Stage();
        VBox layout = new VBox(15);
        layout.setPadding(new Insets(20));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #E4C590;");

        Label label = new Label("Are you sure you want to confirm your booking?");
        label.setStyle("-fx-font-family: 'Playfair Display'; -fx-font-size: 16px; -fx-font-weight: 650; -fx-text-fill: #6e3f0d;");
        label.setWrapText(true);
        label.setMaxWidth(400);

        Button confirmButton = new Button("Confirm Booking");
        Button cancelButton = new Button("Cancel");

        confirmButton.setOnAction(e -> {
            boolean success = bookingDAO.addBooking(booking);
            if (success) {
                showPopup("Booking Confirmed", "Your booking has been successfully created!");
                CancelTimerController.scheduleCancellationNotification(booking.getRoomId());
                cancelStage.close();
                RoomListController.show();
            } else {
                showPopup("Database Error", "Booking could not be saved.");
            }
        });
        confirmButton.setStyle(
                "-fx-background-color: #4d663b; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 18px; " +
                        "-fx-font-family: 'Playfair Display'; " +
                        "-fx-padding: 10 20 10 20; " +
                        "-fx-background-radius: 5; " +
                        "-fx-cursor: hand;"
        );

        confirmButton.setOnMouseEntered(e -> {
            confirmButton.setStyle(
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

        confirmButton.setOnMouseExited(e -> {
            confirmButton.setStyle(
                    "-fx-background-color: #4d663b; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 18px; " +
                            "-fx-font-family: 'Playfair Display'; " +
                            "-fx-padding: 10 20 10 20; " +
                            "-fx-background-radius: 5; " +
                            "-fx-cursor: hand;"
            );
        });

        cancelButton.setOnAction(e -> {
            cancelStage.close();
            RoomListController.show();
        });
        cancelButton.setStyle(
                "-fx-background-color: #d9534f; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 18px; " +
                        "-fx-font-family: 'Playfair Display'; " +
                        "-fx-padding: 10 20 10 20; " +
                        "-fx-background-radius: 5; " +
                        "-fx-cursor: hand;"
        );

        cancelButton.setOnMouseEntered(e -> {
            cancelButton.setStyle(
                    "-fx-background-color: #c9302c; " +
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
cancelButton.setOnMouseExited(e -> {
            cancelButton.setStyle(
                    "-fx-background-color:#d9534f ; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 18px; " +
                            "-fx-font-family: 'Playfair Display'; " +
                            "-fx-padding: 10 20 10 20; " +
                            "-fx-background-radius: 5; " +
                            "-fx-cursor: hand;"
            );
        });

        HBox buttonBox = new HBox(20, cancelButton, confirmButton);
        buttonBox.setAlignment(Pos.CENTER);

        layout.getChildren().addAll(label, buttonBox);
        Scene scene = new Scene(layout, 400, 200);
        cancelStage.getIcons().add(new Image(BookingController.class.getResourceAsStream("/images/logo.jpg")));
        cancelStage.setTitle(" Confirm Booking");
        cancelStage.setScene(scene);
        cancelStage.setResizable(false);
        cancelStage.show();
    }


    private void showPopup(String title, String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(message);

        DialogPane dialogPane = alert.getDialogPane();
        dialogPane.setStyle("-fx-background-color: #E4C590;  -fx-border-color: #6e3f0d;");
        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
        alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.jpg")));
        alert.showAndWait();
    }

    private double calculateTotalPrice(int guests, LocalDate checkIn, LocalDate checkOut) {
        return guests * 100 * (checkOut.toEpochDay() - checkIn.toEpochDay());
    }

    private boolean validateFields(TextField nameField, TextField roomField) {
        return !nameField.getText().isEmpty() && !roomField.getText().isEmpty();
    }

    private GridPane createGridPane() {
        GridPane grid = new GridPane();
        grid.setPadding(new Insets(40));
        grid.setVgap(20);
        grid.setHgap(15);
        grid.setStyle("-fx-background-color: #E4C590;");
        grid.setAlignment(Pos.CENTER_LEFT);
        return grid;
    }

    private void addToGrid(GridPane grid, String labelText, Control inputControl, int rowIndex) {
        Label label = createStyledLabel(labelText);
        grid.addRow(rowIndex, label, inputControl);
    }

    private Label createStyledLabel(String text) {
        Label label = new Label(text);
        label.setFont(Font.font("Playfair Display", FontWeight.BOLD, 22));
        label.setTextFill(Color.web("#6e3f0d"));
        return label;
    }

    private TextField createStyledTextField() {
        TextField textField = new TextField();
        textField.setPrefWidth(250);

        textField.setStyle("-fx-background-color: white; -fx-border-color: #6e3f0d; -fx-border-radius: 8; -fx-font-size: 18px; -fx-padding: 10;");

        textField.focusedProperty().addListener((obs, oldVal, newVal) -> {
            if (newVal) {
                textField.setStyle("-fx-background-color: white; -fx-border-color: #1E90FF; -fx-border-width: 2; -fx-border-radius: 8; -fx-font-size: 18px; -fx-padding: 10;");
            } else {
                textField.setStyle("-fx-background-color: white; -fx-border-color: #6e3f0d; -fx-border-radius: 8; -fx-font-size: 18px; -fx-padding: 10;");
            }
        });

        return textField;
    }

    public static void show(Stage stage, Room room) {
        if (stage == null || room == null) {
            System.err.println("Error: Stage or Room is null!");
            return;
        }

        BookingController bookingController = new BookingController(room);
        Scene bookingScene = bookingController.getBookingScene();
        stage.getIcons().add(new Image(BookingController.class.getResourceAsStream("/images/logo.jpg")));
        stage.setScene(bookingScene);
        stage.setTitle("Booking for " + room.getName());
        stage.setResizable(false);
        stage.centerOnScreen();
        stage.show();
    }
}
