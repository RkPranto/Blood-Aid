package com.example.bloodaid.backand;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.bloodaid.R;

import java.util.zip.Inflater;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class DonorListFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_donorlist,container,false);
    }
}