package com.lagunabreezelodge.util;

import javafx.scene.text.Font;

public class FontLoader {

    private static boolean fontsLoaded = false;

    public static void loadFonts() {
        if (fontsLoaded) return;

        load("/fonts/PlayfairDisplay-Bold.ttf");
        load("/fonts/PlayfairDisplay-SemiBold.ttf");
        load("/fonts/PlayfairDisplay-Regular.ttf");
        load("/fonts/Morning Waves.otf");

        fontsLoaded = true;
    }

    private static void load(String path) {
        try {
            Font.loadFont(FontLoader.class.getResourceAsStream(path), 12);
        } catch (Exception e) {
            System.err.println("Failed to load font: " + path);
        }
    }

    public static Font getBoldFont(double size) {
        return Font.font("Playfair Display Bold", size);
    }

    public static Font getSemiBoldFont(double size) {
        return Font.font("Playfair Display SemiBold", size);
    }

    public static Font getRegularFont(double size) {
        return Font.font("Playfair Display", size); // Regular TTF is often just the base name
    }

    public static Font getItalicSemiBoldFont(double size) {
        return Font.font("Playfair Display SemiBold Italic", size);
    }
}
