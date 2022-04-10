package org.me.gcu.trafficscotlandmpd;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.DatePickerDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

// Craig Burke S2024071

public class ItemList extends AppCompatActivity implements ItemFilter.ItemSelected
{

    ActionBar actionBar;
    RecyclerView recyclerView;
    LinearLayout empty;
    ItemFilter adapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<Item> items;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_item_list);

        actionBar = getSupportActionBar();
        if (actionBar != null) {
            actionBar.setSubtitle(MainActivity.feedName);
            actionBar.setDisplayHomeAsUpEnabled(true);
        }

        if (findViewById(R.id.landscapeList) != null)
        {
            layoutManager = new GridLayoutManager(this, 2, GridLayoutManager.VERTICAL, false);
        }
        else
        {
            layoutManager = new LinearLayoutManager(this);
        }

        recyclerView = findViewById(R.id.rvList);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(layoutManager);

        items = new ArrayList<>(MainActivity.items);
        empty = findViewById(R.id.noResults);

        if (items.isEmpty())
        {
            // if the items ArrayList is empty then show a message to say no results
            recyclerView.setVisibility(View.GONE);
            empty.setVisibility(View.VISIBLE);
        }

        adapter = new ItemFilter(items, this);

        recyclerView.setAdapter(adapter);
    }

    @Override
    public void itemSelected(Item item)
    {
        startActivity(new Intent(ItemList.this, ItemOverview.class).putExtra("data", item));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        getMenuInflater().inflate(R.menu.toolbar_menu, menu);
        MenuItem menuItemSearch = menu.findItem(R.id.action_search);
        SearchView searchView = (SearchView) menuItemSearch.getActionView();

        searchView.setMaxWidth(Integer.MAX_VALUE);
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener()
        {
            @Override
            public boolean onQueryTextSubmit(String query)
            {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText)
            {
                adapter.getFilter().filter(newText);
                return true;
            }
        });
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {

        int id = item.getItemId();

        if (id == R.id.action_search)
        {
            return true;
        }
        if (id == R.id.action_date)
        {
            // Get Current Date
            int year, month, day;
            final Calendar c = Calendar.getInstance();
            year = c.get(Calendar.YEAR);
            month = c.get(Calendar.MONTH);
            day = c.get(Calendar.DAY_OF_MONTH);
            DatePickerDialog datePickerDialog = new DatePickerDialog(this, new DatePickerDialog.OnDateSetListener() {
                @Override
                public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                    // add 1 to month as calendar uses indexes 0-11 for months
                    String selectedDate = dayOfMonth + "/" + (month + 1) + "/" + year;
                    Toast.makeText(ItemList.this, selectedDate, Toast.LENGTH_SHORT).show();
                    Calendar newCalendar = formatDatePickerToCalendar(selectedDate);
                    adapter.filterByCalendar(newCalendar);
                }
            }, year, month, day);
            datePickerDialog.show();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public Calendar formatDatePickerToCalendar(String input)
    {
        Calendar queryCalendar = Calendar.getInstance();
        TimeZone tz = TimeZone.getTimeZone("GMT");
        String pattern = "d/M/yyyy";
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern, Locale.ENGLISH);
        try {
            queryCalendar.setTime(simpleDateFormat.parse(input));
            queryCalendar.setTimeZone(tz);
        } catch (ParseException e) {
            Log.d("Message", "Calender Error Converting from String to Calendar");
        }
        return queryCalendar;
    }
}
