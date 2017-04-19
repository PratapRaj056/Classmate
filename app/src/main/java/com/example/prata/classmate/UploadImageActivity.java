package com.example.prata.classmate;

import android.content.DialogInterface;
import android.content.Intent;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

public class UploadImageActivity extends AppCompatActivity {


    private static final int GALLERY_IMAGE_REQUEST = 3;
    private static final int CAMERA_IMAGE_REQUEST = 1;
    private static final String TAG="custom_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload_image);

        AlertDialog.Builder builder = new AlertDialog.Builder(UploadImageActivity.this);
        builder
                .setMessage(R.string.dialog_select_prompt)
                .setPositiveButton(R.string.dialog_select_gallery, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startGalleryChooser();
                    }
                })
                .setNegativeButton(R.string.dialog_select_camera, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        startCamera();
                    }
                });
        builder.create().show();

    }

    public void startGalleryChooser(){
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);;
        startActivityForResult(Intent.createChooser(intent, "Select a photo"),
                GALLERY_IMAGE_REQUEST);
    }

    public void startCamera(){
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        Uri photoUri = FileProvider.getUriForFile(this, getApplicationContext().getPackageName() + ".provider", getCameraFile());
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, photoUri);
        if(intent.resolveActivity(getPackageManager()) !=null){
            startActivityForResult(intent, CAMERA_IMAGE_REQUEST);
        }
        else{
            Log.d(TAG, "Image picking failed");
        }
//        intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
//        startActivityForResult(intent, CAMERA_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
//        if (requestCode == GALLERY_IMAGE_REQUEST && resultCode == RESULT_OK && data != null) {
//
//            //TODO mobile vision from image from gallery
//        }
//
//        else if (requestCode == CAMERA_IMAGE_REQUEST && resultCode == RESULT_OK) {
//
//            //TODO mobile vision from image from camera
//
//        }

    }

}
