package com.example.happybirthday;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class WelcomeActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.welcome_activity);
        getSupportActionBar().hide();
    }

    public void goToBeginGameScreen(View view){
        Intent goToBeginGame = new Intent(this, BeginGame.class);
        startActivity(goToBeginGame);
    }
}
