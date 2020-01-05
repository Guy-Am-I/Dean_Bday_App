package com.example.happybirthday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class TopicsActivity extends AppCompatActivity implements TopicsListAdapter.TopicsListAdapterOnClickHandler {

    public final int NUMBER_OF_TOPICS = 11;
    private final String ANSWER_ALL = "You must answer all questions to see the clue";
    private RecyclerView topics_list;
    private TopicsListAdapter mAdapter;
    private Topic[] topics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);

        topics_list = (RecyclerView) findViewById(R.id.topics_list);
        topics_list.setHasFixedSize(true);


        topics = new Topic[NUMBER_OF_TOPICS];
        initialiseTopics();


        mAdapter = new TopicsListAdapter(this);
        topics_list.setAdapter(mAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        topics_list.setLayoutManager(layoutManager);

        mAdapter.setTopicData(topics);


    }

    @Override
    public void showClue(String clue, int answered, int total) {
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.clue_popup, null);
        TextView clue_text = popupView.findViewById(R.id.topic_clue);
        if(answered == total) {
            clue_text.setText(clue);
        }
        else {
            clue_text.setText(ANSWER_ALL);
        }

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;

        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, true);
        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(popupView, Gravity.CENTER, 0, 0);

        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    @Override
    public void onClick(Question[] questions) {
        //TODO fix this bit of code to pass question[] object instead
        Intent startQuestionsActivity = new Intent(this, Topic_Questions.class);
        //pass array of strings including questions
        String[] question_array = new String[questions.length];
        String[] answer_array = new String[questions.length];
        boolean[] is_answered_array = new boolean[questions.length];

        for(int i = 0; i<questions.length; i++) {
            question_array[i] = questions[i].getQuestion();
            answer_array[i] = questions[i].getAnswer();
            is_answered_array[i] = questions[i].getIsAnswered();
        }
        startQuestionsActivity.putExtra("QUESTIONS", question_array);
        startQuestionsActivity.putExtra("ANSWERS", answer_array);
        startQuestionsActivity.putExtra("IS_ANSWERED", is_answered_array);


        startActivity(startQuestionsActivity);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_status_menu, menu);
        return true;
    }

    private void initialiseTopics(){
        //change to receive questions from resource
        String[] topic_titles = getResources().getStringArray(R.array.topics);
        String[] topic_clues = getResources().getStringArray(R.array.clues);
        int[] topic_res = getResources().getIntArray(R.array.topic_image_res);

        for(int i = 0; i < topic_titles.length; i++){
            Question[] topic_questions = {new Question("q"+i, "a="+i), new Question("q"+i, "a="+i), new Question("q"+i, "a="+i), new Question("q"+i, "a="+i), new Question("q"+i, "a="+i), new Question("q"+i, "a="+i)};
            topics[i] = new Topic(topic_titles[i], topic_questions, topic_res[i], topic_clues[i]);
        }
    }
}
