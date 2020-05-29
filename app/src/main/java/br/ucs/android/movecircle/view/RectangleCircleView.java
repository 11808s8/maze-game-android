package br.ucs.android.movecircle.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.view.View;


public class RectangleCircleView extends View {

    // canto superior esquerdo
    private float x1;
    private float y1;

    // canto infeiror direito
    private float x2;
    private float y2;

    // cor.
    private int cor = Color.RED;

    public int getCor() {
        return cor;
    }

    public void setCor(int cor) {
        this.cor = cor;
    }

    public void setCantoInicial(float x, float y) {
        this.x1 = x;
        this.y1 = y;
    }

    public void setCantoFinal(float x, float y) {
        this.x2 = x;
        this.y2 = y;
    }

    // Construtor.
    public RectangleCircleView(float x1,float y1,float x2,float y2, Context context) {
        super(context);
        this.setCantoInicial(x1,y1);
        this.setCantoFinal(x2,y2);
    }

    // Construtor.
    public RectangleCircleView(Context context) {
        super(context);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // Create a new Paint object.
        Paint paint = new Paint();

        // Set paint color.
        paint.setColor(this.getCor());

        RectF r = new RectF();
        r.set(x1,y1,x2,y2);
        canvas.drawRect(r,paint);
    }
}
