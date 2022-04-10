package org.me.gcu.trafficscotlandmpd;

import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;

// Craig Burke S2024071
public class MainActivity extends AppCompatActivity
{

    Format format;
    ConstraintLayout content;
    ConstraintLayout loader;

    public static String currentIncidents = "https://trafficscotland.org/rss/feeds/currentincidents.aspx";
    public static String currentRoadworks = "https://trafficscotland.org/rss/feeds/roadworks.aspx";
    public static String plannedRoadworks = "https://trafficscotland.org/rss/feeds/plannedroadworks.aspx";

    public static ArrayList<Item> items = new ArrayList<>();
    public static String itemsString;
    public static String feedName;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        format = new Format();
        content = findViewById(R.id.content);
        loader = findViewById(R.id.loader);
    }

    public void CurrentIncidents(View view)
    {
        Threads rss = new Threads(this);
        rss.execute(currentIncidents);
        feedName = "Current Incidents";
    }

    public void CurrentRoadworks(View view)
    {
        Threads rss = new Threads(this);
        rss.execute(currentRoadworks);
        feedName = "Current Roadworks";
    }

    public void PlannedRoadworks(View view)
    {
        Threads rss = new Threads(this);
        rss.execute(plannedRoadworks);
        feedName = "Planned Roadworks";
    }

    public void loading(final boolean show)
    {
        loader.setVisibility(show ? View.VISIBLE : View.GONE);
        content.setVisibility(show ? View.GONE : View.VISIBLE);
    }
}
