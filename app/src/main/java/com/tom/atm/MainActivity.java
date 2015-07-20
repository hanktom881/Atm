package com.tom.atm;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;


public class MainActivity extends ActionBarActivity {
    boolean logon = false;
    String[] funcNames = {"餘額查詢", "記錄查詢", "變更密碼", "投資項目", "轉帳"};
    int[] funcImage = {R.drawable.f1, R.drawable.f2, R.drawable.f3, R.drawable.f4, R.drawable.f5};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (!logon){ //如果未登入, 則開啟登入Activity
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
        }
//        ListView list = (ListView) findViewById(R.id.list);
        GridView grid = (GridView) findViewById(R.id.grid);
//        String[] data = {"AA", "BB", "CC"};
//        ArrayAdapter<String> adapter =
//                new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, funcNames);
//                new ArrayAdapter<String>(this, R.layout.row_test, data);
        IconAdapter adapter = new IconAdapter();
        grid.setAdapter(adapter);
    }

    class IconAdapter extends BaseAdapter{

        @Override
        public int getCount() {
            return funcNames.length;
        }

        @Override
        public Object getItem(int position) {
            return funcNames[position];
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            if (convertView == null){
                View v = getLayoutInflater().inflate(R.layout.icon_row, null);
                ImageView iv = (ImageView) v.findViewById(R.id.icon_image);
                TextView tv = (TextView) v.findViewById(R.id.icon_text);
                tv.setText(funcNames[position]);
                iv.setImageResource(funcImage[position]);
                convertView = v;
            }
            return convertView;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
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
