package cofc.edu.snake;

/**
 * Created by Alex on 4/21/2017.
 */

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.Button;

public class EnterName extends AppCompatActivity {

    private EditText enterName;
    private Button confirmBtn;
    private Intent iLauncher;
    public static final String USER_NAME = "name";
    public static final String SCORE_DATA = "score_data";
    private static final int REQUEST = 2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entername);

        enterName = (EditText) findViewById(R.id.enterName);
        confirmBtn = (Button) findViewById(R.id.confirmButton);

        final int score = getIntent().getIntExtra(Game.SCORE_DATA, 1);

        confirmBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                iLauncher = new Intent(getApplicationContext(), Scores.class);
                iLauncher.putExtra(USER_NAME, enterName.getText().toString());
                iLauncher.putExtra(SCORE_DATA, score);
                startActivityForResult(iLauncher, REQUEST);
            }
        });
    }

    @Override
    protected void onSaveInstanceState (Bundle outState){
        outState.putString("savedDisplay", enterName.getText().toString());
        super.onSaveInstanceState(outState);
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState){
        super.onRestoreInstanceState(savedInstanceState);
        enterName.setText(savedInstanceState.getString("savedDisplay"));

    }

}
