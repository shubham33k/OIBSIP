package com.example.stopwatch; // Replace with your package name

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.stopwatch.R;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private TextView textViewTimer;
    private Button buttonStart, buttonPause, buttonReset;

    private Handler handler;
    private Runnable runnable;
    private long startTime, timeInMilliseconds, timeSwapBuff, updatedTime;

    private boolean isRunning = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize views
        textViewTimer = findViewById(R.id.textViewTimer);
        buttonStart = findViewById(R.id.buttonStart);
        buttonPause = findViewById(R.id.buttonPause);
        buttonReset = findViewById(R.id.buttonReset);

        handler = new Handler();

        // Start Button Logic
        buttonStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!isRunning) {
                    startTime = System.currentTimeMillis();
                    handler.postDelayed(runnable, 0);
                    isRunning = true;
                }
            }
        });

        // Pause Button Logic
        buttonPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isRunning) {
                    timeSwapBuff += timeInMilliseconds;
                    handler.removeCallbacks(runnable);
                    isRunning = false;
                }
            }
        });

        // Reset Button Logic
        buttonReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                handler.removeCallbacks(runnable);
                isRunning = false;
                timeInMilliseconds = 0L;
                timeSwapBuff = 0L;
                updatedTime = 0L;
                textViewTimer.setText("00:00:00");
            }
        });

        // Runnable for updating the timer
        runnable = new Runnable() {
            @Override
            public void run() {
                timeInMilliseconds = System.currentTimeMillis() - startTime;
                updatedTime = timeSwapBuff + timeInMilliseconds;

                int seconds = (int) (updatedTime / 1000);
                int minutes = seconds / 60;
                seconds = seconds % 60;
                int milliseconds = (int) (updatedTime % 1000);

                textViewTimer.setText(String.format(Locale.getDefault(), "%02d:%02d:%02d", minutes, seconds, milliseconds / 10));

                handler.postDelayed(this, 0);
            }
        };
    }
}