package com.lagunabreezelodge.chatbot;

import java.util.*;

public class FAQBot {
    private static final Map<String, String> qaMap;

    static {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("What are the check-in and check-out times?", "Check-in is from 2:00 PM and check-out is until 11:00 AM.");
        map.put("Is breakfast included in the room booking?", "Yes, complimentary breakfast is included for all bookings.");
        map.put("Do you offer airport shuttle services?", "Yes, we provide paid airport shuttle services upon request.");
        map.put("Are pets allowed at Laguna Breeze Lodge?", "Unfortunately, pets are not allowed in the lodge.");
        map.put("Do you have free Wi-Fi available?", "Yes, we offer free high-speed Wi-Fi throughout the property.");
        map.put("How can I modify or cancel my reservation?", "Please use our app’s booking section or contact the front desk.");
        map.put("Do you have parking facilities?", "Yes, we offer complimentary on-site parking for all guests.");
        map.put("Is early check-in possible?", "Early check-in is subject to availability and may incur additional charges.");
        map.put("What payment methods do you accept?", "We accept credit/debit cards, digital wallets, and cash.");
        map.put("Is there a swimming pool or spa?", "Yes! We have both a temperature-controlled pool and a luxury spa.");
        map.put("Can I request a late checkout?", "Late checkout may be possible on request and based on availability.");
        map.put("How do I contact customer service?", "Tap the 'Help' button in the app or call our 24/7 front desk.");
        qaMap = Collections.unmodifiableMap(map);  // prevent modification after initialization
    }

    public FAQBot() {
        // No need to initialize qaMap here — it's static and already initialized.
    }

    public static int getTotalQuestions() {
        return qaMap.size();
    }

    public List<String> getQuestions() {
        return new ArrayList<>(qaMap.keySet());
    }

    public String getAnswer(String question) {
        return qaMap.getOrDefault(question, "Sorry, I don't have an answer for that.");
    }
}
