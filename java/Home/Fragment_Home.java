package com.valeriogiocondi.user.proverbsapp.Home;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.valeriogiocondi.user.proverbsapp.R;


public class Fragment_Home extends Fragment {

    View mainLayout;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        mainLayout = inflater.inflate(R.layout.fragment_home, container, false);
        return mainLayout;
    }

}
