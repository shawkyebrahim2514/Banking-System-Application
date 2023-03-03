package com.bank.model;

public class Log {
    private final String username;
    private final ActivityType type;
    String createdAt;

    public Log(String username, ActivityType type, String createdAt) {
        this.username = username;
        this.type = type;
        this.createdAt = createdAt;
    }

    public String getUsername() {
        return username;
    }

    public ActivityType getType() {
        return type;
    }

    @Override
    public String toString() {
        return "This activity of type (" + type.name + ")\n" +
                "This activity is created at: (" + createdAt + ")\n";
    }
}
