package com.example.viewbindingandroid.data.mapper;

import com.example.viewbindingandroid.data.dto.CapsuleDto;
import com.example.viewbindingandroid.domain.Capsule;

// Маппер для преобразования данных из CapsuleDto в модель Capsule
public class CapsuleMapper {
    public static Capsule mapFromDto(CapsuleDto dto) {
        // Преобразуем данные из DTO в модель домена
        return new Capsule(dto.getCapsuleSerial(), dto.getType(), dto.getStatus(), dto.getDetails());
    }
}

