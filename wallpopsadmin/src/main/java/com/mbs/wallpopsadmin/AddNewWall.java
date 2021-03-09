package com.mbs.wallpopsadmin;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.provider.OpenableColumns;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class AddNewWall extends AppCompatActivity {

    private EditText wallpaperTitle, photographer;
    private ImageView wallpaper;
    private TextView wallpaperLink;
    private Button saveButton;
    private String stringWallpaperTitle;
    private String stringWallpaperPhotographer;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 22;
    FirebaseStorage storage;
    StorageReference storageReference;
    private String urlOfImage;
    private FirebaseFirestore firebaseFirestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_wall);

        wallpaper = findViewById(R.id.mainWallpaper);
        wallpaperTitle = findViewById(R.id.ETtitleWallpaper);
        photographer = findViewById(R.id.ETPhotografer);
        wallpaperLink = findViewById(R.id.wallpaperLink);
        saveButton = findViewById(R.id.saveImageBtn);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        firebaseFirestore = FirebaseFirestore.getInstance();

        if (wallpaperTitle.getText().toString().isEmpty()) {
            saveButton.setBackground(getResources().getDrawable(R.drawable.bttn_ui_not_enabled));
        }

        wallpaperTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                saveButton.setBackground(getResources().getDrawable(R.drawable.bttn_ui_not_enabled));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                saveButton.setBackground(getResources().getDrawable(R.drawable.bttn_ui));
            }

            @Override
            public void afterTextChanged(Editable s) {
                saveButton.setBackground(getResources().getDrawable(R.drawable.bttn_ui));
                saveButton.setEnabled(true);
            }
        });

        photographer.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                saveButton.setBackground(getResources().getDrawable(R.drawable.bttn_ui_not_enabled));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                saveButton.setBackground(getResources().getDrawable(R.drawable.bttn_ui));
            }

            @Override
            public void afterTextChanged(Editable s) {
                saveButton.setBackground(getResources().getDrawable(R.drawable.bttn_ui));
                saveButton.setEnabled(true);
            }
        });

        wallpaper.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectAnImage();
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stringWallpaperTitle = wallpaperTitle.getText().toString();
                stringWallpaperPhotographer = photographer.getText().toString();

                if (stringWallpaperTitle.isEmpty() && stringWallpaperPhotographer.isEmpty()) {
                    Snackbar.make(findViewById(android.R.id.content), "Please Provide some info on wallpaper", Snackbar.LENGTH_LONG);
                } else {
                    //Snackbar.make(findViewById(android.R.id.content), stringWallpaperTitle + "  " + stringWallpaperPhotographer, Snackbar.LENGTH_LONG);
                    if (filePath != null) {
                        uploadImageWallpaper();
                    }
                }
            }
        });

    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode,
                resultCode,
                data);

        if (requestCode == PICK_IMAGE_REQUEST
                && resultCode == RESULT_OK
                && data != null
                && data.getData() != null) {

            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore
                        .Images
                        .Media
                        .getBitmap(
                                getContentResolver(),
                                filePath);
                wallpaper.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void selectAnImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(
                Intent.createChooser(
                        intent,
                        "Select Image from here..."),
                PICK_IMAGE_REQUEST);
    }


    public void backToMainActivity(View view) {
        onBackPressed();
    }

    private void uploadImageWallpaper() {

            final ProgressDialog progressDialog= new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            // Defining the child of storageReference
            final String titleWallpaper = wallpaperTitle.getText().toString();
            final String photographerName = photographer.getText().toString();
            final StorageReference ref = storageReference.child("wallpaper/"+ titleWallpaper+UUID.randomUUID().toString());

            ref.putFile(filePath).addOnSuccessListener(
                            new OnSuccessListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onSuccess(
                                        UploadTask.TaskSnapshot taskSnapshot) {
                                    //uploadImageThumbnail(titleWallpaper);
                                    ref.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                        @Override
                                        public void onSuccess(Uri uri) {
                                            urlOfImage = uri.toString();
                                            addDataToFirestore(urlOfImage,titleWallpaper,photographerName);
                                        }
                                    });
                                    //Snackbar.make(findViewById(android.R.id.content),urlOfImage,Snackbar.LENGTH_LONG);
                                    progressDialog.dismiss();
                                    //Toast.makeText(AddNewWall.this,"Image Uploaded!!",Toast.LENGTH_SHORT).show();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AddNewWall.this, "Failed " + e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }).addOnProgressListener(
                            new OnProgressListener<UploadTask.TaskSnapshot>() {
                                @Override
                                public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                                    double progress= (100.0* taskSnapshot.getBytesTransferred()/ taskSnapshot.getTotalByteCount());
                                    progressDialog.setMessage("Uploaded " + (int) progress + "%");
                                }
                            });


        }

    private void addDataToFirestore(String urlOfImage, String titleWallpaper, String photographerName) {

        Map<String, String> wallMap = new HashMap<>();
        wallMap.put("wallpaper_title", titleWallpaper);
        wallMap.put("name_photographer", photographerName);
        wallMap.put("wallpaper_link", urlOfImage);

        firebaseFirestore.collection("Wallpapers").add(wallMap).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
            @Override
            public void onSuccess(DocumentReference documentReference) {
                Snackbar.make(findViewById(android.R.id.content),"Added Successfully",Snackbar.LENGTH_SHORT);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                Snackbar.make(findViewById(android.R.id.content),"Failed !",Snackbar.LENGTH_SHORT);
            }
        });

    }
}


