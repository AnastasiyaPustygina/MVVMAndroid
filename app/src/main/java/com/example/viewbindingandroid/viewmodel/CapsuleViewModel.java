package com.example.viewbindingandroid.viewmodel;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.viewbindingandroid.data.repository.CapsuleRepository;
import com.example.viewbindingandroid.data.source.CapsuleApiDataSource;
import com.example.viewbindingandroid.domain.Capsule;

import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

// ViewModel для работы с капсулами
public class CapsuleViewModel extends ViewModel {
    // LiveData для хранения данных капсулы
    private final MutableLiveData<Capsule> capsuleLiveData = new MutableLiveData<>();
    // Репозиторий для получения данных о капсуле
    private final CapsuleRepository capsuleRepository;

    // Конструктор ViewModel
    public CapsuleViewModel() {
        // Инициализация репозитория с источником данных
        capsuleRepository = new CapsuleRepository(new CapsuleApiDataSource());
    }

    // Метод для получения LiveData с капсулой
    public LiveData<Capsule> getCapsuleLiveData() {
        return capsuleLiveData;
    }

    // Метод для запроса капсулы по QR-коду
    public void fetchCapsuleByQRCode(String qrCode) {
        Log.e("MY_LINK", qrCode);  // Логируем QR-код
        capsuleRepository.getCapsule(qrCode, new Callback<Capsule>() {  // Делаем запрос через репозиторий
            @Override
            public void onResponse(Call<Capsule> call, Response<Capsule> response) {
                if (response.isSuccessful() && response.body() != null) {
                    // Устанавливаем данные о капсуле в LiveData, если запрос успешен
                    capsuleLiveData.setValue(response.body());
                }
            }

            @Override
            public void onFailure(Call<Capsule> call, Throwable t) {
                Log.e("CapsuleViewModel", "Error fetching capsule", t);  // Логируем ошибку при неудачном запросе
            }
        });
    }
}

