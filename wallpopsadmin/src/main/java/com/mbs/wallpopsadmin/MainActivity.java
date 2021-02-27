package com.mbs.wallpopsadmin;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private String title[];
    AdaptorRecycler adapter;
    int[] image = {R.drawable.wallpaperr,R.drawable.download};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.recyclerView);

        title = getResources().getStringArray(R.array.title);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        adapter = new AdaptorRecycler(MainActivity.this,title,image);
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

    }
}

