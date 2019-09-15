package com.firechat.Activity;


import android.app.Dialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.firechat.Adapter.HomeScreenAdapter;
import com.firechat.R;
import com.firechat.fragments.Chatfragments;
import com.firechat.fragments.FriendsFragment;
import com.firechat.fragments.RequestFragment;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import butterknife.BindView;
import butterknife.ButterKnife;

public class HomeScreen extends AppCompatActivity implements View.OnClickListener {

    HomeScreenAdapter homeScreenAdapter;
    LinearLayoutManager layoutManager;
    FirebaseDatabase firebaseDatabase;
    DatabaseReference myRef;

    Fragment fragment;
    FragmentManager fragmentManager;
    FragmentTransaction fragmentTransaction;
    @BindView(R.id.ll_farg)
    LinearLayout ll_farg;
    @BindView(R.id.ll_profile)
    LinearLayout ll_profile;
    @BindView(R.id.ll_messages)
    LinearLayout ll_messages;
    @BindView(R.id.ll_matches)
    LinearLayout ll_matches;
    @BindView(R.id.img_bot_matches)
    ImageView img_bot_matches;
    @BindView(R.id.img_bot_messages)
    ImageView img_bot_messages;
    @BindView(R.id.img_bot_profile)
    ImageView img_bot_profile;
    @BindView(R.id.img_setting_dot)
    ImageView img_setting_dot;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_screen);

        ButterKnife.bind(this);

        ll_matches.setOnClickListener(this);
        ll_messages.setOnClickListener(this);
        ll_profile.setOnClickListener(this);
        img_setting_dot.setOnClickListener(this);

        fragment = new Chatfragments();
        fragmentManager = HomeScreen.this.getSupportFragmentManager();
        fragmentTransaction = fragmentManager.beginTransaction();
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.replace(R.id.ll_farg, fragment);
        fragmentTransaction.commit();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.ll_matches:

                fragment = new RequestFragment();
                fragmentManager = HomeScreen.this.getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.ll_farg, fragment);
                fragmentTransaction.commit();

               // ll_matches.setBackgroundColor(getResources().getColor(R.color.darkgrey));
                ll_matches.setBackground(ContextCompat.getDrawable(HomeScreen.this, R.drawable.tab_left_selected_curve));
                ll_messages.setBackgroundColor(getResources().getColor(R.color.notificationgrey));
                ll_profile.setBackground(ContextCompat.getDrawable(HomeScreen.this, R.drawable.tab_right_curve));



                break;

            case R.id.ll_messages:

                fragment = new Chatfragments();
                fragmentManager = HomeScreen.this.getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE);
                fragmentTransaction.replace(R.id.ll_farg, fragment);
                fragmentTransaction.commit();

                ll_matches.setBackground(ContextCompat.getDrawable(HomeScreen.this, R.drawable.tab_left_curve));
                ll_messages.setBackgroundColor(getResources().getColor(R.color.darkgrey));
                ll_profile.setBackground(ContextCompat.getDrawable(HomeScreen.this, R.drawable.tab_right_curve));


                break;

            case R.id.ll_profile:


                fragment = new FriendsFragment();
                fragmentManager = HomeScreen.this.getSupportFragmentManager();
                fragmentTransaction = fragmentManager.beginTransaction();
                fragmentTransaction.addToBackStack(null);
                fragmentTransaction.replace(R.id.ll_farg, fragment);
                fragmentTransaction.commit();

                ll_matches.setBackgroundColor(getResources().getColor(R.color.notificationgrey));
                ll_messages.setBackgroundColor(getResources().getColor(R.color.notificationgrey));
                ll_profile.setBackgroundColor(getResources().getColor(R.color.darkgrey));

                ll_matches.setBackground(ContextCompat.getDrawable(HomeScreen.this, R.drawable.tab_left_curve));
                ll_messages.setBackgroundColor(getResources().getColor(R.color.notificationgrey));
                ll_profile.setBackground(ContextCompat.getDrawable(HomeScreen.this, R.drawable.tab_right_selected_curve));


                break;

            case R.id.img_setting_dot:

                Dialog dialog = new Dialog(HomeScreen.this);
                dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                dialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
                dialog.setContentView(R.layout.acc_setting_layout);
                dialog.setCancelable(true);

                LinearLayout ll_profile_setting= dialog.findViewById(R.id.ll_profile_setting);
                ll_profile_setting.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        startActivity(new Intent(HomeScreen.this, AccountSetting.class));
                        dialog.dismiss();

                    }
                });

                LinearLayout ll_logout= dialog.findViewById(R.id.ll_logout);
                ll_logout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        startActivity(new Intent(HomeScreen.this, SplashActivity.class));
                        finish();
                    }
                });

                dialog.show();

                break;

        }
    }
}