package org.me.gcu.trafficscotlandmpd;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class ItemDetails extends Fragment
{

    public ItemDetails()
    {

    }


    @Override
    // https://developer.android.com/reference/android/view/LayoutInflater
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_item_details, container, false);
    }
}
