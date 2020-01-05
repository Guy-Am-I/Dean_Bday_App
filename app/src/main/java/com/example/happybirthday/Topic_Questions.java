package com.example.happybirthday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.EditText;

public class Topic_Questions extends AppCompatActivity {

    private RecyclerView questionsRV;
    private QuestionsListAdapter mAdapter;
    private QuestionManager manager;
    private Question[] topic_questions;

    private EditText guess;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic__questions);

        questionsRV = (RecyclerView) findViewById(R.id.questions_list);

        Intent topicThatStarted = getIntent();
        parseStringArraysToQuestion(topicThatStarted);

        Log.d("Questions", "Initializing adapter");
        mAdapter = new QuestionsListAdapter(topic_questions);
        questionsRV.setAdapter(mAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        questionsRV.setLayoutManager(linearLayoutManager);
        questionsRV.setHasFixedSize(true);

    }
    public void parseStringArraysToQuestion(Intent topicIntent){
        Log.d("Questions", "parsing intent extras");
        String[] questions = topicIntent.getStringArrayExtra("QUESTIONS");
        String[] answers = topicIntent.getStringArrayExtra("ANSWERS");
        boolean[] is_answered = topicIntent.getBooleanArrayExtra("IS_ANSWERED");

        topic_questions = new Question[questions.length];

        for(int i = 0; i < questions.length; i++){
            Question question = new Question(questions[i], answers[i], is_answered[i]);
            topic_questions[i] = question;
        }
    }
    public void submit_answer(View view){
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_status_menu, menu);
        return true;
    }
}
