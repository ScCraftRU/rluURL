package ru.sccraft.urlshortner;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;

public class LinkInfoActivity extends AppCompatActivity {

    Link link;
    EditText longU, shortU;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_info);
        setTitle(getString(R.string.info_tittle));
        setupActionBar();
        link = getIntent().getParcelableExtra("link");
        shortU = (EditText) findViewById(R.id.info_short);
        longU = (EditText) findViewById(R.id.info_long);
        shortU.setText(link.shortU);
        longU.setText(link.longU);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}