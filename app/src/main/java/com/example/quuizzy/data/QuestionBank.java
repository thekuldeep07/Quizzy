package com.example.quuizzy.data;

import android.content.DialogInterface;
import android.util.Log;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.example.quuizzy.MainActivity;
import com.example.quuizzy.controller.AppController;
import com.example.quuizzy.model.Question;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class QuestionBank {
     int code =0;

    ArrayList<Question> questionArrayList = new ArrayList<>();
    private String url = " https://raw.githubusercontent.com/curiousily/simple-quiz/master/script/statements-data.json\n" +
            "\n";
    public List<Question> getQuestions(final AnswerListAsyncResponse callback){

        JsonArrayRequest jsonArrayRequest = new JsonArrayRequest(
                Request.Method.GET,
                url,
                (JSONArray) null,
                new Response.Listener<JSONArray>() {
                    @Override
                    public void onResponse(JSONArray response) {

                        for(int i =0; i< response.length();i++){
                            try {
                                Question question = new Question();
                                question.setAnswer(response.getJSONArray(i).get(0).toString());
                                question.setAnswerTrue(response.getJSONArray(i).getBoolean(1));
                                question.setCode(100);


                                questionArrayList.add(question);
                                //Log.d("question tAG ",""+response.getJSONArray(i).get(0));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }

                        if(null != callback)
                        {
                            callback.processFinished(questionArrayList);
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("gapach","gapach");
                questionArrayList = null;



            }
        }

        );

        AppController.getInstance().addToRequestQueue(jsonArrayRequest);
        return questionArrayList;
    }




}
