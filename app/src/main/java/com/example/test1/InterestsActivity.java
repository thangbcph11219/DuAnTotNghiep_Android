package com.example.test1;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageButton;
import android.widget.RadioButton;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import com.example.test1.R;

public class InterestsActivity extends AppCompatActivity {
    Button btnContinue;
    ImageButton imgBack;

    CheckBox ckbDulich, ckbChoiGame,ckbDocSach,ckbMuaSam;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);
        btnContinue = findViewById(R.id.btnContinue);
        imgBack = findViewById(R.id.imgBack);
        ckbDulich = findViewById(R.id.ckbDuLich);
        ckbChoiGame = findViewById(R.id.ckbChoiGame);
        ckbDocSach = findViewById(R.id.ckbDocSach);
        ckbMuaSam = findViewById(R.id.ckbMuaSam);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InterestsActivity.this,AddImageActivity.class));
                overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(InterestsActivity.this,ShowActivity.class));
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
    }

    public void ckbTapped(View view) {
        String s ="Sở thích: ";
        if(ckbDulich.isChecked()){
            ckbDulich.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.rdo_sex_on));
            ckbDulich.setTextColor(Color.WHITE);
            s+="\nDu lịch";
        }else{
            ckbDulich.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.cus_btn_sex));
            ckbDulich.setTextColor(Color.BLACK);
            s+="";
        }

        if(ckbChoiGame.isChecked()){
            ckbChoiGame.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.rdo_sex_on));
            ckbChoiGame.setTextColor(Color.WHITE);
            s+="\nChơi game";
        }else{
            ckbChoiGame.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.cus_btn_sex));
            ckbChoiGame.setTextColor(Color.BLACK);
            s+="";
        }

        if(ckbDocSach.isChecked()){
            ckbDocSach.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.rdo_sex_on));
            ckbDocSach.setTextColor(Color.WHITE);
            s+="\nĐọc sách";
        }else{
            ckbDocSach.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.cus_btn_sex));
            ckbDocSach.setTextColor(Color.BLACK);
            s+="";
        }

        if(ckbMuaSam.isChecked()){
            ckbMuaSam.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.rdo_sex_on));
            ckbMuaSam.setTextColor(Color.WHITE);
            s+="\nMua sắm";
        }else{
            ckbMuaSam.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.cus_btn_sex));
            ckbMuaSam.setTextColor(Color.BLACK);
            s+="";
        }
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
    }
}