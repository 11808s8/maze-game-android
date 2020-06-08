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
    private int level = 0, radius = 35; // ball radius!

    int[][][] maze = {
        {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 }, // The last 2 columns are one'd out because of perspective issues
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 1, 1 },
        {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 3, 1, 1 },
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
        },
        {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1 },
        {1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 0, 1, 1, 1 },
        {1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1 },
        {1, 0, 1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1 },
        {0, 0, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1, 1 },
        {0, 0, 0, 1, 1, 1, 1, 0, 1, 1, 1, 1, 1, 1 },
        {1, 1, 0, 1, 1, 1, 0, 0, 1, 1, 1, 1, 1, 1 },
        {1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1, 1 },
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
        },
        {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1 },
        {1, 0, 0, 1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1 },
        {1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1 },
        {1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 0, 1, 1, 1 },
        {0, 0, 1, 1, 0, 1, 0, 0, 1, 1, 0, 1, 1, 1 },
        {0, 0, 0, 1, 0, 1, 1, 0, 1, 1, 0, 1, 1, 1 },
        {1, 1, 0, 0, 0, 1, 0, 0, 1, 1, 0, 1, 1, 1 },
        {1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1 },
        {1, 1, 0, 0, 0, 0, 0, 1, 1, 0, 0, 1, 1, 1 },
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 3, 1, 1, 1, 1 }
        },
        {
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,},
        {1, 0, 0, 0, 1, 1, 0, 1, 0, 0, 0, 3, 1, 1,},
        {1, 1, 1, 0, 0, 0, 0, 1, 0, 1, 1, 1, 1, 1,},
        {1, 1, 0, 0, 1, 0, 1, 1, 0, 1, 0, 1, 1, 1,},
        {0, 0, 0, 1, 1, 0, 0, 1, 0, 1, 0, 1, 1, 1,},
        {0, 0, 1, 1, 1, 1, 1, 1, 0, 1, 0, 1, 1, 1,},
        {1, 0, 0, 0, 1, 0, 0, 0, 0, 0, 0, 1, 1, 1,},
        {1, 1, 1, 0, 1, 0, 1, 1, 1, 1, 1, 1, 1, 1,},
        {1, 1, 1, 0, 0, 0, 0, 0, 1, 1, 1, 1, 1, 1,},
        {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1,}
        }
    };

    private float currX = 50, currY = 100, tileSizeX, tileSizeY;

    private int circleColor = Color.BLACK;

    public int getCircleColor() {
        return circleColor;
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
            if (((auxY) < (screenSizes.y - 300)) && ((auxY) > 0)) // -300 on Y axis because that's what we found to fit the screen
                this.currY += currY;
    }

    // DrawBallView constructor.
    public MazeView(CustomViewActivity customViewActivity) {
        super(customViewActivity);
        level = 0;

        this.soundCreationStage(customViewActivity);
        
        customViewActivity.getWindowManager().getDefaultDisplay().getSize(screenSizes);
        
        vibrator = (Vibrator) customViewActivity.getBaseContext().getSystemService(Context.VIBRATOR_SERVICE);
        this.tileSizeX = (screenSizes.x/this.maze[this.level].length); // tile size for the X axis
        this.tileSizeY = (screenSizes.y/this.maze[this.level][0].length) ;// tile size for the Y axis
        this.resetGame();
    }

    // for the audio manager and media sounds (start game and next level)
    private void soundCreationStage(CustomViewActivity customViewActivity){

        this.audioManager = (AudioManager) customViewActivity.getSystemService(Context.AUDIO_SERVICE);

        this.nextLevelSound = MediaPlayer.create(customViewActivity.getBaseContext(), R.raw.next_level);
        this.startGameSound = MediaPlayer.create(customViewActivity.getBaseContext(), R.raw.start_game);

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

                    // Draw them tiles on our screen please
                   canvas.drawRect(i * tileSizeX, j * tileSizeY, (i + 1) * tileSizeX, (j + 1) * tileSizeY, p1);
                }

            }
        }
    }

    // Method name checks out
    private boolean ballWallCheck(float cX, float cY,int i, int j){

        return  (cX + this.radius >= (i * this.tileSizeX) && cX - this.radius <= ((i + 1) * this.tileSizeX)
                && cY + this.radius >= (j * this.tileSizeY)
                && cY - this.radius <= ((j + 1) * this.tileSizeY));
    
    }

    public boolean hasHitTheWall(float cX, float cY){ 

            // stop vibrating please
            this.vibrator.cancel();

            for(int i = 0; i < maze[this.level].length; i++){
                for(int j = 0; j < maze[this.level][0].length; j++){

                    if(maze[this.level][i][j] == 1 && this.ballWallCheck(cX, cY,i,j)){  // If it's an wall, verify if the ball is contained within it.
                        

                        // BALL IS CONTAINED! VIBRATE AND RETURN TRUE
                        vibrator.vibrate(2);
                        return true;
                    
                    }
                    else if(maze[this.level][i][j] == 3 && this.ballWallCheck(cX,cY,i,j)){ // If it's an ENDING goal, verify if the ball is contained within it.
                                                   
                            // play the funky tune, dj
                            this.nextLevelSound.start();

                            // next level please
                            this.changeLevel(++this.level);

                            // didn't hit a wall!
                            return false;
                        
                    }
                }
            }
            
            // Nope! Can mooooove freely.
            return false;
        }
    
    public void changeLevel(int level){
        if(level > this.maze.length-1){ // End game condition! Go back to beginning (It's one of those hellish looping games, isn't?)
            this.level = 0;
            Toast.makeText(getContext().getApplicationContext(), "Congratulations, you won nothing!", Toast.LENGTH_LONG).show();
        }
        else
        {
            this.level = level;
            Toast.makeText(getContext().getApplicationContext(), "Next Level!", Toast.LENGTH_LONG).show();
        }
        
        this.setStartingPosition();
    }

    public void resetGame(){ // Reset function for a reset button :)
        this.level = 0;
        this.setStartingPosition();
        this.startGameSound.start();
    }

    private void setStartingPosition(){
        this.currX = screenSizes.x/2;
        this.currY = 50;
    }
}