package com.tom.atm;

import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class BalanceActivity extends ActionBarActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_balance);
        Log.d("BALANCE", Member.userid);
        Log.d("BALANCE", Member.password);
        String url = "http://j.snpy.org/atm/b?userid="+Member.userid+"&pw="+Member.password;
        new BalanceTask().execute(url);
    }

    class BalanceTask extends AsyncTask<String, Void, String>{
        @Override
        protected String doInBackground(String... params) {
            StringBuilder sb = new StringBuilder();
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream is = conn.getInputStream();
                InputStreamReader isr = new InputStreamReader(is);
                BufferedReader in = new BufferedReader(isr);
                String line = in.readLine();
                while(line!=null){
                    sb.append(line);
                    line = in.readLine();
                }
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return sb.toString();
        }

        @Override
        protected void onPostExecute(String s) {
            Log.d("JSON", s);
            try {
                JSONObject json = new JSONObject(s);
                int balance = json.getInt("balance");
                Log.d("BALANCE", balance+"");
                TextView tv = (TextView) findViewById(R.id.balance_result);
                tv.setText(balance+"");
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_balance, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
