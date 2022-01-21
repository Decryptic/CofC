package com.example.gswens.button_counter_swenson;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.TextView;

public class CountActivity extends AppCompatActivity {
    private void animateCounter(final TextView tvCount) {
        Animation sgAnim = AnimationUtils.loadAnimation(getApplicationContext(), R.anim.shrink_grow);
        tvCount.startAnimation(sgAnim);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_count);

        final TextView tvCount = (TextView)findViewById(R.id.tvCount);
        Button btnAdd = (Button)findViewById(R.id.btnAdd);
        Button btnSub = (Button)findViewById(R.id.btnSub);
        Button btnClear = (Button)findViewById(R.id.btnClear);

        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(tvCount.getText().toString());
                tvCount.setText(++count + ""); // gage swenson
                animateCounter(tvCount);
            }
        });

        btnSub.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int count = Integer.parseInt(tvCount.getText().toString());
                tvCount.setText(--count + "");
                animateCounter(tvCount);
            }
        });

        btnClear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                tvCount.setText(R.string.init);
                animateCounter(tvCount);
            }
        });
    }

    @Override
    protected void onSaveInstanceState(Bundle newBundle) {
        final TextView tvCount = (TextView)findViewById(R.id.tvCount);
        newBundle.putString("int", tvCount.getText().toString());
        super.onSaveInstanceState(newBundle);
    }
    @Override
    protected void onRestoreInstanceState(Bundle oldBundle) {
        super.onRestoreInstanceState(oldBundle);
        TextView tvCount = (TextView)findViewById(R.id.tvCount);
        tvCount.setText(oldBundle.getString("int"));
    }
}
