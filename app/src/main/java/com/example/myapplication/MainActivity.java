package com.example.myapplication;

import androidx.appcompat.app.AppCompatActivity;

import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private MediaPlayer mediaPlayer;
    private SeekBar volumeSeekBar;
    private Spinner soundSpinner;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        soundSpinner = findViewById(R.id.soundSpinner);
        volumeSeekBar = findViewById(R.id.volumeSeekBar);
        Button playButton = findViewById(R.id.playButton);
        Button stopButton = findViewById(R.id.stopButton);

        volumeSeekBar.setMax(100);
        volumeSeekBar.setProgress(50);

        playButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int selectedSound = soundSpinner.getSelectedItemPosition();
                MainActivity.this.playSound(selectedSound);
            }
        });

        stopButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MainActivity.this.stopSound();
            }
        });

        volumeSeekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                if (mediaPlayer != null) {
                    float volume = progress / 100f;
                    mediaPlayer.setVolume(volume, volume);
                }
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
            }
        });
    }

    private void playSound(int soundIndex) {
        stopSound();

        int soundResId;
        switch (soundIndex) {
            case 0:
                soundResId = R.raw.chinese1;
                break;
            case 1:
                soundResId = R.raw.chinese2;
                break;
            case 2:
                soundResId = R.raw.chinese3;
                break;
            default:
                Toast.makeText(this, "Invalid selection", Toast.LENGTH_SHORT).show();
                return;
        }

        mediaPlayer = MediaPlayer.create(this, soundResId);
        mediaPlayer.setLooping(true);
        mediaPlayer.start();
    }

    private void stopSound() {
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        stopSound();
    }
}
