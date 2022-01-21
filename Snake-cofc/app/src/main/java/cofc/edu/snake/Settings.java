package cofc.edu.snake;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

public class Settings extends AppCompatActivity {

    private Button onBtn;
    private Button offBtn;

    private Button easyBtn;
    private Button mediumBtn;
    private Button hardBtn;

    private void chooseEasy(Resources r, Editor e) {
        easyBtn.setBackgroundColor(Color.RED);
        mediumBtn.setBackgroundResource(android.R.drawable.btn_default);
        hardBtn.setBackgroundResource(android.R.drawable.btn_default);
        e.putInt(r.getString(R.string.difficultySetting), 0);
        e.commit();
    }

    private void chooseMedium(Resources r, Editor e) {
        easyBtn.setBackgroundResource(android.R.drawable.btn_default);
        mediumBtn.setBackgroundColor(Color.RED);
        hardBtn.setBackgroundResource(android.R.drawable.btn_default);
        e.putInt(r.getString(R.string.difficultySetting), 1);
        e.commit();
    }

    private void chooseHard(Resources r, Editor e) {
        easyBtn.setBackgroundResource(android.R.drawable.btn_default);
        mediumBtn.setBackgroundResource(android.R.drawable.btn_default);
        hardBtn.setBackgroundColor(Color.RED);
        e.putInt(r.getString(R.string.difficultySetting), 2);
        e.commit();
    }

    private void chooseVolume(Resources r, Editor e) {
        onBtn.setBackgroundColor(Color.RED);
        offBtn.setBackgroundResource(android.R.drawable.btn_default);
        e.putBoolean(r.getString(R.string.volumeSetting), true);
        e.commit();
    }

    private void chooseMute(Resources r, Editor e) {
        onBtn.setBackgroundResource(android.R.drawable.btn_default);
        offBtn.setBackgroundColor(Color.RED);
        e.putBoolean(r.getString(R.string.volumeSetting), false);
        e.commit();
    }

    @Override
    protected void onCreate(Bundle savedInstatnceState) {
        super.onCreate(savedInstatnceState);
        setContentView(R.layout.activity_settings);

        final Resources rez = getResources();
        SharedPreferences spfs = getApplicationContext().getSharedPreferences(rez.getString(R.string.tableSettings), Context.MODE_PRIVATE);
        final Editor edit = spfs.edit();

        easyBtn = (Button) findViewById(R.id.easyButton);
        easyBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                chooseEasy(rez, edit);
            }
        });

        mediumBtn = (Button) findViewById(R.id.mediumButton);
        mediumBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                chooseMedium(rez, edit);
            }
        });

        hardBtn = (Button) findViewById(R.id.hardButton);
        hardBtn.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v){
                chooseHard(rez, edit);
            }
        });

        onBtn = (Button) findViewById(R.id.onButton);
        onBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseVolume(rez, edit);
            }
        });

        offBtn = (Button) findViewById(R.id.offButton);
        offBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseMute(rez, edit);
            }
        });

        int difficulty = spfs.getInt(rez.getString(R.string.difficultySetting),  1);
        boolean volume = spfs.getBoolean(rez.getString(R.string.volumeSetting), true);

        if (difficulty == 0) {
            chooseEasy(rez, edit);
        } else if (difficulty == 1) {
            chooseMedium(rez, edit);
        } else {
            chooseHard(rez, edit);
        }
        if (volume) {
            chooseVolume(rez, edit);
        } else {
            chooseMute(rez, edit);
        }
    }
}
