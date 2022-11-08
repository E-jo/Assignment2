package com.it481.assignment2;

public class User {
    private String name;
    private String password;
    private String server;
    private String db;

    public User(String name, String password, String server, String db) {
        this.name = name;
        this.password = password;
        this.server = server;
        this.db = db;
    }

    public String getName() {
        return this.name;
    }

    public String getPassword() {
        return this.password;
    }

    public String getServer() {
        return server;
    }

    public String getDb() {
        return db;
    }
}
