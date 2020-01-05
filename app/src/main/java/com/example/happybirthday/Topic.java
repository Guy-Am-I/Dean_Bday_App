package com.example.happybirthday;

public class Topic {

    private String title;
    private Question[] questions;
    private int image_resource;
    private String clue;
    private int questions_answered;


    public Topic(String title, Question[] questions, int res, String clue){
        this.title = title;
        this.questions = questions;
        this.image_resource = res;
        this.clue = clue;
    }

    public Question[] getQuestions() {
        return questions;
    }

    public String getTitle() {
        return title;
    }

    public int getImage_resource() {
        return image_resource;
    }

    public String getClue() {
        return clue;
    }

    public int getQuestions_answered() {
        return questions_answered;
    }
}
