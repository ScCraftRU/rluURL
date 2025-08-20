package ru.sccraft.urlshortner;

import android.os.AsyncTask;
import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.net.UnknownHostException;

public class ShortenURLActivity extends AppCompatActivity {
    private final String LOG_TAG = "ShortenURLActivity";
    EditText longURL, removeTime, shortURL;
    Switch preview;
    Net n;
    Boolean isPreview = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shorten_url);
        Fe fe = new Fe(this);
        //if (!fe.getFile("adid").contains("1")) finish(); //Нет рекламы. Не нужно...
        setupActionBar();
        setTitle(getString(R.string.title_shortenURL));
        longURL = (EditText) findViewById(R.id.longURL);
        shortURL = (EditText) findViewById(R.id.shortURL);
        removeTime = (EditText) findViewById(R.id.removeTime);
        preview = (Switch) findViewById(R.id.preview);
        preview.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                isPreview = isChecked;
            }
        });
        n = (Net) getLastCustomNonConfigurationInstance();
        if (n == null) {
            n = new Net(this);
        } else {
            n.link(this);
        }
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }

    public void shortenURL(View view) {
        try{
            n.execute();
        } catch (IllegalStateException e) {
            e.printStackTrace();
            //Toast.makeText(getApplicationContext(), "ERROR", Toast.LENGTH_SHORT).show();
            n = new Net(this);
            n.execute();
        }
    }

    private class Net extends AsyncTask<Void, Void, String> {
        String longURL;
        ShortenURLActivity a;
        int removeTime;
        boolean preview;

        Net(ShortenURLActivity activity) {
            a = activity;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            longURL = a.longURL.getText().toString();
            try {
                removeTime = Integer.parseInt(a.removeTime.getText().toString());
            }catch (NumberFormatException e) {
                e.printStackTrace();
                removeTime = 0;
            }
            preview = a.isPreview;
        }

        @Override
        protected String doInBackground(Void... params) {
            String длинная_ссылка = "" + this.longURL;
            try {
                длинная_ссылка = URLEncoder.encode(longURL,"UTF8");
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            String HTTPS_ЗАПРОС = "https://rlu.ru/index.sema?a=api&del=" + removeTime + "&preview=" + (preview ? "1" : "0") + "&link=" + длинная_ссылка;
            //if (NetGet.getNetworkConnectionStatus(a)) return a.getString(R.string.noNetwork);
            String КОРОТКАЯ_ССЫЛКА = NetGet.getOneLine(HTTPS_ЗАПРОС);
            if ((КОРОТКАЯ_ССЫЛКА.contains("http"))&&(removeTime == 0)) new Fe(a).saveFile("" + fileList().length + ".json", new Link(длинная_ссылка, КОРОТКАЯ_ССЫЛКА).toJSON());
            return КОРОТКАЯ_ССЫЛКА;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            a.shortURL.setText(s);
        }

        void link(ShortenURLActivity activity) {
            a = activity;
        }
    }

    @Override
    public Object onRetainCustomNonConfigurationInstance() {
        return n;
    }
}