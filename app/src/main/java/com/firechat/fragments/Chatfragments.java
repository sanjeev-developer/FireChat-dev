package com.firechat.fragments;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.firechat.Adapter.HomeScreenAdapter;
import com.firechat.R;


import java.util.ArrayList;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

/**
 * A simple {@link Fragment} subclass.
 */
public class Chatfragments extends Fragment {

    View view;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayList<String> arrayList = new ArrayList<>();
    HomeScreenAdapter homeScreenAdapter;


    public Chatfragments() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState)
    {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_chatfragments, container, false);

        arrayList.add("Adam nickle");
        arrayList.add("Sam willson");
        arrayList.add("Micahale Angelo");
        arrayList.add("Leonardo davinchi");
        arrayList.add("Max Tyson");
        arrayList.add("Mcgregor");

        recyclerView = (RecyclerView) view.findViewById(R.id.my_recycler_view);
        homeScreenAdapter = new HomeScreenAdapter(getActivity(), arrayList);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(homeScreenAdapter);

        return view;
    }

}
