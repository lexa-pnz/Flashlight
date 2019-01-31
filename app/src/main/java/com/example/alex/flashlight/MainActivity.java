package com.example.alex.flashlight;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.SurfaceTexture;
import android.hardware.Camera;
import android.hardware.Camera.Parameters;
import android.media.AudioAttributes;
import android.media.AudioManager;
import android.media.SoundPool;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageButton;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;
import java.util.Objects;

import static android.hardware.Camera.Parameters.*;

public class MainActivity extends AppCompatActivity {
    final String TAG = "myERROR";

    //ВЫЛЕТ ПРИ ОТКРЫТИИ КАМЕРЫ И ВОЗВРАЩЕНИИ В ПРИЛОЖЕНИЕ
    //ИСПРАВИТЬ СТРОБОСКОП

    //Добавить окно, если человек не дал разрешение на запуск камеры

    private int sound;
    private SoundPool soundPool;
    private Camera camera;
    Parameters parameters;
    private Parameters mParams;
    private ImageButton btnPower;
    public SharedPreferences sPref;

    private boolean checkFlash = false;
    private boolean checkBtnPower = false;

    private boolean checkSound;
    private int checkStrobe = 0;

    /**СТРОБОСКОП*/
    public void stroboscope(){

        ImageButton btnStrob = (ImageButton) findViewById(R.id.btnStrob);

        switch (checkStrobe){
            case 1:
                Toast toast_strob_1 = Toast.makeText(MainActivity.this, "Стробоскоп 1", Toast.LENGTH_SHORT);
                toast_strob_1.show();
                //btnPower.setImageResource(R.drawable.power_off);
                btnStrob.setImageResource(R.drawable.voltage_num1);

                Thread thread = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        if (camera != null) {
                            while (checkStrobe == 1) {

                                mParams = camera.getParameters();
                                String mode = mParams.getFlashMode();

                                if (mode.equals(Parameters.FLASH_MODE_OFF))
                                    mode = Parameters.FLASH_MODE_TORCH;
                                else
                                    mode = Parameters.FLASH_MODE_OFF;

                                mParams.setFlashMode(mode);
                                camera.setParameters(mParams);

                                try {
                                    Thread.sleep(300);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });
                thread.start();

                break;

            case 2:
                Toast toast_strob_2 = Toast.makeText(MainActivity.this, "Стробоскоп 2", Toast.LENGTH_SHORT);
                toast_strob_2.show();
                //btnPower.setImageResource(R.drawable.power_off);
                btnStrob.setImageResource(R.drawable.voltage_num2);

                Thread thread2 = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        if (camera != null) {
                            while (checkStrobe == 2) {
                                mParams = camera.getParameters();
                                String mode = mParams.getFlashMode();

                                if (mode.equals(Parameters.FLASH_MODE_OFF))
                                    mode = Parameters.FLASH_MODE_TORCH;
                                else
                                    mode = Parameters.FLASH_MODE_OFF;

                                mParams.setFlashMode(mode);
                                camera.setParameters(mParams);

                                try {
                                    Thread.sleep(600);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });
                thread2.start();

                break;

            case 3:
                Toast toast_strob_3 = Toast.makeText(MainActivity.this, "Стробоскоп 3", Toast.LENGTH_SHORT);
                toast_strob_3.show();
                //btnPower.setImageResource(R.drawable.power_off);
                btnStrob.setImageResource(R.drawable.voltage_num3);

                Thread thread3 = new Thread(new Runnable() {

                    @Override
                    public void run() {
                        if (camera != null) {
                            while (checkStrobe == 3) {
                                mParams = camera.getParameters();
                                String mode = mParams.getFlashMode();

                                if (mode.equals(Parameters.FLASH_MODE_OFF))
                                    mode = Parameters.FLASH_MODE_TORCH;
                                else
                                    mode = Parameters.FLASH_MODE_OFF;

                                mParams.setFlashMode(mode);
                                camera.setParameters(mParams);

                                try {
                                    Thread.sleep(1000);
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }
                        }
                    }
                });
                thread3.start();

                break;

            default:
                checkStrobe = 0;
                Toast toast_strob_0 = Toast.makeText(MainActivity.this, "Стробоскоп OFF", Toast.LENGTH_SHORT);
                toast_strob_0.show();

                mParams = camera.getParameters();
                String mode = mParams.getFlashMode();
                if (mode.equals(Parameters.FLASH_MODE_TORCH)){
                    mode = Parameters.FLASH_MODE_OFF;
                }
                btnPower.setImageResource(R.drawable.power_on);
                btnStrob.setImageResource(R.drawable.voltage_num0);


                mParams.setFlashMode(mode);
                camera.setParameters(mParams);
        }

    }


    /**ОБРАБОТЧИК НАЖАТИЙ*/
    public void buttonClick(View view) {
        ImageButton btnMenu = (ImageButton) findViewById(R.id.btnMenu);
        ImageButton btnStrob = (ImageButton) findViewById(R.id.btnStrob);
        ImageButton btnSound = (ImageButton) findViewById(R.id.btnSound);
        ImageButton btnPower = (ImageButton) findViewById(R.id.btnPower);

        switch (view.getId()){

            /*МЕНЮ*/
            case R.id.btnMenu:
                //Toast toast_menu_info = Toast.makeText(MainActivity.this, "Меню", Toast.LENGTH_SHORT);
                //toast_menu_info.show();
                Intent intent = new Intent(this, SettingsActivity.class);
                startActivity(intent);
                break;

             /*ЗВУК*/
            case R.id.btnSound:
                if (!checkSound){
                    btnSound.setImageResource(R.drawable.sound_pump_off);
                    checkSound = true;

                    btnSound.setSoundEffectsEnabled(true);
                    btnMenu.setSoundEffectsEnabled(true);
                    btnStrob.setSoundEffectsEnabled(true);

                    Toast toast_sound_info = Toast.makeText(MainActivity.this, "Звук ON", Toast.LENGTH_SHORT);
                    toast_sound_info.show();

                    saveSound();
                }
                else {
                    btnSound.setImageResource(R.drawable.sound_pump_on);
                    checkSound = false;

                    btnSound.setSoundEffectsEnabled(false);
                    btnMenu.setSoundEffectsEnabled(false);
                    btnStrob.setSoundEffectsEnabled(false);

                    Toast toast_sound_info = Toast.makeText(MainActivity.this, "Звук OFF", Toast.LENGTH_SHORT);
                    toast_sound_info.show();

                    saveSound();
                }
                break;

            /*ВКЛЮЧЕНИЕ*/
            case R.id.btnPower:
                checkRunFlash();

                parameters = camera.getParameters();
                String mode = parameters.getFlashMode();

                if (mode.equals(Parameters.FLASH_MODE_OFF))
                {

                    setFlashLightOn();
                    playSound();

                    //Toast toast_flash_on = Toast.makeText(MainActivity.this, "Свет ON", Toast.LENGTH_SHORT);
                    //toast_flash_on.show();
                }
                else if (mode.equals(Parameters.FLASH_MODE_TORCH))
                {
                    setFlashLightOff();
                    playSound();

                    //Toast toast_flash_off = Toast.makeText(MainActivity.this, "Свет OFF", Toast.LENGTH_SHORT);
                    //toast_flash_off.show();
                }
                break;

            /*СТРОБОСКОП*/
            case R.id.btnStrob:
                checkStrobe++;
                stroboscope();
                break;
        }
    }



    // !!!
    private void checkCam(){
        if (Build.VERSION.SDK_INT >= 23) {
            //динамическое получение прав
            if (checkSelfPermission(Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
                ActivityCompat.requestPermissions(MainActivity.this, new String[]{Manifest.permission.CAMERA}, 1);
                System.exit(0); //ИСПРАВИТЬ !!!
            }
        }

    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        checkCam();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Log.d(TAG, "START");

        //Загрузка данных кнопки Sound
        loadSound();

        //Скрытие кнопок
        ImageButton btnStrob = (ImageButton) findViewById(R.id.btnStrob);
        ImageButton btnMenu = (ImageButton) findViewById(R.id.btnMenu);
        btnStrob.setVisibility(View.INVISIBLE);
        btnMenu.setVisibility(View.INVISIBLE);

        //Полноэкранный режим
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

        //Убрать ActionBar
        Objects.requireNonNull(getSupportActionBar()).hide();


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP)
            createSoundPoolWithBuilder();
        else
            createSoundPoolWithConstructor();

        //Добавление новго звука
        sound = soundPool.load(this, R.raw.click, 1);


        btnPower = (ImageButton) findViewById(R.id.btnPower);
        //Отключение стандартного звука кнопки
        btnPower.setSoundEffectsEnabled(false);

        try {
            boolean isCameraFlash = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
            if (isCameraFlash)
                camera = Camera.open();
        }
        catch (Exception e){
            Toast toast = Toast.makeText(MainActivity.this, "Нет доступа к камере", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void setFlashLightOn() {
        changeIconOn();

        new Thread(new Runnable() {
            @Override
            public void run() {

                if (camera != null) {

                    parameters = camera.getParameters();
                    List supportedFlashModes = parameters.getSupportedFlashModes();

                    if (supportedFlashModes.contains(FLASH_MODE_TORCH)) {
                        parameters.setFlashMode(FLASH_MODE_TORCH);
                    } else if (supportedFlashModes.contains(FLASH_MODE_ON)) {
                        parameters.setFlashMode(FLASH_MODE_ON);
                    }

                    camera.setParameters(parameters);
                    camera.startPreview();
                    try {
                        camera.setPreviewTexture(new SurfaceTexture(0));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    Log.d(TAG, " ");
                }
            }
        }).start();
    }

    private void setFlashLightOff() {
        changeIconOff();

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (camera != null) {

                    parameters.setFlashMode(FLASH_MODE_OFF);

                    camera.setParameters(parameters);

                    camera.stopPreview();
                }
            }
        }).start();
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    protected void createSoundPoolWithBuilder() {
        AudioAttributes attributes = new AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_GAME)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build();

        soundPool = new SoundPool.Builder().setAudioAttributes(attributes).setMaxStreams(1).build();
    }

    @SuppressWarnings("deprecation")
    protected void createSoundPoolWithConstructor() {
        soundPool = new SoundPool(1, AudioManager.STREAM_MUSIC, 0);
    }

    private void releaseCamera() {
        if ( camera != null) {
            camera.release();
            camera = null ;
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        parameters = camera.getParameters();
        String mode = parameters.getFlashMode();
        if (mode.equals(Parameters.FLASH_MODE_OFF))
            releaseCamera();
    }

    @Override
    protected void onResume() {
        super.onResume();
        boolean isCameraFlash = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager.FEATURE_CAMERA_FLASH);
        if ( camera == null) {
            if (isCameraFlash)
                camera = Camera.open();
        }
        loadSound();
    }

    private void checkRunFlash(){
        boolean isCameraFlash = getApplicationContext().getPackageManager().hasSystemFeature(PackageManager. FEATURE_CAMERA_FLASH);
        if (!isCameraFlash) {
            Toast toast = Toast.makeText(MainActivity.this, "Нет доступа к камере", Toast.LENGTH_LONG);
            toast.show();
        }
    }

    private void saveSound() {
        sPref = getPreferences(MODE_PRIVATE);
        SharedPreferences.Editor editor = sPref.edit();
        editor.putBoolean("sound", checkSound);
        editor.apply();
    }

    private void loadSound() {
        sPref = getPreferences(MODE_PRIVATE);
        checkSound = sPref.getBoolean("sound", true);

        ImageButton btnSound = (ImageButton) findViewById(R.id.btnSound);
        if (checkSound)
            btnSound.setImageResource(R.drawable.sound_pump_off);
        else  if (!checkSound)
            btnSound.setImageResource(R.drawable.sound_pump_on);
    }

    private  void playSound(){
        if (checkSound){
            soundPool.play(sound, 1, 1, 0, 0, 1);
        }
    }

    private  void changeIconOn(){
        ImageButton btnPower2 = (ImageButton) findViewById(R.id.btnPower);
        btnPower2.setImageResource(R.drawable.pumpkin_on);
        checkBtnPower = true;
    }

    private  void changeIconOff(){
        ImageButton btnPower3 = (ImageButton) findViewById(R.id.btnPower);
        btnPower3.setImageResource(R.drawable.pumpkin_off2);
        checkBtnPower = false;
    }
}



