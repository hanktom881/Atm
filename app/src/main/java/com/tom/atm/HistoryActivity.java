package com.tom.atm;

import android.app.Activity;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.SimpleAdapter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class HistoryActivity extends Activity {
    ListView list;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_history);
        list = (ListView) findViewById(R.id.list);
        String url = "http://j.snpy.org/atm/h?userid="+Member.userid+"&pw="+Member.password;
        new HistoryTask().execute(url);
    }

    class HistoryTask extends AsyncTask<String, Void, String>{

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
                JSONArray array = new JSONArray(s);
                /*Gson gson = new Gson();
                ArrayList<History> hists = gson.fromJson(s, new TypeToken<List<History>>(){}.getType());
                Log.d("HIST", hists.size()+"");
                for (History h : hists){
                    Log.d("HIST",h.getDate()+"/"+h.getAmount()+"/"+h.getUserid());
                }*/
                List<Map<String, String>> data = new ArrayList<Map<String, String>>();
                for (int i=0; i<array.length(); i++){
                    JSONObject obj = array.getJSONObject(i);
                    int amount = obj.getInt("amount");
                    String date = obj.getString("date");
                    String userid = obj.getString("userid");
                    Log.d("OBJ", amount+"/"+date+"/"+userid);
                    Map<String, String> row = new HashMap<String,String>();
                    row.put("amount", amount+"");
                    row.put("date", date);
                    row.put("userid", userid);
                    data.add(row);
                }
                String[] from = {"date", "amount", "userid"};
                int[] to = {R.id.col_date, R.id.col_amount, R.id.col_userid};
                SimpleAdapter adapter = new SimpleAdapter(getApplicationContext(),
                        data, R.layout.row_history, from , to);

                list.setAdapter(adapter);

            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_history, menu);
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
