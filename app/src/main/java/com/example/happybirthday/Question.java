package com.example.happybirthday;

public class Question {
    private String question;
    private String answer;
    private String topic;
    private boolean answered;

    public Question(String question, String answer, String topic) {
        this.question = question;
        this.answer = answer;
        this.topic = topic;
        this.answered = false;
    }

    public String getQuestion(){
        return this.question;
    }
}
