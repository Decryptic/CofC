package com.here.nothing.stockquotes_swenson;

import android.os.AsyncTask;

import java.io.IOException;
import java.net.MalformedURLException;

public class StockAsyncTask extends AsyncTask<String, Void, Stock> {
    @Override
    protected Stock doInBackground(String... params) {
        if (params.length > 0) {
            Stock stock = new Stock(params[0]);
            boolean success = false;
            try {
                stock.load();
                success = true;
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            if (success)
                return stock;
        }
        return null;
    }
}
