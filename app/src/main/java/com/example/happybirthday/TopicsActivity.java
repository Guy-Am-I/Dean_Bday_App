package com.example.happybirthday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.widget.LinearLayout;

public class TopicsActivity extends AppCompatActivity implements TopicsListAdapter.TopicsListAdapterOnClickHandler {

    private RecyclerView topics_list;
    private TopicsListAdapter mAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);

        topics_list = (RecyclerView) findViewById(R.id.topics_list);
        topics_list.setHasFixedSize(true);

        String[] topic_titles = getResources().getStringArray(R.array.topics);
        Topic[] topics = new Topic[topic_titles.length];
        for(int i = 0; i < topic_titles.length; i++){
            Question[] topic_questions = {new Question("q"+i, "a="+i, topic_titles[i])};
            topics[i] = new Topic(topic_titles[i], topic_questions);
        }

        mAdapter = new TopicsListAdapter(this);
        topics_list.setAdapter(mAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        topics_list.setLayoutManager(layoutManager);

        mAdapter.setTopicData(topics);


    }

    @Override
    public void onClick(Question[] questions) {
        //TODO launch new questions list adapter activity with questions
        startActivity(new Intent(this, Topic_Questions.class));
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_status_menu, menu);
        return true;
    }
}
