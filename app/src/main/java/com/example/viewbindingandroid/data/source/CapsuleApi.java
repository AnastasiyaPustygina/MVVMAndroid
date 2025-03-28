package com.example.viewbindingandroid.data.source;

import com.example.viewbindingandroid.data.dto.CapsuleDto;
import com.example.viewbindingandroid.domain.Capsule;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

// Интерфейс API для работы с капсулами
public interface CapsuleApi {
    // Метод для получения капсулы по серийному номеру
    @GET("capsules/{serial}")
    Call<CapsuleDto> getCapsuleBySerial(@Path("serial") String serial);  // Получение данных по серийному номеру
}