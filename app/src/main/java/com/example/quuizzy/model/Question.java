package com.example.quuizzy.model;

public class Question {
    private String answer;
    private boolean answerTrue;
    private  int code ;

    public Question(String answer, boolean answerTrue,int code) {
        this.answer = answer;
        this.answerTrue = answerTrue;
        this.code = code;

    }

    public Question() {
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public boolean isAnswerTrue() {
        return answerTrue;
    }

    public void setAnswerTrue(boolean answerTrue) {
        this.answerTrue = answerTrue;
    }

    @Override
    public String toString() {
        return "Question{" +
                "answer='" + answer + '\'' +
                ", answerTrue=" + answerTrue +
                '}';
    }
}
