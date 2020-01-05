package com.example.happybirthday;

public class Question {
    private String question;
    private String answer;
    private boolean answered;

    public Question(String question, String answer) {
        this.question = question;
        this.answer = answer;
        this.answered = false;
    }

    public Question(String question, String answer, boolean is_answered) {
        this.question = question;
        this.answer = answer;
        this.answered = is_answered;
    }

    public String getQuestion(){
        return this.question;
    }

    public String getAnswer() {
        return answer;
    }

    public boolean getIsAnswered(){
        return this.answered;
    }

    public void setAnswered(boolean answered) {
        this.answered = answered;
    }
}
