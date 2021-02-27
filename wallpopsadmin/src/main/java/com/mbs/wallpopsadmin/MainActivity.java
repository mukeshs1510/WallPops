package com.mbs.wallpopsadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String title[];
    AdaptorRecycler adapter;
    int[] image = {R.drawable.wallpaperr,R.drawable.download};
    private FirebaseAuth mAuth;
    private ImageView logoutBtn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);
        logoutBtn = findViewById(R.id.logoutBtn);
        mAuth = FirebaseAuth.getInstance();

        logoutBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                logOut();
                startActivity(new Intent(getApplicationContext(), LoginActivity.class));
                finish();
            }
        });

        title = getResources().getStringArray(R.array.title);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        adapter = new AdaptorRecycler(MainActivity.this,title,image);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }

    private void logOut() {
        SharedPreferences sharedPreferences = getSharedPreferences("com.mbs.wallpops_admin_login_status", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("login_status","off");
        editor.commit();
    }

}

