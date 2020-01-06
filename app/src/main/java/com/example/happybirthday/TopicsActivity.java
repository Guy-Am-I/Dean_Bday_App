package com.example.happybirthday;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
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
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;

public class TopicsActivity extends AppCompatActivity implements TopicsListAdapter.TopicsListAdapterOnClickHandler {

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

        initialiseTopics();


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
    public boolean onPrepareOptionsMenu(Menu menu) {
        int answered = 0;
        for(int i =0;i<BeginGame.TOTAL_TOPICS; i++) {
            answered += topics[i].getQuestions_answered();
        }
        String questions_answered_formatted = "" + answered + "/" + getString(R.string.total_number_of_questions);
        menu.findItem(R.id.questions_answered).setTitle(questions_answered_formatted);
        return super.onPrepareOptionsMenu(menu);
    }

    @Override
    protected void onPause() {
        super.onPause();
        saveTopicsData();
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

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_status_menu, menu);
        menu.findItem(R.id.binary_digit_for_num).setVisible(false);
        return true;
    }

    private void initialiseTopics(){
        topics = new Topic[BeginGame.TOTAL_TOPICS];


        String[] topic_titles = getResources().getStringArray(R.array.topics);
        String[] topic_clues = getResources().getStringArray(R.array.clues);
        String[] topic_res = getResources().getStringArray(R.array.topic_image_res);

        int id;
        Question[] topic_questions;

        for(int i = 0;i < BeginGame.TOTAL_TOPICS;i++){
            switch (i){
                case 0:
                    id = BeginGame.FANTASY_ID;
                    topic_questions = fill_topic_questions(R.array.fantasy_questions,
                            R.array.fantasy_questions_answers, R.array.fantasy_questions_info, id);
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
                    id = BeginGame.SUPERHEROES_ID;
                    topic_questions = fill_topic_questions(R.array.superhero_questions,
                            R.array.superhero_questions_answers, R.array.superhero_questions_info, id);
                    break;
                case 10:
                    id = BeginGame.GAMES_ID;
                    topic_questions = fill_topic_questions(R.array.games_questions,
                            R.array.games_questions_answers, R.array.games_questions_info, id);
                    break;
                case 11:
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
        return topic_questions;
    }

    public void getTopicsSavedData(){
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        int id;
        for(int i = 0; i < BeginGame.TOTAL_TOPICS; i++){
            switch (i){
                case 0:
                    id = BeginGame.FANTASY_ID;
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
                    id = BeginGame.SUPERHEROES_ID;
                    break;
                case 10:
                    id = BeginGame.GAMES_ID;
                    break;
                case 11:
                    id = BeginGame.GUY_ID;
                    break;
                default:
                    id = -1;
                    break;
            }
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
            switch (i){
                case 0:
                    id = BeginGame.FANTASY_ID;
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
                    id = BeginGame.SUPERHEROES_ID;
                    break;
                case 10:
                    id = BeginGame.GAMES_ID;
                    break;
                case 11:
                    id = BeginGame.GUY_ID;
                    break;
                default:
                    id = -1;
            }

            editor.putInt(Integer.toString(id), topics[i].getQuestions_answered());
            editor.commit();
        }
    }

}
