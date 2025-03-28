package com.example.viewbindingandroid;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import com.example.viewbindingandroid.databinding.ActivityMainBinding;
import com.example.viewbindingandroid.viewmodel.CapsuleViewModel;

// Главная активность приложения
public class MainActivity extends AppCompatActivity {
    // Объект для привязки с макетом через ViewBinding
    private ActivityMainBinding binding;
    // ViewModel для работы с данными о капсулах
    private CapsuleViewModel capsuleViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Инициализация ViewBinding
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());  // Устанавливаем корневой элемент для активности

        // Инициализация ViewModel
        capsuleViewModel = new ViewModelProvider(this).get(CapsuleViewModel.class);

        // Устанавливаем слушатель для кнопки
        binding.scanButton.setOnClickListener(v -> {
            // Получаем строку из EditText и убираем пробелы с обеих сторон
            String url = binding.urlEditText.getText().toString().trim();
            // Если серия пуста, показываем Toast с сообщением
            if (url.isEmpty()) {
                Toast.makeText(MainActivity.this, "Введите серийный номер", Toast.LENGTH_SHORT).show();
            } else {
                // Логирование введенной ссылки
                Log.e("GET_RES_URL", "URL: " + url);
                // Запрос данных по ссылке через ViewModel
                capsuleViewModel.fetchCapsuleByQRCode(url);
            }
        });

        // Наблюдаем за изменениями данных в ViewModel
        capsuleViewModel.getCapsuleLiveData().observe(this, capsule -> {
            if (capsule != null) {
                // Обновляем UI с полученными данными о капсуле
                binding.capsuleSerial.setText(capsule.getSerial());
                binding.capsuleType.setText(capsule.getType());
                binding.capsuleStatus.setText(capsule.getStatus());
                binding.capsuleDetails.setText(capsule.getDetails());
            }
        });
    }
}
