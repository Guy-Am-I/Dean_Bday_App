package com.example.happybirthday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class BeginGame extends AppCompatActivity {

    //TODO delete later
    public static int questionID = 0;
    public static final int TOTAL_TOPICS = 12;

    public static final int FANTASY_ID = 91;
    public static final int GAMES_ID = 92;
    public static final int SUPERHEROES_ID = 93;
    public static final int HISTORICAL_FIGURES_ID = 94;
    public static final int INVENTIONS_ID = 95;
    public static final int MATH_PROBLEMS_ID = 96;
    public static final int COMPUTER_SCIENCE_ID = 97;
    public static final int GEOGRAPHY_ID = 98;
    public static final int MUSIC_ID = 99;
    public static final int SPORTS_ID = 100;
    public static final int BOOKS_ID = 101;
    public static final int GUY_ID = 9797;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_begin_game);
        //TODO create one big array of questions_is_answered with known index's where each topic begins

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_status_menu, menu);
        menu.findItem(R.id.binary_digit_for_num).setVisible(false);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.balance:
                Log.d("balance", "get balance");
                break;
            case R.id.questions_answered:
                Log.d("answered", "get total answered");
                break;
            default:
                Log.d("def", "reached default");
        }
        return true;
    }

    public void goToTopics(View view){
        startActivity(new Intent(this, TopicsActivity.class));
    }
}
