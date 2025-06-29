package com.lagunabreezelodge.controller;

//imports
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.text.Text;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.text.FontWeight;
import javafx.scene.layout.StackPane;
import javafx.scene.text.TextAlignment;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import com.lagunabreezelodge.service.AuthService;


import java.util.ArrayList;
import java.util.List;

import static javafx.application.Application.launch;


public class HomeController {
    private VBox hamburgerMenu;
    private boolean isMenuOpen = false;
    private StackPane rootPane; // Used to hold header + content + dropdown


    private BorderPane root;

    public HomeController() {

        root = new BorderPane();
        root.setTop(createHeader());
        root.setCenter(createContent());
        root.setBottom(createFooter());

        root.setStyle("-fx-background-color: #b5ead7;");
// StackPane initialize + add BorderPane inside
        rootPane = new StackPane();
        rootPane.getChildren().add(root);
    }


        public static void show(Stage stage) {

                HomeController controller = new HomeController();
                Scene scene = new Scene(controller.getView(), 900, 700);
            stage.getIcons().add(new Image(HomeController.class.getResourceAsStream("/images/logo.jpg")));
            stage.setTitle(" Laguna Breeze Lodge");
            stage.setMaximized(true);

            stage.setScene(scene);
                stage.show();
            }


    public Parent getView() {
        return rootPane; // StackPane return
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


        // Final layout: search on left, title centered, hamburger on right

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
                                   BookingHistoryController.show(new Stage());
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




    private Node createContent() {
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setFitToWidth(true);
        //here
        scrollPane.setStyle("-fx-background-color: #b5ead7;");

        VBox content = new VBox(30);
        content.setPadding(new Insets(30, 40, 30, 40));
        content.setAlignment(Pos.TOP_CENTER);
        //here
        content.setStyle("-fx-background-color: #b5ead7;");  // Soft light blueish



        // Main titles


        Text welcomeText = new Text("\n\nWelcome to");
        welcomeText.setFont(Font.font("PlayFair Display", FontWeight.BOLD, 40));
        welcomeText.setFill(Color.web("#fff"));

        Text lodgeText = new Text("Laguna Breeze Lodge");
        lodgeText.setFont(Font.font("PlayFair Display", FontWeight.BOLD, 49));
        lodgeText.setFill(Color.web("#fff"));

// Use TextFlow to combine the two Text nodes inline

        VBox mainTitle = new VBox(welcomeText, lodgeText);
        mainTitle.setAlignment(Pos.CENTER);  // center horizontally and vertically in VBox
        mainTitle.setSpacing(0);  // spacing between texts if needed

// Then your subtitle and description as before
        Text subTitle = new Text("\n˖⁺‧₊˚✦ Stay Where Paradise Begins ˖⁺‧₊˚✦");
        subTitle.setFont(Font.font("Morning Waves", 30));
        subTitle.setFill(Color.web("#c5effc"));


        Text description = new Text(
                "\nYour tropical escape made easy.\n" +
                        "Whether it’s a family adventure or a solo retreat,\n" +
                        "book your perfect stay in just a few taps with our online room booking app.\n" +
                        "Breeze in. Stay easy.✧˖°"
        );
        description.setFont(Font.font("Playfair Display SemiBold", 23));
        description.setFill(Color.web("#fff"));
        description.setTextAlignment(TextAlignment.CENTER);
        description.setLineSpacing(4);

        //VBox introBox = new VBox(10, mainTitle, subTitle, description);
       // introBox.setAlignment(Pos.CENTER);

        // Video background
        String videoPath = getClass().getResource("/videos/bgvideo.mp4").toExternalForm();
        Media media = new Media(videoPath);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        mediaPlayer.setAutoPlay(true);
        mediaPlayer.setCycleCount(MediaPlayer.INDEFINITE);
        MediaView mediaView = new MediaView(mediaPlayer);
        mediaView.setPreserveRatio(true);

// Overlay intro text
        VBox introOverlay = new VBox(10, mainTitle, subTitle, description);
        introOverlay.setAlignment(Pos.CENTER);
        introOverlay.setPadding(new Insets(60, 20, 60, 20));


// StackPane for video + text
        StackPane videoIntroStack = new StackPane(mediaView, introOverlay);
        // Create the logo ImageView
        ImageView logoView = new ImageView(new Image(getClass().getResourceAsStream("/images/logo.jpg")));
        logoView.setFitWidth(145);  // adjust size as you want
        logoView.setPreserveRatio(true);
        logoView.setSmooth(true);

// Container to position logo on left side
        HBox logoContainer = new HBox(logoView);
        logoContainer.setAlignment(Pos.CENTER_LEFT); // vertical center, aligned left
        logoContainer.setPadding(new Insets(0, 0, 0, 20));
        logoContainer.setTranslateY(-150); // Moves logo up by 30 pixels
// left padding to move a bit from edge

// Add logoContainer on top of videoIntroStack contents
        videoIntroStack.getChildren().add(logoContainer);

// Align logoContainer to left inside StackPane explicitly
        StackPane.setAlignment(logoContainer, Pos.CENTER_LEFT);

        videoIntroStack.setMinHeight(400);
        videoIntroStack.setPrefHeight(500);
        videoIntroStack.setStyle("-fx-background-color: #e6c8a0");
        content.setStyle("-fx-background-color: #e6c8a0");
        scrollPane.setStyle("-fx-background: #e6c8a0");
        videoIntroStack.setStyle("-fx-background-color: #e6c8a0");
// Optional: controls vertical space
        videoIntroStack.setAlignment(Pos.CENTER);
        StackPane.setAlignment(mediaView, Pos.CENTER);
        StackPane.setAlignment(introOverlay, Pos.CENTER);

// Bind video width to parent so it stretches with window
        mediaView.fitWidthProperty().bind(videoIntroStack.widthProperty());

// Now add videoIntroStack to your root container


        // Spacer
        Region spacer1 = new Region();
        spacer1.setMinHeight(30);

// Our Utilities section
        VBox utilitiesSection = new VBox(10);
        utilitiesSection.setAlignment(Pos.TOP_LEFT);
        utilitiesSection.setPadding(new Insets(0, 0, 0, 20)); // Left indent

        Text utilitiesTitle = new Text("\n✧˖°Our Utilities✧˖°");
        utilitiesTitle.setFont(Font.font("PlayFair Display", FontWeight.BOLD, 35));
        utilitiesTitle.setFill(Color.web("#26524b"));
        HBox titleBox = new HBox(utilitiesTitle);
        titleBox.setAlignment(Pos.CENTER);
        titleBox.setPadding(new Insets(0, 0, 10, 0));  // Add some bottom padding for spacing



// Spacer
        Region spacer = new Region();
        spacer.setMinHeight(10);

// Headings
        String[] utilitiesHeadings = new String[] {
                "Refreshing Swimming Pool",
                "Private Beach Access",
                "Free High-Speed Wi-Fi",
                "24/7 Front Desk Service",
                "On-site Restaurant & Bar",
                "Air-Conditioned Rooms",
                "Complimentary Breakfast",
                "Airport Shuttle Service",
                "Fitness Center",
                "Laundry Facilities"
        };

// Updated Descriptions (~2–3 lines each)
        String[] utilitiesDescriptions = new String[] {
                "Dive into our crystal-clear pool that refreshes and relaxes you. Whether it's morning laps or sunset dips, enjoy it at your own pace.",
                "Enjoy exclusive access to our pristine private beach just steps away. Feel the soft sand and let the waves melt your stress away.",
                "Stay connected with complimentary ultra-fast Wi-Fi throughout the lodge. Stream, work, or browse effortlessly during your stay.",
                "Our front desk is available around the clock to assist with your needs. We're here to make your stay smooth, any time of day.",
                "Savor delicious meals and drinks at our on-site restaurant and bar. Experience seasonal specialties and cozy evening ambiance.",
                "All rooms come with modern air-conditioning for your comfort. Adjust your climate just how you like it for restful nights.",
                "Start your day right with our complimentary breakfast service. Fresh fruit, hot options, and coffee await every morning.",
                "Take advantage of our convenient airport shuttle service. We’ll get you here and back with no hassle or hidden fees.",
                "Keep fit during your stay with our fully equipped fitness center. Treadmills, weights, and wellness await just downstairs.",
                "We provide laundry facilities to keep your clothes fresh and clean. Perfect for extended stays or quick refreshes alike."
        };

// VBox for all bullet items
        VBox bulletsBox = new VBox(15);
        bulletsBox.setAlignment(Pos.TOP_LEFT);
        bulletsBox.setMaxWidth(600);
        bulletsBox.setPadding(new Insets(0, 0, 0, 20)); // Top, Right, Bottom, Left

        for (int i = 0; i < utilitiesHeadings.length; i++) {
            Text bullet = new Text("\u2022 ");
            bullet.setFont(Font.font("PlayFair Display SemiBold", 23));
            bullet.setFill(Color.web("#26524b"));

            Text heading = new Text(utilitiesHeadings[i]);
            heading.setFont(Font.font("PlayFair Display SemiBold", 25));
            heading.setFill(Color.web("#26524b"));  // ← Set heading color instead


            TextFlow headingFlow = new TextFlow(bullet, heading);
            headingFlow.setTextAlignment(TextAlignment.LEFT);
            headingFlow.setMaxWidth(600);

            Text description1 = new Text(utilitiesDescriptions[i]);
            description1.setFont(Font.font("PlayFair Display", 19));
            description1.setFill(Color.web("#6e3f0d"));
            description1.setWrappingWidth(600);

            VBox itemBox = new VBox(4);
            itemBox.setAlignment(Pos.TOP_LEFT);
            itemBox.getChildren().addAll(headingFlow, description1);

            bulletsBox.getChildren().add(itemBox);
        }

        utilitiesSection.getChildren().addAll(titleBox, spacer, bulletsBox);


        // Spacer
        Region spacer2 = new Region();
        spacer2.setMinHeight(40);

        // Our Signature Suites section
        VBox suitesSection = new VBox(20);
        suitesSection.setAlignment(Pos.TOP_CENTER);

        Text suitesTitle = new Text("✧˖°Our Signature Suites✧˖°");
        suitesTitle.setFont(Font.font("PlayFair Display", FontWeight.BOLD, 35));
        suitesTitle.setFill(Color.web("#26524b"));

// HBox for images
        HBox imageBox = new HBox(30); // spacing between images
       // imageBox.setPadding(new Insets(0, 0, 0, 60)); // shift images to the right
        imageBox.setPrefHeight(200);
        imageBox.setAlignment(Pos.TOP_CENTER);

// Paths to images
        String[] imagePaths = new String[] {
                "/images/suite1.jpg",
                "/images/suite2.jpg",
                "/images/suite3.jpg"
        };

        for (String path : imagePaths) {
            Image img = new Image(getClass().getResourceAsStream(path));

            ImageView imageView = new ImageView(img);
            imageView.setFitWidth(400);     // consistent width
            imageView.setFitHeight(250);    // consistent height
            imageView.setPreserveRatio(false); // force same dimensions

            imageView.setSmooth(true); // better scaling quality
            imageBox.getChildren().add(imageView);
        }

        suitesSection.getChildren().addAll(suitesTitle, imageBox);
//explore more rooms button
        Button exploreRoomsBtn = new Button("Explore more rooms!");
        exploreRoomsBtn.setStyle(
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
        exploreRoomsBtn.setOnMouseEntered(e -> {
            exploreRoomsBtn.setStyle(
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
        exploreRoomsBtn.setOnMouseExited(e -> {
            exploreRoomsBtn.setStyle(
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
        exploreRoomsBtn.setOnMousePressed(e -> {
            exploreRoomsBtn.setStyle(
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

// Wrap in HBox (optional spacing)
        HBox buttonContainer = new HBox(exploreRoomsBtn);
        buttonContainer.setAlignment(Pos.CENTER);
        buttonContainer.setPadding(new Insets(10, 0, 10, 0)); // Reduced spacing

        // Spacer
        Region spacer4 = new Region();
        spacer4.setMinHeight(0);



        exploreRoomsBtn.setOnAction(e -> {
            System.out.println("✅ Button Clicked");
            RoomListController.show();
        });

        // Chatbot info section
        VBox chatbotSection = new VBox(8);
        chatbotSection.setAlignment(Pos.TOP_CENTER);
        chatbotSection.setPadding(new Insets(0, 0, 10, 0)); // 40px bottom padding

        Text chatbotHeading = new Text("\n✧˖°Looking for More information?✧˖°");
        chatbotHeading.setFont(Font.font("Playfair Display", FontWeight.BOLD, 35));
        chatbotHeading.setFill(Color.web("#26524b"));

        Text chatbotText = new Text("\nAsk our chatbot buddy Breeze!\n");
        chatbotText.setFont(Font.font("Playfair Display", 25));
        chatbotText.setFill(Color.web("#34665f"));
      //chatbot button
        Button chatbotBtn = new Button("Tap here to access it");
        chatbotBtn.setStyle(
                "-fx-background-color:  #4d663b; " +  // Original green
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-family: 'Playfair Display'; " +
                        "-fx-padding: 12 25 12 25; " +
                        "-fx-background-radius: 5; " +
                        "-fx-cursor: hand; " +
                        "-fx-border-color: transparent; " +
                        "-fx-border-width: 1px; " +
                        "-fx-border-radius: 5;"
        );

// Hover effect (darker green + border)
        chatbotBtn.setOnMouseEntered(e -> {
            chatbotBtn.setStyle(
                    "-fx-background-color:  #7a8c6d; " +  // Darker green
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 16px; " +
                            "-fx-font-family: 'Playfair Display'; " +
                            "-fx-padding: 12 25 12 25; " +
                            "-fx-background-radius: 5; " +
                            "-fx-cursor: hand; " +
                            "-fx-border-color: #5a6a4f; " +  // Darkest green border
                            "-fx-border-width: 1px; " +
                            "-fx-border-radius: 5;"
            );
        });

// Reset to original green when mouse leaves
        chatbotBtn.setOnMouseExited(e -> {
            chatbotBtn.setStyle(
                    "-fx-background-color: #4d663b; " +  // Original green
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 16px; " +
                            "-fx-font-family: 'Playfair Display'; " +
                            "-fx-padding: 12 25 12 25; " +
                            "-fx-background-radius: 5; " +
                            "-fx-cursor: hand; " +
                            "-fx-border-color: transparent; " +  // No border
                            "-fx-border-width: 1px; " +
                            "-fx-border-radius: 5;"
            );

        });

// Optional: Pressed effect (even darker)
        chatbotBtn.setOnMousePressed(e -> {
            chatbotBtn.setStyle(
                    "-fx-background-color: #6a7b5f; " +  // Darkest green
                            "-fx-text-fill: white; " +
                            "-fx-font-size: 16px; " +
                            "-fx-font-family: 'Playfair Display'; " +
                            "-fx-padding: 12 25 12 25; " +
                            "-fx-background-radius: 5; " +
                            "-fx-cursor: hand; " +
                            "-fx-border-color: #5a6a4f; " +
                            "-fx-border-width: 1px; " +
                            "-fx-border-radius: 5;"
            );
        });
        // Inside your HomeController.java
        //connecting bot with button
        chatbotBtn.setOnAction(e -> {
            ChatbotController chatbotController = new ChatbotController();
            chatbotController.showChatbotWindow();
        });


// Revert to hover state when released




        chatbotSection.getChildren().addAll(chatbotHeading, chatbotText, chatbotBtn);
        // Chatbot round icon bottom-right


//main box
        VBox mainVBox = new VBox(30);
        mainVBox.setFillWidth(true);

        mainVBox.getChildren().addAll(
                videoIntroStack,
                spacer1,
                utilitiesSection,
                spacer2,
                suitesSection,

                buttonContainer,
                spacer4,
                chatbotSection
        );


        scrollPane.setContent(mainVBox); // ✅ not scrollContent
        scrollPane.setFitToWidth(true);
        mainVBox.setFillWidth(true);


        // ✅ Scroll fix here
        Platform.runLater(() -> scrollPane.setVvalue(0));



        // Chatbot icon button bottom right overlay
        Button chatbotIcon = new Button("✧");
        chatbotIcon.setShape(new Circle(20));
        chatbotIcon.setMinSize(70, 70);
        chatbotIcon.setMaxSize(70, 70);
        chatbotIcon.setStyle(
                "-fx-background-color: #336961; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-weight: bold; " +
                        "-fx-font-size: 24px; " +  // Increased font size
                        "-fx-cursor: hand; " +
                        // Hover effect
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 2); " +
                        "}");

// Add hover effects using a separate stylesheet or inline
        chatbotIcon.setOnMouseEntered(e -> {
            chatbotIcon.setStyle(
                    "-fx-background-color: #3a7a72; " +  // Slightly lighter color on hover
                            "-fx-text-fill: white; " +
                            "-fx-font-weight: bold; " +
                            "-fx-font-size: 24px; " +
                            "-fx-cursor: hand; " +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 3);");
        });

        chatbotIcon.setOnMouseExited(e -> {
            chatbotIcon.setStyle(
                    "-fx-background-color: #336961; " +
                            "-fx-text-fill: white; " +
                            "-fx-font-weight: bold; " +
                            "-fx-font-size: 24px; " +
                            "-fx-cursor: hand; " +
                            "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 2);");
        });
        // Inside your HomeController.java
        //connecting bot with icon
        chatbotIcon.setOnAction(e -> {
            ChatbotController chatbotController = new ChatbotController();
            chatbotController.showChatbotWindow();
        });


        // Position chatbot icon bottom right
        StackPane.setAlignment(chatbotIcon, Pos.BOTTOM_RIGHT);
        StackPane.setMargin(chatbotIcon, new Insets(20));

        StackPane container = new StackPane(scrollPane, chatbotIcon);
        container.setPrefHeight(600);

        return container;
    }
//bullet circle color
    private Circle createBulletCircle() {
        Circle circle = new Circle(4, Color.web("#041c24"));
        return circle;
    }

    private void openChatbot() {
        System.out.println("Chatbot button clicked - open chatbot (to be implemented)");
        // TODO: Open chatbot window or functionality
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


}

