package com.example.viewbindingandroid.data.dto;

// DTO (Data Transfer Object) для передачи данных о капсуле
public class CapsuleDto {
    private String capsule_serial;  // Серийный номер капсулы
    private String type;            // Тип капсулы
    private String details;         // Подробности о капсуле

    public String getCapsuleSerial() { return capsule_serial; }  // Геттер для серийного номера
    public String getType() { return type; }                     // Геттер для типа капсулы
    public String getDetails() { return details; }               // Геттер для подробностей капсулы
}