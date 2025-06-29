module com.lagunabreezelodge {
    requires javafx.controls;
    requires javafx.graphics;
    requires javafx.base;
    requires javafx.media;
    requires java.net.http;
    requires java.sql;


    exports com.lagunabreezelodge.app;
    exports com.lagunabreezelodge.controller;
    exports com.lagunabreezelodge.model;
   // exports com.lagunabreezelodge.service;
    exports com.lagunabreezelodge.db;
    exports com.lagunabreezelodge.chatbot;
    exports com.lagunabreezelodge.util;
}
