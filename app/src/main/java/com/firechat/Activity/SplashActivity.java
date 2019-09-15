package com.firechat.Activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.firechat.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import androidx.annotation.NonNull;
import butterknife.BindView;
import butterknife.ButterKnife;

public class SplashActivity extends BasicActivity implements View.OnClickListener {


    @BindView(R.id.txt_change_login_signup)
    TextView txt_change_login_signup;

    @BindView(R.id.but_login_splash)
    Button but_login_splash;

    @BindView(R.id.input_email_main)
    EditText input_email;

    @BindView(R.id.input_password_main)
    EditText input_password;

    @BindView(R.id.but_signup_reg)
    Button but_signup_reg;

    @BindView(R.id.ll_signup)
    LinearLayout ll_signup;

    @BindView(R.id.ll_login)
    LinearLayout ll_login;

    @BindView(R.id.edt_name_reg)
    EditText edt_name_reg;

    @BindView(R.id.edt_email_reg)
    EditText edt_email_reg;

    @BindView(R.id.edt_pass_reg)
    EditText edt_pass_reg;

    @BindView(R.id.edt_confirm_pass_reg)
    EditText edt_confirm_pass_reg;

    @BindView(R.id.ll_splash)
    LinearLayout ll_splash;

    boolean change = false;
    Animation slideUp;
    FirebaseAuth auth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        ButterKnife.bind(this);
        txt_change_login_signup.setOnClickListener(this);
        auth = FirebaseAuth.getInstance();
        slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        but_login_splash.setOnClickListener(this);
        but_signup_reg.setOnClickListener(this);

        hidekeyboard();
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.but_login_splash:

                String txt_email = input_email.getText().toString().trim();
                String txt_password = input_password.getText().toString().trim();

