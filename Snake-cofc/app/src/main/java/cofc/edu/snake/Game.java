package cofc.edu.snake;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.MotionEvent;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

public class Game extends Activity implements View.OnTouchListener
{
    private final Handler handler = new Handler();
    private ImageView ivSnake;
    private TextView tvScore;
    private ImageView ivPause;
    private ImageView ivStop;

    private float pointX, pointY;

    private Snake snake;
    private boolean volume;
    private boolean play;
    private int difficulty;
    public static final String SCORE_DATA = "score";
    private static final int REQUEST = 1;

    private int getDelay() {
        switch (difficulty) {
            case 0:
                return 400;
            case 1:
                return 300;
            default:
                return 200;
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        ivSnake = (ImageView)findViewById(R.id.ivSnake);
        tvScore = (TextView)findViewById(R.id.tvScore);
        ivPause = (ImageView)findViewById(R.id.ivPause);
        ivStop = (ImageView)findViewById(R.id.ivStop);

        ivPause.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                play = !play;
                if (play) {
                    ivPause.setImageResource(R.drawable.play);
                }
                else {
                    ivPause.setImageResource(R.drawable.pause);
                }
            }
        });

        ivStop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

        ivSnake.setOnTouchListener(this);

        Resources rez = getResources();
        SharedPreferences spfs = getApplicationContext().getSharedPreferences(rez.getString(R.string.tableSettings), Context.MODE_PRIVATE);
        difficulty = spfs.getInt(rez.getString(R.string.difficultySetting),  1);
        volume = spfs.getBoolean(rez.getString(R.string.volumeSetting), true);
        play = true;

        snake = new Snake(rez.getInteger(R.integer.gridRows), rez.getInteger(R.integer.gridCols));

        update();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event)
    {
        Toast.makeText(Game.this, "YOLO", Toast.LENGTH_LONG);
        switch (event.getAction())
        {
            case MotionEvent.ACTION_DOWN:
                pointX = event.getX();
                pointY = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                float nPointX = event.getX();
                float nPointY = event.getY();

                if (Math.abs(nPointX - pointX) > Math.abs(nPointY - pointY))
                {
                    //Go right
                    if(nPointX > pointX) {
                        snake.swipeRight();
                    } else { // go left
                        snake.swipeLeft();
                    }
                } else {
                    if (nPointY > pointY){ // go down
                        snake.swipeDown();
                    } else { // go up
                        snake.swipeUp();
                    }
                }
                break;
        }
        return true;
    }

    private void gameOver() {
        Intent intent = new Intent(getApplicationContext(), EnterName.class);
        intent.putExtra(SCORE_DATA, snake.getScore());
        startActivityForResult(intent, REQUEST);
    }

    private void update()
    {
        final MediaPlayer move = MediaPlayer.create(this, R.raw.blip);
        final MediaPlayer lose = MediaPlayer.create(this, R.raw.lose);
        handler.postDelayed(new Runnable()
        {
            @Override
            public void run()
            {
                if (play) {
                    boolean gameOver = !snake.move();
                    if (gameOver) {
                        if (volume) {
                            lose.start();
                        }
                        gameOver();
                    } else {
                        if (volume) {
                            move.start();
                        }
                        handler.postDelayed(this, getDelay());
                    }
                    ivSnake.setImageBitmap(snake.toBitmap(ivSnake.getWidth(), ivSnake.getHeight()));
                    tvScore.setText("" + snake.getScore());
                }
                else {
                    handler.postDelayed(this, getDelay());
                }
            }//END run
        }, getDelay());
    }
}
