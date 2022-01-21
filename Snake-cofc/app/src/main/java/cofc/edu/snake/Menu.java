package cofc.edu.snake;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Menu extends AppCompatActivity {

    private Button startBtn;
    private Button settingsBtn;
    private Button highScoresBtn;
    private Intent iLauncher;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu);

        startBtn = (Button) findViewById(R.id.startButton);
        settingsBtn = (Button) findViewById(R.id.settingsButton);
        highScoresBtn = (Button) findViewById(R.id.highScoresButton);

        //sets on click listeners to launch other activities
        startBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                iLauncher = new Intent(getApplicationContext(), Game.class);
                startActivity(iLauncher);
            }
        });

        settingsBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                iLauncher = new Intent(getApplicationContext(), Settings.class);
                startActivity(iLauncher);
            }
        });

        highScoresBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                iLauncher = new Intent(getApplicationContext(), Scores.class);
                startActivity(iLauncher);
            }
        });
    }
}
