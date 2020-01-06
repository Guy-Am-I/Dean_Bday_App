package com.example.happybirthday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MotionEvent;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class Topic_Questions extends AppCompatActivity implements QuestionsListAdapter.QuestionsListAdapterSubmitHandler {

    private RecyclerView questionsRV;
    private QuestionsListAdapter mAdapter;
    private Question[] topic_questions;
    private int parent_topic_id;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic__questions);
        //TODO get data from shared preferences whenever questions load (to see which are answered)

        questionsRV = (RecyclerView) findViewById(R.id.questions_list);

        Intent topicThatStarted = getIntent();
        parent_topic_id = topicThatStarted.getIntExtra("TOPIC_ID", -1);
        parseStringArraysToQuestion(topicThatStarted);

        mAdapter = new QuestionsListAdapter(this);
        questionsRV.setAdapter(mAdapter);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        questionsRV.setLayoutManager(linearLayoutManager);
        questionsRV.setHasFixedSize(true);

        mAdapter.setQuestionsData(topic_questions);

    }
    @Override
    protected void onResume() {
        super.onResume();
        getQuestionsAnsweredSavedData();
        Log.d("resuming questions activity", "about to set DATA");
        mAdapter.setQuestionsData(topic_questions);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveAllData();
    }

    public void parseStringArraysToQuestion(Intent topicIntent){
        String[] questions = topicIntent.getStringArrayExtra("QUESTIONS");
        String[] answers = topicIntent.getStringArrayExtra("ANSWERS");
        String[] infos = topicIntent.getStringArrayExtra("INFOS");
        int[] ids = topicIntent.getIntArrayExtra("IDS");


        topic_questions = new Question[questions.length];

        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);

        for(int i = 0; i < questions.length; i++){
            Question question = new Question(questions[i], answers[i],sharedPreferences.getBoolean("Q"+ids[i], false), infos[i], parent_topic_id, ids[i]);
            topic_questions[i] = question;
        }

    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_status_menu, menu);
        int num_digit; //666 for now :)
        switch (parent_topic_id){
            case BeginGame.FANTASY_ID:
                num_digit = 0;
                break;
            case BeginGame.GAMES_ID:
                num_digit = 0;
                break;
            case BeginGame.SUPERHEROES_ID:
                num_digit = 1;
                break;
            case BeginGame.HISTORICAL_FIGURES_ID:
                num_digit = 0;
                break;
            case BeginGame.INVENTIONS_ID:
                num_digit = 1;
                break;
            case BeginGame.MATH_PROBLEMS_ID:
                num_digit = 0;
                break;
            case BeginGame.COMPUTER_SCIENCE_ID:
                num_digit = 0;
                break;
            case BeginGame.GEOGRAPHY_ID:
                num_digit = 1;
                break;
            case BeginGame.MUSIC_ID:
                num_digit = 1;
                break;
            case BeginGame.SPORTS_ID:
                num_digit = 0;
                break;
            case BeginGame.BOOKS_ID:
                num_digit = 1;
                break;
            case BeginGame.GUY_ID:
                num_digit = 0;
                break;
            default:
                num_digit = -1;
                break;
        }
        menu.findItem(R.id.binary_digit_for_num).setTitle("" + num_digit);
        return true;
    }

    @Override
    public void submitAnswer(boolean isCorrect, String info) {
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.clue_popup, null);
        TextView clue_text = popupView.findViewById(R.id.topic_clue);

        if(isCorrect) {
            clue_text.setText("Good Job! \n Did you Know: " + info);
        }
        else {
            clue_text.setText("I am sorry that is incorrect");
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
    public void saveData(int questionID, int topicID) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();

        int topic_num_answered = sharedPref.getInt(Integer.toString(topicID), 0);
        editor.putInt(Integer.toString(topicID), topic_num_answered + 1);

        editor.putBoolean("Q"+questionID, true);
        editor.commit();

        invalidateOptionsMenu();
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        String total_questions = "" + topic_questions.length;
        int answered = 0;
        for(int i = 0; i < topic_questions.length; i++) {
            if(topic_questions[i].getIsAnswered()){
                answered++;
            }
        }
        String questions_answered = "" + answered;
        menu.findItem(R.id.questions_answered).setTitle(questions_answered + "/" + total_questions);
        return super.onPrepareOptionsMenu(menu);
    }

    public void getQuestionsAnsweredSavedData(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        for(int i = 0; i < topic_questions.length; i++){
            int questionID = topic_questions[i].getId();
            boolean isAnswered = sharedPreferences.getBoolean("Q"+questionID, false);
            topic_questions[i].setAnswered(isAnswered);
        }
        invalidateOptionsMenu();
    }
    public void saveAllData(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();

        for(int i = 0; i < topic_questions.length; i++){
            int questionID = topic_questions[i].getId();
            editor.putBoolean("Q"+questionID, topic_questions[i].getIsAnswered());
        }
        editor.commit();
    }

}
