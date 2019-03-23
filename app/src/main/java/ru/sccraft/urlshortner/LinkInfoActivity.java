package ru.sccraft.urlshortner;

import android.os.Bundle;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import android.widget.EditText;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;

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

        AdView mAdView;
        mAdView = (AdView) findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    private void setupActionBar() {
        ActionBar actionBar = getSupportActionBar();
        if (actionBar != null) {
            // Show the Up button in the action bar.
            actionBar.setDisplayHomeAsUpEnabled(true);
        }
    }
}
