package com.mbs.wallpopsadmin;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;
import com.theartofdev.edmodo.cropper.CropImage;

import java.io.IOException;

public class AddNewWall extends AppCompatActivity {

    private ImageView wallpaper,thumbnail;
    private Button saveWallpaper,saveThumbnail,saveDatabase;
    private EditText wallpaperUrl,thumbnailUrl,titleWallpaper,titleThumbnail;
    private static final int PICK_ONE = 10;
    private static final int PICK_TWO = 20;

    private StorageReference storageRefWallpaper;
    private StorageReference storageRefThumbnail;
    private Uri resultUriWallpaer;
    private Uri resultUriThumbnail;

    private String titleWallpaperString,titleThumbnailString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_wall);

        wallpaper = findViewById(R.id.imageViewWallpaper);
        thumbnail = findViewById(R.id.imageThumbnail);

        saveWallpaper = findViewById(R.id.addWallpaperbtn);
        saveThumbnail = findViewById(R.id.addThumbnailbtn);
        saveDatabase = findViewById(R.id.saveBtn);

        titleWallpaper = findViewById(R.id.ETtitleWallpaper);
        titleThumbnail = findViewById(R.id.ETtitleThumbnail);

        storageRefWallpaper = FirebaseStorage.getInstance()
                .getReference().child("Wallpaper");
        storageRefThumbnail = FirebaseStorage.getInstance().getReference()
                .child("Thumbnail");

            wallpaper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    titleThumbnailString = titleThumbnail.getText().toString();
                    titleWallpaperString = titleWallpaper.getText().toString();

                    if(titleWallpaperString.isEmpty() && titleThumbnailString.isEmpty()) {
                        Snackbar.make(findViewById(android.R.id.content),"Please set the Title first",Snackbar.LENGTH_LONG).show();
                    } else {
                        Intent intentGallery = new Intent();
                        intentGallery.setType("image/*");
                        intentGallery.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intentGallery, PICK_ONE);
                    }
                }
            });

            thumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                    titleThumbnailString = titleThumbnail.getText().toString();
                    titleWallpaperString = titleWallpaper.getText().toString();

                    if(titleWallpaperString.isEmpty() && titleThumbnailString.isEmpty()) {
                        Snackbar.make(findViewById(android.R.id.content),"Please set the Title first",Snackbar.LENGTH_LONG).show();
                    } else {
                        Intent intentGallery = new Intent();
                        intentGallery.setType("image/*");
                        intentGallery.setAction(Intent.ACTION_GET_CONTENT);
                        startActivityForResult(intentGallery, PICK_TWO);
                    }
                }
            });


            saveWallpaper.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {



                }
            });

            saveThumbnail.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {

                }
            });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == PICK_ONE && resultCode == RESULT_OK && data != null) {
            Uri imageUriWallpaper = data.getData();
            CropImage.activity(imageUriWallpaper)
                    .start(this);
        }
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult resultWallpaper = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK) {
                resultUriWallpaer = resultWallpaper.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),resultUriWallpaer);
                    wallpaper.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception exceptionWallpaper = resultWallpaper.getError();
            }
        }

        if(requestCode == PICK_TWO && resultCode == RESULT_OK && data != null) {
            Uri imageUriThumbnail= data.getData();
            CropImage.activity(imageUriThumbnail)
                    .start(this);
        }
        if(requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult resultThumbnail = CropImage.getActivityResult(data);
            if(resultCode == RESULT_OK) {
                resultUriThumbnail = resultThumbnail.getUri();
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(),resultUriThumbnail);
                    thumbnail.setImageBitmap(bitmap);
                } catch (IOException e) {
                    e.printStackTrace();
                }

            } else if(resultCode == CropImage.CROP_IMAGE_ACTIVITY_RESULT_ERROR_CODE) {
                Exception exceptionThumbnail = resultThumbnail.getError();
            }
        }

    }
}