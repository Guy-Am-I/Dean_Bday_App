package com.example.happybirthday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.LinearLayout;

public class TopicsActivity extends AppCompatActivity {

    private RecyclerView topics_list;
    private TopicsListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);

        topics_list = (RecyclerView) findViewById(R.id.topics_list);
        topics_list.setHasFixedSize(true);

        mAdapter = new TopicsListAdapter(getResources().getStringArray(R.array.topics));
        topics_list.setAdapter(mAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        topics_list.setLayoutManager(layoutManager);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_status_menu, menu);
        return true;
    }
}
