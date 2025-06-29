package com.lagunabreezelodge.controller;

import com.lagunabreezelodge.chatbot.FAQBot;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import java.util.List;

public class ChatbotController {
    private int questionIndex = 0;
    private static final int QUESTIONS_PER_BATCH = 3;
    private final FAQBot faqBot = new FAQBot();

    private HBox initialMessage; // Reference to the initial bot message

    public void showChatbotWindow() {
        VBox chatVBox = new VBox(10);
        chatVBox.setPadding(new Insets(20));
        chatVBox.setStyle("-fx-background-color: #e6c8a0;");
        chatVBox.setPrefWidth(450);


// Make sure content is aligned top-left (default for VBox)
        chatVBox.setAlignment(Pos.TOP_LEFT);

//  Limit max height so it doesn't stretch too much
        chatVBox.setMaxHeight(Region.USE_COMPUTED_SIZE);


        initialMessage = createBotMessage("Hello! I'm Breeze✧˖°\nWhat can I help you with today? :)");
        chatVBox.getChildren().add(initialMessage);

        VBox suggestionBox = new VBox(10);
        updateSuggestions(suggestionBox, chatVBox);

        chatVBox.getChildren().add(suggestionBox);
        Scene chatbotScene = new Scene(chatVBox);
        Stage stage = new Stage();
        stage.getIcons().add(new Image(ChatbotController.class.getResourceAsStream("/images/logo.jpg")));
        stage.setTitle(" FAQ Bot - Breeze");
        stage.setScene(chatbotScene);

// Set size explicitly so the window isn't too big and doesn't add space
        stage.setWidth(480);
        stage.setHeight(500);
        stage.setResizable(false);

        stage.show();

    }

    private void updateSuggestions(VBox suggestionBox, VBox chatVBox) {
        suggestionBox.getChildren().clear();
        suggestionBox.setVisible(true);
        List<String> questions = faqBot.getQuestions();

        int end = Math.min(questionIndex + QUESTIONS_PER_BATCH, questions.size());
        for (int i = questionIndex; i < end; i++) {
            String question = questions.get(i);
            Button qButton = createSuggestionButton(question);
            qButton.setOnAction(e -> {
                if (chatVBox.getChildren().contains(initialMessage)) {
                    chatVBox.getChildren().remove(initialMessage);
                }
                chatVBox.getChildren().remove(suggestionBox);
                chatVBox.getChildren().add(createUserMessage(question));
                chatVBox.getChildren().add(createBotMessage(faqBot.getAnswer(question)));
                chatVBox.getChildren().add(createResetButton(suggestionBox, chatVBox));
            });
            suggestionBox.getChildren().add(qButton);
        }

        if (end < questions.size()) {
            // Show Load More
            suggestionBox.getChildren().add(createLoadMoreButtonWithHandler(suggestionBox, chatVBox));
        } else if (questionIndex != 0) {
            // Show Go Back button with same style
            suggestionBox.getChildren().add(createGoBackButton(suggestionBox, chatVBox));
        }
    }

    // Helper method to add Load More button and event handler
    private HBox createLoadMoreButtonWithHandler(VBox suggestionBox, VBox chatVBox) {
        HBox loadMoreContainer = createLoadMoreButton();
        Button loadMoreButton = (Button) loadMoreContainer.getChildren().get(0);

        loadMoreButton.setOnAction(e -> {
            questionIndex += QUESTIONS_PER_BATCH;
            updateSuggestions(suggestionBox, chatVBox);
        });

        return loadMoreContainer;
    }


    private Button createSuggestionButton(String text) {
        Button button = new Button(text);
        button.setWrapText(true);
        button.setMaxWidth(Double.MAX_VALUE);

        String baseStyle = "-fx-background-color: #f5e6cc; " +
                "-fx-text-fill: black; " +
                "-fx-font-family: 'Playfair Display'; " +
                "-fx-font-size: 16px; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 14 20 14 20; " +
                "-fx-background-radius: 15; " +
                "-fx-cursor: hand;";

        String hoverStyle = "-fx-background-color: #e4d2b5; " +
                "-fx-text-fill: black; " +
                "-fx-font-family: 'Playfair Display'; " +
                "-fx-font-size: 16px; " +
                "-fx-font-weight: 600; " +
                "-fx-padding: 14 20 14 20; " +  // <-- keep this same!
                "-fx-background-radius: 15; " +
                "-fx-cursor: hand;";

        button.setStyle(baseStyle);

        button.setOnMouseEntered(e -> button.setStyle(hoverStyle));
        button.setOnMouseExited(e -> button.setStyle(baseStyle));

        return button;
    }

