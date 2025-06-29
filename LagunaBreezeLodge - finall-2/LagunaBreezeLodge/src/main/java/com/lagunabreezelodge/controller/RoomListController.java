package com.lagunabreezelodge.controller;

import com.lagunabreezelodge.model.Room;
import com.lagunabreezelodge.service.AuthService;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.effect.DropShadow;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import java.math.BigDecimal;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

import static javafx.scene.paint.Color.web;

public class RoomListController {
    private VBox hamburgerMenu;
    private boolean isMenuOpen = false;
    private StackPane rootPane; // Used to hold header + content + dropdown

    private final BorderPane root;

    public RoomListController() {
        root = new BorderPane();
        root.setTop(createHeader());
        root.setCenter(createContent());
        root.setBottom(createFooter());

        root.setStyle("-fx-background-color: #b5ead7;");
        rootPane = new StackPane();
        rootPane.getChildren().add(root);
    }

    public static void show() {
        RoomListController controller = new RoomListController();

        Scene scene = new Scene(controller.getRoot(), 800, 600); // ✅ Use the StackPane rootPane

        Stage stage = new Stage();
        stage.getIcons().add(new Image(RoomListController.class.getResourceAsStream("/images/logo.jpg")));
        stage.setTitle(" Explore Rooms");
        stage.setScene(scene);
        stage.show();
    }




    // Displays the list of rooms grouped by category with scrolling
    private Node createContent() {
        VBox content = new VBox(10);
        content.setAlignment(Pos.CENTER);
        content.setStyle("-fx-padding: 30; -fx-background-color: #f3ddbb;");

        //3 - star room
        Label head1 = new Label("3-Star Rooms – Comfort with a Coastal Touch");
        head1.setFont(Font.font("PlayFair Display", FontWeight.BOLD, 30));
        head1.setStyle("-fx-text-fill: #26524b;");

        content.getChildren().add(head1);
        content.getChildren().add(createCategorySection(getThreeStarRooms(), getThreeStarFeatures()));

        //4 - star room
        Label head2 = new Label("4-Star Rooms – Enhanced Relaxation by the Sea");
        head2.setFont(Font.font("PlayFair Display", FontWeight.BOLD, 30));
        head2.setStyle("-fx-text-fill: #26524b;");

        content.getChildren().add(head2);
        content.getChildren().add(createCategorySection(getFourStarRooms(), getFourStarFeatures()));

        //5 - star room
        Label head3 = new Label("5-Star Rooms – Luxury with a Breathtaking Oceanfront Experience");
        head3.setFont(Font.font("PlayFair Display", FontWeight.BOLD, 30));
        head3.setStyle("-fx-text-fill: #26524b;");

        content.getChildren().add(head3);
        content.getChildren().add(createCategorySection(getFiveStarRooms(), getFiveStarFeatures()));

        // Wrap content inside a ScrollPane
        ScrollPane scrollPane = new ScrollPane(content);
        scrollPane.setFitToWidth(true);
        scrollPane.setPadding(new Insets(10));
        scrollPane.setStyle("-fx-background: transparent;");

        return scrollPane;
    }


