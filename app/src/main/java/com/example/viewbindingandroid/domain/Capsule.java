package com.example.viewbindingandroid.domain;

public class Capsule {
    private String capsule_serial;
    private String type;
    private String details;

    public Capsule(String capsule_serial, String type, String details) {
        this.capsule_serial = capsule_serial;
        this.type = type;
        this.details = details;
    }

    public String getSerial() { return capsule_serial; }
    public String getType() { return type; }
    public String getDetails() { return details; }
}
