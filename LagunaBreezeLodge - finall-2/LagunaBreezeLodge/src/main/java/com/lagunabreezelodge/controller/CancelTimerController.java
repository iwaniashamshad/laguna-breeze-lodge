package com.lagunabreezelodge.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.Timer;
import java.util.TimerTask;
import java.util.HashMap;
import java.util.Map;

public class CancelTimerController {
    private static final Map<Integer, Timer> timers = new HashMap<>();

    public static void scheduleCancellationNotification(int bookingId) {
        long delay = 24 * 60 * 60 * 1000; // Example: Schedule cancellation in 24 hours

        Timer timer = new Timer();
        timers.put(bookingId, timer);

        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                System.out.println("⏳ Reminder: Booking ID " + bookingId + " is scheduled for cancellation.");
                timers.remove(bookingId); // Cleanup after execution
            }
        }, delay);
    }

    public static void cancelScheduledNotification(int bookingId) {
        Timer timer = timers.get(bookingId);
        if (timer != null) {
            timer.cancel();
            timers.remove(bookingId);
            System.out.println("❌ Cancellation notification for Booking ID " + bookingId + " has been removed.");
        } else {
            System.err.println("⚠ No scheduled cancellation found for Booking ID " + bookingId);
        }
    }

    public Scene getCancelScene(LocalDate checkOutDate) {
        Label messageLabel = new Label("Your booking is scheduled for cancellation on: " + checkOutDate);
        messageLabel.setStyle("-fx-font-size: 22px; -fx-font-weight: bold; -fx-text-fill: #26524b;");

        Button cancelButton = new Button("Cancel Booking");
        cancelButton.setStyle("-fx-background-color: #d9534f; -fx-text-fill: white; -fx-font-size: 20px; -fx-padding: 14px;");
        cancelButton.setOnAction(e -> {
            System.out.println("✅ Cancellation for check-out date " + checkOutDate + " has been processed.");
        });

        VBox layout = new VBox(20, messageLabel, cancelButton);
        layout.setPadding(new Insets(30));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #E4C590; -fx-border-radius: 10; -fx-border-color: #6e3f0d;");

        return new Scene(layout, 450, 250);
    }
}
