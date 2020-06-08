package br.ucs.android.movecircle;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import br.ucs.android.movecircle.view.MazeView;
import android.app.Service;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.widget.TextView;

public class CustomViewActivity extends AppCompatActivity {
    MazeView mazeView;
    SensorManager sensorManager;
    Sensor acelerometer;
    private float x, y;

    // Sensor fun stuff!
    SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent sensorEvent) {
            if (sensorEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                x = sensorEvent.values[0]; // x coords from acelerometer sensor
                y = sensorEvent.values[1]; // y coords from acelerometer sensor

                TextView level = (TextView) findViewById(R.id.level);
                level.setText("Level ".concat(String.valueOf(mazeView.getLevel() + 1))); // what level the user is currently in (counts from 0)

                mazeView.setCurrX(x);
                mazeView.setCurrY(y);

                mazeView.invalidate();
            }
        }

        @Override
        public void onAccuracyChanged(Sensor sensor, int accuracy) {
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_custom_view);

        // sensor setup
        sensorManager = (SensorManager) this.getBaseContext().getSystemService(Service.SENSOR_SERVICE);
        acelerometer = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        LinearLayout rootLayout = (LinearLayout) findViewById(R.id.idDrawCircleView);

        mazeView = new MazeView(this);
        mazeView.setMinimumWidth(500);
        mazeView.setMinimumHeight(800);

        rootLayout.addView(this.mazeView);

        final Button restart = (Button) findViewById(R.id.restartButton);
        restart.setOnClickListener(new View.OnClickListener() { // Reset the game button
            @Override
            public void onClick(View v) {
                mazeView.resetGame();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        sensorManager.registerListener(sensorListener, acelerometer, SensorManager.SENSOR_DELAY_GAME);
    }
}