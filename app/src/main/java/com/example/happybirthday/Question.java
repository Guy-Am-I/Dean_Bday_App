package com.example.happybirthday;

public class Question {
    private String question;
    private String answer;
    private boolean answered;
    private int id; //unique id to keep track of questions answered in sharedPref
    private int parentTopicID;
    private String info;

    public Question(String question, String answer, int parentTopicID, int id) {
        this.question = question;
        this.answer = answer;
        this.parentTopicID = parentTopicID;
        this.answered = false;
        this.info = "";
        this.id = id;
    }

    public Question(String question, String answer, boolean is_answered, String info, int parentTopicID, int id) {
        this.question = question;
        this.answer = answer;
        this.answered = is_answered;
        this.info = info;
        this.parentTopicID = parentTopicID;
        this.id = id;
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

    public String getInfo() {
        return info;
    }

    public int getParentTopicID() {
        return parentTopicID;
    }

    public int getId() {
        return id;
    }
}
