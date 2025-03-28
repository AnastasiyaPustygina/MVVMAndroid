package com.example.viewbindingandroid.data.repository;

import android.util.Log;

import com.example.viewbindingandroid.data.dto.CapsuleDto;
import com.example.viewbindingandroid.data.mapper.CapsuleMapper;
import com.example.viewbindingandroid.data.source.CapsuleApiDataSource;
import com.example.viewbindingandroid.domain.Capsule;

import okhttp3.Request;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.Objects;

// Репозиторий для взаимодействия с источниками данных
public class CapsuleRepository {
    // Источник данных для получения капсул
    private final CapsuleApiDataSource capsuleApiDataSource;

    // Конструктор репозитория
    public CapsuleRepository(CapsuleApiDataSource capsuleApiDataSource) {
        this.capsuleApiDataSource = capsuleApiDataSource;
    }

    // Метод для получения капсулы по серийному номеру
    public void getCapsule(String serial, Callback<Capsule> callback) {
        // Получаем данные о капсуле через источник данных
        capsuleApiDataSource.getCapsuleBySerial(serial, new Callback<CapsuleDto>() {
            @Override
            public void onResponse(Call<CapsuleDto> call, Response<CapsuleDto> response) {
                Log.d("GET_RESPONSE", response.toString());  // Логируем ответ

                if (response.isSuccessful()) {
                    CapsuleDto capsuleDto = response.body();  // Получаем данные о капсуле

                    if (capsuleDto != null) {
                        // Маппинг данных из DTO в доменную модель
                        Capsule capsule = CapsuleMapper.mapFromDto(capsuleDto);
                        Response<Capsule> mappedResponse = Response.success(capsule);
                        Call<Capsule> adaptedCall = new AdaptedCall<>(call, mappedResponse);

                        callback.onResponse(adaptedCall, mappedResponse);  // Отправляем адаптированный ответ
                    } else {
                        // Логируем и обрабатываем случай, когда тело ответа пустое
                        Log.w("CapsuleRepository", "Successful response but body is null for serial: " + serial);
                        callback.onFailure(adaptToCapsuleCall(call), new Throwable("Successful response but CapsuleDto body is null."));
                    }
                } else {
                    // Логируем ошибку, если ответ был неуспешным
                    Log.e("CapsuleRepository", "Unsuccessful response with code: " + response.code());

                    try {
                        Log.e("CapsuleRepository", "Error body: " + Objects.requireNonNull(response.errorBody()).string());
                    } catch (IOException e) {
                        Log.e("CapsuleRepository", "Error while reading errorBody: " + e.getMessage(), e);
                        callback.onFailure(adaptToCapsuleCall(call), new Throwable("Error while reading errorBody: " + e.getMessage()));
                        return;
                    }

                    callback.onFailure(adaptToCapsuleCall(call), new Throwable("API returned an error with code: " + response.code()));
                }
            }

            @Override
            public void onFailure(Call<CapsuleDto> call, Throwable t) {
                Log.e("CapsuleRepository", "API call failed: " + t.getMessage(), t);  // Логируем ошибку при запросе
                callback.onFailure(adaptToCapsuleCall(call), t);  // Отправляем ошибку в callback
            }
        });
    }

    // Адаптируем вызов, чтобы вернуть нужный тип
    private Call<Capsule> adaptToCapsuleCall(Call<CapsuleDto> call) {
        return new AdaptedCall<>(call, null);
    }

    // Вспомогательный класс для адаптации вызовов
    private static class AdaptedCall<T> implements Call<T> {
        private final Call<?> originalCall;
        private final Response<T> response;

        public AdaptedCall(Call<?> originalCall, Response<T> response) {
            this.originalCall = originalCall;
            this.response = response;
        }

        @Override
        public Response<T> execute() throws IOException {
            if (response != null) {
                return response;
            }
            throw new IllegalStateException("This call is not intended for synchronous execution after adaptation.");
        }

        @Override
        public void enqueue(Callback<T> callback) {
            if (response != null) {
                callback.onResponse(this, response);
            } else {
                callback.onFailure(this, new IllegalStateException("This adapted call is not intended for enqueue in failure cases."));
            }
        }

        @Override
        public boolean isExecuted() {
            return originalCall.isExecuted();
        }

        @Override
        public void cancel() {
            originalCall.cancel();
        }

        @Override
        public boolean isCanceled() {
            return originalCall.isCanceled();
        }

        @Override
        public Call<T> clone() {
            return new AdaptedCall<>(originalCall.clone(), response);
        }

        @Override
        public Request request() {
            return originalCall.request();
        }

        @Override
        public okio.Timeout timeout() {
            return originalCall.timeout();
        }
    }
}
