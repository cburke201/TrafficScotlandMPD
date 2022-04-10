package org.me.gcu.trafficscotlandmpd;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;

// Craig Burke S2024071

// https://developer.android.com/reference/android/os/AsyncTask
// replaces starter code as I could not get it to link with the
// Pull Parser This was only way that it worked.
public class Threads extends AsyncTask<String, Integer, Void>
{
    @SuppressLint("StaticFieldLeak")
    private MainActivity activity;

    public Threads()
    {

    }

    Threads(MainActivity activity)
    {
        this.activity = activity;
    }

    @Override
    protected Void doInBackground(String... strings)
    {
        StringBuilder result = new StringBuilder();
        try
        {
            URL url = new URL(strings[0]);
            URLConnection connection = url.openConnection();
            BufferedReader reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

            String input;
            while ((input = reader.readLine()) != null)
            {
               result.append(input);
            }
            reader.close();
            MainActivity.itemsString = result.toString();
            XMLPullParser xpp = new XMLPullParser();
            xpp.parseData(MainActivity.itemsString);

            if (!MainActivity.items.isEmpty())
            {
                Log.d("Message", "These are not empty");
            }

        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPreExecute()
    {
        super.onPreExecute();
        activity.loading(true);
        Log.d("Message", "startProgress");
    }

    @Override
    protected void onPostExecute(Void aVoid)
    {
        super.onPostExecute(aVoid);
        Log.d("Message", "Progress Complete " + MainActivity.itemsString);
        Intent intent = new Intent();
        intent.setClass(activity.getApplicationContext(), ItemList.class);
        activity.startActivity(intent);
        activity.loading(false);
    }

    @Override
    protected void onProgressUpdate(Integer... values)
    {
        super.onProgressUpdate(values);
    }
}
