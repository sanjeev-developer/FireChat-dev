package com.firechat.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;


import com.firechat.Adapter.FriendsAdapter;
import com.firechat.Adapter.HomeScreenAdapter;
import com.firechat.R;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

/**
 * A simple {@link Fragment} subclass.
 */
public class FriendsFragment extends Fragment {

    View view;
    RecyclerView recyclerView;
    LinearLayoutManager layoutManager;
    ArrayList<String> arrayList = new ArrayList<>();
    FriendsAdapter friendsAdapter;
    public FriendsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_friends, container, false);

        arrayList.add("Adam nickle");
        arrayList.add("Sam willson");
        arrayList.add("Micahale Angelo");
        arrayList.add("Leonardo davinchi");
        arrayList.add("Max Tyson");
        arrayList.add("Mcgregor");

        recyclerView = (RecyclerView) view.findViewById(R.id.rec_friendlist);
        friendsAdapter = new FriendsAdapter(getActivity(), arrayList);
        layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(friendsAdapter);

        return view;
    }
}
