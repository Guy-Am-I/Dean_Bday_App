package com.example.happybirthday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class Topic_Questions extends AppCompatActivity implements QuestionsListAdapter.QuestionsListAdapterSubmitHandler {

    private RecyclerView questionsRV;
    private QuestionsListAdapter mAdapter;
    private Question[] topic_questions;
    private int parent_topic_id;
    public static final String INFO_INTRO = "Did You Know: ";



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topic__questions);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.main_app_darkish)));


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
            case BeginGame.POP_CULTURE_ID:
                num_digit = 0;
                break;
            case BeginGame.GAMES_ID:
                num_digit = 0;
                break;
            case BeginGame.QUOTES_ID:
                num_digit = 0;
                break;
            case BeginGame.HISTORICAL_FIGURES_ID:
                num_digit = 1;
                break;
            case BeginGame.INVENTIONS_ID:
                num_digit = 0;
                break;
            case BeginGame.MATH_PROBLEMS_ID:
                num_digit = 1;
                break;
            case BeginGame.COMPUTER_SCIENCE_ID:
                num_digit = 0;
                break;
            case BeginGame.GEOGRAPHY_ID:
                num_digit = 0;
                break;
            case BeginGame.MUSIC_ID:
                num_digit = 1;
                break;
            case BeginGame.SPORTS_ID:
                num_digit = 1;
                break;
            case BeginGame.BOOKS_ID:
                num_digit = 0;
                break;
            case BeginGame.WILDLIFE_ID:
                num_digit = 1;
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
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();
        switch(itemID){
            case R.id.menu_pols:
                BeginGame.showPopup(getString(R.string.pol_info), (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE));
                break;
            case R.id.menu_slaps:
                BeginGame.showPopup(getString(R.string.slaps_info), (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE));
                break;
            case R.id.questions_answered:
                BeginGame.showPopup(getString(R.string.total_answered_info), (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE));
                break;
        }
        return true;
    }

    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        String total_questions = "" + topic_questions.length;
        int answered = getAmountAnswered();
        String questions_answered = "" + answered;
        menu.findItem(R.id.questions_answered).setTitle(questions_answered + "/" + total_questions);


        MenuItem menu_pols = menu.findItem(R.id.menu_pols);
        menu_pols.setTitle("P: " + BeginGame.POLS_PER_QUESTION*(topic_questions.length-answered));

        MenuItem menu_slaps = menu.findItem(R.id.menu_slaps);
        int num_clues_not_received = BeginGame.TOTAL_TOPICS - PreferenceManager.getDefaultSharedPreferences(this).getInt(getString(R.string.clues_pref_key), 0);
        menu_slaps.setTitle("S: " + num_clues_not_received);
        return super.onPrepareOptionsMenu(menu);
    }


    @Override
    public void showInfo(String info) {
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        BeginGame.showPopup(INFO_INTRO + info, inflater);
    }

    @Override
    public void submitAnswer(boolean isCorrect, String info) {
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        if(isCorrect) {
            BeginGame.showPopup("Good Job! \n" +INFO_INTRO + info, inflater);
        }
        else {
            BeginGame.showPopup("I am sorry that is incorrect", inflater);
        }
    }

    public int getAmountAnswered(){
        int answered = 0;
        for(int i = 0; i < topic_questions.length; i++) {
            if(topic_questions[i].getIsAnswered()){
                answered++;
            }
        }
        return answered;
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

    @Override
    public void saveData(int questionID, int topicID) {
        SharedPreferences sharedPref = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPref.edit();

        int topic_num_answered = sharedPref.getInt(Integer.toString(topicID), 0);
        //int topic_num_answered = getAmountAnswered();
        editor.putInt(Integer.toString(topicID), topic_num_answered + 1);

        //update number of clues recieved (answered all questions in topic)
        if(topic_num_answered == topic_questions.length){
            editor.putInt(getString(R.string.clues_pref_key), sharedPref.getInt(getString(R.string.clues_pref_key), 0) + 1);
        }

        editor.putBoolean("Q"+questionID, true);
        editor.commit();

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
