package com.example.happybirthday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

import static com.example.happybirthday.BeginGame.TOTAL_TOPICS;
import static com.example.happybirthday.BeginGame.showPopup;
import static com.example.happybirthday.BeginGame.topics;

public class TopicsActivity extends AppCompatActivity implements TopicsListAdapter.TopicsListAdapterOnClickHandler {

    private final String ANSWER_ALL = "You must answer all questions to see the clue";
    private RecyclerView topics_list;
    private TopicsListAdapter mAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_topics);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.main_app_darkish)));

        topics_list = (RecyclerView) findViewById(R.id.topics_list);
        topics_list.setHasFixedSize(true);

        mAdapter = new TopicsListAdapter(this);
        topics_list.setAdapter(mAdapter);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        topics_list.setLayoutManager(layoutManager);


    }

    @Override
    protected void onResume() {
        super.onResume();
        getTopicsSavedData();

        //update menu items to change base on questions answered
        invalidateOptionsMenu();

        mAdapter.setTopicData(topics);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveTopicsData();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_status_menu, menu);
        menu.findItem(R.id.binary_digit_for_num).setVisible(false);
        return true;
    }


    @Override
    public boolean onPrepareOptionsMenu(Menu menu) {
        int answered = 0;
        for(int i =0;i<BeginGame.TOTAL_TOPICS; i++) {
            answered += topics[i].getQuestions_answered();
        }
        String questions_answered_formatted = "" + answered + "/" + BeginGame.TOTAL_QUESTIONS;
        menu.findItem(R.id.questions_answered).setTitle(questions_answered_formatted);

        MenuItem menu_pols = menu.findItem(R.id.menu_pols);
        menu_pols.setTitle("P: " + BeginGame.POLS_PER_QUESTION*(BeginGame.TOTAL_QUESTIONS-answered));

        //show slaps based on topics clues received
        int num_clues_not_received = BeginGame.TOTAL_TOPICS - PreferenceManager.getDefaultSharedPreferences(this).getInt(getString(R.string.clues_pref_key), 0);
        MenuItem menu_slaps = menu.findItem(R.id.menu_slaps);
        menu_slaps.setTitle("S: " + num_clues_not_received);

        return super.onPrepareOptionsMenu(menu);
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
    public void showClue(String clue, int answered, int total) {
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        String text;
        if(answered == total) {
            text = clue;
        }
        else {
            text = ANSWER_ALL;
        }
        showPopup(text, inflater);
    }

    @Override
    public void onClick(Question[] questions, int topicID) {
        //TODO fix this bit of code to pass question[] object instead
        Intent startQuestionsActivity = new Intent(this, Topic_Questions.class);
        //pass array of strings including questions
        String[] question_array = new String[questions.length];
        String[] answer_array = new String[questions.length];
        String[] question_info_array = new String[questions.length];

        int[] questionIDs = new int[questions.length];

        for(int i = 0; i<questions.length; i++) {
            question_array[i] = questions[i].getQuestion();
            answer_array[i] = questions[i].getAnswer();
            questionIDs[i] = questions[i].getId();
            question_info_array[i] = questions[i].getInfo();
        }
        startQuestionsActivity.putExtra("QUESTIONS", question_array);
        startQuestionsActivity.putExtra("ANSWERS", answer_array);
        startQuestionsActivity.putExtra("IDS", questionIDs);
        startQuestionsActivity.putExtra("TOPIC_ID", topicID);
        startQuestionsActivity.putExtra("INFOS", question_info_array);

        startActivity(startQuestionsActivity);
    }

    public void getTopicsSavedData(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int id;
        for(int i = 0; i < BeginGame.TOTAL_TOPICS; i++){
            id = getIdFromIndex(i);
            int num_quest_answered = sharedPreferences.getInt(Integer.toString(id), 0);
            topics[i].setQuestions_answered(num_quest_answered);
        }
    }
    public void saveTopicsData(){
        //initialize topic #questions answered values -> 0
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        int id;

        for(int i = 0; i < BeginGame.TOTAL_TOPICS; i++){
            id = getIdFromIndex(i);
            editor.putInt(Integer.toString(id), topics[i].getQuestions_answered());

        }
        int clues_gotten = 0;
        for(int i=0; i<TOTAL_TOPICS;i++){
            if(topics[i].getQuestions_answered() == topics[i].getQuestions().length){
                clues_gotten++;
            }
        }
        //Number of CLUES / TOPICS received or answered in full
        editor.putInt(getString(R.string.clues_pref_key), clues_gotten);
        editor.commit();
    }

    public int getIdFromIndex(int i){
        int id;
        switch (i){
            case 0:
                id = BeginGame.POP_CULTURE_ID;
                break;
            case 1:
                id = BeginGame.BOOKS_ID;
                break;
            case 2:
                id = BeginGame.MATH_PROBLEMS_ID;
                break;
            case 3:
                id = BeginGame.MUSIC_ID;
                break;
            case 4:
                id = BeginGame.GEOGRAPHY_ID;
                break;
            case 5:
                id = BeginGame.INVENTIONS_ID;
                break;
            case 6:
                id = BeginGame.SPORTS_ID;
                break;
            case 7:
                id = BeginGame.COMPUTER_SCIENCE_ID;
                break;
            case 8:
                id = BeginGame.HISTORICAL_FIGURES_ID;
                break;
            case 9:
                id = BeginGame.QUOTES_ID;
                break;
            case 10:
                id = BeginGame.GAMES_ID;
                break;
            case 11:
                id = BeginGame.WILDLIFE_ID;
            case 12:
                id = BeginGame.GUY_ID;
                break;
            default:
                id = -1;
        }
        return id;
    }

}
