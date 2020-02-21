package com.pizzapp.v2;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.auth.FirebaseAuth;

public class CurMenuActivity extends AppCompatActivity {
    //---------
    private Button btn_curProf, btn_logout;
    private FirebaseAuth fauth;

    //---------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cur_menu);
        //---------
        inito();
        //---------
        btn_logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                FirebaseAuth.getInstance().signOut();
                startActivity(new Intent(CurMenuActivity.this, fakeLoad.class));
                finish();
            }
        });
        //---------
        btn_curProf.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(CurMenuActivity.this, CurProfActivity.class));
            }
        });
    }

    //---------
    private void inito() {
        btn_curProf = findViewById(R.id.btn_cur_prof);
        btn_logout = findViewById(R.id.btn_logoutCur);
    }
    //---------
}