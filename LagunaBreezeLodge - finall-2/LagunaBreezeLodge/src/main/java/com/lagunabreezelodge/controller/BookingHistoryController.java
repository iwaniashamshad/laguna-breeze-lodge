package com.lagunabreezelodge.controller;

import com.lagunabreezelodge.db.dao.BookingDAO;
import com.lagunabreezelodge.db.dao.impl.BookingDAOImpl;
import com.lagunabreezelodge.model.Booking;
import com.lagunabreezelodge.service.AuthService;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.time.LocalDate;
import java.util.List;

public class BookingHistoryController {

    private BookingDAO bookingDAO;

    public BookingHistoryController() {
        try {
            bookingDAO = new BookingDAOImpl();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void show(Stage stage) {
        BookingHistoryController controller = new BookingHistoryController();
        Scene scene = controller.getBookingHistoryScene();
        stage.setScene(scene);

        // Set application logo (window icon)
        stage.getIcons().add(new Image(BookingHistoryController.class.getResourceAsStream("/images/logo.jpg")));

        stage.setTitle(" My Booking History");
        stage.show();
    }


    public Scene getBookingHistoryScene() {
        VBox layout = new VBox(20);
        layout.setPadding(new Insets(40));
        layout.setAlignment(Pos.CENTER);
        layout.setStyle("-fx-background-color: #E4C590;");

        Label title = new Label("✧˖°MY BOOKING HISTORY✧˖°");
        title.setFont(Font.font("Playfair Display", FontWeight.BOLD, 28));
        title.setTextFill(Color.web("#26524b"));

        TableView<Booking> table = new TableView<>();
        table.setItems(getUserBookings());
        table.setColumnResizePolicy(TableView.CONSTRAINED_RESIZE_POLICY);
        //table.setStyle("-fx-background-color: #FFFFFF; -fx-table-cell-border-color: #FFFFFF;");


        TableColumn<Booking, Integer> roomCol = new TableColumn<>("Room ID");
        roomCol.setCellValueFactory(new PropertyValueFactory<>("roomId"));
        roomCol.setStyle("-fx-alignment: CENTER;");


        TableColumn<Booking, Integer> guestsCol = new TableColumn<>("Guests");
        guestsCol.setCellValueFactory(new PropertyValueFactory<>("numberOfGuests"));
        guestsCol.setStyle("-fx-alignment: CENTER;");


        TableColumn<Booking, LocalDate> checkInCol = new TableColumn<>("Check-in");
        checkInCol.setCellValueFactory(new PropertyValueFactory<>("checkInDate"));
        checkInCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Booking, LocalDate> checkOutCol = new TableColumn<>("Check-out");
        checkOutCol.setCellValueFactory(new PropertyValueFactory<>("checkOutDate"));
        checkOutCol.setStyle("-fx-alignment: CENTER;");

        TableColumn<Booking, Double> priceCol = new TableColumn<>("Total Price");
        priceCol.setCellValueFactory(new PropertyValueFactory<>("totalPrice"));
        priceCol.setStyle("-fx-alignment: CENTER;");

        table.getColumns().addAll(roomCol, guestsCol, checkInCol, checkOutCol, priceCol);
        layout.getChildren().addAll(title, table);

        return new Scene(layout, 800, 500);
    }

    private ObservableList<Booking> getUserBookings() {
        int userId = AuthService.getLoggedInUserId();
        List<Booking> bookings = bookingDAO.getBookingsByUserId(userId);
        return FXCollections.observableArrayList(bookings);
    }
}