package com.example.quuizzy;

import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.quuizzy.data.AnswerListAsyncResponse;
import com.example.quuizzy.data.QuestionBank;
import com.example.quuizzy.model.Question;
import com.example.quuizzy.model.SCore;
import com.example.quuizzy.utility.Prefs;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.List;

public class trivia extends AppCompatActivity implements View.OnClickListener {

    private TextView questionTextView;
    private TextView questionCounterTextView;
    private Button trueButton;
    private Button falseButton;
    private ImageButton prevButton;
    private ImageButton nextButton;
    private TextView currentScoreText;
    private TextView highScoreText;
    private int scoreCounter = 0;
    private int currentQuestionIndex =0;
    private List<Question> questionList;
    private Prefs pref;
    private SCore score;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);


        score = new SCore();
        pref= new Prefs(trivia.this);

        nextButton = findViewById(R.id.nextButton);
        prevButton=findViewById(R.id.prevButton);
        currentScoreText=findViewById(R.id.currentscoretext);
        highScoreText=findViewById(R.id.highScoretext);
        falseButton=findViewById(R.id.falsebutton);
        trueButton=findViewById(R.id.truebutton);
        questionTextView=findViewById(R.id.QuestionText);
        nextButton=findViewById(R.id.nextButton);
        questionCounterTextView=findViewById(R.id.Counter_text);

        nextButton.setOnClickListener(this);
        prevButton.setOnClickListener(this);
        trueButton.setOnClickListener(this);
        falseButton.setOnClickListener(this);



        highScoreText.setText(MessageFormat.format("High Score = {0}",
                String.valueOf(pref.getHighSCore())));
        currentQuestionIndex=pref.getState();


        questionList = new QuestionBank().getQuestions(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {
                questionTextView.setText(questionArrayList.get(currentQuestionIndex).getAnswer());
                questionCounterTextView.setText(MessageFormat.format("{0}/{1}",
                        currentQuestionIndex,questionList.size()));



            }
        });

    }

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.prevButton:
                if (currentQuestionIndex !=0) {
                    currentQuestionIndex = (currentQuestionIndex - 1) % questionList.size();
                    updateQuestion();
                }
                break;
            case R.id.nextButton:
                currentQuestionIndex= (currentQuestionIndex+1)%questionList.size();
                updateQuestion();
                break;
            case R.id.truebutton:
                checkAnswer(true);
                updateQuestion();
                break;
            case R.id.falsebutton:
                checkAnswer(false);
                updateQuestion();
                break;

        }

    }

    private void addPoints(){
        scoreCounter+=100;
        score.setScore(scoreCounter);
        currentScoreText.setText(MessageFormat.format("Current Score = {0}", String.valueOf(score.getScore())));

    }

    private void deductPoints(){
        if(scoreCounter>0){
            scoreCounter-=100;
            score.setScore(scoreCounter);
            currentScoreText.setText(MessageFormat.format("Current Score = {0}", String.valueOf(score.getScore())));

        }
    }

    private void checkAnswer(boolean userChoice) {
        boolean answer = questionList.get(currentQuestionIndex).isAnswerTrue();
        if(answer==userChoice){
            Toast.makeText(getApplicationContext(),"correct",Toast.LENGTH_SHORT).show();
            fade_animation();


        }
        else{
            shake_animation();

            Toast.makeText(getApplicationContext(),"incorrect",Toast.LENGTH_SHORT).show();


        }
    }

    private void shake_animation() {
        final CardView cardView= findViewById(R.id.cardView);
        Animation shake = AnimationUtils.loadAnimation(this,R.anim.shake_animation);
        cardView.setAnimation(shake);
        shake.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.RED);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);
                next();
                deductPoints();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void fade_animation() {
        final CardView cardView = findViewById(R.id.cardView);
        AlphaAnimation alphaAnimation = new AlphaAnimation(1.0f,0.0f);
        alphaAnimation.setDuration(350);
        alphaAnimation.setRepeatCount(1);
        alphaAnimation.setRepeatMode(Animation.REVERSE);

        cardView.setAnimation(alphaAnimation);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {
                cardView.setCardBackgroundColor(Color.GREEN);
            }

            @Override
            public void onAnimationEnd(Animation animation) {
                cardView.setCardBackgroundColor(Color.WHITE);
                next();
                addPoints();

            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
    }

    private void next() {
        currentQuestionIndex= (currentQuestionIndex +1)% questionList.size();
        updateQuestion();

    }


    private void updateQuestion() {

        String que = questionList.get(currentQuestionIndex).getAnswer();
        questionTextView.setText(que);
        questionCounterTextView.setText(MessageFormat.format("{0}/{1}",
                currentQuestionIndex,questionList.size()));
    }
    @Override
    protected void onPause(){
        pref.setState(currentQuestionIndex);
        pref.saveHighScore(score.getScore());
        super.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        rotateAnimation();
    }

    private void rotateAnimation() {
        Animation rotate = AnimationUtils.loadAnimation(this,R.anim.rotate);
        final ImageView imageView = findViewById(R.id.imageView);
        imageView.setAnimation(rotate);
    }
}