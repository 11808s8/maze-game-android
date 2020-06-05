package br.ucs.android.movecircle.view;
import android.media.AudioManager;
import android.os.Vibrator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.View;
import android.media.MediaPlayer;
import android.widget.Toast;

import br.ucs.android.movecircle.CustomViewActivity;
import br.ucs.android.movecircle.R;


public class MazeView extends View {

    private Point screenSizes = new Point();
    private Vibrator vibrator;
    private AudioManager audioManager;
    private MediaPlayer nextLevelSound, startGameSound;
    private int level = 0, radius = 35;

    int[][][] maze = {
        {
        {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1 },
        {1, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1 },
        {1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1 },
        {1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1 },
        {1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1 },
        {1, 1, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
        {1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1 },
        {1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1 },
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },        
        },
        {
        {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1 },
        {0, 0, 0, 1, 1, 1, 0, 0, 3, 3, 0, 0, 0, 1, 1, 1 },
        {0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1 },
        {0, 1, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1 },
        {0, 1, 1, 1, 0, 1, 0, 0, 1, 1, 0, 1, 1, 1, 0, 1 },
        {0, 1, 1, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1, 0, 1 },
        {0, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 1, 0, 0, 0, 1 },
        {0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1 },
        {0, 1, 0, 0, 0, 0, 0, 1, 1, 1, 0, 1, 0, 0, 0, 0 },
        {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1 }
        },
        {
        {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1 },
        {0, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1 },
        {0, 1, 1, 0, 1, 0, 0, 1, 0, 0, 0, 1, 1, 0, 1, 0 },
        {0, 1, 0, 0, 1, 0, 1, 1, 1, 0, 0, 1, 0, 0, 1, 0 },
        {0, 1, 0, 1, 1, 0, 0, 1, 1, 3, 0, 1, 0, 1, 1, 0 },
        {0, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1 },
        {0, 1, 0, 0, 1, 0, 0, 1, 1, 1, 0, 1, 0, 0, 1, 0 },
        {0, 1, 1, 0, 1, 0, 1, 1, 1, 1, 0, 1, 1, 0, 1, 0 },
        {0, 1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 1, 1, 0, 0, 0 },
        {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1 }
        }
    };

    private float currX = 50, currY = 100, TILE_SIZE = 10f, tileSizeX, tileSizeY;

    private int circleColor = Color.BLACK;

    public int getCircleColor() {
        return circleColor;
    }

    public void setCircleColor(int circleColor) {
        this.circleColor = circleColor;
    }

    public int getLevel(){
        return this.level;
    }

    public void setCurrX(float currX) {

        float auxX = this.currX - currX;
        if(!this.hasHitTheWall(auxX, this.currY))
            if (((auxX < screenSizes.x) && ((auxX) > 0)))
                this.currX -= currX;
    }

    public void setCurrY(float currY) {

        float auxY = this.currY + currY;
        if(!this.hasHitTheWall(this.currX, auxY))
            if (((auxY) < (screenSizes.y - 300)) && ((auxY) > 0))
                this.currY += currY;
    }

    // DrawBallView constructor.
    public MazeView(Context context) {
        super(context);
        level = 0;

        audioManager = (AudioManager) context.getSystemService(Context.AUDIO_SERVICE);

        nextLevelSound = MediaPlayer.create(getContext().getApplicationContext(), R.raw.next_level);
        startGameSound = MediaPlayer.create(getContext().getApplicationContext(), R.raw.start_game);
        startGameSound.start();
        ((CustomViewActivity) getContext()).getWindowManager().getDefaultDisplay().getSize(screenSizes);
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
        this.tileSizeX = (screenSizes.x/this.maze[this.level].length); // TILE_SIZE;
        this.tileSizeY = (screenSizes.y/this.maze[this.level][0].length) ;// TILE_SIZE;
        
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        Paint paint = new Paint();
        paint.setColor(this.getCircleColor());

        canvas.drawCircle(currX, currY, this.radius, paint);

        for(int i = 0; i < maze[this.level].length; i++){
            for(int j = 0; j < maze[this.level][0].length; j++){
                Paint p1 = new Paint();
                p1.setColor(Color.WHITE);
                if(maze[this.level][i][j] == 1){
                   canvas.drawRect(i * tileSizeX, j * tileSizeY, (i + 1) * tileSizeX, (j + 1) * tileSizeY, p1);
                }

            }
        }
    }


    public boolean hasHitTheWall(float cX, float cY){ // https://stackoverflow.com/a/29477516

            for(int i = 0; i < maze[this.level].length; i++){
                for(int j = 0; j < maze[this.level][0].length; j++){
                    if(maze[this.level][i][j] == 1){ 
                        if(cX + this.radius >= (i * this.tileSizeX)
                           && cX - this.radius <= ((i + 1) * this.tileSizeX)
                           && cY + this.radius >= (j * this.tileSizeY)
                           && cY - this.radius <= ((j + 1) * this.tileSizeY))
                        {
                            // VIBRA
                            vibrator.vibrate(2);
                            return true;
                        }
                    }
                    else if(maze[this.level][i][j] == 3){
                        if(cX + this.radius >= (i * this.tileSizeX)
                           && cX - this.radius <= ((i + 1) * this.tileSizeX)
                           && cY + this.radius >= (j * this.tileSizeY)
                           && cY - this.radius <= ((j + 1) * this.tileSizeY))
                        {
                            
                            this.nextLevelSound.start();
                            this.changeLevel(++this.level);
                            return false;
                        }
                    }
                }
            }

            this.vibrator.cancel();
            return false;
        }
    
    public void changeLevel(int level){
        if(level > this.maze.length){
            this.level = 0;
            Toast.makeText(getContext().getApplicationContext(), "Você venceu!! Parabéns, não ganhou nada", Toast.LENGTH_LONG).show();
        }
        else
        {
            this.level = level;
            Toast.makeText(getContext().getApplicationContext(), "Próximo nível!", Toast.LENGTH_LONG).show();
        }
        
        this.currX = 100;
        this.currY = 100;
    }

    public void resetGame(){
        this.level = 0;
        this.currX = 100;
        this.currY = 50;
        this.startGameSound.start();
    }
}