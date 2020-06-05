package br.ucs.android.movecircle;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;

import br.ucs.android.movecircle.view.DrawCircleView;
import br.ucs.android.movecircle.view.RectangleCircleView;
import android.app.Service;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.RectF;

public class CustomViewActivity extends AppCompatActivity {
    DrawCircleView drawCircleView;
    SensorManager mManager;
    Sensor mAccelerometre;
    private float x, y;
    SensorEventListener testinhoBr = new SensorEventListener() {

        @Override
        public void onSensorChanged(SensorEvent pEvent) {
            // Set drawCircleView currX and currY value to user finger x y ordinate value..
            if (pEvent.sensor.getType() == Sensor.TYPE_ACCELEROMETER) {
                x = pEvent.values[0];
                y = pEvent.values[1];

                Point screenSizes = new Point();
                getWindowManager().getDefaultDisplay().getSize(screenSizes);

                drawCircleView.setCurrX(x);
                drawCircleView.setCurrY(y);

                // Set circle color to blue.
                drawCircleView.setCircleColor(Color.RED);

                // Notify drawCircleView to redraw. This will invoke DrawBallView's onDraw() method.
                drawCircleView.invalidate();
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
//        mAccelerometre = mManager.getDefaultSensor(Sensor.TYPE_LINEAR_ACCELERATION);

        // Get the root Linearlayout object.
        LinearLayout rootLayout = (LinearLayout) findViewById(R.id.idDrawCircleView);

        // Create the DrawCircleView custom view object.
        drawCircleView = new DrawCircleView(this);

        //set min width and height.
        drawCircleView.setMinimumWidth(500);
        drawCircleView.setMinimumHeight(800);



        // Register onTouchListener object to drawBallView.
//        drawCircleView.setOnTouchListener(onTouchListener);

        //final RectangleCircleView retangulo = new RectangleCircleView(10,10,30,40,this);

        //rootLayout.addView(retangulo);

        // Add drawBallView object in root LinearLayout object.
        rootLayout.addView(drawCircleView);
    }


    @Override
    protected void onResume() {
        super.onResume();
        mManager.registerListener(
                testinhoBr
                , mAccelerometre, SensorManager.SENSOR_DELAY_GAME);
    }
}