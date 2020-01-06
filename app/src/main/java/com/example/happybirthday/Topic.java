package com.example.happybirthday;

public class Topic {

    private String title;
    private Question[] questions;
    private String img_path;
    private String clue;
    private int questions_answered;
    private int id;


    public Topic(String title, Question[] questions, String path, String clue, int id){
        this.title = title;
        this.questions = questions;
        this.img_path = path;
        this.clue = clue;
        this.questions_answered = 0;
        this.id = id;
    }

    public void setQuestions_answered(int questions_answered) {
        this.questions_answered = questions_answered;
    }

    public Question[] getQuestions() {
        return questions;
    }

    public String getTitle() {
        return title;
    }

    public String getImage_resource() {
        return img_path;
    }

    public String getClue() {
        return clue;
    }

    public int getQuestions_answered() {
        return questions_answered;
    }

    public int getId() {
        return id;
    }
}
