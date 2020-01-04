package com.example.happybirthday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.Menu;

public class Topic_Questions extends AppCompatActivity {

    private RecyclerView questionsRV;
    private QuestionsListAdapter mAdapter;
    private QuestionManager manager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic__questions);

        questionsRV = (RecyclerView) findViewById(R.id.questions_list);

        Question[] questions = {new Question("1", "2", "3"), new Question("!", "2", "3")};
        mAdapter = new QuestionsListAdapter(questions);
        questionsRV.setAdapter(mAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        questionsRV.setLayoutManager(linearLayoutManager);
        questionsRV.setHasFixedSize(true);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_status_menu, menu);
        return true;
    }
}
