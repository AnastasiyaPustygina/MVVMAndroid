package com.example.viewbindingandroid.domain;

public class Capsule {
    private String capsule_serial;
    private String type;
    private String status;
    private String details;

    public Capsule(String capsule_serial, String type, String status, String details) {
        this.capsule_serial = capsule_serial;
        this.type = type;
        this.status = status;
        this.details = details;
    }

    public String getSerial() { return capsule_serial; }
    public String getType() { return type; }
    public String getStatus() { return status; }
    public String getDetails() { return details; }
}
