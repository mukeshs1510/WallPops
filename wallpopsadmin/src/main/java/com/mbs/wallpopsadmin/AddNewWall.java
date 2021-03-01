package com.mbs.wallpopsadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.snackbar.Snackbar;

public class AddNewWall extends AppCompatActivity {

    private EditText wallpaperTitle, photographer;
    private ImageView wallpaper;
    private TextView wallpaperLink;
    private Button saveButton;
    private String stringWallpaperTitle;
    private String stringWallpaperPhotographer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_wall);

        wallpaper = findViewById(R.id.mainWallpaper);
        wallpaperTitle = findViewById(R.id.ETtitleWallpaper);
        photographer = findViewById(R.id.ETPhotografer);
        wallpaperLink = findViewById(R.id.wallpaperLink);
        saveButton = findViewById(R.id.saveImageBtn);

        wallpaperTitle.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                saveButton.setBackground(getResources().getDrawable(R.drawable.bttn_ui_not_enabled));
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                photographer.addTextChangedListener(new TextWatcher() {
                    @Override
                    public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                        saveButton.setBackground(getResources().getDrawable(R.drawable.bttn_ui_not_enabled));
                    }

                    @Override
                    public void onTextChanged(CharSequence s, int start, int before, int count) {

                    }

                    @Override
                    public void afterTextChanged(Editable s) {
                        if(s.toString().isEmpty()) {

                        } else {
                            saveButton.setBackground(getResources().getDrawable(R.drawable.bttn_ui));
                        }
                    }
                });
            }
        });

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                stringWallpaperTitle = wallpaperTitle.getText().toString();
                stringWallpaperPhotographer = photographer.getText().toString();

                if (stringWallpaperTitle.isEmpty() && stringWallpaperPhotographer.isEmpty()) {
                    Snackbar.make(findViewById(android.R.id.content),"Please Provide some info on wallpaper", Snackbar.LENGTH_LONG);
                } else {
                    Snackbar.make(findViewById(android.R.id.content),stringWallpaperTitle + "  "+ stringWallpaperPhotographer, Snackbar.LENGTH_LONG);
                }

            }
        });

    }

    public void backToMainActivity(View view) {
        onBackPressed();
    }
}