package ru.mark1z11.runbuddies;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import ru.mark1z11.runbuddies.databinding.ActivityLoginBinding;
import ru.mark1z11.runbuddies.databinding.ActivityRastsoloBinding;

public class Rastsolo extends AppCompatActivity implements SensorEventListener {
    private ActivityRastsoloBinding binding;
    SensorManager sm;
    TextView mySteps;
    boolean running = false;
    Sensor countSensor, AccelSensor;
    private int steps = 0;
    private float mAccelCurrent; // current acceleration including gravity
    private float mAccelLast;
    private float mAccel;
    float current_steps = 0.0f;
    boolean shake = true;
    int system_steps = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRastsoloBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        binding.buttonGooback.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Rastsolo.this, MainActivity.class));
            }
        });


        mySteps = (TextView) findViewById(R.id.rast_title);
        sm = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        countSensor = sm.getDefaultSensor(Sensor.TYPE_STEP_COUNTER);
        AccelSensor = sm.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        mAccel = 0.00f;
        mAccelCurrent = SensorManager.GRAVITY_EARTH;
        mAccelLast = SensorManager.GRAVITY_EARTH;
    }

    @Override
    protected void onResume() {
        super.onResume();
        sm.registerListener(this,AccelSensor,SensorManager.SENSOR_DELAY_NORMAL);
        if(countSensor != null){
            running = true;
            sm.registerListener(this,countSensor,SensorManager.SENSOR_DELAY_UI);
            Toast.makeText(this,"Sensor Found",Toast.LENGTH_LONG).show();
        }
        else {
            Toast.makeText(this,"Sensor Not available",Toast.LENGTH_LONG).show();
        }
    }

    @Override
    protected void onPause() {
        super.onPause();
        running = false;
        sm.unregisterListener(this,AccelSensor);
    }

    @Override
    public void onSensorChanged(SensorEvent event) {
        if (event.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
            float x = event.values[0];
            float y = event.values[1];
            float z = event.values[2];
            mAccelLast = mAccelCurrent;
            mAccelCurrent = (float) Math.sqrt((double) (x * x + y * y + z * z));
            float delta = mAccelCurrent - mAccelLast;
            mAccel = mAccel * 0.9f + delta; // perform low-cut filter
            if (mAccel > 30) {
                Toast toast = Toast.makeText(getApplicationContext(), "Device has shaken.", Toast.LENGTH_SHORT);
                toast.show();
                mySteps.setText("0");
                shake = true;
            }
        }
        else if(event.sensor.getType() == Sensor.TYPE_STEP_COUNTER) {
            if(shake){
                shake = false;
                system_steps = (int)event.values[0];
            }
            if (running) {
                mySteps.setText(String.valueOf((int)event.values[0]-system_steps));
            }
        }

    }

    @Override
    public void onAccuracyChanged(Sensor sensor, int accuracy) {

    }
}