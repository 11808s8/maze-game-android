package br.ucs.android.movecircle.view;

import android.app.Activity;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.view.View;

import br.ucs.android.movecircle.CustomViewActivity;


public class DrawCircleView extends View {

    // Record current ball horizontal ordinate.
    private float currX = 100;

    // Record current ball vertical ordinate
    private float currY = 100;

    // This is the circle color.
    private int circleColor = Color.GREEN;

    public int getCircleColor() {
        return circleColor;
    }

    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;
    }

    // getter and setter method for currX and currY.
    public float getCurrX() {
        return currX;
    }

    public float getCurrY() {
        return currY;
    }

    public void setCurrX(float currX) {
        this.currX = currX;
    }

    public void setCurrY(float currY) {
        this.currY = currY;
    }

    // DrawBallView constructor.
    public DrawCircleView(Context context) {
        super(context);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Create a new Paint object.
        Paint paint = new Paint();

        // Set paint color.
        paint.setColor(this.getCircleColor());

        // Draw a circle in the canvas.
        canvas.drawCircle(currX, currY, 35, paint);

        Point screenSizes = new Point();
        ((CustomViewActivity) getContext()).getWindowManager().getDefaultDisplay().getSize(screenSizes);
        float tileSizeX = screenSizes.x / (float)10;
        float tileSizeY = screenSizes.y / (float)10;

        int[][] maze = {
                {0, 0, 0, 0, 0,0, 0, 0, 0, 0 },
                {0, 1, 1, 1, 1,1, 1, 1, 1, 0 },
                {0, 1, 0, 1, 0,0, 0, 0, 1, 0 },
                {0, 1, 0, 0, 0,0, 1, 1, 1, 0 },
                {0, 1, 0, 0, 0,0, 0, 0, 1, 0 },
                {0, 1, 1, 1, 1,1, 0, 0, 1, 0 },
                {0, 0, 0, 0, 0,1, 0, 0, 1, 0 },
                {0, 0, 0, 0, 0,1, 0, 0, 1, 0 },
                {0, 1, 1, 1, 1,1, 0, 0, 1, 0 },
                {0, 0, 0, 0, 0,0, 0, 0, 0, 0 }};

        int tam = 50;

        for(int i = 0; i < 10; i++){
            for(int j = 0; j < 10; j++){
                Paint p1 = new Paint();
                p1.setColor(Color.GREEN);
                if(maze[i][j] == 1){
                    canvas.drawRect(i * tileSizeX, j * tileSizeY, (i + 1) * tileSizeX,(j + 1) * tileSizeY, p1);
                }
            }
        }
    }
}