    private HBox createLoadMoreButton() {
        Button button = new Button("Load more");
        String baseStyle =
                "-fx-background-color: #4d663b; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-family: 'Playfair Display'; " +
                        "-fx-padding: 12 30 12 30; " +
                        "-fx-background-radius: 8; " +
                        "-fx-cursor: hand; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 2);";
        String hoverStyle =
                "-fx-background-color: #5a7a4a; " +  // lighter green on hover
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-family: 'Playfair Display'; " +
                        "-fx-padding: 12 30 12 30; " +
                        "-fx-background-radius: 8; " +
                        "-fx-cursor: hand; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 3);";

        button.setStyle(baseStyle);
        button.setOnMouseEntered(e -> button.setStyle(hoverStyle));
        button.setOnMouseExited(e -> button.setStyle(baseStyle));

        HBox loadMoreContainer = new HBox(button);
        loadMoreContainer.setAlignment(Pos.CENTER);
        loadMoreContainer.setPadding(new Insets(10, 0, 0, 0));
        return loadMoreContainer;
    }

    private HBox createGoBackButton(VBox suggestionBox, VBox chatVBox) {
        Button button = new Button("Go Back");
        String baseStyle =
                "-fx-background-color: #4d663b; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-family: 'Playfair Display'; " +
                        "-fx-padding: 12 30 12 30; " +
                        "-fx-background-radius: 8; " +
                        "-fx-cursor: hand; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 2);";
        String hoverStyle =
                "-fx-background-color: #5a7a4a; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 16px; " +
                        "-fx-font-family: 'Playfair Display'; " +
                        "-fx-padding: 12 30 12 30; " +
                        "-fx-background-radius: 8; " +
                        "-fx-cursor: hand; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 3);";

        button.setStyle(baseStyle);
        button.setOnMouseEntered(e -> button.setStyle(hoverStyle));
        button.setOnMouseExited(e -> button.setStyle(baseStyle));

        button.setOnAction(e -> {
            questionIndex = 0;
            updateSuggestions(suggestionBox, chatVBox);
        });

        HBox container = new HBox(button);
        container.setAlignment(Pos.CENTER);
        container.setPadding(new Insets(10, 0, 0, 0));
        return container;
    }

    private Button createResetButton(VBox suggestionBox, VBox chatVBox) {
        Button resetBtn = new Button("Reset");
        String baseStyle =
                "-fx-background-color: #336961; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-family: 'Playfair Display'; " +
                        "-fx-padding: 8 20 8 20; " +
                        "-fx-background-radius: 10; " +
                        "-fx-cursor: hand; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.2), 10, 0, 0, 2);";
        String hoverStyle =
                "-fx-background-color: #3a7a72; " +
                        "-fx-text-fill: white; " +
                        "-fx-font-size: 14px; " +
                        "-fx-font-family: 'Playfair Display'; " +
                        "-fx-padding: 8 20 8 20; " +
                        "-fx-background-radius: 10; " +
                        "-fx-cursor: hand; " +
                        "-fx-effect: dropshadow(gaussian, rgba(0,0,0,0.3), 15, 0, 0, 3);";

        resetBtn.setStyle(baseStyle);
        resetBtn.setOnMouseEntered(e -> resetBtn.setStyle(hoverStyle));
        resetBtn.setOnMouseExited(e -> resetBtn.setStyle(baseStyle));

        resetBtn.setOnAction(e -> {
            chatVBox.getChildren().clear();
            questionIndex = 0;
            chatVBox.getChildren().add(initialMessage);
            VBox newSuggestionBox = new VBox(10);
            updateSuggestions(newSuggestionBox, chatVBox);
            chatVBox.getChildren().add(newSuggestionBox);
        });

        return resetBtn;
    }

    private HBox createUserMessage(String message) {
        Label msg = new Label(message);
        msg.setWrapText(true);
        msg.setMaxWidth(320);
        msg.setStyle(
                "-fx-background-color: #b5ead7; " +
                        "-fx-padding: 16; " +
                        "-fx-background-radius: 17; " +
                        "-fx-font-family: 'Playfair Display'; " +
                        "-fx-font-size: 16px;"
        );
        HBox box = new HBox(msg);
        box.setAlignment(Pos.CENTER_RIGHT);
        box.setPadding(new Insets(5, 0, 5, 0));
        return box;
    }

    private HBox createBotMessage(String message) {
        Label msg = new Label(message);
        msg.setWrapText(true);
        msg.setMaxWidth(320);
        msg.setStyle(
                "-fx-background-color: #aec8b2; " +
                        "-fx-padding: 16; " +
                        "-fx-background-radius: 17; " +
                        "-fx-font-family: 'Playfair Display'; " +
                        "-fx-font-size: 16px;"
        );
        Label icon = new Label("✧");
        icon.setTextFill(Color.WHITE);
        icon.setStyle("-fx-font-size: 16px; -fx-font-weight: bold;");

        StackPane circle = new StackPane(icon);
        circle.setStyle(
                "-fx-background-color: #336961; " +
                        "-fx-background-radius: 50%; " +
                        "-fx-min-width: 30px; " +
                        "-fx-min-height: 30px; " +
                        "-fx-max-width: 30px; " +
                        "-fx-max-height: 30px; " +
                        "-fx-alignment: center;"
        );

        HBox box = new HBox(10, circle, msg);
        box.setAlignment(Pos.CENTER_LEFT);
        box.setPadding(new Insets(5, 0, 5, 0));
        return box;
    }
}