                if (TextUtils.isEmpty(txt_email) || TextUtils.isEmpty(txt_password)){
                    //Toast.makeText(SplashActivity.this, "All fileds are required", Toast.LENGTH_SHORT).show();
                    displayAlert("All fileds are required",ll_splash);
                } else
                    {
                    displaydialog("Loading please wait....");
                    auth.signInWithEmailAndPassword(txt_email, txt_password)
                            .addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                                @Override
                                public void onComplete(@NonNull Task<AuthResult> task) {
                                    if (task.isSuccessful()){

                                        dialog.dismiss();

                                        FirebaseUser firebaseUser  = FirebaseAuth.getInstance().getCurrentUser();
                                        System.out.println(">>>>>>>>>>>>>"+firebaseUser.getUid());
                                        System.out.println(">>>>>>>>>>>>>"+firebaseUser.getPhoneNumber());
                                        //System.out.println(">>>>>>>>>>>>>"+firebaseUser.getUid());

                                        Intent intent = new Intent(SplashActivity.this, HomeScreen.class);
                                        startActivity(intent);

                                        //Toast.makeText(SplashActivity.this, "Login successfully", Toast.LENGTH_SHORT).show();
                                        displayAlert("Login successfully",ll_splash);
                                        finish();
                                    }
                                    else
                                        {
                                        dialog.dismiss();
                                       //Toast.makeText(SplashActivity.this, "Authentication failed!", Toast.LENGTH_SHORT).show();
                                            displayAlert("Authentication failed!",ll_splash);
                                    }
                                }
                            });
                }

                break;

            case R.id.but_signup_reg:


                if (edt_name_reg.getText().toString().equals("")) {
                    displayAlert("First name can't be blank",ll_splash);
                } else if (edt_email_reg.getText().toString().equals("")) {

                    displayAlert("Email can't be blank",ll_splash);
                } else if (!isValidEmail(edt_email_reg.getText().toString())) {

                    displayAlert( "Please enter the valid email",ll_splash);
                } else if (edt_pass_reg.getText().toString().equals("")) {

                    displayAlert( "Password can't be blank",ll_splash);
                } else if (edt_pass_reg.getText().toString().length() < 7) {

                    displayAlert("Password must be between 8-16 Character",ll_splash);
                }
                else if (edt_confirm_pass_reg.getText().toString().equals("")) {

                    displayAlert( "Confirm Password can't be blank",ll_splash);
                } else if (!edt_pass_reg.getText().toString().equals(edt_confirm_pass_reg.getText().toString())) {

                    displayAlert( "Confirm Password and Password not matched",ll_splash);
                } else
                    {
                    String txt_email_reg = edt_email_reg.getText().toString().trim();
                    String txt_password_reg = edt_pass_reg.getText().toString().trim();
                    registeruser(txt_email_reg, txt_password_reg);
                }

                break;


            case R.id.txt_change_login_signup:

                if(change)
                {
                    try {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    slide_To_Left_invisible(ll_signup);
                    slide_To_left_visible(ll_login);

                    but_login_splash.setVisibility(View.VISIBLE);

                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //Do something after 500ms
                            but_signup_reg.setVisibility(View.GONE);
                        }
                    }, 500);


                    edt_name_reg.setText("");
                    edt_email_reg.setText("");
                    edt_pass_reg.setText("");
                    edt_confirm_pass_reg.setText("");

                    txt_change_login_signup.setText("Don't have an account? Signup");

                    change=false;
                }
                else
                {
                    try {
                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }

                    slide_To_Right_invisible(ll_login);

                    if (ll_signup.getVisibility() == View.GONE) {
                        ll_signup.setVisibility(View.VISIBLE);
                        ll_signup.startAnimation(slideUp);
                    }


                    final Handler handler = new Handler();
                    handler.postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            //Do something after 500ms
                            but_login_splash.setVisibility(View.GONE);
                        }
                    }, 500);

                    input_email.setText("");
                    input_password.setText("");


                    but_signup_reg.setVisibility(View.VISIBLE);

                    txt_change_login_signup.setText("Already have an account? Login");

                    change=true;
                }
                break;
        }
    }

    public static boolean isValidEmail(String st_email) {
        if (st_email == null) {
            return false;
        } else {
            return Patterns.EMAIL_ADDRESS.matcher(st_email).matches();
        }

    }

    public boolean match() {
        String password = edt_pass_reg.getText().toString();

        //Minimum 8 and Maximum 16 characters at least 1 Uppercase Alphabet, 1 Lowercase Alphabet, 1 Number and 1 Special Character:

        Boolean retun = false;
        String myPattern = "^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[$@$!#%*?&])[A-Za-z\\d$@$#!%*?&]{8,16}";
        Pattern p = Pattern.compile(myPattern, Pattern.DOTALL);
        Matcher m = p.matcher(password);

        if (m.matches()) {
            Log.d("Matcher", "PATTERN MATCHES!");
            retun = true;
        } else {
            Log.d("MATCHER", "PATTERN DOES NOT MATCH!");
            retun = false;
        }

        return retun;
    }


    public void registeruser(String txt_email, String txt_password)
    {
        displaydialog("Loading please wait....");
        auth.createUserWithEmailAndPassword(txt_email, txt_password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful())
                        {
                            userProfile();

                        } else {

                           // Toast.makeText(SplashActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                            displayAlert( "Authentication failed.",ll_splash);
                            dialog.dismiss();
                        }
                    }
                });
    }


    //Set UserDisplay Name
    private void userProfile()
    {
        FirebaseUser user = auth.getCurrentUser();
        if(user!= null)
        {
            UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                    .setDisplayName(edt_name_reg.getText().toString().trim())
                    //.setPhotoUri(Uri.parse("https://example.com/jane-q-user/profile.jpg"))  // here you can set image link also.
                    .build();

            user.updateProfile(profileUpdates).addOnCompleteListener(new OnCompleteListener<Void>() {
                        @Override
                        public void onComplete(@NonNull Task<Void> task) {
                            if (task.isSuccessful()) {
                                Log.d("TESTING", "User profile updated.");

                                String currentuserid = user.getUid();

                                FirebaseUser user = auth.getCurrentUser();
                                String UserID=user.getEmail().replace("@","").replace(".","");

                                DatabaseReference mRootRef = FirebaseDatabase.getInstance().getReference();
                                DatabaseReference ref1= mRootRef.child("Users").child(UserID);

                                ref1.child("Name").setValue(edt_name_reg.getText().toString().trim());
                                ref1.child("Image_Url").setValue("Null");
                                ref1.child("Email").setValue(user.getEmail());


                                Log.d("TESTING", "Sign up Successful" + task.isSuccessful());
                                Toast.makeText(SplashActivity.this, "Account Created ", Toast.LENGTH_SHORT).show();
                                Log.d("TESTING", "Created Account");

                                startActivity(new Intent(SplashActivity.this, HomeScreen.class));
                               // Toast.makeText(SplashActivity.this, "Registered successfully", Toast.LENGTH_SHORT).show();
                                displayAlert( "Registered successfully",ll_splash);
                                dialog.dismiss();
                            }
                        }
                    });
        }
    }
}