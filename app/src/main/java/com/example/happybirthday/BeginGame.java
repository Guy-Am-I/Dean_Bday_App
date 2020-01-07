package com.example.happybirthday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
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

public class BeginGame extends AppCompatActivity {

    //TODO delete later
    public static int questionID = 0;
    public static final int TOTAL_TOPICS = 13;
    public static final double POLS_PER_QUESTION = 0.5;
    public static int TOTAL_QUESTIONS = 0;

    public static final int POP_CULTURE_ID = 91;
    public static final int GAMES_ID = 92;
    public static final int QUOTES_ID = 93;
    public static final int HISTORICAL_FIGURES_ID = 94;
    public static final int INVENTIONS_ID = 95;
    public static final int MATH_PROBLEMS_ID = 96;
    public static final int COMPUTER_SCIENCE_ID = 97;
    public static final int GEOGRAPHY_ID = 98;
    public static final int MUSIC_ID = 99;
    public static final int SPORTS_ID = 100;
    public static final int BOOKS_ID = 101;
    public static final int WILDLIFE_ID = 102;
    public static final int GUY_ID = 9797;

    public static Topic[] topics;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin_game);

        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(getResources().getColor(R.color.main_app_darkish)));
    }

    public static void showPopup(String text, LayoutInflater inflater){
        View popupView = inflater.inflate(R.layout.clue_popup, null);
        TextView clue_text = popupView.findViewById(R.id.topic_clue);
        clue_text.setText(text);

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

    public void showPopupClue(View view){
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);

        showPopup(getString(R.string.initial_question_1), inflater);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_status_menu, menu);
        menu.findItem(R.id.binary_digit_for_num).setVisible(false);
        return true;
    }



    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int itemID = item.getItemId();
        switch(itemID){
            case R.id.menu_pols:
                showPopup(getString(R.string.pol_info), (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE));
                break;
            case R.id.menu_slaps:
                showPopup(getString(R.string.slaps_info), (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE));
                break;
            case R.id.questions_answered:
                showPopup(getString(R.string.total_answered_info), (LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE));
                break;
        }
        return true;
    }

    public void goToTopics(View view){
        initTopics();
        startActivity(new Intent(this, TopicsActivity.class));
    }

    public void initTopics(){
        questionID = 0;
        TOTAL_QUESTIONS = 0;

        topics = new Topic[BeginGame.TOTAL_TOPICS];

        String[] topic_titles = getResources().getStringArray(R.array.topics);
        String[] topic_clues = getResources().getStringArray(R.array.clues);
        String[] topic_res = getResources().getStringArray(R.array.topic_image_res);

        int id;
        Question[] topic_questions;

        for(int i = 0;i < BeginGame.TOTAL_TOPICS;i++){
            switch (i){
                case 0:
                    id = BeginGame.POP_CULTURE_ID;
                    topic_questions = fill_topic_questions(R.array.pop_culture_questions,
                            R.array.pop_culture_questions_answers, R.array.pop_culture_questions_info, id);
                    break;
                case 1:
                    id = BeginGame.BOOKS_ID;
                    topic_questions = fill_topic_questions(R.array.books_questions,
                            R.array.books_questions_answers, R.array.books_questions_info, id);
                    break;
                case 2:
                    id = BeginGame.MATH_PROBLEMS_ID;
                    topic_questions = fill_topic_questions(R.array.math_questions,
                            R.array.math_questions_answers, R.array.math_questions_info, id);
                    break;
                case 3:
                    id = BeginGame.MUSIC_ID;
                    topic_questions = fill_topic_questions(R.array.music_questions,
                            R.array.music_questions_answers, R.array.music_questions_info, id);
                    break;
                case 4:
                    id = BeginGame.GEOGRAPHY_ID;
                    topic_questions = fill_topic_questions(R.array.geography_questions,
                            R.array.geography_questions_answers, R.array.geography_questions_info, id);
                    break;
                case 5:
                    id = BeginGame.INVENTIONS_ID;
                    topic_questions = fill_topic_questions(R.array.invention_questions,
                            R.array.invention_questions_answers, R.array.invention_questions_info, id);
                    break;
                case 6:
                    id = BeginGame.SPORTS_ID;
                    topic_questions = fill_topic_questions(R.array.sports_questions,
                            R.array.sports_questions_answers, R.array.sports_questions_info, id);
                    break;
                case 7:
                    id = BeginGame.COMPUTER_SCIENCE_ID;
                    topic_questions = fill_topic_questions(R.array.computer_questions,
                            R.array.computer_questions_answers, R.array.computer_questions_info, id);
                    break;
                case 8:
                    id = BeginGame.HISTORICAL_FIGURES_ID;
                    topic_questions = fill_topic_questions(R.array.historical_questions,
                            R.array.historical_questions_answers, R.array.historical_questions_info, id);
                    break;
                case 9:
                    id = BeginGame.QUOTES_ID;
                    topic_questions = fill_topic_questions(R.array.quotes_questions,
                            R.array.quotes_questions_answers, R.array.quotes_questions_info, id);
                    break;
                case 10:
                    id = BeginGame.GAMES_ID;
                    topic_questions = fill_topic_questions(R.array.games_questions,
                            R.array.games_questions_answers, R.array.games_questions_info, id);
                    break;
                case 11:
                    id = BeginGame.WILDLIFE_ID;
                    topic_questions = fill_topic_questions(R.array.games_questions,
                            R.array.games_questions_answers, R.array.games_questions_info, id);
                    break;
                case 12:
                    id = BeginGame.GUY_ID;
                    topic_questions = fill_topic_questions(R.array.guy_questions,
                            R.array.guy_questions_answers, R.array.guy_questions_info, id);
                    break;
                default:
                    id = -1;
                    topic_questions = null;
                    break;
            }
            topics[i] = new Topic(topic_titles[i], topic_questions, topic_res[i], topic_clues[i], id);
        }
    }
    private Question[] fill_topic_questions(int questions_res, int questions_answers_res, int questions_info_res,
                                            int topic_id){
        String[] questions, questions_answers, questions_info;

        questions = getResources().getStringArray(questions_res);
        questions_answers = getResources().getStringArray(questions_answers_res);
        questions_info = getResources().getStringArray(questions_info_res);

        Question[] topic_questions = new Question[questions.length];

        for(int j = 0; j < questions.length; j++){
            topic_questions[j] = new Question(questions[j], questions_answers[j], false,
                    questions_info[j], topic_id, BeginGame.questionID);
            BeginGame.questionID++;
        }
        Log.d("begin", "TOtal questions:" + TOTAL_QUESTIONS);
        TOTAL_QUESTIONS += questions.length;

        return topic_questions;
    }

}
