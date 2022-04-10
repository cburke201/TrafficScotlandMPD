package org.me.gcu.trafficscotlandmpd;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


public class Loader extends Fragment
{
    public Loader()
    {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // https://developer.android.com/reference/android/view/LayoutInflater
        return inflater.inflate(R.layout.fragment_loader, container, false);
    }
}
