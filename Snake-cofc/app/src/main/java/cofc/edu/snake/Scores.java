package cofc.edu.snake;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import cofc.edu.database.DBHelper;
import cofc.edu.scores.GameScores;

public class Scores extends AppCompatActivity
{
    private DBHelper dataScore;
    private ListView listData;
    private String name;
    private TextView test;
    private GameScores addData;
    private Intent intent;
    private int score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scores);

        listData = (ListView) findViewById(R.id.list);

        name = getIntent().getStringExtra(EnterName.USER_NAME);
        score = getIntent().getIntExtra(EnterName.SCORE_DATA, 2);
        addData = new GameScores(name, score);

        dataScore = new DBHelper(getApplicationContext());
        dataScore.addScores(addData);

        ArrayAdapter<GameScores> adapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1, dataScore.getAllScores());
        listData.setAdapter(adapter);
    }
}