    // Returns a list of 3-Star Rooms
    /** Returns a list of 3-Star Rooms with manual IDs **/
    private List<Room> getThreeStarRooms() {
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room(301, "Standard Room", "3-Star", new BigDecimal("150.00"), "Partial beach view, simple décor, free Wi-Fi", "suite6.jpg", 2));
        rooms.add(new Room(302, "Deluxe Room", "3-Star", new BigDecimal("180.00"), "Private balcony, ocean tones, tea/coffee station", "suite5.jpg", 2));
        rooms.add(new Room(303, "Family Room", "3-Star", new BigDecimal("220.00"), "Spacious, garden view, handcrafted toiletries", "suite3.jpg", 4));
        return rooms;
    }

    /**  Returns a list of 4-Star Rooms with manual IDs **/
    private List<Room> getFourStarRooms() {
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room(401, "Superior Room", "4-Star", new BigDecimal("250.00"), "Sea-facing balcony, premium linens, mini-bar", "suite7.jpg", 2));
        rooms.add(new Room(402, "Junior Suite", "4-Star", new BigDecimal("320.00"), "Spa-style bathroom, sun loungers, smart TV", "suite8.jpg", 2));
        rooms.add(new Room(403, "Executive Room", "4-Star", new BigDecimal("350.00"), "Work desk, complimentary Wi-Fi, coastal décor", "suite9.jpg", 2));
        return rooms;
    }

    /** Returns a list of 5-Star Rooms with manual IDs **/
    private List<Room> getFiveStarRooms() {
        List<Room> rooms = new ArrayList<>();
        rooms.add(new Room(501, "Luxury Sea View Room", "5-Star", new BigDecimal("450.00"), "Pretty ocean view, infinity bath, butler service", "suite10.jpg", 2));
        rooms.add(new Room(502, "Presidential Suite", "5-Star", new BigDecimal("700.00"), "Private terrace, mood lighting, concierge service", "suite4.jpg", 2));
        rooms.add(new Room(503, "Penthouse Suite", "5-Star", new BigDecimal("900.00"), "Royal beachfront suite, spa access, premium amenities", "suite11.jpeg", 2));
        return rooms;
    }


    // Returns a list of 3-Star Feature
    private List<String> getThreeStarFeatures() {
        return List.of("Partial beach view or garden view", "Private balcony (in deluxe/family rooms)", "Simple, breezy décor with ocean tones", "Ceiling fan or basic air conditioning", "Free Wi-Fi, flat-screen TV", "Compact workspace", "Tea/coffee station", "Local handcrafted toiletries");
    }

    // Returns a list of 4-Star Features
    private List<String> getFourStarFeatures() {
        return List.of("Full or partial sea-facing balcony", "Coastal-inspired décor (driftwood, shells, nautical accents)", "King/queen bed with premium linens", "Mini-bar with beach refreshments", "Air conditioning with personal climate control", "Spa-style bathroom (rain shower, larger vanity)", "Work desk, complimentary Wi-Fi", "Smart TV with beach lounge music channel", "Sun loungers on balcony (in suites)", "Room service (12–16 hrs)");
    }

    // Returns a list of 5-Star Features
    private List<String> getFiveStarFeatures() {
        return List.of("Full panoramic beach or ocean view", "Private balcony or terrace with lounge beds", "In-room Jacuzzi or infinity bath", "Beach butler service", "Beach-themed luxury interiors", "Complimentary Nespresso machine, mini wine fridge", "Designer bathroom amenities", "Free unlimited gym access + spa discount", "Personal concierge or butler", "Mood lighting, smart curtains, voice-controlled room features", "Daily tropical fruit platter and premium welcome drink", "24/7 room service");
    }


    // Creates a section for each room category
    private VBox createCategorySection(List<Room> rooms, List<String> features) {
        VBox categoryBox = new VBox(15);
        categoryBox.setMaxWidth(1300);
        categoryBox.setAlignment(Pos.CENTER_LEFT);
        categoryBox.setStyle("-fx-background-color: #e6c8a0; -fx-border-color: #4d2919; -fx-border-radius: 10; -fx-padding: 15;");

        // Add Room Boxes with Headings Before Each Box
        for (Room room : rooms) {
            Label roomCategoryLabel = new Label(room.getName());
            roomCategoryLabel.setFont(Font.font("PlayFair Display SemiBold",20));
            roomCategoryLabel.setStyle("-fx-text-fill: #26524b;");
            categoryBox.getChildren().add(roomCategoryLabel);
            VBox roomBoxContainer = new VBox(createRoomBox(room)); // Wrap roomBox inside VBox
            roomBoxContainer.setAlignment(Pos.CENTER);

            categoryBox.getChildren().add(roomBoxContainer);

        }

        // Add Features List *AFTER* Images
        VBox featuresBox = new VBox(5);
        Label featureTitle = new Label("Room Features:");
        featureTitle.setFont(Font.font("PlayFair Display Bold",20));
        featureTitle.setStyle("-fx-text-fill: #6e3f0d;");
        featuresBox.getChildren().add(featureTitle);

        for (String feature : features) {
            Label featureLabel = new Label("• " + feature);
            featureLabel.setFont(Font.font("PlayFair Display",18));
            featureLabel.setStyle("-fx-text-fill: #6e3f0d;");
            featuresBox.getChildren().add(featureLabel);
        }
        categoryBox.getChildren().add(featuresBox);

        return categoryBox;
    }


    // Creates a rectangular box for each room with light shadow effect
    private HBox createRoomBox(Room room) {
        HBox roomBox = new HBox(20);
        roomBox.setMaxWidth(1200);
        roomBox.setMaxHeight(400);
        roomBox.setPadding(new Insets(15));
        roomBox.setAlignment(Pos.CENTER);
        roomBox.setStyle("-fx-background-color:#e6c8a0; -fx-border-color: ##4d2919; -fx-padding: 10;");

        // Add Light Shadow Effect
        DropShadow shadow = new DropShadow();
        shadow.setColor(Color.LIGHTGRAY);
        shadow.setRadius(8);
        shadow.setSpread(0.15);
        shadow.setOffsetX(4);
        shadow.setOffsetY(4);
        roomBox.setEffect(shadow);

        // Room Image
        URL imageUrl = getClass().getResource(room.getImagePath());
        ImageView roomImage = new ImageView();
        if (imageUrl != null) {
            roomImage.setImage(new Image(imageUrl.toExternalForm()));
        } else {
            System.err.println("Image not found: " + room.getImagePath());
        }
        roomImage.setFitWidth(400);
        roomImage.setFitHeight(250);

        // Room Details
        VBox roomDetails = new VBox(10);

        //  Display Room ID
        Label idLabel = new Label("Room ID: " + room.getId());
        idLabel.setFont(Font.font("PlayFair Display",18));
        idLabel.setStyle("-fx-text-fill: #6e3f0d;");

        Label detailsLabel = new Label(room.getRoomDetails());
        detailsLabel.setFont(Font.font("PlayFair Display",18));
        detailsLabel.setStyle("-fx-text-fill: #6e3f0d;");
        Label descriptionLabel = new Label(room.getDescription());
        descriptionLabel.setFont(Font.font("PlayFair Display",18));
        descriptionLabel.setStyle("-fx-text-fill: #6e3f0d;");
        roomDetails.getChildren().addAll(idLabel, detailsLabel, descriptionLabel);


        // Book Button
        VBox priceBox = new VBox(10);
        Button bookNowButton = new Button("Proceed To Booking");
        bookNowButton.setStyle(
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

        bookNowButton.setOnMouseEntered(e -> {
            bookNowButton.setStyle(
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
        bookNowButton.setOnMouseExited(e -> {
            bookNowButton.setStyle(
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

        bookNowButton.setOnMousePressed(e -> {
            bookNowButton.setStyle(
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

        bookNowButton.setOnAction(e -> {
                    if (AuthService.isLoggedIn()) {
                        //  User is logged in — open booking page
                        BookingController.show((Stage) bookNowButton.getScene().getWindow(), room);
                    } else {
                        //  User not logged in — redirect to login page
                        LoginController.show();

                        //  Optional: Show a message too (optional)
                        Alert alert = new Alert(Alert.AlertType.INFORMATION);
                        alert.setTitle("Login Required");
                        alert.setHeaderText(null);
                        alert.setContentText("Please log in to book a room.");
                        DialogPane dialogPane = alert.getDialogPane();
                        dialogPane.setStyle("-fx-font-family: 'Playfair Display'; -fx-background-color: #E4C590; -fx-border-color: #6e3f0d;");
                        Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                        alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.jpg")));
                        alert.showAndWait();
                    }
                });
        priceBox.getChildren().addAll(bookNowButton);
        priceBox.setAlignment(Pos.CENTER_RIGHT);



        // Add sections to roomBox
        roomBox.getChildren().addAll(roomImage, roomDetails, priceBox);
        return roomBox;
    }


    private Node createHeader() {
        HBox header = new HBox();
        header.setPadding(new Insets(10, 20, 10, 20));
        header.setSpacing(10);

        header.setPrefHeight(100);

        // Paths and styles
        String imagePath = getClass().getResource("/images/header.jpg").toExternalForm();
        String solidColorStyle =
                "-fx-background-color: #9caf88; " +  // Sage green hex code
                        "-fx-border-color: transparent transparent white transparent; " +
                        "-fx-border-width: 0 0 1 0;";
        String imageBackgroundStyle =
                "-fx-background-image: url('" + imagePath + "'); " +
                        "-fx-background-repeat: no-repeat; " +
                        "-fx-background-size: cover; " +
                        "-fx-border-color: transparent transparent white transparent; " +
                        "-fx-border-width: 0 0 1 0;";

        // Initially set solid color background
        header.setStyle(solidColorStyle);

        // Create Title
        Text title = new Text("✧˖°Laguna Breeze Lodge - Virtual Hotel Room Booking✧˖°");
        title.setFont(Font.font("PlayFair Display", FontWeight.BOLD, 24));
        title.setFill(Color.web("#fff"));

        // Search Button
        Button searchBtn = new Button("\uD83D\uDD0D"); // Unicode for magnifying glass
        searchBtn.setFont(Font.font(18));
        searchBtn.setTextFill(Color.WHITE); // Set text color to white
        searchBtn.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        searchBtn.setOnMouseEntered(e -> searchBtn.setStyle(
             "-fx-background-color: rgba(255,255,255,0.2); -fx-cursor: hand; -fx-text-fill: white;"));
        searchBtn.setOnMouseExited(e -> searchBtn.setStyle(
              "-fx-background-color: transparent; -fx-cursor: hand; -fx-text-fill: white;"));
        searchBtn.setOnAction(e -> RoomListController.show());

// Create the hamburger icon
        VBox hamburger = createHamburgerIcon();
        hamburger.setPadding(new Insets(10));
        hamburger.setAlignment(Pos.CENTER);

// Ensure all rectangles inside the hamburger are white
        for (Node node : hamburger.getChildren()) {
            if (node instanceof Rectangle rect) {
                rect.setFill(Color.WHITE); // Make each line white
                rect.setArcWidth(3);       // Optional: rounded edges
                rect.setArcHeight(3);
            }
        }

// Hover effect: subtle white background overlay
        hamburger.setOnMouseEntered(e -> {
            hamburger.setStyle("-fx-background-color: rgba(255, 255, 255, 0.15); -fx-cursor: hand;");
        });
        hamburger.setOnMouseExited(e -> {
            hamburger.setStyle("-fx-background-color: transparent; -fx-cursor: hand;");
        });

// Click action
        hamburger.setOnMouseClicked(e -> toggleHamburgerMenu(hamburger));



        // Create spacers
        Region leftSpacer = new Region();
        Region rightSpacer = new Region();
        HBox.setHgrow(leftSpacer, Priority.ALWAYS);
        HBox.setHgrow(rightSpacer, Priority.ALWAYS);


        header.getChildren().addAll(searchBtn, leftSpacer, title, rightSpacer, hamburger);
        header.setAlignment(Pos.CENTER); // Ensures all elements vertically aligned
        HBox.setMargin(hamburger, new Insets(0, 0, 0, 0));
        HBox.setMargin(searchBtn, new Insets(0, 18, 0, 0));

        // Add hover effect: on mouse enter, set background image, on exit revert to solid color
        header.setOnMouseEntered(e -> header.setStyle(imageBackgroundStyle));
        header.setOnMouseExited(e -> header.setStyle(solidColorStyle));

        return header;

    }

    private VBox createHamburgerIcon() {
        VBox box = new VBox(5);
        box.setCursor(Cursor.HAND);
        box.setAlignment(Pos.CENTER); // Center lines inside VBox
        box.setPadding(new Insets(6, 0, 0, 0)); // Push down a bit more

        for (int i = 0; i < 3; i++) {
            Region line = new Region();
            line.setPrefSize(20, 2);
            line.setStyle("-fx-background-color: #fff;");
            box.getChildren().add(line);
        }

        return box;
    }
    private void toggleHamburgerMenu(Node hamburgerIcon) {
        if (isMenuOpen) {
            rootPane.getChildren().remove(hamburgerMenu);
            isMenuOpen = false;
        } else {
            hamburgerMenu = new VBox(10);
            hamburgerMenu.setPadding(new Insets(12));
            hamburgerMenu.setStyle("""
            -fx-background-color: #c5effc;
            -fx-border-radius: 8;
            -fx-background-radius: 8;
            -fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 8, 0.1, 0, 4);
        """);

            hamburgerMenu.setPrefWidth(230);
            hamburgerMenu.setMaxHeight(Region.USE_PREF_SIZE);
            hamburgerMenu.setMaxWidth(Region.USE_PREF_SIZE);

            List<String> menuItems = new ArrayList<>(List.of("Home", "Explore", "Book Now", "FAQs(Bot Buddy Breeze)"));

            if (AuthService.isLoggedIn()) {
                menuItems.add("Booking History");
                menuItems.add("Logout");
            } else {
                menuItems.add("Login");
                menuItems.add("Register");
            }

            for (String itemText : menuItems) {
                Label item = new Label(itemText);
                item.setStyle("""
                -fx-padding: 8 12 8 12;
                -fx-font-family: 'Playfair Display';
                -fx-font-weight: 600; /* For semi-bold */
                -fx-font-size: 14px;
                -fx-text-fill: #26524b;
                -fx-alignment: center-left;
            """);

                // Hover effect
                item.setOnMouseEntered(e -> item.setStyle(item.getStyle() +
                        "-fx-background-color: #e8f9ff; -fx-cursor: hand;"));
                item.setOnMouseExited(e -> item.setStyle("""
                -fx-padding: 8 12 8 12;
                -fx-font-family: 'Playfair Display';
                -fx-font-weight: 600; /* For semi-bold */
                -fx-font-size: 14px;
                -fx-text-fill: #26524b;
                -fx-alignment: center-left;
            """));

                // Actions on click
                item.setOnMouseClicked(e -> {
                    switch (itemText) {
                        case "Home" -> HomeController.show(new Stage());

                        case "Explore" -> RoomListController.show();

                        case "Book Now" -> {
                            if (AuthService.isLoggedIn()) {
                                RoomListController.show();
                            } else {
                                LoginController.show();
                                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                                alert.setTitle("Login Required");
                                alert.setHeaderText(null);
                                alert.setContentText("Please log in to book a room.");
                                DialogPane dialogPane = alert.getDialogPane();
                                dialogPane.setStyle("-fx-font-family: 'Playfair Display'; -fx-background-color: #E4C590; -fx-border-color: #6e3f0d;");
                                Stage alertStage = (Stage) alert.getDialogPane().getScene().getWindow();
                                alertStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.jpg")));
                                alert.showAndWait();
                            }
                        }

                        case "Booking History" -> {
                                      BookingHistoryController.show(new Stage());  // You can create this controller
                        }

                        case "FAQs(Bot Buddy Breeze)" -> {
                            ChatbotController chatbotController = new ChatbotController();
                            chatbotController.showChatbotWindow();
                        }

                        case "Login" -> LoginController.show();

                        case "Register" -> RegisterController.show();

                        case "Logout" -> {
                            AuthService.logout();
                            toggleHamburgerMenu(hamburgerIcon); // Refresh the menu
                            return; // Exit early so it doesn’t remove hamburger below
                        }
                    }

                    // Close hamburger after action (except logout which reopens)
                    rootPane.getChildren().remove(hamburgerMenu);
                    isMenuOpen = false;
                });

                hamburgerMenu.getChildren().add(item);
            }

            StackPane.setAlignment(hamburgerMenu, Pos.TOP_RIGHT);
            StackPane.setMargin(hamburgerMenu, new Insets(100, 20, 0, 0));
            rootPane.getChildren().add(hamburgerMenu);
            isMenuOpen = true;

            rootPane.setOnMousePressed(e -> {
                if (isMenuOpen &&
                        !hamburgerMenu.getBoundsInParent().contains(e.getX(), e.getY()) &&
                        !hamburgerIcon.getBoundsInParent().contains(e.getX(), e.getY())) {
                    rootPane.getChildren().remove(hamburgerMenu);
                    isMenuOpen = false;
                }
            });
        }
    }

    private void addHeaderHoverEffect(HBox header) {
        String imagePath = getClass().getResource("/images/header.jpg").toExternalForm();
        String backgroundImageStyle = "-fx-background-image: url('" + imagePath + "'); " +
                "-fx-background-repeat: no-repeat; " +
                "-fx-background-size: cover; ";

        header.setOnMouseEntered(e -> {
            header.setStyle(
                    backgroundImageStyle +
                            "-fx-background-color: rgba(255, 255, 255, 0.8); " +  // subtle overlay on hover
                            "-fx-border-color: transparent transparent #bbb transparent; " +
                            "-fx-border-width: 0 0 1 0;"
            );
        });

        header.setOnMouseExited(e -> {
            header.setStyle(
                    backgroundImageStyle +
                            "-fx-background-color: transparent; " +
                            "-fx-border-color: transparent transparent #ddd transparent; " +
                            "-fx-border-width: 0 0 1 0;"
            );
        });
    }


    private Node createFooter() {
        HBox footer = new HBox();
        footer.setPadding(new Insets(15));
        footer.setAlignment(Pos.CENTER);
        footer.setStyle("-fx-background-color: #9caf88; -fx-border-color: #ddd; -fx-border-width: 1 0 0 0;");

        Label footerText = new Label("© 2025 Laguna Breeze Lodge. All rights reserved.");
        footerText.setFont(Font.font("Playfair Display Bold", 15));
        footerText.setTextFill(Color.web("#fff"));

        footer.getChildren().add(footerText);
        return footer;
    }
    public Parent getRoot() {
        return rootPane;
    }
}