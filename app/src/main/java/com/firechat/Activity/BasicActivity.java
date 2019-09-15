package com.firechat.Activity;

import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.TranslateAnimation;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;


import com.firechat.R;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

public class BasicActivity extends AppCompatActivity {

    Dialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    // To animate view slide out from left to right
    public void slide_To_Right_invisible(View view){
        TranslateAnimation animate = new TranslateAnimation(0,view.getWidth(),0,0);
        animate.setDuration(300);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }

    public void slide_To_right_visible(View view){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(-view.getWidth(),0,0,0);
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
       // view.setVisibility(View.VISIBLE);
    }

    // To animate view slide out from left to right
    public void slide_To_left_visible(View view){
        view.setVisibility(View.VISIBLE);
        TranslateAnimation animate = new TranslateAnimation(view.getWidth(),0,0,0);
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
       // view.setVisibility(View.VISIBLE);
    }

    //Right to Left
    // To animate view slide out from right to left
    public void slide_To_Left_invisible(View view){
        TranslateAnimation animate = new TranslateAnimation(0,-view.getWidth(),0,0);
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }

    //Top to Bottom
    // To animate view slide out from top to bottom
    public void slideToBottom(View view){
        TranslateAnimation animate = new TranslateAnimation(0,0,0,view.getHeight());
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }
    //Bottom to Top
    // To animate view slide out from bottom to top
    public void slideToTop(View view){
        TranslateAnimation animate = new TranslateAnimation(0,0,0,-view.getHeight());
        animate.setDuration(500);
        animate.setFillAfter(true);
        view.startAnimation(animate);
        view.setVisibility(View.GONE);
    }

//    public  void displayAlert(Context mContext, String strMessage) {
//        //dialog intialization
//        dialog = new Dialog(mContext);
//        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
//        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        dialog.setContentView(R.layout.alert_diag);
//
//        Button okplaced = (Button) dialog.findViewById(R.id.ok_alert);
//        TextView alertext= (TextView) dialog.findViewById(R.id.text_alert);
//
//        alertext.setText(strMessage);
//
//        okplaced.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view)
//            {
//                dialog.cancel();
//
//            }
//        });
//
//        dialog.show();
//    }

        public  void displaydialog( String strMessage) {

            dialog = new Dialog(BasicActivity.this);
            dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(ContextCompat.getColor(BasicActivity.this, R.color.transparent)));
            dialog.setContentView(R.layout.delete_chat_layout);
            dialog.setCancelable(true);

            TextView message= dialog.findViewById(R.id.txt_loading);
            message.setText(strMessage);

            dialog.show();
    }

    public void hidekeyboard()
    {
        try {
            InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void displayAlert(String msg, LinearLayout ll_splash) {
        Snackbar.make(ll_splash, msg, Snackbar.LENGTH_SHORT).show();
    }

}
