package com.example.quuizzy;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.quuizzy.data.AnswerListAsyncResponse;
import com.example.quuizzy.data.QuestionBank;
import com.example.quuizzy.model.Question;

import java.util.ArrayList;
import java.util.List;

public class trivia extends AppCompatActivity {

    private TextView questionTextView;
    private TextView questionCounterTextView;
    private Button trueButton;
    private Button falseButton;
    private ImageButton prevButton;
    private ImageButton nextButton;
    private TextView currentScoreText;
    private TextView highScoreText;
    private int scoreCounter = 0;
    private int currentQuestionIndex ;
    private List<Question> questionList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trivia);

        nextButton = findViewById(R.id.nextButton);
        prevButton=findViewById(R.id.prevButton);
        currentScoreText=findViewById(R.id.currentscoretext);
        highScoreText=findViewById(R.id.highScoretext);
        falseButton=findViewById(R.id.falsebutton);
        trueButton=findViewById(R.id.truebutton);
        nextButton=findViewById(R.id.nextButton);

        questionList = new QuestionBank().getQuestions(new AnswerListAsyncResponse() {
            @Override
            public void processFinished(ArrayList<Question> questionArrayList) {
                questionTextView.setText(questionArrayList.get(currentQuestionIndex).getAnswer());



            }
        });

    }
}