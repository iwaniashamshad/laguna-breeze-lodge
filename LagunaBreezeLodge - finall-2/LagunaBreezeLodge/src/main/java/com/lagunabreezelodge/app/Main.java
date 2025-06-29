package com.lagunabreezelodge.app;

import com.lagunabreezelodge.db.DBInitializer;
import com.lagunabreezelodge.util.FontLoader;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import com.lagunabreezelodge.controller.HomeController;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        // ✅ Initialize database
        DBInitializer.initializeDatabase();

        // ✅ Load custom fonts if any
        FontLoader.loadFonts();

        // ✅ Load home UI
        HomeController homeController = new HomeController();
        Scene scene = new Scene(homeController.getView(), 900, 700);

        // Set application logo (window icon)
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/images/logo.jpg")));

        primaryStage.setTitle(" Laguna Breeze Lodge");
        primaryStage.setScene(scene);
        primaryStage.show();
        primaryStage.setMaximized(true);

    }

    public static void main(String[] args) {
        launch(args);
    }
}
