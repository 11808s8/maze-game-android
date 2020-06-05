package br.ucs.android.movecircle;
import androidx.appcompat.app.AppCompatActivity;
import android.graphics.Point;
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
    SensorManager mManager;
    Sensor mAccelerometre;
    private float x, y;

    SensorEventListener sensorListener = new SensorEventListener() {
        @Override
        public void onSensorChanged(SensorEvent pEvent) {
            if (pEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                x = pEvent.values[0];
                y = pEvent.values[1];

                Point screenSizes = new Point();
                getWindowManager().getDefaultDisplay().getSize(screenSizes);

                TextView level = (TextView) findViewById(R.id.level);
                level.setText("Level ".concat(String.valueOf(mazeView.getLevel() + 1)));

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

        mManager = (SensorManager) this.getBaseContext().getSystemService(Service.SENSOR_SERVICE);
        mAccelerometre = mManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);

        LinearLayout rootLayout = (LinearLayout) findViewById(R.id.idDrawCircleView);

        mazeView = new MazeView(this);
        mazeView.setMinimumWidth(500);
        mazeView.setMinimumHeight(800);

        rootLayout.addView(mazeView);

        final Button restart = (Button) findViewById(R.id.restartButton);
        restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mazeView.resetGame();
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        mManager.registerListener(sensorListener, mAccelerometre, SensorManager.SENSOR_DELAY_GAME);
    }
}