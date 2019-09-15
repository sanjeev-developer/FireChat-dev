package com.firechat.Activity;

import android.app.Activity;
import android.app.Dialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.firechat.R;
import com.firechat.Util.ImageCompressionLikeWhatsapp;
import com.firechat.Util.ImagePicker;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Callback;
import com.squareup.picasso.NetworkPolicy;
import com.squareup.picasso.Picasso;
import java.io.File;
import java.io.IOException;

import androidx.annotation.NonNull;
import androidx.core.content.FileProvider;

import butterknife.BindView;
import butterknife.ButterKnife;

public class AccountSetting extends BasicActivity implements View.OnClickListener
{


    @BindView(R.id.but_edit)
    Button but_edit;

    @BindView(R.id.img_profile_account)
    ImageView img_profile_account;

    @BindView(R.id.txt_account_name)
    TextView txt_account_name;

    @BindView(R.id.txt_account_email)
    TextView txt_account_email;

    @BindView(R.id.txt_account_status)
    TextView txt_account_status;

    Intent intent;
    Uri mediaUri;
    File photoFile;
    ImageCompressionLikeWhatsapp imageCompressionLikeWhatsapp;
    FirebaseUser firebaseUser;
    DatabaseReference databaseReference;
    private static final String TAG = "AccountSetting";
    int REQUEST_CAMERA =100;
    int SELECT_IMAGES =200;
    FirebaseAuth auth;
    StorageReference mStorageRef;
    String UserID;
    Dialog chooserdialog,editdialog;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_setting);

        ButterKnife.bind(this);
        but_edit.setOnClickListener(this);
        img_profile_account.setOnClickListener(this);
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        mStorageRef = FirebaseStorage.getInstance().getReference();

        //dialog.show();
        UserID=firebaseUser.getEmail().replace("@","").replace(".","");
        databaseReference = FirebaseDatabase.getInstance().getReference().child("Users").child(UserID);
        databaseReference.addValueEventListener(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot)
            {
                txt_account_name.setText(dataSnapshot.child("Name").getValue().toString());
                txt_account_email.setText(dataSnapshot.child("Email").getValue().toString());
                final String image = dataSnapshot.child("Image_Url").getValue().toString();
                //dialog.dismiss();

                if(!image.equals("default"))
                {
                    Picasso.get().load(image).networkPolicy(NetworkPolicy.OFFLINE).placeholder(R.drawable.profile_icon).into(img_profile_account, new Callback()
                    {
                        @Override
                        public void onSuccess()
                        {
                            Toast.makeText(getApplicationContext(), "successfully set", Toast.LENGTH_LONG).show();
                        }

                        @Override
                        public void onError(Exception e)
                        {
                            Picasso.get().load(image).placeholder(R.drawable.profile_icon).into(img_profile_account);
                            Toast.makeText(getApplicationContext(), "not set", Toast.LENGTH_LONG).show();
                        }
                    });
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError)
            {
                dialog.dismiss();
            }
        });
    }

    @Override
    public void onClick(View v)
    {
        switch (v.getId()) {

            case R.id.img_profile_account:

                showchooser();

                break;

            case R.id.but_edit:

                editdialog = new Dialog(AccountSetting.this);
                editdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
                getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
                editdialog.getWindow().setBackgroundDrawable(new ColorDrawable(getResources().getColor(android.R.color.transparent)));
                editdialog.setContentView(R.layout.edit_layout);
                editdialog.setCancelable(true);
                editdialog.show();

                LinearLayout ll_click_update_eusername ,ll_update_username_update ,ll_click_update_status ,ll_update_status_update ;
                Button but_update_name,but_update_status,but_update_password;
                EditText edt_name_update,edt_status_update;

                ll_click_update_eusername=editdialog.findViewById(R.id.ll_click_update_eusername);
                ll_update_username_update=editdialog.findViewById(R.id.ll_update_username_update);
                ll_click_update_status=editdialog.findViewById(R.id.ll_click_update_status);
                ll_update_status_update=editdialog.findViewById(R.id.ll_update_status_update);
                but_update_status=editdialog.findViewById(R.id.but_update_status);
                but_update_name=editdialog.findViewById(R.id.but_update_name);
                edt_name_update=editdialog.findViewById(R.id.edt_name_update);

                but_update_name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        databaseReference.child("Name").setValue(edt_name_update.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                            @Override
                            public void onComplete(@NonNull Task<Void> task) {

                                if(task.isSuccessful()){

                                   // mProgress.dismiss();

                                } else {

                                    Toast.makeText(getApplicationContext(), "There was some error in saving Changes.", Toast.LENGTH_LONG).show();
                                }
                            }
                        });
                    }
                });

                ll_click_update_eusername.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (ll_update_username_update.getVisibility() == View.VISIBLE) {

                            ll_update_username_update.setVisibility(View.GONE);
                            // Its visible
                        } else {
                            // Either gone or invisible
                            ll_update_username_update.setVisibility(View.VISIBLE);
                            ll_update_status_update.setVisibility(View.GONE);
                        }
                    }
                });

                ll_click_update_status.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        if (ll_update_status_update.getVisibility() == View.VISIBLE) {

                            ll_update_status_update.setVisibility(View.GONE);
                            // Its visible
                        } else {
                            // Either gone or invisible
                            ll_update_status_update.setVisibility(View.VISIBLE);
                            ll_update_username_update.setVisibility(View.GONE);
                        }
                    }
                });
                break;
        }
    }

    public void showchooser()
    {
        //dialog intialization
        chooserdialog = new Dialog(AccountSetting.this);
        chooserdialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setLayout(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        chooserdialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        chooserdialog.setContentView(R.layout.gallery_camera);
        chooserdialog.setCancelable(true);

        LinearLayout camera_choose=(LinearLayout)chooserdialog.findViewById(R.id.camera_picker);
        LinearLayout gallery_choose=(LinearLayout)chooserdialog.findViewById(R.id.gallery_picker);

        camera_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                System.out.println("CAMERA >>>>>> ");
                try {
                    launchCameraForImage();
                } catch (IOException e) {
                    e.printStackTrace();
                }
                chooserdialog.cancel();
            }
        });

        gallery_choose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                galleryImageIntent();
                chooserdialog.cancel();
            }
        });

        chooserdialog.show();
    }

    //for high quality image
    public void launchCameraForImage() throws IOException
    {
        try {
            intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            photoFile = getPhotoFileUri();
            mediaUri = FileProvider.getUriForFile(AccountSetting.this, "com.firechat.provider", photoFile);
            intent.putExtra(MediaStore.EXTRA_OUTPUT, mediaUri);

            if (intent.resolveActivity(getPackageManager()) != null)
            {
                startActivityForResult(intent, REQUEST_CAMERA);
            }
        }
        catch (Exception e)
        {
            try {
                Log.e("launchCameraForImage: ", e.getMessage());
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }

    void galleryImageIntent() {
        Intent intent = null;
        try {
            intent = ImagePicker.getPickImageIntent(AccountSetting.this);
            startActivityForResult(intent, SELECT_IMAGES);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {

            String realPth = "";

            if (requestCode == SELECT_IMAGES) {

                if (data != null && data.getData() != null) {

                    Uri imageUri = data.getData();
                    realPth = getRealPathFromURI(imageUri);
                    img_profile_account.setImageURI(imageUri);
                    uploadimage(imageUri);

                    displaydialog("Uploading please wait....");

                }  else {
                    Uri uri = ImagePicker.getImageUri();
                    img_profile_account.setImageURI(uri);
                    uploadimage(uri);

                    displaydialog("Uploading please wait....");
                }
            }
            else if (requestCode == REQUEST_CAMERA)
            {
              //  Uri imageUri = data.getData();
                realPth = photoFile.getPath();
                img_profile_account.setImageURI(Uri.parse(photoFile.getAbsolutePath()));
                uploadimage(Uri.parse(photoFile.getAbsolutePath()));

                displaydialog("Uploading please wait....");
            }
        }
    }

    public String getRealPathFromURI (Uri contentUri) {
        String path = null;
        String[] proj = { MediaStore.MediaColumns.DATA };
        Cursor cursor = getContentResolver().query(contentUri, proj, null, null, null);
        if (cursor.moveToFirst()) {
            int column_index = cursor.getColumnIndexOrThrow(MediaStore.MediaColumns.DATA);
            path = cursor.getString(column_index);
        }
        cursor.close();
        return path;
    }

    // Returns the File for a photo stored on disk given the fileName
    public File getPhotoFileUri() {
        // Get safe storage directory for photos
        // Use `getExternalFilesDir` on Context to access package-specific directories.
        // This way, we don't need to request external read/write runtime permissions.
        File mediaStorageDir = new File(getExternalFilesDir(Environment.DIRECTORY_PICTURES), "firechat");

        // Create the storage directory if it does not exist
        if (!mediaStorageDir.exists() && !mediaStorageDir.mkdirs()) {
            Log.e(TAG, "failed to create directory");
        }

        // Return the file target for the photo based on filename
        File file = new File(mediaStorageDir.getPath() + File.separator + System.currentTimeMillis() + ".jpg");
        return file;
    }

    public void uploadimage(Uri parse)
    {
        StorageReference filepath = mStorageRef.child("Profile_images").child(UserID + ".jpg");
        filepath.putFile(parse).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {

        @Override
        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
            // Get a URL to the uploaded content
            // Uri downloadUrl = taskSnapshot.getUploadSessionUri();
            // final String download_url = mStorageRef.getDownloadUrl().toString();
           // final String download_url =  taskSnapshot.getMetadata().getReference().getDownloadUrl().toString();

            filepath.getDownloadUrl().addOnCompleteListener(new OnCompleteListener<Uri>() {
                @Override
                public void onComplete(@NonNull Task<Uri> task) {

                    String download_url = task.getResult().getPath();
                    dialog.dismiss();
                }
            });

            //Toast.makeText(AccountSetting.this, "Success Uploaded.", Toast.LENGTH_LONG).show();
//            Map update_hashMap = new HashMap();
//            update_hashMap.put("Image_Url", download_url);
//
//            databaseReference.updateChildren(update_hashMap).addOnCompleteListener(new OnCompleteListener<Void>() {
//                @Override
//                public void onComplete(@NonNull Task<Void> task) {
//
//                    if(task.isSuccessful()){
//
//                        dialog.dismiss();
//                        Toast.makeText(AccountSetting.this, "Success Uploaded", Toast.LENGTH_LONG).show();
//                    }
//                }
//            });
        }
    })
            .addOnFailureListener(new OnFailureListener() {
        @Override
        public void onFailure(@NonNull Exception exception) {
            // Handle unsuccessful uploads
            dialog.dismiss();
            Toast.makeText(AccountSetting.this, "Uploading error.", Toast.LENGTH_LONG).show();
        }
    });
}
}
