package com.tom.atm;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;


import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;


public class LoginActivity extends ActionBarActivity {
    boolean rememberUserid = false;
    private String uid;
    private String password;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        CheckBox cbUserid = (CheckBox) findViewById(R.id.cb_remember_userid);
        cbUserid.setChecked(getSharedPreferences("atm", MODE_PRIVATE)
                .getBoolean("PREF_REMEMBER_USERID", false));
        cbUserid.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                getSharedPreferences("atm", MODE_PRIVATE).edit()
                        .putBoolean("PREF_REMEMBER_USERID", isChecked)
                        .commit();
                rememberUserid = isChecked;
                if (!rememberUserid){
                    getSharedPreferences("atm", MODE_PRIVATE).edit().putString("PREF_USERID", "").commit();
                }
            }
        });

        EditText userid = (EditText) findViewById(R.id.userid);
        userid.setText(getSharedPreferences("atm", MODE_PRIVATE)
                .getString("PREF_USERID", ""));
    }

    class LoginTask extends AsyncTask<String, Void, Integer>{

        @Override
        protected Integer doInBackground(String... params) {
            int data = 0;
            try {
                URL url = new URL(params[0]);
                HttpURLConnection conn = (HttpURLConnection) url.openConnection();
                InputStream is = conn.getInputStream();
                data = is.read();
                Log.d("DATA", data+"");
            } catch (MalformedURLException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return data;
        }

        @Override
        protected void onPostExecute(Integer integer) {
            super.onPostExecute(integer);
            Log.d("RET", integer+"");
            if (integer == 49){
                Member.userid = uid;
                Member.password = password;
                if (rememberUserid) {
                    SharedPreferences setting = getSharedPreferences("atm", MODE_PRIVATE);
                    setting.edit().putString("PREF_USERID", uid).commit();
                }
//            Toast.makeText(this, "登入成功", Toast.LENGTH_LONG).show();
                new AlertDialog.Builder(LoginActivity.this)
                        .setMessage("登入成功")
                        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent data = new Intent();
                                data.putExtra("LOGIN", true);
                                setResult(RESULT_OK, data);
                                finish();
                            }
                        }).show();
            }else{
                new AlertDialog.Builder(LoginActivity.this)
                        .setTitle("Atm")
                        .setMessage("登入失敗")
                        .setPositiveButton("OK", null)
                        .show();
            }
        }
    }

    public void login(View v){
        EditText edUserid = (EditText) findViewById(R.id.userid);
        EditText edPasswd = (EditText) findViewById(R.id.passwd);
        uid = edUserid.getText().toString();
        password = edPasswd.getText().toString();
        //網路連線
        String s = "http://j.snpy.org/atm/login?userid="+ uid +"&pw="+ password;
        Log.d("URL", s);
        new LoginTask().execute(s);
    }

    public void cancel(View v){

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_login, menu);
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
