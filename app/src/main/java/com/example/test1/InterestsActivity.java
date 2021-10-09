package com.example.test1;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.Volley;
import com.example.test1.adapters.InterestAdapter;
import com.example.test1.interfaces.InterestListener;
import com.example.test1.volleys.FunctionGetListVolley;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

public class InterestsActivity extends AppCompatActivity implements InterestListener {
    Button btnContinue;
    ImageButton imgBack;
    String interest;
    TextView tvInterestCount;
    RecyclerView rycInterest;
    List<String> interestList = new ArrayList<>();
    InterestAdapter interestAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_interests);
        btnContinue = findViewById(R.id.btnContinue);
        imgBack = findViewById(R.id.imgBack);
        tvInterestCount = findViewById(R.id.tvInterestCount);
        rycInterest = findViewById(R.id.rycInterest);


        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        String birthday = intent.getStringExtra("birthday");
        String sex = intent.getStringExtra("sex");
        String specialzed = intent.getStringExtra("specialzed");
        String course = intent.getStringExtra("course");
        String addressStudy = intent.getStringExtra("addressStudy");
        String show = intent.getStringExtra("show");

        FunctionGetListVolley functionGetListVolley = new FunctionGetListVolley();
        functionGetListVolley.getListInterestAPI(this,rycInterest,interestList,this);

        btnContinue.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (interest!=null && interest.length() > 10){
                    Intent intent1 = new Intent(InterestsActivity.this,AddImageActivity.class);
                    intent1.putExtra("name",name);
                    intent1.putExtra("birthday",birthday);
                    intent1.putExtra("sex",sex);
                    intent1.putExtra("specialized",specialzed);
                    intent1.putExtra("course",course);
                    intent1.putExtra("addressStudy",addressStudy);
                    intent1.putExtra("show",show);
                    intent1.putExtra("interest",interest);
                    startActivity(intent1);
                    overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
                }
                else {
                    Toast.makeText(InterestsActivity.this,"không được để trống",Toast.LENGTH_SHORT).show();
                }
            }
        });

        imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("result",show);
                setResult(RESULT_OK,resultIntent);
                finish();
                overridePendingTransition(R.anim.slide_in_left,R.anim.slide_out_right);
            }
        });
    }

//    @Override
//    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
//        super.onActivityResult(requestCode, resultCode, data);
//        if (requestCode == 1){
//            if (resultCode == RESULT_OK){
//                String result = data.getStringExtra("result");
//                if (result == "Du lịch"){
//                    ckbDulich.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.rdo_sex_on));
//                }
//                if (result == "Chơi game"){
//                    ckbChoiGame.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.rdo_sex_on));
//                }
//                if (result == "Đọc sách"){
//                    ckbDocSach.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.rdo_sex_on));
//                }
//                if (result == "Mua sắm"){
//                    ckbMuaSam.setBackground(ContextCompat.getDrawable(getApplicationContext(),R.drawable.rdo_sex_on));
//                }
//            }
//        }
//    }

    @Override
    public void changeInterest(List<String> arr,int count) {
        String s = "Sở thích: ";
        for (int i = 0;i<arr.size();i++){
            s+=arr.get(i)+", ";
        }
        Toast.makeText(this, s, Toast.LENGTH_SHORT).show();
        interest = s;
        btnContinue.setText("Tiếp tục: "+count+"/1");
    }


}