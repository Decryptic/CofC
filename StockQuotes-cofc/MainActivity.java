package com.here.nothing.stockquotes_swenson;

import android.app.Activity;
import android.content.res.Resources;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends Activity {

    private void fetch(String symbol) {
        final TextView tvSymbol = (TextView)this.findViewById(R.id.tvSymbol);
        final TextView tvName = (TextView)this.findViewById(R.id.tvName);
        final TextView tvLastTradePrice = (TextView)this.findViewById(R.id.tvLastTradePrice);
        final TextView tvLastTradeTime = (TextView)this.findViewById(R.id.tvLastTradeTime);
        final TextView tvChange = (TextView)this.findViewById(R.id.tvChange);
        final TextView tvRange = (TextView)this.findViewById(R.id.tvRange);

        new StockAsyncTask() {
            @Override
            protected void onPostExecute(final Stock result) {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Resources ss = getResources();
                        tvSymbol.setText(ss.getString(R.string.defaultSymbol) + result.getSymbol());
                        tvName.setText(ss.getString(R.string.defaultName) + result.getName());
                        tvLastTradePrice.setText(ss.getString(R.string.defaultLastTradePrice) + result.getLastTradePrice());
                        tvLastTradeTime.setText(ss.getString(R.string.defaultLastTradeTime) + result.getLastTradeTime());
                        tvChange.setText(ss.getString(R.string.defaultChange) + result.getChange());
                        tvRange.setText(ss.getString(R.string.defaultRange) + result.getRange());
                    }
                });
            }
        }.execute(symbol);
    }

    @Override
    protected void onSaveInstanceState(Bundle b) {
        super.onSaveInstanceState(b);

        final EditText etSymbol = (EditText)this.findViewById(R.id.etSymbol);
        b.putString("etSymbol", etSymbol.getText().toString());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText etSymbol = (EditText)this.findViewById(R.id.etSymbol);

        if (savedInstanceState != null) {
            String s = savedInstanceState.getString("etSymbol", "");
            etSymbol.setText(s);
            if (!s.equals("")) {
                fetch(etSymbol.getText().toString());
            }
        }

        etSymbol.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) { }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                fetch(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) { }
        });
    }
}
