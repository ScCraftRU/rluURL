package ru.sccraft.urlshortner;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lw;
    String[] file;
    ArrayList<Link> links;
    private static final String LOG_TAG = "MainActivity";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShortenURLActivity.class);
                startActivity(intent);
            }
        });
        lw = (ListView) findViewById(R.id.lw);
        file = fileList();
        Fe fe = new Fe(this);
        links = new ArrayList<>();
        for (String файл : file) {
            if (файл.contains("rList-ru.sccraft.urlshortner.")) continue; //устраняет сбой на Samsung GALAXY S6
            Log.i(LOG_TAG, "Содержание файла " + файл + " : " + fe.getFile(файл));
            if (!(файл.equals("instant-run"))) links.add(Link.fromJSON(fe.getFile(файл)));
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (file.length < 1) {
            lw.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.noLinksHistory)));
            return;
        }
        String[] длинные_ссылки = new String[links.size()];
        for(int i = 0; i < links.size(); i++) {
            длинные_ссылки[i] = links.get(i).longU;
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, длинные_ссылки);
        lw.setAdapter(adapter);
        lw.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                info(links.get(position));
            }
        });
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
        if (id == R.id.action_about) {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void info(Link link) {
        Intent intent = new Intent(MainActivity.this, LinkInfoActivity.class);
        intent.putExtra("link", link);
        startActivity(intent);
    }
}
