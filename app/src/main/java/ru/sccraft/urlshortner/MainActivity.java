package ru.sccraft.urlshortner;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {

    ListView lw;
    String[] file;
    ArrayList<Link> links;
    private static final String LOG_TAG = "MainActivity";
    private boolean разрешить_использование_интендификатора = false;
    Fe fe;
    boolean задать_адаптер = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(MainActivity.this, ShortenURLActivity.class);
                startActivity(intent);
            }
        });
        lw = findViewById(R.id.lw);
        file = fileList();
        fe = new Fe(this);
        {
            String рекламаID = fe.getFile("adid");
            if (рекламаID.contains("1")) {
                разрешить_использование_интендификатора = true;
            } else {
                //Рекламы нет. Сейчас не нужно.
                //запросить_интендификатор();
            }
        }
        links = new ArrayList<>();
        for (String файл : file) {
            if (!файл.contains(".json")) continue; //устраняет сбой на Samsung GALAXY S6
            if (файл.contains("PersistedInstallation")) continue; //Устраняет сбой и пустые строки на Android 11
            Log.i(LOG_TAG, "Содержание файла " + файл + " : " + fe.getFile(файл));
            if (!(файл.equals("instant-run"))){
                links.add(Link.fromJSON(fe.getFile(файл)));
                задать_адаптер = true;
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (!задать_адаптер) {
            lw.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_list_item_1, getResources().getStringArray(R.array.noLinksHistory)));
            return;
        }
        String[] длинные_ссылки = new String[links.size()];
        for(int i = 0; i < links.size(); i++) {
            длинные_ссылки[i] = links.get(i).longU;
            if (длинные_ссылки[i] == null) {
                Log.e(LOG_TAG, "В массиве \"Длинные ссылки\" на индексе №" + i + " обнаружен NULL");
                длинные_ссылки[i] = "";
            }
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
        if (!разрешить_использование_интендификатора) Toast.makeText(getApplicationContext(), "Please, restart this app!", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(MainActivity.this, LinkInfoActivity.class);
        intent.putExtra("link", link);
        startActivity(intent);
    }

    private void запросить_интендификатор() {
        /*
        Нужно только если в приложении реклама. Иначе - бесполезный код.
        */
        AlertDialog.Builder диалог = new AlertDialog.Builder(this);
        диалог.setTitle(R.string.intendificatorReqest)
                .setMessage(R.string.intendificatorMessage)
                .setCancelable(false)
                .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        fe.saveFile("adid", "1");
                        разрешить_использование_интендификатора = true;
                    }
                })
                .setNegativeButton(R.string.about, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        Intent intent = new Intent(MainActivity.this, AboutActivity.class);
                        startActivity(intent);
                        finish();
                    }
                });
        диалог.show();
    }
}
