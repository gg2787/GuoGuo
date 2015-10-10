package com.example.administrator.guoguo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import com.example.administrator.guoguo.Utils.TimeUtil;
import com.example.administrator.guoguo.json.SimpleJsonDataHelper;
import com.example.administrator.guoguo.prefs.AppPrefs;
import com.example.administrator.guoguo.shortcut.AppShortCut;

public class MainActivity extends AppCompatActivity {

    AppShortCut appShortCut = new AppShortCut();
    private Button btnClickHere = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppPrefs.init(this);

        appShortCut.addShortCut(this);

        btnClickHere = (Button)findViewById(R.id.button_clickhere);
        btnClickHere.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                SimpleJsonDataHelper jsonHepler = new SimpleJsonDataHelper();
                jsonHepler.jsonMakeAndParse();
            }
        });

        TimeUtil.timeTest();
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

