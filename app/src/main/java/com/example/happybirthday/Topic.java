package com.example.happybirthday;

public class Topic {

    private String title;
    private Question[] questions;


    public Topic(String title, Question[] questions){
        this.title = title;
        this.questions = questions;
    }

    public Question[] getQuestions() {
        return questions;
    }

    public String getTitle() {
        return title;
    }
}
