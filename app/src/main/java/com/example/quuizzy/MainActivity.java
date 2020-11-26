package com.example.quuizzy;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.quuizzy.controller.AppController;
import com.example.quuizzy.controller.ConnectivityReciever;
import com.example.quuizzy.data.QuestionBank;

public class MainActivity extends AppCompatActivity
implements ConnectivityReciever.ConnectivityReceiverListener {

private Button getStarted;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        getStarted = findViewById(R.id.getStartedButton);
        getStarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkConnection();
            }
        });





    }

    private void checkConnection() {
        boolean isConnected = ConnectivityReciever.isConnected();
        if (isConnected){
            startActivity(new Intent(MainActivity.this,trivia.class));
            finish();
    }
        else{
            AlertDialog.Builder alertDialog = new AlertDialog.Builder(this);
            alertDialog.setTitle("No Internet Connection");
            alertDialog.setMessage("Please Turn on Internet Connection to Continue" );
            alertDialog.setNegativeButton("retry", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {
                    checkConnection();
                    dialog.dismiss();
                }
            });
            AlertDialog alertDialog1 = alertDialog.create();
            alertDialog1.show();
        }





    }

    @Override
    protected void onResume() {
        super.onResume();

        // register connection status listener
        AppController.getInstance().setConnectivityListener(this);
    }



    @Override
    public void onNetworkConnectionChanged(boolean isConnected) {
        checkConnection();

    }
}

