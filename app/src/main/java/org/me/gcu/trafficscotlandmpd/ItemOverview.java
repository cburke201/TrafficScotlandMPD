package org.me.gcu.trafficscotlandmpd;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.SupportMapFragment;
import com.google.android.gms.maps.UiSettings;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MapStyleOptions;
import com.google.android.gms.maps.model.MarkerOptions;

// Craig Burke S2024071

public class ItemOverview extends AppCompatActivity implements OnMapReadyCallback
{

    private ActionBar actionBar;
    private TextView tvTitle;
    private TextView tvDescription;
    private TextView tvLink;
    private TextView tvAuthor;
    private TextView tvComments;
    private TextView tvPublished;
    private Intent intent;
    private Item item;

    private GoogleMap map;
    private UiSettings mapSettings;
    private double[] latLng;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_overview);
        SupportMapFragment mapFragment = (SupportMapFragment) getSupportFragmentManager().findFragmentById(R.id.map);
        mapFragment.getMapAsync(this);

        actionBar = getSupportActionBar();
        if (actionBar != null)
        {
            actionBar.setSubtitle("Details");
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        tvTitle = findViewById(R.id.tvTitle);
        tvDescription = findViewById(R.id.tvDescription);
        tvLink = findViewById(R.id.tvLink);
        tvAuthor = findViewById(R.id.tvAuthor);
        tvComments = findViewById(R.id.tvComments);
        tvPublished = findViewById(R.id.tvPublished);

        intent = getIntent();

        if (intent.getExtras() != null)
        {
            item = (Item) intent.getSerializableExtra("data");

            tvTitle.setText(item.getTitle());
            tvDescription.setText(item.getDescription());
            tvLink.setText(item.getLink());
            tvAuthor.setText(item.getAuthor());
            tvComments.setText(item.getComments());
            tvPublished.setText(String.format("%s%s", getString(R.string.published_label), item.getPubDate().substring(0, item.getPubDate().length() - 13)));
        }
    }

    @Override
    public void onMapReady(GoogleMap googleMap)
    {
        map = googleMap;
        latLng = item.getLatLng();
        mapSettings = map.getUiSettings();
        mapSettings.setZoomControlsEnabled(true);
        map.setMapStyle(MapStyleOptions.loadRawResourceStyle(this, R.raw.style_json));
        LatLng position = new LatLng(latLng[0], latLng[1]);
        map.addMarker(new MarkerOptions().position(position).title(item.getTitle()).visible(true));
        map.animateCamera(CameraUpdateFactory.newLatLngZoom(position, 15));
    }
}
