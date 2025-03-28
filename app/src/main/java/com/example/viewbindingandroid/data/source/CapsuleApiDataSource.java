package com.example.viewbindingandroid.data.source;

import android.util.Log;

import com.example.viewbindingandroid.data.dto.CapsuleDto;
import com.example.viewbindingandroid.domain.Capsule;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

// Источник данных, который взаимодействует с API для получения капсул
public class CapsuleApiDataSource {
    // Интерфейс для выполнения API-запросов
    private final CapsuleApi capsuleApi;

    // Конструктор, инициализирующий Retrofit и интерфейс API
    public CapsuleApiDataSource() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.spacexdata.com/v3/")  // Базовый URL API
                .addConverterFactory(GsonConverterFactory.create())  // Конвертер для работы с JSON
                .build();
        capsuleApi = retrofit.create(CapsuleApi.class);  // Создание экземпляра интерфейса API
    }

    // Метод для получения капсулы по серийному номеру
    public void getCapsuleBySerial(String serial, Callback<CapsuleDto> callback) {
        Log.e("SEND_REQUEST", serial);  // Логируем запрос
        capsuleApi.getCapsuleBySerial(serial).enqueue(callback);  // Отправляем запрос асинхронно
    }
}